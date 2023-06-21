package powerboard.powerboard.board;

import powerboard.powerboard.cardlist.CardList;
import powerboard.powerboard.user.User;
import powerboard.powerboard.user.UserDTO;


import java.util.Set;

public record BoardDTO(
        Long id,
        String title,
        UserDTO owner,
        Set<UserDTO> users,
        Set<CardList> cardLists
) {

}
