package powerboard.powerboard.board;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import powerboard.powerboard.user.UserDTO;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/boards")
    public ResponseEntity<Set<BoardDTO>> getUserBoards(){
        return ResponseEntity.ok(boardService.getUserBoards());
    }
    @PostMapping
    public ResponseEntity<BoardDTO> create(@RequestBody BoardRequest request) {
        return ResponseEntity.ok(boardService.create(request));
    }
    @DeleteMapping
    public ResponseEntity<Void> remove(@RequestParam Long boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.ok().build();
    }
    @PatchMapping
    public ResponseEntity<BoardDTO> update(@RequestBody BoardRequest request,
                                           @RequestParam Long boardId) {
        return ResponseEntity.ok(boardService.update(request, boardId));
    }
    @PatchMapping("/add-user")
    public ResponseEntity <UserDTO> addUser(@RequestParam String userEmail,
                                             @RequestParam Long boardId) {
        return ResponseEntity.ok(boardService.addUser(userEmail, boardId));
    }
    @GetMapping("/users")
    public ResponseEntity <Set<UserDTO>> getBoardUsers(@RequestParam Long boardId) {
        return ResponseEntity.ok(boardService.getBoardUsers(boardId));
    }
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDTO> getBoard(@PathVariable Long boardId) {
        return ResponseEntity.ok(boardService.getBoard(boardId));
    }
}
