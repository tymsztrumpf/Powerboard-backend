package powerboard.powerboard.board;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import powerboard.powerboard.user.UserDTOMapper;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BoardDTOMapper implements Function<Board, BoardDTO> {
private final UserDTOMapper userDTOMapper;
    @Override
    public BoardDTO apply(Board board) {
        return new BoardDTO(
                board.getId(),
                board.getTitle(),
                userDTOMapper.apply(board.getOwner()),
                board.getUsers()
                        .stream()
                        .map(userDTOMapper)
                        .collect(Collectors.toSet()),
                board.getCardLists()
                );
    }
}
