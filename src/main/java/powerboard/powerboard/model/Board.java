package powerboard.powerboard.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Board {
    @Id
    private Long id;
    private String title;
    @OneToOne
    private User owner;
    //TODO THINK ABOUT ROLES INSTEAD OF OWNER CLASS FIELD
    @OneToMany
    private Set<User> users;
}
