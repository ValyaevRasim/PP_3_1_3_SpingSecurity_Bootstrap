package spring_boot.service;

import org.springframework.stereotype.Service;
import spring_boot.entity.Role;
import spring_boot.repository.RoleRepository;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleByName(String roleName) {
        return roleRepository.getRoleByName(roleName);
    }

    public Role getRoleById(Long idRole) {
        return roleRepository.getRoleById(idRole);
    }

}
