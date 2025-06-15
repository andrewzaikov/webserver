package org.example.repositiry;

import org.example.data.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Service class (singleton, static).
 * Manages actions with MyPerson.class: store, get, delete.
 * No database is used in this release.
 */
public class MyRepository {
    private static final Map<Long, Player> MY_PERSON_STORE = new ConcurrentHashMap<>();
    private static final AtomicLong COUNTER = new AtomicLong();

    public static long addPerson(String firstName, String lastName) {
        Player person = new Player(COUNTER.incrementAndGet(), firstName, lastName);
        MY_PERSON_STORE.put(person.id(), person);
        return person.id();
    }

    public static void removeById(long id) {
        MY_PERSON_STORE.remove(id);
    }

    public static int size() {
        return MY_PERSON_STORE.size();
    }

    public static List<Game> gameList() {
        //todo: stub
        return List.of(
                new Game("First game", "111"),
                new Game("Second game", "222"),
                new Game("Third game", "333")
        );
    }

    public static GameInfo getPlayerInfo() {
        //todo: stub
        return new GameInfo(
                List.of(new Card(6, Suit.SPADE), new Card(8, Suit.CLUB)),
                List.of(new Card(11, Suit.HEART), new Card(3, Suit.DIAMOND)),
                PlayerState.PLAYER_MOVE
        );
    }
}
