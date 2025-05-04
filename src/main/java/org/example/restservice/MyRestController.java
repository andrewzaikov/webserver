package org.example.restservice;

import org.example.data.MyPerson;
import org.example.repositiry.MyRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class MyRestController {
    private static AtomicLong counter = new AtomicLong();

    @PutMapping("/register")
    public Long register(
            @RequestParam(value = "firstName") String firstName,
            @RequestParam(value = "lastName", defaultValue = "(unknown))") String lastName
    ) {
        return MyRepository.addPerson(new MyPerson(counter.incrementAndGet(), firstName, lastName));
    }

    @GetMapping("/list")
    public Collection<MyPerson> list() {
        return MyRepository.list();
    }

    @GetMapping("/size")
    public int size() {
        return MyRepository.size();
    }

    @DeleteMapping("/remove")
    public void remove(@RequestParam(value = "id") Long id) {
        MyRepository.removeById(id);
    }
}
