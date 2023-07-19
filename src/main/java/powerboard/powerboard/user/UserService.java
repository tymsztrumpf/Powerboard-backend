package powerboard.powerboard.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import powerboard.powerboard.exception.ApiRequestException;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;
    public UserDTO getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return userDTOMapper.apply(userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new ApiRequestException("User not found")));
    }
    public Set<UserDTO> getUsers(){
        Set<User> users = userRepository.getAll();
        return users.stream().map(userDTOMapper).collect(Collectors.toSet());
    }

}
