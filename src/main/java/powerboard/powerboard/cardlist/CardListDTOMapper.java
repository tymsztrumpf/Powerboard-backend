package powerboard.powerboard.cardlist;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import powerboard.powerboard.card.CardDTO;
import powerboard.powerboard.card.CardDTOMapper;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CardListDTOMapper implements Function<CardList, CardListDTO> {
    private final CardDTOMapper cardDTOMapper;
    @Override
    public CardListDTO apply(CardList cardList) {
        Set<CardDTO> cards = (cardList.getCards() != null) ?
                cardList.getCards()
                        .stream()
                        .map(cardDTOMapper)
                        .collect(Collectors.toSet()) :
                new HashSet<>();

        return new CardListDTO(
                cardList.getId(),
                cardList.getTitle(),
                cards);
    }
}
