package sit.oasip.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import sit.oasip.dtos.UserDTO.AddUserDTO;
import sit.oasip.dtos.UserDTO.UserDTO;
import sit.oasip.entities.User;
import sit.oasip.repositories.UserRepository;
import sit.oasip.utils.ListMapper;
import sit.oasip.utils.PageMapper;

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
                .mapList(repository.findAll(Sort.by("userName").ascending()), UserDTO.class, modelMapper);
        return pageMapper.mapToPage(pageable, userDTOS);

    }

    public User add(AddUserDTO newUser){
        newUser.setUserName(newUser.getUserName().trim());
        newUser.setEmail(newUser.getEmail().trim());
        User user1 = modelMapper.map(newUser, User.class);
        repository.saveAndFlush(user1);
        return user1;
    }

    public void delete(int userId){
        repository.findById(userId).orElseThrow(()-> new RuntimeException(userId + "Does not exit !!!"));
        repository.deleteById(userId);
    }

    public User add(AddUserDTO newUser){
       newUser.setUserName(newUser.getUserName().trim());
        newUser.setEmail(newUser.getEmail().trim());
        User user1 = modelMapper.map(newUser, User.class);
        repository.saveAndFlush(user1);
        return user1;
    }

    public void delete(int userId){
        repository.findById(userId).orElseThrow(()-> new RuntimeException(userId + "Does not exit !!!"));
        repository.deleteById(userId);
    }

}
