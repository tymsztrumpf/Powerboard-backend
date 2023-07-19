package powerboard.powerboard.cardlist;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import powerboard.powerboard.board.Board;
import powerboard.powerboard.board.BoardRepository;
import powerboard.powerboard.card.Card;
import powerboard.powerboard.card.CardRepository;
import powerboard.powerboard.exception.ApiRequestException;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CardListService {
    private final CardListRepository cardListRepository;
    private final BoardRepository boardRepository;
    private final CardListDTOMapper cardListDTOMapper;

    public CardListDTO addListToBoard(CardListRequest request, Long boardId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        Board board = optionalBoard.orElseThrow(() -> new ApiRequestException("Board not found"));

        CardList cardList = CardList.builder()
                .title(request.getTitle())
                .board(board)
                .build();

        board.getCardLists().add(cardList);
        cardListRepository.save(cardList);
        boardRepository.save(board);
        return cardListDTOMapper.apply(cardList);
    }
    public void deleteCardList(Long cardListId, Long boardId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        Board board = optionalBoard.orElseThrow(() -> new ApiRequestException("Board not found"));

        CardList cardList = board.getCardLists().stream().filter(c -> c.getId() == cardListId).findAny().get();

        board.getCardLists().remove(cardList);
        cardListRepository.delete(cardList);
        boardRepository.save(board);
    }
    @Transactional
    public CardListDTO update(CardListRequest request, Long boardId, Long cardListId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        Board board = optionalBoard.orElseThrow(() -> new ApiRequestException("Board bo found"));
        CardList cardList = board.getCardLists().stream().filter(c -> c.getId() == cardListId).findAny().get();
        cardList.setTitle(request.getTitle());
        cardList.setOrderNumber(request.getOrderNumber());

        cardListRepository.save(cardList);
        return cardListDTOMapper.apply(cardList);
    }

}
