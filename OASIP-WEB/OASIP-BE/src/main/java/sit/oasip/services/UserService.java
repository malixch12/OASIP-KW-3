package sit.oasip.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;

import sit.oasip.dtos.UserDTO.AddUserDTO;
import sit.oasip.dtos.UserDTO.EditUserDTO;
import sit.oasip.dtos.UserDTO.UserDTO;
import sit.oasip.entities.User;
import sit.oasip.repositories.UserRepository;
import sit.oasip.utils.ListMapper;
import sit.oasip.utils.PageMapper;
import sit.oasip.utils.Role;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper modelMapper;
    private ListMapper listMapper = new ListMapper();
    private PageMapper pageMapper = new PageMapper();

    public Page<UserDTO> getUserAll(Pageable pageable){
        List<UserDTO> userDTOS = listMapper
                .mapList(repository.findAll(Sort.by("Name").ascending()), UserDTO.class, modelMapper);
        return pageMapper.mapToPage(pageable, userDTOS);

    }

    public UserDTO getUserById(int userId){
        User user= repository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, userId + " Does Not Exist !!!"));
        return modelMapper.map(user, UserDTO.class);
    }

    public User add(AddUserDTO newUser){
        User user = new User();

        switch (newUser.getRole().toString()){
            case "Student" :
                user.setRole(Role.Student.name());
                System.out.println("Hi I'm Student");
                break;
            case "Admin" :
                user.setRole(Role.Lecturer.name());
                System.out.println("Hi I'm Admin");
                break;
            case "Lecturer" :
                user.setRole(Role.Admin.name());
                break;

        }

        user.setName(newUser.getName().trim());
        user.setEmail(newUser.getEmail().trim());

        User user1 = modelMapper.map(user, User.class);
        repository.saveAndFlush(user1);
        return user1;
    }

    public User edit(EditUserDTO editUserDTO, int userId){
        User user = repository.findById(userId).map(e -> {
            if(editUserDTO.getName() != null && editUserDTO.getEmail() != null && editUserDTO.getRole() != null){
                e.setName(editUserDTO.getName().trim());
                e.setEmail(editUserDTO.getEmail().trim());
                e.setRole(editUserDTO.getRole());
            }else if(editUserDTO.getName()!=null && editUserDTO.getEmail()!=null){
                e.setName(editUserDTO.getName().trim());
                e.setEmail(editUserDTO.getEmail().trim());
                e.setRole(e.getRole());
            }else if(editUserDTO.getName()!=null && editUserDTO.getRole()!=null){
                e.setName(editUserDTO.getName().trim());
                e.setRole(editUserDTO.getRole());
                e.setEmail(e.getEmail());
            }else if(editUserDTO.getEmail()!=null&&editUserDTO.getRole()!=null){
                e.setEmail(editUserDTO.getEmail().trim());
                e.setRole(editUserDTO.getRole());
                e.setName(e.getName());
            }else if(editUserDTO.getName()!=null){
                e.setName(editUserDTO.getName().trim());
                e.setEmail(e.getEmail());
                e.setRole(e.getRole());
            }else if(editUserDTO.getEmail()!=null){
                e.setEmail(editUserDTO.getEmail().trim());
                e.setName(e.getName());
                e.setRole(e.getRole());
            }else if(editUserDTO.getRole()!=null){
                e.setEmail(e.getEmail());
                e.setName(e.getName());
                e.setRole(editUserDTO.getRole());
            }
            return repository.saveAndFlush(e);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "test"));
        return modelMapper.map(user,User.class);
    }
    public void delete(int userId){
        repository.findById(userId).orElseThrow(()-> new RuntimeException(userId + "Does not exit !!!"));
        repository.deleteById(userId);
    }


}
