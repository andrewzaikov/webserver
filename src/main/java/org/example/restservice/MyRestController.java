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
        if (!id.equals("111") && !id.equals("222") && !id.equals("333")) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game with gameId="+id+" not found!");
        }
        System.out.println("Session "+session+" joins the game "+id);
        //todo: join player 'session' to game 'gameId'
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

    //todo: rework
    @GetMapping("/size")
    public int size(
            @RequestParam(value = "session") String session
    ) {
        if (!SessionManager.checkSession(session)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Session is not valid");
        }
        return MyRepository.size();
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
