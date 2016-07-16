package cn.nonocast.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import cn.nonocast.repository.*;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.*;

@Controller("adminTaskController")
@RequestMapping("/admin/tasks")
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskRepository taskRepository;

    @RequestMapping("")
    public String tasks(Model model, Pageable pageable) {
        model.addAttribute("page", taskRepository.findAll(pageable));
        return "admin/task/index";
    }
}
