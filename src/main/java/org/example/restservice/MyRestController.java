package org.example.restservice;

import org.example.data.Game;
import org.example.data.GameInfo;
import org.example.data.Player;
import org.example.repositiry.MyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;

@RestController
public class MyRestController {

    @PostMapping("/register")
    public String register(
            @RequestParam(value = "firstName") String firstName,
            @RequestParam(value = "lastName") String lastName
    ) {
        System.out.println("Register player: "+firstName+' '+lastName);
        MyRepository.register();
        return SessionManager.register();
    }

    @GetMapping("/getInfo")
    public GameInfo getInfo(
            @RequestParam(value = "session") String session
    ) {
        if (!SessionManager.checkSession(session)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Session is not valid");
        }
        System.out.println("Get information...");
        return MyRepository.getPlayerInfo();
    }

    @PostMapping("/join")
    public void join(
            @RequestParam(value = "session") String session,
            @RequestParam(value = "id") String id
    ) {
        if (!SessionManager.checkSession(session)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Session is not valid");
        }
        if (!MyRepository.gameExists(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game with gameId="+id+" not found!");
        }
        System.out.println("Session "+session+" joins the game "+id);
        MyRepository.joinGame(id);
    }

    @PostMapping("/startGame")
    public String startGame(
            @RequestParam(value = "session") String session
    ) {
        if (!SessionManager.checkSession(session)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Session is not valid");
        }
        return MyRepository.startGame();
    }

    @GetMapping("/gameList")
    public Collection<Game> gameList(
            @RequestParam(value = "session") String session
    ) {
        if (!SessionManager.checkSession(session)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Session is not valid");
        }
        System.out.println("List of games...");
        return MyRepository.gameList();
    }

    @PostMapping("/takeCard")
    public void takeCard(
            @RequestParam(value = "session") String session
    ) {
        if (!SessionManager.checkSession(session)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Session is not valid");
        }
        MyRepository.takeCard();
    }

    @PostMapping("/passMove")
    public void passMove(
            @RequestParam(value = "session") String session
    ) {
        if (!SessionManager.checkSession(session)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Session is not valid");
        }
        MyRepository.passMove();
    }

    @PostMapping("/stopGame")
    public void stopGame(
            @RequestParam(value = "session") String session
    ) {
        if (!SessionManager.checkSession(session)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Session is not valid");
        }
        MyRepository.stopGame();
    }

    @GetMapping("/stats")
    public List<String> stats() {
        return SessionManager.getStat();
    }

    //todo: rework
    @DeleteMapping("/remove")
    public void remove(
            @RequestParam(value = "session") String session,
            @RequestParam(value = "id") Long id
    ) {
        if (!SessionManager.checkSession(session)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Session is not valid");
        }
        MyRepository.removeById(id);
    }
}
