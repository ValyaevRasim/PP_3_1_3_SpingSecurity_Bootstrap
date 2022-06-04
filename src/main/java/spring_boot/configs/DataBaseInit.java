package spring_boot.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring_boot.entity.Role;
import spring_boot.entity.User;
import spring_boot.repository.UserRepository;
import spring_boot.service.RoleService;
import spring_boot.service.UserDetailServiceImpl;
import spring_boot.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataBaseInit {
    private final UserDetailServiceImpl userServiceImpl;
    private final RoleService roleService;

    @Autowired
    private UserRepository userRepository;

    public DataBaseInit(UserDetailServiceImpl userServiceImpl, RoleService roleService) {
        this.userServiceImpl = userServiceImpl;
        this.roleService = roleService;
    }

    @PostConstruct
    private void startDB() {
        Role roleAdmin = new Role("ADMIN");
        Role roleAdmin1 = new Role("TEST");
        Role roleUser = new Role("USER");
        roleService.saveRole(roleAdmin);
        roleService.saveRole(roleAdmin1);
        roleService.saveRole(roleUser);
        Set<Role> roles = Set.copyOf(roleService.getAllRoles());

        User admin = new User("admin", "FirstNameAdmin", "lastNameAdmin", "admin", 33, true);
        userServiceImpl.saveUser(admin);
        admin.setRoles(roles);
        userServiceImpl.saveUser(admin);
    }
}