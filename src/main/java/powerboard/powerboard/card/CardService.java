package powerboard.powerboard.card;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import powerboard.powerboard.board.Board;
import powerboard.powerboard.board.BoardRepository;
import powerboard.powerboard.cardlist.CardList;
import powerboard.powerboard.cardlist.CardListRepository;
import powerboard.powerboard.exception.ApiRequestException;
import powerboard.powerboard.user.User;
import powerboard.powerboard.user.UserDTO;
import powerboard.powerboard.user.UserDTOMapper;
import powerboard.powerboard.user.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final CardListRepository cardListRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CardDTOMapper cardDTOMapper;
    private final UserDTOMapper userDTOMapper;

    public CardDTO addCardToCardList(CardRequest request, Long boardId, Long cardListId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        Board board = optionalBoard.orElseThrow(() -> new ApiRequestException("Board not found"));

        CardList cardList = board.getCardLists().stream().filter(c -> c.getId() == cardListId).findAny().get();

        int orderNum = cardList.getCards().size();

        Card card = Card.builder()
                .title(request.getTitle())
                .cardList(cardList)
                .orderNum(orderNum)
                .build();

        cardList.getCards().add(card);
        cardRepository.save(card);
        cardListRepository.save(cardList);
        return cardDTOMapper.apply(card);
    }
    public void deleteCard(Long cardId, Long cardListId, Long boardId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        Board board = optionalBoard.orElseThrow(() -> new ApiRequestException("Board not found"));

        CardList cardList = board.getCardLists().stream().filter(c -> c.getId() == cardListId).findAny().get();

        Card card = cardList.getCards().stream().filter(c -> c.getId() == cardId).findAny().get();

        cardList.getCards().remove(card);
        cardRepository.delete(card);
        cardListRepository.save(cardList);
    }

    @Transactional
    public CardDTO updateCard(CardRequest request, Long cardId, Long cardListId, Long boardId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        Board board = optionalBoard.orElseThrow(() -> new ApiRequestException("Board not found"));

        CardList cardList = board.getCardLists().stream().filter(c -> c.getId() == cardListId).findAny().get();

        Card card = cardList.getCards().stream().filter(c -> c.getId() == cardId).findAny().get();

        card.setTitle(request.getTitle());
        card.setDescription(request.getDescription());
        card.setExecutors(request.getExecutors());
        card.setCardList(cardListRepository.findById(request.getCardListId()).orElseThrow(() -> new ApiRequestException("CardList not found")));
        cardRepository.save(card);
        return cardDTOMapper.apply(card);
    }
    @Transactional
    public UserDTO addUser(Long cardId, Long cardListId, Long boardId, String userEmail) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        Board board = optionalBoard.orElseThrow(() -> new ApiRequestException("Board not found"));

        Optional<User> optionalUser = userRepository.findByEmail(userEmail);
        User user = optionalUser.orElseThrow(() -> new ApiRequestException("User not found"));

        CardList cardList = board.getCardLists().stream().filter(c -> c.getId() == cardListId).findAny().get();

        Card card = cardList.getCards().stream().filter(c -> c.getId() == cardId).findAny().get();

        if(board.getUsers().contains(user)){
            card.addUser(user);
        }
        else throw new ApiRequestException("This user is not assigned to this board");
        cardRepository.save(card);
        return userDTOMapper.apply(user);
    }
}
