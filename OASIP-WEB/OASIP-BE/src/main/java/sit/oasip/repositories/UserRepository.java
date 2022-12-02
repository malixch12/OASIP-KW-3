package sit.oasip.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.oasip.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    User findByUserName(String userName);
//    User findById(int userId);
}
