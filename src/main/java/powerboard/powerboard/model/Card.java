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
