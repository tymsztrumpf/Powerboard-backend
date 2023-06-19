package powerboard.powerboard.cardlist;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import powerboard.powerboard.board.Board;
import powerboard.powerboard.board.BoardRepository;
import powerboard.powerboard.card.Card;
import powerboard.powerboard.card.CardRepository;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CardListService {
    private final CardListRepository cardListRepository;
    private final BoardRepository boardRepository;
    private final CardRepository cardRepository;

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
    public void deleteCardList(Long cardListId, Long boardId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        Board board = optionalBoard.orElseThrow(() -> new RuntimeException("Board not found"));

        CardList cardList = board.getCardLists().stream().filter(c -> c.getId() == cardListId).findAny().get();

        board.getCardLists().remove(cardList);
        cardListRepository.delete(cardList);
        boardRepository.save(board);
    }
}
