package powerboard.powerboard.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import powerboard.powerboard.cardlist.CardList;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequest {
    private String title;
    private Set<CardList> cardLists;
}
