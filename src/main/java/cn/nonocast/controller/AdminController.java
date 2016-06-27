package cn.nonocast.controller;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @RequestMapping("/login")
    public String login() {
        return "admin/login";
    }

    @RequestMapping("/console")
    public String console() {
        return "admin/console";
    }
}
