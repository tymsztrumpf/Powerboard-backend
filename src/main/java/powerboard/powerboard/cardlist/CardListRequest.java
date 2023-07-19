package powerboard.powerboard.cardlist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardListRequest {
    private String title;
    private Integer orderNumber;
}
