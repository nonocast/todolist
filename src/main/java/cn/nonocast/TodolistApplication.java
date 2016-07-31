package cn.nonocast;

import cn.nonocast.repository.TaskRepository;
import cn.nonocast.repository.UserRepository;
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
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

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

	@Resource(name="redisTemplate")
	private ValueOperations<Object, Object> valOps;

	@Autowired
	private UserRepository userRepository;

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


//	    cn.nonocast.cache.User p = new cn.nonocast.cache.User(1L, "nonocast", "nonocast@gmail.com", "123456");

//	    User p = userRepository.findByEmail("nonocast@gmail.com");
//	    valOps.set("user:"+p.getId(), p);
//
//	    Task t = taskRepository.findOne(1L);
//	    valOps.set("task:"+t.getId(), t);
    }
}
