package org.example.repositiry;

import org.example.data.MyPerson;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Service class (singleton, static).
 * Manages actions with MyPerson.class: store, get, delete.
 * No database is used in this release.
 */
public class MyRepository {
    private static final Map<Long, MyPerson> MY_PERSON_STORE = new ConcurrentHashMap<>();
    private static final AtomicLong COUNTER = new AtomicLong();

    public static long addPerson(String firstName, String lastName) {
        MyPerson person = new MyPerson(COUNTER.incrementAndGet(), firstName, lastName);
        MY_PERSON_STORE.put(person.id(), person);
        return person.id();
    }

    public static Collection<MyPerson> list() {
        return MY_PERSON_STORE.values();
    }

    public static void removeById(long id) {
        MY_PERSON_STORE.remove(id);
    }

    public static int size() {
        return MY_PERSON_STORE.size();
    }

    public static MyPerson get(Long id) {
        return MY_PERSON_STORE.get(id);
    }
}
