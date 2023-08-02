package powerboard.powerboard.board;

import powerboard.powerboard.cardlist.CardListDTO;
import powerboard.powerboard.user.UserDTO;


import java.util.Set;

public record BoardDTO(
        Long id,
        String title,
        UserDTO owner,
        Set<UserDTO> users,
        Set<CardListDTO> cardLists,
        String imagePath
) {

}
