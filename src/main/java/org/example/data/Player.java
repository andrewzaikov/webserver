package org.example.data;

/**
 * Stored data type.
 * @param id        gameId of object
 * @param firstName first name of person
 * @param lastName  last name of person
 */
public record Player(long id, String firstName, String lastName) {
}
