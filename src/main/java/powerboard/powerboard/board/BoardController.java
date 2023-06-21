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
    public ResponseEntity<Set<BoardDTO>> getUserBoards(){
        return ResponseEntity.ok(boardService.getUserBoards());
    }
    @PostMapping
    public ResponseEntity<BoardDTO> create(@RequestBody BoardRequest request) {
        return ResponseEntity.ok(boardService.create(request));
    }
    @DeleteMapping ResponseEntity<Void> remove(@RequestParam Long boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.ok().build();
    }
    @PatchMapping ResponseEntity<BoardDTO> update(@RequestBody BoardRequest request, @RequestParam Long boardId) {
        return ResponseEntity.ok(boardService.update(request, boardId));
    }
}
