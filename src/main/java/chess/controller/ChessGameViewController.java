package chess.controller;

import chess.dto.request.PasswordRequest;
import chess.dto.request.RoomRequest;
import chess.service.ChessService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@Controller
public class ChessGameViewController {

    private final ChessService chessService;

    public ChessGameViewController(ChessService chessService) {
        this.chessService = chessService;
    }

    @GetMapping("/")
    public String returnHomeView() {
        return "index";
    }

    @GetMapping("/game")
    public String returnRoomView() {
        return "game";
    }

    @GetMapping("/game/list")
    public String returnGameListView() {
        return "gamelist";
    }

    @GetMapping("/board/{id}")
    public String returnBoardView(@PathVariable Long id) throws SQLException {
        chessService.validateGameId(id);
        return "board";
    }

    @PostMapping("/initialize/board")
    public String createRoom(@ModelAttribute RoomRequest roomRequest) {
        Long id = chessService.initializeGame(roomRequest);
        return "redirect:/board/" + id;
    }

    @PostMapping("/participate/{id}")
    public String participateRoom(@PathVariable Long id) throws SQLException {
        chessService.validateGameId(id);
        chessService.loadExistGame(id);
        return "redirect:/board/" + id;
    }

    @DeleteMapping("/{id}")
    public String deleteGame(@PathVariable Long id, PasswordRequest passwordRequest,
                             HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        if (chessService.isPossibleDeleteGame(id, passwordRequest.getPassword())) {
            chessService.endGame(id);
            out.println("<script>alert('체스가 삭제되었습니다.'); location.href='/game/list';</script>");
            out.flush();
        }
        out.println("<script>alert('체스를 종료하고, 올바른 비밀번호를 눌러주세요.'); location.href='/game/list';</script>");
        out.flush();
        return "redirect:/game/list";
    }
}
