package cn.nonocast.admin.controller;

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
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import cn.nonocast.repository.*;
import cn.nonocast.model.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;
import java.util.ArrayList;

@Controller("adminUserController")
@RequestMapping("/admin/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("")
    public String users(Model model,
                        @RequestParam(required = false) String role,
                        @RequestParam(required = false) String q,
                        Pageable pageable) {
        Page<User> page = new PageImpl<User>(new ArrayList<>(), pageable, 0);
        if (!Strings.isNullOrEmpty(q)) {
            page = userRepository.findByKeyword(q, pageable);
        } else if (!Strings.isNullOrEmpty(role)) {
            User.Role r = User.Role.valueOf(role.toUpperCase());
            if (r != null) {
                page = userRepository.findByRole(r, pageable);
            }
        } else {
            page = userRepository.findAll(pageable);
        }
        model.addAttribute("page", page);
        return "admin/user/index";
    }

    @RequestMapping("/create")
    public String newUser(@ModelAttribute("form") UserForm form) {
        return "admin/user/edit";
    }

    @RequestMapping("/{id:[0-9]+}/edit")
    public String edit(@PathVariable Long id, @Valid @ModelAttribute("form") UserForm form, Errors errors) {
        form.pull(userRepository.findOne(id));
        return "admin/user/edit";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("form") UserForm form, Errors errors, Model model, RedirectAttributes redirectAttributes) {
        if(form.getPassword().length() < 6) {
            errors.rejectValue("password", "Size", "密码不少于6个字符");
        }

        if(userRepository.existsByEmail(form.getEmail())) {
            errors.rejectValue("email", "Duplication", "邮箱地址已被使用");
        }

        if (errors.hasErrors()) {
            return "admin/user/edit";
        }

        User user = null;
        try {
            user = new User();
            userRepository.save(form.push(user, passwordEncoder));
            redirectAttributes.addFlashAttribute("user", user);
        } catch (DataAccessException ex) {
            return "admin/user/edit";
        }

        return "redirect:/admin/users?q=" + user.getName();
    }

    @RequestMapping(value="/{id:[0-9]+}")
    public String update(@PathVariable("id") Long id, @Valid @ModelAttribute("form") UserForm form, Errors errors, RedirectAttributes redirectAttributes) {
        form.pull(id);

        if(!Strings.isNullOrEmpty(form.getPassword())) {
            if(form.getPassword().length() < 6) {
                errors.rejectValue("password", "Size", "密码不少于6个字符");
                return "admin/user/edit";
            }
        }

        if (errors.hasErrors()) {
            return "admin/user/edit";
        }

        User user = null;
        try {
            user = userRepository.getOne(id);

            if(!user.getEmail().equals(form.getEmail())) {
                if (userRepository.existsByEmail(form.getEmail())) {
                    errors.rejectValue("email", "Duplication", "邮箱地址已被使用");
                    return "admin/user/edit";
                }
            }

            form.push(user, passwordEncoder);
            userRepository.save(user);
        } catch (DataAccessException ex) {
            return "admin/user/edit";
        }

        return "redirect:/admin/users?q=" + user.getName();
    }
}
