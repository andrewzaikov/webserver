package org.example.restservice;

import org.example.data.MyPerson;
import org.example.repositiry.MyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;

@RestController
public class MyRestController {

    @PostMapping("/register")
    public String register() {
        return SessionManager.register();
    }

    @PostMapping("/addUser")
    public Long addUser(
            @RequestParam(value = "session") String session,
            @RequestParam(value = "firstName") String firstName,
            @RequestParam(value = "lastName", defaultValue = "(unknown))") String lastName
    ) {
        if (!SessionManager.checkSession(session)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Session is not valid");
        }
        return MyRepository.addPerson(firstName, lastName);
    }

    @GetMapping("/person")
    public MyPerson person(
            @RequestParam(value = "session") String session,
            @RequestParam(value = "id") Long id
    ) {
        if (!SessionManager.checkSession(session)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Session is not valid");
        }
        return MyRepository.get(id);
    }

    @GetMapping("/list")
    public Collection<MyPerson> list(
            @RequestParam(value = "session") String session
    ) {
        if (!SessionManager.checkSession(session)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Session is not valid");
        }
        return MyRepository.list();
    }

    @GetMapping("/size")
    public int size(
            @RequestParam(value = "session") String session
    ) {
        if (!SessionManager.checkSession(session)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Session is not valid");
        }
        return MyRepository.size();
    }

    @GetMapping("/stat")
    public List<String> stat() {
        return SessionManager.getStat();
    }

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
