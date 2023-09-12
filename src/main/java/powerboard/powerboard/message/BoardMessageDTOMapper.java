package powerboard.powerboard.message;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import powerboard.powerboard.board.Board;
import powerboard.powerboard.board.BoardDTO;
import powerboard.powerboard.cardlist.CardList;
import powerboard.powerboard.cardlist.CardListDTOMapper;
import powerboard.powerboard.user.UserDTOMapper;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BoardMessageDTOMapper implements Function<BoardEventRequest, BoardDTO> {
    private final UserDTOMapper userDTOMapper;
    private final CardListDTOMapper cardListDTOMapper;
    @Override
    public BoardDTO apply(BoardEventRequest board) {
        return new BoardDTO(
                board.getId(),
                board.getTitle(),
                userDTOMapper.apply(board.getOwner()),
                board.getUsers()
                        .stream()
                        .map(userDTOMapper)
                        .collect(Collectors.toSet()),
                board.getCardLists()
                        .stream()
                        .map(cardListDTOMapper)
                        .collect(Collectors.toSet()),
                board.getImagePath()
        );
    }
}
