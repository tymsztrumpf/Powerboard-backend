package powerboard.powerboard.cardlist;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import powerboard.powerboard.board.Board;
import powerboard.powerboard.board.BoardRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardListService {
    private final CardListRepository cardListRepository;
    private final BoardRepository boardRepository;

    public void addListToBoard(CardListRequest request, Long boardId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        Board board = optionalBoard.orElseThrow(() -> new RuntimeException("Board not found"));
        // TODO HANDLE EXCEPTION IN PROPER ELEGANT WAY

        CardList cardList = CardList.builder()
                .title(request.getTitle())
                .board(board)
                .build();

        board.getCardLists().add(cardList);
        cardListRepository.save(cardList);
        boardRepository.save(board);
    }

}
