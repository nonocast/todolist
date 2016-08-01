package cn.nonocast;

import cn.nonocast.repository.TaskRepository;
import cn.nonocast.service.BackupService;
import cn.nonocast.social.WechatLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.nonocast.cache.*;

@Controller
@EnableCaching
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

	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;


	@Autowired
	private HelloService helloService;

	@Autowired
	private TaskRepository taskRepository;

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

	    helloService.findHello(1L);
	    helloService.findHello(1L);
	    Hello p = helloService.findHello(1L);
	    logger.info(p.getName());

	    World q = helloService.findWorld(1L);
	    logger.info(q.getName());
    }
}
