package org.example.data;

/**
 * Stored data type.
 * @param id        id of object
 * @param firstName first name of person
 * @param lastName  last name of person
 */
public record MyPerson(long id, String firstName, String lastName) {
}
