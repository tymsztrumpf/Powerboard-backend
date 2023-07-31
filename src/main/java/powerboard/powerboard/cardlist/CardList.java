package powerboard.powerboard.cardlist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import powerboard.powerboard.board.Board;
import powerboard.powerboard.card.Card;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CardList {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Exclude
    private Long id;
    private String title;
    @ManyToOne
    @JoinColumn(name = "board_id")
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Board board;
    @OneToMany(mappedBy = "cardList", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Card> cards;
}
