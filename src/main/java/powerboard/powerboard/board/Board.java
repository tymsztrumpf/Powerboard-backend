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
    @ManyToMany(cascade = {CascadeType.MERGE})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Set<User> users;
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    private Set<CardList> cardLists;
    private String imagePath;
    @Builder
    public Board(String title, User owner, String imagePath) {
        this.title = title;
        this.owner = owner;
        this.imagePath = imagePath;
        this.users = new HashSet<>();
        this.cardLists = new HashSet<>();
    }
    void addUser(User user) {
        this.users.add(user);
    }
    void removeUser(User user) {
        this.users.remove(user);
    }
}
