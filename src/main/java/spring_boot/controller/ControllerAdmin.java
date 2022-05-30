package spring_boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    public String showAllUsers(Model model, Principal principal) {
        User user = (User) userDetailServiceImpl.loadUserByUsername(principal.getName());
        model.addAttribute("newUser", new User());
        model.addAttribute("currentUserRoleList", user.getRoles());
        model.addAttribute("userList", userDetailServiceImpl.getAllUsers());
        model.addAttribute("roleList", roleServiceImpl.getAllRoles());
        System.out.println("showAllUsers/allUsers " + user.getRoles().toString());
        return "allUsers";
    }

    // добавление нового пользователяю, используем 2 метода
    @PostMapping("/saveUser")
    public String addUser(@ModelAttribute User newUser, @RequestParam(value = "checkboxName", required = false) Long[] checkboxName){
        Set<Role> rolesSet = new HashSet<>();
        if(checkboxName != null) {
            for (int i = 0; i < checkboxName.length; i++) {
                rolesSet.add(roleServiceImpl.getRoleById(checkboxName[i]));
            }
        }
        newUser.setRoles(rolesSet);
        userDetailServiceImpl.saveUser(newUser);
        return "redirect:/admin/";
    }


    //    обновление данных пользователя, используем 2 метода
    @GetMapping("/updateUser/{id}")
    public String updateUser(@PathVariable("id") long id, Model model) {
        System.out.println("updateUser/updateUser");
        User user = userDetailServiceImpl.getUserById(id);
        List<Role> roles = roleServiceImpl.getAllRoles();
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roles);
        return "editUser";
    }

    @RequestMapping("/{id}")
    public String edit(@ModelAttribute("user") User user) {
        System.out.println("edit");
        userDetailServiceImpl.saveUser(user);
        return "redirect:/admin/";
    }

    //    удаление пользователя, используем 2 метода
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        System.out.println("deleteUser/deleteUser");
        userDetailServiceImpl.deleteUserById(id);
        return "redirect:/admin/";
    }

    // добавление новой роли, используем 2 метода
//    @RequestMapping("/addRole")
    @GetMapping("/addRole")
    public String newRolePage(Model model) {
        System.out.println("addNewRole/new");
        model.addAttribute("role", new Role());
        return "newRole";
    }

    //    @PostMapping()
//    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    @PostMapping("/saveRole")
    public String saveRole(@ModelAttribute("role") Role role) {
        System.out.println("createNewRole");
//        role.setName("ROLE_" + role.getName().toUpperCase());
        role.setName(role.getName().toUpperCase());
        roleServiceImpl.saveRole(role);
        return "redirect:/admin/";
    }
}