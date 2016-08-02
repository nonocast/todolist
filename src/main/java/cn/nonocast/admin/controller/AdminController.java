package cn.nonocast.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("adminController")
@RequestMapping("/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

//	@Autowired
//	private TokenService tokenService;

    @RequestMapping("login")
    public String login() {
        return "admin/login";
    }

    @RequestMapping(value = {"", "console"})
    public String console() {
        return "admin/console";
    }

	@RequestMapping("tokens")
	public String token(Model model) {
//		model.addAttribute("tokens", tokenService.findAll());
		return "admin/token/index";
	}
}