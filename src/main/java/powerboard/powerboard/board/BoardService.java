package powerboard.powerboard.board;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import powerboard.powerboard.exception.ApiRequestException;
import powerboard.powerboard.user.User;
import powerboard.powerboard.user.UserDTO;
import powerboard.powerboard.user.UserDTOMapper;
import powerboard.powerboard.user.UserRepository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BoardDTOMapper boardDTOMapper;
    private final UserDTOMapper userDTOMapper;
    @Transactional
    public BoardDTO create(BoardRequest request){
        User user = getCurrentUser();
        Board board = Board.builder()
                .title(request.getTitle())
                .owner(user)
                .build();
        board.getUsers().add(user);
        user.getBoards().add(board);
        boardRepository.save(board);
        return boardDTOMapper.apply(board);
    }
    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return userRepository.findByEmail(user.getEmail()).orElseThrow();
    }
    public Set<BoardDTO> getUserBoards() {
        return boardRepository.findAllByUserEmail(getCurrentUser().getEmail())
                .stream()
                .map(boardDTOMapper).collect(Collectors.toSet());
    }

    public void deleteBoard(Long boardId) {
        User user = getCurrentUser();
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        Board board = optionalBoard.orElseThrow(() -> new ApiRequestException("Board not found"));
        user.getBoards().remove(board);

        userRepository.save(user);
        boardRepository.delete(board);
    }
    @Transactional
    public BoardDTO update(BoardRequest request, Long boardId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        Board board = optionalBoard.orElseThrow(() -> new RuntimeException("Board not found"));

        board.setTitle(request.getTitle());
        boardRepository.save(board);
        return boardDTOMapper.apply(board);
    }
    @Transactional
    public BoardDTO addUser(String userEmail, Long boardId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        Board board = optionalBoard.orElseThrow(() -> new RuntimeException("Board not found"));

        Optional<User> optionalUser = userRepository.findByEmail(userEmail);
        User user = optionalUser.orElseThrow(() -> new RuntimeException("User not found"));

        board.addUser(user);
        boardRepository.save(board);
        return boardDTOMapper.apply(board);
    }
    public Set<UserDTO> getBoardUsers(Long boardId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        Board board = optionalBoard.orElseThrow(() -> new RuntimeException("Board not found"));

        return board.getUsers().stream().map(userDTOMapper).collect(Collectors.toSet());
    }
}
