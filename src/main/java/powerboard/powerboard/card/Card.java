package powerboard.powerboard.card;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Card {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    @ManyToMany
    private Set<User> executors;
    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private CardList cardList;
    private Integer orderNum;
    public void addUser(User user) {
        this.executors.add(user);
    }

}
