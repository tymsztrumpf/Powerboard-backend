package powerboard.powerboard.card;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import powerboard.powerboard.user.UserDTO;
import powerboard.powerboard.user.UserDTOMapper;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CardDTOMapper implements Function<Card, CardDTO> {
    private final UserDTOMapper userDTOMapper;
    @Override
    public CardDTO apply(Card card) {
        Set<UserDTO> executors = (card.getExecutors() != null) ?
                card.getExecutors()
                        .stream()
                        .map(userDTOMapper)
                        .collect(Collectors.toSet()) :
                new HashSet<>();

        return new CardDTO(
                card.getId(),
                card.getTitle(),
                card.getDescription(),
                executors,
                card.getCardList().getId(),
                card.getOrderNum());
    }
}
