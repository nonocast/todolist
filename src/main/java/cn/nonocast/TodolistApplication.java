package cn.nonocast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.*;
import cn.nonocast.model.User;
import cn.nonocast.repository.UserRepository;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @Value("${spring.profiles.active}")
    private String profile;

    @RequestMapping("/")
    public String welcome(Model model) {
        model.addAttribute("version", this.version);
        model.addAttribute("profile", this.profile);
        return "index";
    }

    public static void main(String[] args) {
        SavedRequestAwareAuthenticationSuccessHandler p;
        SpringApplication.run(TodolistApplication.class, args);
    }
}
