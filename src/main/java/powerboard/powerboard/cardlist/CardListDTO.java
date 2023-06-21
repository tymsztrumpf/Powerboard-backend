package powerboard.powerboard.cardlist;

import powerboard.powerboard.card.CardDTO;

import java.util.Set;

public record CardListDTO(
        Long Id,
        String title,
        Set<CardDTO> cards
) {
}
