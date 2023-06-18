package powerboard.powerboard.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import powerboard.powerboard.cardlist.CardList;
import powerboard.powerboard.user.User;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Board {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    @ManyToOne
    private User owner;
    //TODO THINK ABOUT ROLES INSTEAD OF OWNER CLASS FIELD
    @ManyToMany(cascade = {CascadeType.MERGE})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Set<User> users;
    @OneToMany
    @EqualsAndHashCode.Exclude
    private Set<CardList> cardLists;
    @Builder
    public Board(String title, User owner) {
        this.title = title;
        this.owner = owner;
        this.users = new HashSet<>();
        this.cardLists = new HashSet<>();
    }

}
