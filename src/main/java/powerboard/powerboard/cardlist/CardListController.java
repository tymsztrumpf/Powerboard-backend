package powerboard.powerboard.cardlist;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import powerboard.powerboard.board.BoardRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/card-list")
public class CardListController {
    private final CardListService cardListService;
    @PostMapping
    public ResponseEntity<Void> add(@RequestBody CardListRequest request, @RequestParam Long boardId) {
        cardListService.addListToBoard(request, boardId);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping
    public ResponseEntity<Void> remove(@RequestParam Long cardListId, @RequestParam Long boardId) {
        cardListService.deleteCardList(cardListId, boardId);
        return ResponseEntity.ok().build();
    }
    @PatchMapping
    public ResponseEntity<Void> update(@RequestBody CardListRequest request, @RequestParam Long boardId, @RequestParam Long cardListId){
        cardListService.update(request, boardId, cardListId);
        return ResponseEntity.ok().build();
    }
}
