package sit.oasip.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sit.oasip.dtos.UserDTO.UserDTO;
import sit.oasip.entities.User;
import sit.oasip.repositories.UserRepository;
import sit.oasip.utils.ListMapper;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper modelMapper;
    private ListMapper listMapper = new ListMapper();

    public List<UserDTO> getUserAll(){
        return listMapper.mapList(repository.findAll(), UserDTO.class, modelMapper);
    }

}
