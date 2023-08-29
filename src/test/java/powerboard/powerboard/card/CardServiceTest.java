package powerboard.powerboard.card;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import powerboard.powerboard.board.Board;
import powerboard.powerboard.board.BoardRepository;
import powerboard.powerboard.cardlist.CardList;
import powerboard.powerboard.cardlist.CardListRepository;
import powerboard.powerboard.exception.ApiRequestException;
import powerboard.powerboard.user.User;
import powerboard.powerboard.user.UserDTO;
import powerboard.powerboard.user.UserDTOMapper;
import powerboard.powerboard.user.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {
    @Mock
    private CardRepository cardRepository;
    @Mock
    private CardListRepository cardListRepository;
    @Mock
    private BoardRepository boardRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CardDTOMapper cardDTOMapper;
    @Mock
    private UserDTOMapper userDTOMapper;
    private Board board;
    private CardList cardList;
    private CardService cardService;

    @BeforeEach
    void setUp() {
        board = new Board();
        cardList = new CardList();
        cardList.setId(1L);
        board.setId(1L);
        board.setCardLists(Set.of(cardList));
        cardService = new CardService(cardRepository, cardListRepository, boardRepository, userRepository, cardDTOMapper, userDTOMapper);
    }

    @Test
    void canAddCardToCardList() {
        // given
        CardRequest request = new CardRequest();
        request.setTitle("testTitle");

        Set<Card> cards = new HashSet<>();
        cardList.setCards(cards);

        int orderNum = 1;

        Card expectedCard = Card.builder()
                .title(request.getTitle())
                .cardList(cardList)
                .orderNum(orderNum)
                .description(null)
                .executors(null)
                .build();

        CardDTO expectedDTO = new CardDTO(expectedCard.getId(), expectedCard.getTitle(), null, null, null, orderNum);

        // when
        when(boardRepository.findById(board.getId())).thenReturn(Optional.of(board));
        when(cardDTOMapper.apply(any(Card.class))).thenReturn(expectedDTO);
        CardDTO result = cardService.addCardToCardList(request, board.getId(), cardList.getId());

        // then
        ArgumentCaptor<Card> cardArgumentCaptor = ArgumentCaptor.forClass(Card.class);

        verify(cardRepository).save(cardArgumentCaptor.capture());
        assertEquals(expectedDTO, result);
    }

    @Test
    void canNotAddCardToCardList() {
        // given
        CardRequest request = new CardRequest();
        request.setTitle("testTitle");

        Long nonExistingId = 2L;

        // then
        assertThrows(ApiRequestException.class, () -> {
            cardService.addCardToCardList(request, nonExistingId, cardList.getId());
        });
    }
    @Test
    void canDeleteCard() {
        // given
        Card card = new Card();
        card.setId(1L);
        Set<Card> cards = new HashSet<>();
        cards.add(card);
        cardList.setCards(cards);

        Set<Card> expected = Set.of();

        // when
        when(boardRepository.findById(board.getId())).thenReturn(Optional.of(board));
        cardService.deleteCard(card.getId(), cardList.getId(), board.getId());

        Set<Card> result = cardList.getCards();

        // then
        ArgumentCaptor<Card> cardArgumentCaptor = ArgumentCaptor.forClass(Card.class);

        verify(cardRepository).delete(cardArgumentCaptor.capture());
        assertEquals(expected, result);
    }

    @Test
    void canNotDeleteCard() {
        // given
        Card card = new Card();
        card.setId(1L);

        Long nonExistingId = 2L;

        // then
        assertThrows(ApiRequestException.class, () -> {
            cardService.deleteCard(card.getId(), cardList.getId(), nonExistingId);
        });
    }
    @Test
    void updateCard() {
        // given
        CardRequest request = new CardRequest();
        request.setTitle("testTitle");

        Card card = new Card();
        card.setId(1L);

        Set<Card> cards = new HashSet<>();
        cards.add(card);
        cardList.setCards(cards);

        CardDTO expectedDTO = new CardDTO(card.getId(), "testTitle", null, null, null, null);

        // when
        when(boardRepository.findById(board.getId())).thenReturn(Optional.of(board));
        when(cardDTOMapper.apply(any(Card.class))).thenReturn(expectedDTO);

        CardDTO result = cardService.updateCard(request, card.getId(), cardList.getId(), board.getId());

        // then
        ArgumentCaptor<Card> cardArgumentCaptor = ArgumentCaptor.forClass(Card.class);
        verify(cardRepository).save(cardArgumentCaptor.capture());

        assertEquals(expectedDTO, result);
    }

    @Test
    void canNotUpdateCard() {
        // given
        CardRequest request = new CardRequest();
        request.setTitle("testTitle");

        Card card = new Card();
        card.setId(1L);

        Long nonExistingId = 2L;

        // then
        assertThrows(ApiRequestException.class, () -> {
            cardService.updateCard(request, card.getId(), cardList.getId(), nonExistingId);
        });
    }
    @Test
    void addUser() {
        // given
        User user = new User();
        String email = "test@gmail.com";
        user.setEmail(email);

        Card card = new Card();
        card.setId(1L);

        Set<Card> cards = new HashSet<>();
        cards.add(card);
        cardList.setCards(cards);
        board.setUsers(Set.of(user));
        Set<User> executors = new HashSet<>();
        card.setExecutors(executors);

        Set<User> expected = Set.of(user);

        // when
        when(boardRepository.findById(board.getId())).thenReturn(Optional.of(board));
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        cardService.addUser(card.getId(), cardList.getId(), board.getId(), email);

        Set<User> result = card.getExecutors();

        // then
        ArgumentCaptor<Card> cardArgumentCaptor = ArgumentCaptor.forClass(Card.class);
        verify(cardRepository).save(cardArgumentCaptor.capture());

        assertEquals(expected, result);
    }
    @Test
    void canNotAddUserBecauseBoardDoNotExist() {
        // given
        User user = new User();
        String email = "test@gmail.com";
        user.setEmail(email);

        Card card = new Card();
        card.setId(1L);

        Long nonExistingId = 2L;

        // then
        assertThrows(ApiRequestException.class, () -> {
            cardService.addUser(card.getId(), cardList.getId(), nonExistingId, email);
        });
    }
    @Test
    void canNotAddUserBecauseUserDoNotExist() {
        // given
        String nonExistingEmail = "not@email.com";

        Card card = new Card();
        card.setId(1L);

        // when
        when(boardRepository.findById(board.getId())).thenReturn(Optional.of(board));

        // then
        assertThrows(ApiRequestException.class, () -> {
            cardService.addUser(card.getId(), cardList.getId(), board.getId(), nonExistingEmail);
        });
    }
    @Test
    void canNotAddUserToSameCardTwice() {
        // given
        User user = new User();
        String email = "test@gmail.com";
        user.setEmail(email);

        Card card = new Card();
        card.setId(1L);

        Set<Card> cards = new HashSet<>();
        cards.add(card);
        cardList.setCards(cards);
        board.setUsers(Set.of(user));
        Set<User> executors = new HashSet<>();
        executors.add(user);
        card.setExecutors(executors);

        // when
        when(boardRepository.findById(board.getId())).thenReturn(Optional.of(board));

        // then
        assertThrows(ApiRequestException.class, () -> {
            cardService.addUser(card.getId(), cardList.getId(), board.getId(), email);
        });
    }
    @Test
    void swapCards () {
        // given
        CardRequest request = new CardRequest();
        request.setId(1L);
        request.setCardListId(1L);
        request.setOrderNum(1);
        Set<CardRequest> requests = Set.of(request);

        Card card = new Card();
        card.setId(1L);

        Set<Card> cards = new HashSet<>();
        cards.add(card);
        cardList.setCards(cards);

        CardDTO expectedDTO = new CardDTO(1L, null, null, null, 1L, 1);
        Set<CardDTO> expected = Set.of(expectedDTO);

        // when
        when(boardRepository.findById(board.getId())).thenReturn(Optional.of(board));
        when(cardRepository.findById(request.getId())).thenReturn(Optional.of(card));
        when(cardDTOMapper.apply(any(Card.class))).thenReturn(expectedDTO);

        Set<CardDTO> result = cardService.swapCards(requests, board.getId());

        // then
        ArgumentCaptor<Card> cardArgumentCaptor = ArgumentCaptor.forClass(Card.class);
        verify(cardRepository).save(cardArgumentCaptor.capture());

        assertEquals(expected, result);
    }

    @Test
    void canNotSwapCardsBecauseBoardDoNotExist() {
        // given
        CardRequest request = new CardRequest();
        Set<CardRequest> requests = Set.of(request);

        Long nonExistingId = 2L;

        // then
        assertThrows(ApiRequestException.class, () -> {
            cardService.swapCards(requests, nonExistingId);
        });
    }

    @Test
    void canNotSwapCardsBecauseCardDoNotExist() {
        // given
        CardRequest request = new CardRequest();
        request.setId(2L);
        request.setCardListId(1L);
        request.setOrderNum(1);
        Set<CardRequest> requests = Set.of(request);

        Card card = new Card();
        card.setId(1L);

        Set<Card> cards = new HashSet<>();
        cards.add(card);
        cardList.setCards(cards);

        // when
        when(boardRepository.findById(board.getId())).thenReturn(Optional.of(board));

        // then
        assertThrows(ApiRequestException.class, () -> {
            cardService.swapCards(requests, board.getId());
        });
    }
    @Test
    void canNotSwapCardsToNonExistingCardList() {
        // given
        CardRequest request = new CardRequest();
        request.setId(1L);
        request.setCardListId(2L);
        request.setOrderNum(1);
        Set<CardRequest> requests = Set.of(request);

        Card card = new Card();
        card.setId(1L);

        Set<Card> cards = new HashSet<>();
        cards.add(card);
        cardList.setCards(cards);

        // when
        when(boardRepository.findById(board.getId())).thenReturn(Optional.of(board));
        when(cardRepository.findById(request.getId())).thenReturn(Optional.of(card));

        // then
        assertThrows(ApiRequestException.class, () -> {
            cardService.swapCards(requests, board.getId());
        });
    }
}