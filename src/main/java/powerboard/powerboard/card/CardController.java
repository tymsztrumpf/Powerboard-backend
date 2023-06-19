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
    @DeleteMapping
    public ResponseEntity<Void> remove(@RequestParam Long cardId,
                                       @RequestParam Long cardListId,
                                       @RequestParam Long boardId) {
        cardService.deleteCard(cardId, cardListId, boardId);
        return ResponseEntity.ok().build();
    }
    @PatchMapping
    public ResponseEntity<Void> update(@RequestBody CardRequest request,
                                       @RequestParam Long cardId,
                                       @RequestParam Long cardListId,
                                       @RequestParam Long boardId) {
        cardService.updateCard(request, cardId, cardListId, boardId);
        return ResponseEntity.ok().build();
    }

}
