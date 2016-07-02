package cn.nonocast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.*;
import cn.nonocast.model.User;
import cn.nonocast.repository.UserRepository;
import org.springframework.web.servlet.DispatcherServlet;

@Controller
@SpringBootApplication
public class TodolistApplication {
    private static final Logger logger = LoggerFactory.getLogger(TodolistApplication.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Value("${project.version}")
    private String version;

    @RequestMapping("/")
    public String welcome(Model model) {
        DispatcherServlet p;
        model.addAttribute("version", this.version);
        return "index";
    }

    public static void main(String[] args) {
        SpringApplication.run(TodolistApplication.class, args);
    }
}
