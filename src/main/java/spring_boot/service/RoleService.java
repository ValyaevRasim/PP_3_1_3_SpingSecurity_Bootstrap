package spring_boot.service;

import spring_boot.entity.Role;

import java.util.List;

public interface RoleService {
    void saveRole(Role role);
    List<Role> getAllRoles();
}
