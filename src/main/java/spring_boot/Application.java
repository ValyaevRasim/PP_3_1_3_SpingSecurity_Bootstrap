package spring_boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import spring_boot.configs.DataBaseInit;
import spring_boot.entity.Role;
import spring_boot.entity.User;
import spring_boot.repository.RoleRepository;
import spring_boot.repository.UserRepository;
import spring_boot.service.RoleService;
import spring_boot.service.UserService;

import java.util.Set;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

//        ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
//        RoleService roleService = applicationContext.getBean(RoleService.class);
//        UserService userService = applicationContext.getBean(UserService.class);
//        Role roleAdmin = new Role("ADMIN");
//        Role roleAdmin1 = new Role("TEST");
//        Role roleUser = new Role("USER");
//        roleService.saveRole(roleAdmin);
//        roleService.saveRole(roleAdmin1);
//        roleService.saveRole(roleUser);
//        Set<Role> roles = Set.copyOf(roleService.getAllRoles());
//        User admin = new User("admin", "FirstNameAdmin", "lastNameAdmin", "admin", 33, true);
//        userService.saveUser(admin);
//        admin.setRoles(roles);
//        userService.saveUser(admin);
    }
}
