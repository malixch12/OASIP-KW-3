package sit.oasip.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;

import sit.oasip.dtos.UserDTOs.AddUserDTO;
import sit.oasip.dtos.UserDTOs.EditUserDTO;
import sit.oasip.dtos.UserDTOs.MatchUserDTO;
import sit.oasip.dtos.UserDTOs.GetUserDTO;
import sit.oasip.entities.User;
import sit.oasip.repositories.UserRepository;
import sit.oasip.utils.ListMapper;
import sit.oasip.utils.PageMapper;

import sit.oasip.utils.RoleAttribute;

import java.util.List;

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


    public Page<GetUserDTO> getUserAll(Pageable pageable) {
        List<GetUserDTO> userDTOS = listMapper
                .mapList(repository.findAll(Sort.by("UserName").ascending()), GetUserDTO.class, modelMapper);
        return pageMapper.mapToPage(pageable, userDTOS);

    }

    public GetUserDTO getUserById(int userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, userId + " Does Not Exist !!!"));
        return modelMapper.map(user, GetUserDTO.class);
    }


    public User add(AddUserDTO newUser) {
        User user = new User();
        RoleAttribute roleAttribute = new RoleAttribute();

        String password = argon2PasswordEncoder.encode(newUser.getPassword());
        String role = roleAttribute.roleChoice(newUser.getRole().toString());

        user.setRole(role);
        user.setUserName(newUser.getName());
        user.setEmail(newUser.getEmail());
        user.setPassword(password);

        User user1 = modelMapper.map(user, User.class);
        repository.saveAndFlush(user1);
        return user1;
    }

    public void match(MatchUserDTO matchUser){
        User user = repository.findByEmail(matchUser.getEmail());

        if(user != null){
            boolean isMatchPassword = argon2PasswordEncoder.matches(matchUser.getPassword(),user.getPassword());

            if(isMatchPassword){
                throw new ResponseStatusException(HttpStatus.OK,"Password Match");
            }
            else if(!isMatchPassword){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Password NOT Match");
            }

        }else if(user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"A user with the specified email DOES NOT exist");
        }
    }

    public User edit(EditUserDTO editUserDTO, int userId) {


        User user = repository.findById(userId).map(e -> {


                RoleAttribute roleAttribute = new RoleAttribute();


                if (editUserDTO.getName() != null && editUserDTO.getEmail() != null && editUserDTO.getRole() != null) {
                    e.setUserName(editUserDTO.getName().trim());
                    e.setEmail(editUserDTO.getEmail().trim());
                    e.setRole( roleAttribute.roleChoice(editUserDTO.getRole().toString()));
                } else if (editUserDTO.getName() != null && editUserDTO.getEmail() != null) {
                    e.setUserName(editUserDTO.getName().trim());
                    e.setEmail(editUserDTO.getEmail().trim());
                    e.setRole(e.getRole().toString());
                } else if (editUserDTO.getName() != null && editUserDTO.getRole() != null) {
                    e.setUserName(editUserDTO.getName().trim());
                    e.setRole( roleAttribute.roleChoice(editUserDTO.getRole().toString()));
                    e.setEmail(e.getEmail());
                } else if (editUserDTO.getEmail() != null && editUserDTO.getRole() != null) {
                    e.setEmail(editUserDTO.getEmail().trim());
                    e.setRole( roleAttribute.roleChoice(editUserDTO.getRole().toString()));
                    e.setUserName(e.getUserName());
                } else if (editUserDTO.getName() != null) {
                    e.setUserName(editUserDTO.getName().trim());
                    e.setEmail(e.getEmail());
                    e.setRole(e.getRole().toString());
                } else if (editUserDTO.getEmail() != null) {
                    e.setEmail(editUserDTO.getEmail().trim());
                    e.setUserName(e.getUserName());
                    e.setRole(e.getRole().toString());
                } else if (editUserDTO.getRole() != null) {
                    e.setEmail(e.getEmail());
                    e.setUserName(e.getUserName());
                    e.setRole( roleAttribute.roleChoice(editUserDTO.getRole().toString()));

            }
            return repository.saveAndFlush(e);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"ID "+ userId + " does not exit !!!"));
        return modelMapper.map(user, User.class);

    }

    public void delete(int userId) {
        User user = repository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"ID "+ userId + " does not exit !!!"));
        repository.deleteById(userId);
        throw new ResponseStatusException(HttpStatus.OK,"Email : "+ user.getEmail() + " have been deleted");
    }


}
