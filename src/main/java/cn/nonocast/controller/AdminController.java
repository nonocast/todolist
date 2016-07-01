package cn.nonocast.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import cn.nonocast.repository.UserRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/login")
    public String login() {
        return "admin/login";
    }

    @RequestMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin/users";
    }

    @RequestMapping("/create_user")
    public String createUser() {
        return "admin/create_user";
    }
}
