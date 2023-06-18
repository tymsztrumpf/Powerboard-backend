package powerboard.powerboard.card;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import powerboard.powerboard.board.Board;
import powerboard.powerboard.board.BoardRepository;
import powerboard.powerboard.cardlist.CardList;
import powerboard.powerboard.cardlist.CardListRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final CardListRepository cardListRepository;
    private final BoardRepository boardRepository;
    public void addCardToCardList(CardRequest request, Long boardId, Long cardListId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        Board board = optionalBoard.orElseThrow(() -> new RuntimeException("Board not found"));

        CardList cardList = board.getCardLists().stream().filter(c -> c.getId() == cardListId).toList().get(0);

        Card card = Card.builder()
                .title(request.getTitle())
                .cardList(cardList)
                .build();

        cardList.getCards().add(card);
        cardListRepository.save(cardList);
        cardRepository.save(card);
    }
}
