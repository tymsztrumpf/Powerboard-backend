package powerboard.powerboard.card;

import powerboard.powerboard.user.UserDTO;

import java.util.Set;

public record CardDTO (
    Long Id,
    String title,
    String description,
    Set<UserDTO> executors
){
}
