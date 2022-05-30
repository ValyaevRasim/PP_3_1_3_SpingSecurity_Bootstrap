package spring_boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring_boot.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getRoleByName(String roleName);
    Role getRoleById(Long id);
}
