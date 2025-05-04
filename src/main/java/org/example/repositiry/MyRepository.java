package org.example.repositiry;

import org.example.data.MyPerson;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyRepository {
    private static final Map<Long, MyPerson> personMap = new ConcurrentHashMap<>();

    public static long addPerson(MyPerson person) {
        personMap.put(person.id(), person);
        return person.id();
    }

    public static Collection<MyPerson> list() {
        return personMap.values();
    }

    public static void removeById(long id) {
        personMap.remove(id);
    }

    public static int size() {
        return personMap.size();
    }
}
