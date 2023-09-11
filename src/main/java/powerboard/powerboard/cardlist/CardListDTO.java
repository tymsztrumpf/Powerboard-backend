package powerboard.powerboard.cardlist;

import powerboard.powerboard.card.CardDTO;

import java.util.Set;

public record CardListDTO(
        Long id,
        String title,
        Set<CardDTO> cards
) {
}
