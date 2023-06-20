package powerboard.powerboard.board;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {
    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<Set<Board>> getUserBoards(){
        return ResponseEntity.ok(boardService.getUserBoards());
    }
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody BoardRequest request) {
        boardService.create(request);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping ResponseEntity<Void> remove(@RequestParam Long boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.ok().build();
    }
    @PatchMapping ResponseEntity<Void> update(@RequestBody BoardRequest request, @RequestParam Long boardId) {
        boardService.update(request, boardId);
        return ResponseEntity.ok().build();
    }
}
