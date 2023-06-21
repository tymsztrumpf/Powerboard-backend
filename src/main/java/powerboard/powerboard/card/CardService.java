package powerboard.powerboard.card;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final CardDTOMapper cardDTOMapper;
    public CardDTO addCardToCardList(CardRequest request, Long boardId, Long cardListId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        Board board = optionalBoard.orElseThrow(() -> new RuntimeException("Board not found"));

        CardList cardList = board.getCardLists().stream().filter(c -> c.getId() == cardListId).findAny().get();

        Card card = Card.builder()
                .title(request.getTitle())
                .cardList(cardList)
                .build();

        cardList.getCards().add(card);
        cardRepository.save(card);
        cardListRepository.save(cardList);
        return cardDTOMapper.apply(card);
    }
    public void deleteCard(Long cardId, Long cardListId, Long boardId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        Board board = optionalBoard.orElseThrow(() -> new RuntimeException("Board not found"));

        CardList cardList = board.getCardLists().stream().filter(c -> c.getId() == cardListId).findAny().get();

        Card card = cardList.getCards().stream().filter(c -> c.getId() == cardId).findAny().get();

        cardList.getCards().remove(card);
        cardRepository.delete(card);
        cardListRepository.save(cardList);
    }

    @Transactional
    public CardDTO updateCard(CardRequest request, Long cardId, Long cardListId, Long boardId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        Board board = optionalBoard.orElseThrow(() -> new RuntimeException("Board not found"));

        CardList cardList = board.getCardLists().stream().filter(c -> c.getId() == cardListId).findAny().get();

        Card card = cardList.getCards().stream().filter(c -> c.getId() == cardId).findAny().get();

        card.setTitle(request.getTitle());
        card.setDescription(request.getDescription());
        card.setExecutors(request.getExecutors());
        card.setCardList(cardListRepository.findById(request.getCardListId()).orElseThrow());
        cardRepository.save(card);
        return cardDTOMapper.apply(card);
    }
}
