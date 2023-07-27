package powerboard.powerboard.card;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import powerboard.powerboard.user.UserDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/card")
public class CardController {

    private final CardService cardService;
    @PostMapping
    public ResponseEntity<CardDTO> add(@RequestBody CardRequest request,
                                    @RequestParam Long boardId,
                                    @RequestParam Long cardListId) {
        return ResponseEntity.ok(cardService.addCardToCardList(request, boardId, cardListId));
    }
    @DeleteMapping
    public ResponseEntity<Void> remove(@RequestParam Long cardId,
                                       @RequestParam Long cardListId,
                                       @RequestParam Long boardId) {
        cardService.deleteCard(cardId, cardListId, boardId);
        return ResponseEntity.ok().build();
    }
    @PatchMapping
    public ResponseEntity<CardDTO> update(@RequestBody CardRequest request,
                                       @RequestParam Long cardId,
                                       @RequestParam Long cardListId,
                                       @RequestParam Long boardId) {
        System.out.println(cardId);
        System.out.println(cardListId);
        System.out.println(boardId);
        System.out.println(request);
        return ResponseEntity.ok(cardService.updateCard(request, cardId, cardListId, boardId));
    }
    @PatchMapping("/add-user")
    public ResponseEntity<UserDTO> addUser(@RequestParam Long cardId,
                                           @RequestParam Long cardListId,
                                           @RequestParam Long boardId,
                                           @RequestParam String userEmail) {
        return ResponseEntity.ok(cardService.addUser(cardId, cardListId, boardId, userEmail));
    }
}
