package powerboard.powerboard.board;

import jakarta.persistence.*;
import lombok.*;
import powerboard.powerboard.cardlist.CardList;
import powerboard.powerboard.user.User;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Board {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    @OneToOne
    private User owner;
    //TODO THINK ABOUT ROLES INSTEAD OF OWNER CLASS FIELD
    @OneToMany
    private Set<User> users;
    @OneToMany
    private Set<CardList> cardLists;
}
