package cn.nonocast.controller;

import cn.nonocast.form.UserForm;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.*;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import cn.nonocast.repository.*;
import org.springframework.web.bind.annotation.RequestMethod;
import cn.nonocast.model.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;

import cn.nonocast.service.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/login")
    public String login() {
        return "admin/login";
    }

    @RequestMapping("/users")
    public String users(Model model,
                        @RequestParam(required=false) String role,
                        @RequestParam(required=false) String q,
                        Pageable pageable) {
        Page<User> page = new PageImpl<User>(new ArrayList<>(), pageable, 0);
        if(!Strings.isNullOrEmpty(q)) {
            page = userRepository.findByKeyword(q, pageable);
        }else if(!Strings.isNullOrEmpty(role)) {
            User.Role r = User.Role.valueOf(role.toUpperCase());
            if(r != null) {
                page = userRepository.findByRole(r, pageable);
            }
        } else {
            page = userRepository.findAll(pageable);
        }
        model.addAttribute("page", page);
        return "admin/users";
    }

    @RequestMapping("/create_user")
    public String userForm(@ModelAttribute("form") UserForm form) {
        return "admin/create_user";
    }

    @RequestMapping(value="/create_user", method=RequestMethod.POST)
    public String userSubmit(Principal principal, @Valid @ModelAttribute("form") UserForm form, Errors errors, Model model, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            return "admin/create_user";
        }

        try {
            User user = new User(form.getEmail(), form.getName(), passwordEncoder.encode(form.getPassword()));
            userRepository.save(user);
            redirectAttributes.addFlashAttribute("user", user);
        }catch(DataAccessException ex) {
            return "admin/create_user";
        }

        return "redirect:/admin/create_user/result";
    }

    @RequestMapping("/create_user/result")
    public String userCreateResult(Model model) {
        return "admin/create_user_result";
    }

    @RequestMapping("/tasks")
    public String tasks(Model model, Pageable pageable) {
        model.addAttribute("page", taskRepository.findAll(pageable));
        return "admin/tasks";
    }

    @RequestMapping("/dba")
    public String dba() {
        return "admin/dba";
    }

    @RequestMapping(value="/dba_rebuild", method=RequestMethod.POST)
    public String rebuildDatabase() {
        // 如果数据库有问题都看不到这个页面...
        return "redirect:/dba";
    }
}