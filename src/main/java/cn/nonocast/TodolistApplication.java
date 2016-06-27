package cn.nonocast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.*;

@Controller
@SpringBootApplication
public class TodolistApplication {
    private static final Logger logger = LoggerFactory.getLogger(TodolistApplication.class);

    @Value("${project.version}")
    private String version;

    @RequestMapping("/")
    public String welcome(Model model) {
        logger.info("welcome");

        model.addAttribute("version", this.version);
        return "index";
    }

    /*
    @RequestMapping("/error")
    public String show_error() {
        return "error";
    }
    */

    public static void main(String[] args) {
        SpringApplication.run(TodolistApplication.class, args);
    }
}
