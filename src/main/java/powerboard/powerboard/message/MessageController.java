package powerboard.powerboard.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import powerboard.powerboard.board.BoardDTO;

import java.io.DataInput;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final ObjectMapper objectMapper;

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public BoardDTO getMessages(String jsonMessage) throws IOException {
        BoardEventRequest request = objectMapper.readValue(jsonMessage, BoardEventRequest.class);
        return messageService.convert(request);
    }

}