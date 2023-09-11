package powerboard.powerboard.message;

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
public class BoardEventRequest {
    private Long id;
    private String title;
    private User owner;
    private Set<User> users;
    private Set<CardList> cardLists;
    private String imagePath;
}
