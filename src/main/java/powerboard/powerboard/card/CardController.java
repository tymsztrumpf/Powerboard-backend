package powerboard.powerboard.card;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import powerboard.powerboard.cardlist.CardListRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/card")
public class CardController {

    private final CardService cardService;
    @PostMapping
    public ResponseEntity<Void> add(@RequestBody CardRequest request,
                                    @RequestParam Long boardId,
                                    @RequestParam Long cardListId) {
        cardService.addCardToCardList(request, boardId, cardListId);
        return ResponseEntity.ok().build();
    }
}
