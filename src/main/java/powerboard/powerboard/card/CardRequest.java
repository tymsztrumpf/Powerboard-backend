package powerboard.powerboard.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import powerboard.powerboard.cardlist.CardList;
import powerboard.powerboard.user.User;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardRequest {
    private Long id;
    private String title;
    private String description;
    private Set<User> executors;
    private Long cardListId;
    private Integer orderNum;
}
