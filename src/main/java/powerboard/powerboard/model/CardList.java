package powerboard.powerboard.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CardList {
    @Id
    private Long id;
    private String title;
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
    @OneToMany
    private Set<Card> cards;

}
