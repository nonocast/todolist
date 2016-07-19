package cn.nonocast.admin.controller;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import cn.nonocast.repository.*;
import cn.nonocast.form.*;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import cn.nonocast.model.*;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller("adminTaskController")
@RequestMapping("/admin/tasks")
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    private Pattern searchIdPattern  = Pattern.compile("^id\\s*:\\s*(\\d+)$", Pattern.CASE_INSENSITIVE);
    private Pattern searchBelongsToPattern  = Pattern.compile("^belongs\\s*:\\s*(.+)$", Pattern.CASE_INSENSITIVE);

    @RequestMapping("")
    public String tasks(Model model,
                        @RequestParam(required = false) String q,
                        Pageable pageable) {
        Page<Task> page = null;
        if (!Strings.isNullOrEmpty(q)) {
            Matcher m1 = searchIdPattern.matcher(q);
            if(m1.matches()) {
                String ret = m1.group(1);
                page = taskRepository.findByKeyword(Long.valueOf(ret), pageable);
            }

            Matcher m2 = searchBelongsToPattern.matcher(q);
            if(m2.matches()) {
                String ret = m2.group(1);
                User user = userRepository.findByEmailOrName(ret);
                if(user != null) {
                    page = taskRepository.findByBelongsTo(user, pageable);
                }
            }

            if(page == null) {
                page = taskRepository.findByKeyword(q, pageable);
            }
        } else {
            page = taskRepository.findAll(pageable);
        }
        model.addAttribute("page", page);
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

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("form") TaskForm form, Errors errors) {
        if (errors.hasErrors()) {
            return "admin/task/edit";
        }

        Task task = null;
        try {
            task = new Task();
            User user = userRepository.findOne(1L);
            task.setBelongsTo(user);
            task.setBelongsToName(user.getName());
            taskRepository.save(form.push(task));
        } catch (DataAccessException ex) {
            errors.rejectValue("error", "DataAccessException", ex.getMessage());
            return "admin/task/edit";
        }

        return "redirect:/admin/tasks?q=id:" + task.getId();
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
            task.setBelongsToName(task.getBelongsTo().getName());
            taskRepository.save(task);
        } catch (DataAccessException ex) {
            return "admin/task/edit";
        }

        return "redirect:/admin/tasks?q=id:" + task.getId();
    }

    @RequestMapping(value="/delete", method=RequestMethod.POST)
    public Object delete(HttpServletRequest request) {
        List<Long> ids = Stream.of(request.getParameterValues("selected")).map(Long::valueOf).collect(Collectors.toList());
        List<Task> tasks = taskRepository.findByIds(ids);
        taskRepository.delete(tasks);
        return "redirect:/admin/tasks";
    }
}
