package cn.nonocast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.*;
import cn.nonocast.repository.*;
import cn.nonocast.social.*;

@Controller
@SpringBootApplication
public class TodolistApplication implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(TodolistApplication.class);

    @Autowired
    private ApplicationContext context;

    @Autowired
    private WechatLoader wechatLoader;

    @Autowired
    private TaskRepository taskRepository;

    @Value("${project.version}")
    private String version;

    @Value("${spring.profiles.active}")
    private String profile;

    @Value("${wechat.path}")
    private String wechatPath;

    @RequestMapping("/")
    public String welcome(Model model) {
        model.addAttribute("version", this.version);
        model.addAttribute("profile", this.profile);
        return "index";
    }

    public static void main(String[] args) {
        SpringApplication.run(TodolistApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        wechatLoader.load(wechatPath);


    }
}
