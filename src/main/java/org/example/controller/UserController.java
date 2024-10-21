package org.example.controller;

import org.example.model.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;
@Controller
@RequestMapping
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users")
    public String printUsers(ModelMap model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("usersList", users);
        return "users";
    }

    @GetMapping("/edit/")
    public ModelAndView editPage(@RequestParam String id) {
        User u = userService.getById(Integer.parseInt(id));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editPage");
        modelAndView.addObject("user", u);
        return modelAndView;
    }

    @PostMapping(value="/edit")
    public ModelAndView editUser(@ModelAttribute("user") @Valid User user, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        if (result.hasErrors()) {
            modelAndView.setViewName("editPage");
            modelAndView.addObject("user", user);
            return  modelAndView;
        }
        modelAndView.setViewName("redirect:/users");
        userService.edit(user);
        return modelAndView;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addPage() {
        User u = new User();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editPage");
        modelAndView.addObject("user", u);
        return modelAndView;
    }
    @PostMapping(value="/add")
    public ModelAndView addUser(@ModelAttribute("user") @Valid User user, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        if(result.hasErrors()) {
            modelAndView.setViewName("editPage");
            modelAndView.addObject("user", user);
            return modelAndView;
        }
        userService.add(user);
        modelAndView.setViewName("redirect:/users");
        return modelAndView;
    }

    @GetMapping("/delete/")
    public ModelAndView deleteUser(@RequestParam String id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/users");
        userService.delete(Integer.parseInt(id));
        return modelAndView;
    }
}
