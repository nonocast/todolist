package cn.nonocast.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.security.Principal;
import cn.nonocast.repository.UserRepository;

@Controller
@RequestMapping("/")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/login")
    public String login() {
        return "user/login";
    }

    @RequestMapping("/home")
    public String home(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        model.addAttribute("users", userRepository.findAll());
        return "user/home";
    }

    @RequestMapping("/logout")
    public String logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return "redirect:/login?logout";
    }
}