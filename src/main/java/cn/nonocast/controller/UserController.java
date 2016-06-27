package cn.nonocast.controller;

import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class UserController {
    @RequestMapping("/login")
    public String login() {
        return "user/login";
    }

    @RequestMapping("/home")
    public String home(Model model, Principal principal) {
        model.addAttribute("usernmae", principal.getName());
        return "user/home";
    }
}
