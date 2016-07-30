package cn.nonocast;

import cn.nonocast.social.WechatLoader;
import cn.nonocast.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@SpringBootApplication
public class TodolistApplication implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(TodolistApplication.class);

    @Autowired
    private ApplicationContext context;

    @Autowired
    private WechatLoader wechatLoader;

	@Autowired
	private BackupService backupService;

    @Value("${project.version}")
    private String version;

    @Value("${spring.profiles.active}")
    private String profile;

    @Value("${wechat.path}")
    private String wechatPath;

    @RequestMapping("/")
    public String welcome(Model model) {
        return "redirect:/home";
    }

    public static void main(String[] args) {
        SpringApplication.run(TodolistApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
	    backupService.sync();
        wechatLoader.load(wechatPath);
    }
}
