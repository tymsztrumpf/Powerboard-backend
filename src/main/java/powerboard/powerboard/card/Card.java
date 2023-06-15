package powerboard.powerboard.card;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private Long id;
    private String title;
    private String description;
    @OneToMany
    private Set<User> executors;
    @ManyToOne
    private CardList cardList;
}
