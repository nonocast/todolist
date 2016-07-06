package cn.nonocast.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

import cn.nonocast.repository.UserRepository;
import cn.nonocast.model.*;
import cn.nonocast.social.*;
import cn.nonocast.security.CustomUserService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Wechat wechat;

    @RequestMapping("/login")
    public String login() {
        return "user/login";
    }

    @RequestMapping("/wechat/callback")
    public String wechatCallback(HttpServletRequest request,
                                 HttpServletResponse response,
                                 @RequestParam("code") String code,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        String result;

        // TODO: 判定exists unionid
        String unionid = wechat.getUnionid(code);
        if(unionid == null) throw new IllegalArgumentException();

        model.addAttribute("unionid", unionid);
        model.addAttribute("code", code);

        User target = userRepository.findByWechatid(unionid);
        if(target != null) {
            // existed
            Authentication auth = new UsernamePasswordAuthenticationToken(target, null, target.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            rememberMeServices.loginSuccess(request, response ,auth);
            result = "redirect:/home";
        } else {
            // new user
            Map<String,String> info = wechat.getInfo();
            if(info == null) throw new IllegalArgumentException();
            redirectAttributes.addFlashAttribute("unionid", info.get("unionid"));
            redirectAttributes.addFlashAttribute("nickname", info.get("nickname"));
            redirectAttributes.addFlashAttribute("avatar", info.get("headimgurl"));
            result = "redirect:/register";
        }

        return result;
    }

    @RequestMapping("/register")
    public String registerForm(@ModelAttribute("form") UserForm form, Model model) {
        if(model.containsAttribute("unionid")) {
            form.setName(model.asMap().get("nickname").toString());
            form.setAvatar(model.asMap().get("avatar").toString());
        }
        return "user/register";
    }

    @RequestMapping(value="/register", method=RequestMethod.POST)
    public String registerSubmit(Principal principal, @Valid @ModelAttribute("form") UserForm form, Errors errors, Model model, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            return "user/register";
        }

        User user = new User(form.getEmail(), form.getName(), passwordEncoder.encode(form.getPassword()));
        userRepository.save(user);
        redirectAttributes.addFlashAttribute("user", user);
        return "redirect:/register/result";
    }

    @RequestMapping("/register/result")
    public String registerResult(Model model) {
        return "user/register_result";
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