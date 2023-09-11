package powerboard.powerboard.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import powerboard.powerboard.board.Board;
import powerboard.powerboard.board.BoardDTO;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final BoardMessageDTOMapper boardMessageDTOMapper;
    public BoardDTO convert(BoardEventRequest request) {
       return boardMessageDTOMapper.apply(request);
    }
}
