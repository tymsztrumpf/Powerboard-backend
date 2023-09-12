package powerboard.powerboard.message;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import powerboard.powerboard.board.BoardDTO;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public BoardDTO getMessages(BoardEventRequest request) throws IOException {
        return messageService.convert(request);
    }


}