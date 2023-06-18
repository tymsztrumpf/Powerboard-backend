package powerboard.powerboard.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import powerboard.powerboard.board.Board;
import powerboard.powerboard.board.BoardController;
import powerboard.powerboard.board.BoardRequest;
import powerboard.powerboard.board.BoardService;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BoardControllerTest {

    @Mock
    private BoardService boardService;

    @InjectMocks
    private BoardController boardController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(boardController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldGetUserBoards() throws Exception {
        Set<Board> boards = new HashSet<>();

        when(boardService.getUserBoards()).thenReturn(boards);

        mockMvc.perform(get("/api/board")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(boardService, times(1)).getUserBoards();
    }

    @Test
    void shouldCreateBoard() throws Exception {
        BoardRequest request = new BoardRequest();

        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/board")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());

        verify(boardService, times(1)).create(request);
    }
}