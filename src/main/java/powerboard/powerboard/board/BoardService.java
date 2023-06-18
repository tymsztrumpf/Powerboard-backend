package powerboard.powerboard.board;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import powerboard.powerboard.user.User;
import powerboard.powerboard.user.UserRepository;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    @Transactional
    public void create(BoardRequest request){
        User user = getCurrentUser();
        Board board = Board.builder()
                .title(request.getTitle())
                .owner(user)
                .build();
        board.getUsers().add(user);
        user.getBoards().add(board);
        boardRepository.save(board);
        userRepository.save(user);
    }
    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optionalUser = Optional.ofNullable((User) auth.getPrincipal());
        return optionalUser.orElseThrow(); // TODO OBSLUZYC WYJATEK
    }
    public Set<Board> getUserBoards() {
        return boardRepository.findAllByUserEmail(getCurrentUser().getEmail());
    }
}
