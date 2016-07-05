package cn.nonocast.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.security.Principal;
import cn.nonocast.repository.UserRepository;
import org.springframework.web.bind.annotation.RequestParam;
import cn.nonocast.security.CustomUserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserService userDetailsManager;

    @Autowired
    private TokenBasedRememberMeServices rememberMeServices;

    @RequestMapping("/login")
    public String login() {
        return "user/login";
    }

    @RequestMapping("/wechat/callback")
    public String wechatCallback(@RequestParam("code") String code, Model model) {
        model.addAttribute("code", code);
        return "user/wechat";
    }

    @RequestMapping("/wechat/pp")
    public String mockLogin(HttpServletRequest request, HttpServletResponse response) {
        String name = "linda@yahoo.com";
        UserDetails user = userDetailsManager.loadUserByUsername(name);
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        rememberMeServices.loginSuccess(request, response ,auth);
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        model.addAttribute("users", userRepository.findAll());
        return "user/home";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            rememberMeServices.logout(request, response, auth);
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        SecurityContextHolder.getContext().setAuthentication(null);
        SecurityContextHolder.clearContext();

        return "redirect:/login?logout";
    }
}