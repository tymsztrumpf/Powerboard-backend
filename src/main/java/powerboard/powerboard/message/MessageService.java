package powerboard.powerboard.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import powerboard.powerboard.board.Board;
import powerboard.powerboard.board.BoardDTO;
import powerboard.powerboard.cardlist.CardList;
import powerboard.powerboard.cardlist.CardListDTO;
import powerboard.powerboard.cardlist.CardListRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final BoardMessageDTOMapper boardMessageDTOMapper;
    public BoardDTO convert(BoardEventRequest request) {
        request.getCardLists().forEach(cardList ->
                cardList.getCards().forEach(card -> card.setCardList(cardList)));
       return boardMessageDTOMapper.apply(request);
    }

}
