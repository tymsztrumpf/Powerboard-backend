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
    @OneToMany
    private Set<User> executors;
    @ManyToOne
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private CardList cardList;
}
