package spring_boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring_boot.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

//    User findByUsername(String username);
    User getUserByUsername(String username);
    User findUserById(Long id);
}
