package org.example.repositiry;

import org.example.data.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static org.example.data.PlayerState.*;

/**
 * Service class (singleton, static).
 * Manages actions with MyPerson.class: store, get, delete.
 * No database is used in this release.
 */
public class MyRepository {
    private static final Map<Long, Player> MY_PERSON_STORE = new ConcurrentHashMap<>();
    private static final AtomicLong COUNTER = new AtomicLong();
    public static final List<Game> GAME_LIST = List.of(
            new Game("First game", "111"),
            new Game("Second game", "222"),
            new Game("Third game", "333")
    );
    private static PlayerState internalState;
    private static final List<Card> playerCards = new ArrayList<>();
    private static final List<Card> opponentCards = new ArrayList<>();
    private static long lastUpdated = System.currentTimeMillis();

    public static long addPerson(String firstName, String lastName) {
        Player person = new Player(COUNTER.incrementAndGet(), firstName, lastName);
        MY_PERSON_STORE.put(person.id(), person);
        internalState = CONNECTED;
        return person.id();
    }

    public static void removeById(long id) {
        MY_PERSON_STORE.remove(id);
    }

    public static List<Game> gameList() {
        //todo: stub
        return GAME_LIST;
    }

    public static GameInfo getPlayerInfo() {
        //todo: stub
        if (internalState == OPPONENT_MOVE && (System.currentTimeMillis()-lastUpdated) > 3000) {
            lastUpdated = System.currentTimeMillis();
            opponentCards.add(createRandomCard());
            int randVal = new Random(System.currentTimeMillis()).nextInt(10);
            if (randVal > 7) {
                internalState = PLAYER_WON;
            } else if (randVal < 3) {
                internalState = PLAYER_LOST;
            }
        }
        return new GameInfo(
                playerCards,
                opponentCards,
                internalState
        );
    }

    public static String startGame() {
        internalState = PLAYER_MOVE;
        return GAME_LIST.get(0).gameId();
    }

    public static boolean gameExists(String gameId) {
        for (var game : GAME_LIST) {
            if (game.gameId().equals(gameId)) {
                return true;
            }
        }
        return false;
    }

    private static Card createRandomCard() {
        Random random = new Random(System.currentTimeMillis());
        int value = random.nextInt(10) + 2;
        if (value == 5) {
            value++;
        }
        Suit suit = Suit.of(random.nextInt(4));
        return new Card(value, suit);
    }

    public static void takeCard() {
        //todo: stub
        playerCards.add(createRandomCard());
        int random = new Random(System.currentTimeMillis()).nextInt(10);
        if (random > 5) {
            internalState = OPPONENT_MOVE;
        } else if (random < 2) {
            internalState = PLAYER_LOST;
        } else {
            internalState = PLAYER_MOVE;
        }
    }

    public static void passMove() {
        //todo: stub
        int random = new Random(System.currentTimeMillis()).nextInt(10);
        if (random < 2) {
            internalState = PLAYER_WON;
        } else {
            internalState = OPPONENT_MOVE;
        }
    }

    public static void stopGame() {
        internalState = CONNECTED;
        playerCards.clear();
        opponentCards.clear();
    }

    public static void joinGame(String id) {
        internalState = PLAYER_MOVE;
    }

    public static void register() {
        internalState = CONNECTED;
        playerCards.clear();
        opponentCards.clear();
    }
}
