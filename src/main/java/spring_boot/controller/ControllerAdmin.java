package spring_boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring_boot.entity.Role;
import spring_boot.entity.User;
import spring_boot.service.RoleServiceImpl;
import spring_boot.service.UserDetailServiceImpl;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class ControllerAdmin {

    private final UserDetailServiceImpl userDetailServiceImpl;
    private final RoleServiceImpl roleServiceImpl;

    @Autowired
    public ControllerAdmin(UserDetailServiceImpl userDetailServiceImpl, RoleServiceImpl roleServiceImpl) {
        this.userDetailServiceImpl = userDetailServiceImpl;
        this.roleServiceImpl = roleServiceImpl;
    }

    // начальная страница
    @RequestMapping("/")
    public String showAllUsers(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = (User) userDetailServiceImpl.loadUserByUsername(userDetails.getUsername());
        model.addAttribute("newUser", new User());
        model.addAttribute("roleList", roleServiceImpl.getAllRoles());
        model.addAttribute("currentUserRoleList", user.getRoles());
        model.addAttribute("userList", userDetailServiceImpl.getAllUsers());
        System.out.println("showAllUsers/allUsers " + user.getRoles().toString());
        return "admin";
    }

    // добавление нового пользователяю, используем 2 метода
    @PostMapping("/saveUser")
    public String addUser(@ModelAttribute User newUser, @RequestParam(value = "checkboxName", required = false) Long[] checkboxName) {
        Set<Role> rolesSet = new HashSet<>();
        if (checkboxName != null) {
            for (long i : checkboxName) {
                rolesSet.add(roleServiceImpl.getRoleById(i));
            }
        }
        newUser.setRoles(rolesSet);
        userDetailServiceImpl.saveUser(newUser);
        return "redirect:/admin/";
    }


    //    обновление данных пользователя, используем 2 метода
    @PatchMapping("updateUser/{id}")
    public String updateUser(@ModelAttribute User editUser,
                             @RequestParam(value = "checkboxName", required = false) Long[] checkboxName,
                             @RequestParam(value = "enabled", required = false) String enabledCheckbox) {
        System.out.println(enabledCheckbox);
        Set<Role> rolesSet = new HashSet<>();
        if ((enabledCheckbox != null) && (enabledCheckbox.equals("1"))) {
            editUser.setEnabled(true);
        } else {
            editUser.setEnabled(false);
        }

        if (checkboxName != null) {
            for (long i : checkboxName) {
                rolesSet.add(roleServiceImpl.getRoleById(i));
            }
        }
        editUser.setRoles(rolesSet);
        userDetailServiceImpl.saveUser(editUser);
        return "redirect:/admin/";
    }

    //    удаление пользователя, используем 2 метода
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        System.out.println("deleteUser/deleteUser");
        userDetailServiceImpl.deleteUserById(id);
        return "redirect:/admin/";
    }

}