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
import sit.oasip.Component.JwtTokenUtil;
import sit.oasip.config.JwtRequestFilter;
import sit.oasip.dtos.UserDTOs.AddUserDTO;
import sit.oasip.dtos.UserDTOs.EditUserDTO;
import sit.oasip.dtos.UserDTOs.GetUserDTO;
import sit.oasip.dtos.UserDTOs.MatchUserDTO;
import sit.oasip.entities.EventCategoryOwner;
import sit.oasip.entities.User;
import sit.oasip.repositories.EventCategoryOwnerRepository;
import sit.oasip.repositories.EventcategoryRepository;
import sit.oasip.repositories.UserRepository;
import sit.oasip.utils.ListMapper;
import sit.oasip.utils.PageMapper;
import sit.oasip.utils.RoleAttribute;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private EventcategoryRepository eventcategoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;
    @Autowired
    private PageMapper pageMapper;
    @Autowired
    private Argon2PasswordEncoder argon2PasswordEncoder;
    @Autowired
    private EventCategoryOwnerRepository eventCategoryOwnerRepository;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    public Page<GetUserDTO> getUserAll(Pageable pageable) {
        List<GetUserDTO> userDTOS = listMapper
                .mapList(repository.findAll(Sort.by("UserName").ascending()), GetUserDTO.class, modelMapper);
        return pageMapper.mapToPage(pageable, userDTOS);
    }


    public List<GetUserDTO> getUserByRole(String role) {
        return listMapper
                .mapList(repository.findByRole(role), GetUserDTO.class, modelMapper);
    }

    public GetUserDTO getUserByEmail() {
        String email = jwtTokenUtil.getAllClaimsFromToken(jwtRequestFilter.getJwtToken()).getSubject();
        User user = repository.findByEmail(email);
        GetUserDTO users = modelMapper.map(user, GetUserDTO.class);

        List<EventCategoryOwner> eco = eventCategoryOwnerRepository.findCategoryName(user.getUserId());

        if (eco == null) {
            users.setOwners(null);
        } else {

            Map cateName = new LinkedHashMap();
            eco.forEach((e) -> {
                cateName.put(e.getId(), e.getEventCategoryID().getEventCategoryName());
                users.setOwners(cateName);
            });
        }

        return users;
    }

    public GetUserDTO getUserById(int userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, userId + " Does Not Exist !!!"));
        GetUserDTO users = modelMapper.map(user, GetUserDTO.class);

        List<EventCategoryOwner> eco = eventCategoryOwnerRepository.findCategoryName(userId);

        if (eco == null) {
            users.setOwners(null);
        } else {

            Map cateName = new LinkedHashMap();
            eco.forEach((e) -> {
                cateName.put(e.getId(), e.getEventCategoryID().getEventCategoryName());
                users.setOwners(cateName);
            });
        }

        return users;
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

    public void match(MatchUserDTO matchUser) {
        User user = repository.findByEmail(matchUser.getEmail());

        if (user != null) {
            boolean isMatchPassword = argon2PasswordEncoder.matches(matchUser.getPassword(), user.getPassword());

            if (isMatchPassword) {
                throw new ResponseStatusException(HttpStatus.OK, "Password Match");
            } else if (!isMatchPassword) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password NOT Match");
            }

        } else if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A user with the specified email DOES NOT exist");
        }
    }

    public User edit(EditUserDTO editUserDTO, int userId) {


        User user = repository.findById(userId).map(e -> {


            RoleAttribute roleAttribute = new RoleAttribute();


            if (editUserDTO.getName() != null && editUserDTO.getEmail() != null && editUserDTO.getRole() != null) {
                e.setUserName(editUserDTO.getName().trim());
                e.setEmail(editUserDTO.getEmail().trim());
                e.setRole(roleAttribute.roleChoice(editUserDTO.getRole().toString()));
            } else if (editUserDTO.getName() != null && editUserDTO.getEmail() != null) {
                e.setUserName(editUserDTO.getName().trim());
                e.setEmail(editUserDTO.getEmail().trim());
                e.setRole(e.getRole().toString());
            } else if (editUserDTO.getName() != null && editUserDTO.getRole() != null) {
                e.setUserName(editUserDTO.getName().trim());
                e.setRole(roleAttribute.roleChoice(editUserDTO.getRole().toString()));
                e.setEmail(e.getEmail());
            } else if (editUserDTO.getEmail() != null && editUserDTO.getRole() != null) {
                e.setEmail(editUserDTO.getEmail().trim());
                e.setRole(roleAttribute.roleChoice(editUserDTO.getRole().toString()));
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
                e.setRole(roleAttribute.roleChoice(editUserDTO.getRole().toString()));

            }
            return repository.saveAndFlush(e);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID " + userId + " does not exit !!!"));
        return modelMapper.map(user, User.class);

    }

    public void delete(int userId) {
        User user = repository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID " + userId + " does not exit !!!"));

        List<EventCategoryOwner> eco = eventCategoryOwnerRepository.findCategoryName(userId);

        ArrayList owners = new ArrayList();
        if (eco == null) {
            owners.add(null);
        } else {
            for (int i = 0; i < eco.size(); i++) {
                owners.add(eco.get(i).getEventCategoryID());
            }
        }

        for (int i = 0; i < owners.size(); i++) {
            int numOwnerEachCate = eventCategoryOwnerRepository.countAllByEventCategoryID(eventcategoryRepository.findById((Integer) owners.get(i)));
            if (numOwnerEachCate == 1) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The system must not allow the user to delete this account");
            }
        }
        repository.deleteById(userId);
        throw new ResponseStatusException(HttpStatus.OK, "Email : " + user.getEmail() + " have been deleted");
    }


}
