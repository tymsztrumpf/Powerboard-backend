package powerboard.powerboard.board;

import powerboard.powerboard.cardlist.CardList;
import powerboard.powerboard.user.UserDTO;


import java.util.Set;

public record BoardDTO(
        Long id,
        String title,
        UserDTO owner,
        Set<CardList> cardLists

) {

}
