package spring_boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring_boot.entity.User;
import spring_boot.service.RoleServiceImpl;
import spring_boot.service.UserDetailServiceImpl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class ControllerUser {
    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;
    @Autowired
    private RoleServiceImpl roleServiceImpl;

    // начальная страница
    @GetMapping({"/", "/index"})
    public String indexPage() {
        return "index";
    }

    @GetMapping({"authorized"})
    public String authorizedPage() {
        return "authorized";
    }

    @RequestMapping("/user")
    public String showAllUsers(Model model, Principal principal) {
        User user = (User) userDetailServiceImpl.loadUserByUsername(principal.getName());
        model.addAttribute("currentUserRoleList", user.getRoles());
        model.addAttribute("currentUser", user);
        System.out.println("showAllUsers/allUsers " + user.getRoles().toString());
        return "user";
    }


}
