package cn.nonocast.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import cn.nonocast.repository.*;
import cn.nonocast.form.*;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import cn.nonocast.model.*;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller("adminTaskController")
@RequestMapping("/admin/tasks")
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("")
    public String tasks(Model model, Pageable pageable) {
        model.addAttribute("page", taskRepository.findAll(pageable));
        return "admin/task/index";
    }

    @RequestMapping("/create")
    public String newTask(@ModelAttribute("form") TaskForm form) {
        return "admin/task/edit";
    }

    @RequestMapping("/{id:[0-9]+}/edit")
    public String edit(@PathVariable Long id, @Valid @ModelAttribute("form") TaskForm form, Errors errors) {
        form.pull(taskRepository.findOne(id));
        return "admin/task/edit";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("form") TaskForm form, Errors errors) {
        if (errors.hasErrors()) {
            return "admin/task/edit";
        }

        Task task = null;
        try {
            task = new Task();
            task.setBelongsTo(userRepository.findOne(1L));
            taskRepository.save(form.push(task));
        } catch (DataAccessException ex) {
            errors.rejectValue("error", "DataAccessException", ex.getMessage());
            return "admin/task/edit";
        }

        return "redirect:/admin/tasks";
    }


    @RequestMapping(value = "/{id:[0-9]+}", method = RequestMethod.POST)
    public String update(@PathVariable("id") Long id, @Valid @ModelAttribute("form") TaskForm form, Errors errors) {
        if (errors.hasErrors()) {
            return "admin/task/edit";
        }

        Task task = null;
        try {
            task = taskRepository.getOne(id);
            form.push(task);
            taskRepository.save(task);
        } catch (DataAccessException ex) {
            return "admin/task/edit";
        }

        return "redirect:/admin/tasks";
    }
}
