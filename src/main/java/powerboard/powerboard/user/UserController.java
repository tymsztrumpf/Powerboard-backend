package powerboard.powerboard.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    @GetMapping
    public ResponseEntity<UserDTO> getUser() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }
    @GetMapping("/all")
    public ResponseEntity<Set<UserDTO>> getUsers(){
        return ResponseEntity.ok(userService.getUsers());
    }
}
