package sit.oasip.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;

import sit.oasip.dtos.UserDTO.AddUserDTO;
import sit.oasip.dtos.UserDTO.EditUserDTO;
import sit.oasip.dtos.UserDTO.UserDTO;
import sit.oasip.entities.User;
import sit.oasip.repositories.UserRepository;
import sit.oasip.utils.ListMapper;
import sit.oasip.utils.PageMapper;

import sit.oasip.utils.RoleAttribute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper ;
    @Autowired
    private PageMapper pageMapper;
    @Autowired
    private Argon2PasswordEncoder argon2PasswordEncoder;


    public Page<UserDTO> getUserAll(Pageable pageable) {
        List<UserDTO> userDTOS = listMapper
                .mapList(repository.findAll(Sort.by("Name").ascending()), UserDTO.class, modelMapper);
        return pageMapper.mapToPage(pageable, userDTOS);

    }

    public UserDTO getUserById(int userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, userId + " Does Not Exist !!!"));
        return modelMapper.map(user, UserDTO.class);
    }


    public User add(AddUserDTO newUser) {
        User user = new User();
        RoleAttribute roleAttribute = new RoleAttribute();

        String password = argon2PasswordEncoder.encode(newUser.getPassword());
        String role = roleAttribute.roleChoice(newUser.getRole().toString());

        user.setRole(role);
        user.setName(newUser.getName().trim());
        user.setEmail(newUser.getEmail().trim());
        user.setPassword(password);

        User user1 = modelMapper.map(user, User.class);
        repository.saveAndFlush(user1);
        return user1;
    }

    public User edit(EditUserDTO editUserDTO, int userId) {


        User user = repository.findById(userId).map(e -> {


                RoleAttribute roleAttribute = new RoleAttribute();


                if (editUserDTO.getName() != null && editUserDTO.getEmail() != null && editUserDTO.getRole() != null) {
                    e.setName(editUserDTO.getName().trim());
                    e.setEmail(editUserDTO.getEmail().trim());
                    e.setRole( roleAttribute.roleChoice(editUserDTO.getRole().toString()));
                } else if (editUserDTO.getName() != null && editUserDTO.getEmail() != null) {
                    e.setName(editUserDTO.getName().trim());
                    e.setEmail(editUserDTO.getEmail().trim());
                    e.setRole(e.getRole().toString());
                } else if (editUserDTO.getName() != null && editUserDTO.getRole() != null) {
                    e.setName(editUserDTO.getName().trim());
                    e.setRole( roleAttribute.roleChoice(editUserDTO.getRole().toString()));
                    e.setEmail(e.getEmail());
                } else if (editUserDTO.getEmail() != null && editUserDTO.getRole() != null) {
                    e.setEmail(editUserDTO.getEmail().trim());
                    e.setRole( roleAttribute.roleChoice(editUserDTO.getRole().toString()));
                    e.setName(e.getName());
                } else if (editUserDTO.getName() != null) {
                    e.setName(editUserDTO.getName().trim());
                    e.setEmail(e.getEmail());
                    e.setRole(e.getRole().toString());
                } else if (editUserDTO.getEmail() != null) {
                    e.setEmail(editUserDTO.getEmail().trim());
                    e.setName(e.getName());
                    e.setRole(e.getRole().toString());
                } else if (editUserDTO.getRole() != null) {
                    e.setEmail(e.getEmail());
                    e.setName(e.getName());
                    e.setRole( roleAttribute.roleChoice(editUserDTO.getRole().toString()));

            }
            return repository.saveAndFlush(e);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "test"));
        return modelMapper.map(user, User.class);

    }

    public void delete(int userId) {
        repository.findById(userId).orElseThrow(() -> new RuntimeException(userId + "Does not exit !!!"));
        repository.deleteById(userId);
    }


}
