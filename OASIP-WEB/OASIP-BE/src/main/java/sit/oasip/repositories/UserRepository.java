package sit.oasip.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.oasip.entities.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    User findByUserName(String userName);
    List<User> findByRole (String role);
}
