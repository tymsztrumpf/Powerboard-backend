package powerboard.powerboard.service;

import org.springframework.stereotype.Service;
import powerboard.powerboard.repository.UserRepository;
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


}
