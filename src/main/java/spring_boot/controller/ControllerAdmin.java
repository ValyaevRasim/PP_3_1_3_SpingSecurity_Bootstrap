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
import java.util.List;
import java.util.Set;

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
        List<User> allUsers = userDetailServiceImpl.getAllUsers();
        User user = (User) userDetailServiceImpl.loadUserByUsername(principal.getName());
        Set<Role> currentUserRoleList = user.getRoles();
        model.addAttribute("newUser", new User());
        model.addAttribute("currentUserRoleList", currentUserRoleList);
        model.addAttribute("userList", allUsers);
        model.addAttribute("allRoles", roleServiceImpl.getAllRoles());
        System.out.println("showAllUsers/allUsers " + currentUserRoleList.toString());
        return "allUsers";
    }

    // добавление нового пользователяю, используем 2 метода
//    @Secured("ROLE_ADMIN")
    //в config добавить @EnableGlobalMethodSecurity(securedEnabled = true) //защищаем отдельные методы
    @RequestMapping("/addUser")
    public String addNewUser(Model model) {
        System.out.println("addUser/new");
        List<Role> roles = roleServiceImpl.getAllRoles();
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roles);
        return "newUser";
    }

    //    @Secured("ROLE_ADMIN")
    @PostMapping("/saveUser")
    public String createNewUser(@ModelAttribute("user") User user) {
        System.out.println("createNewUser");
        userDetailServiceImpl.saveUser(user);
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