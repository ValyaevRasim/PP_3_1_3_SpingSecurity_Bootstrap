package spring_boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring_boot.entity.User;
import spring_boot.service.UserDetailServiceImpl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class ControllerUser {
    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;

    // начальная страница
    @GetMapping({"/", "/index"})
    public String indexPage() {
        return "index";
    }

    @GetMapping({"authorized"})
    public String authorizedPage() {
        return "authorized";
    }

    @RequestMapping("user/")
    public String user(Model model, Principal principal) {
        System.out.println("showAllUsers/allUsers");
        List<User> allUsers = new ArrayList<>();
        User user = userDetailServiceImpl.getUserByUsername(principal.getName());
        if (user != null) {
            allUsers.add(user);
        }
        model.addAttribute("userList", allUsers);
        return "user";
    }

}
