package sit.oasip.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import sit.oasip.dtos.UserDTO.AddUserDTO;
import sit.oasip.entities.Event;
import sit.oasip.entities.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User saveAndFlush (AddUserDTO newUser);

}
