package org.example.data;

public enum Suit {
    DIAMOND,
    CLUB,
    HEART,
    SPADE;

    public static Suit of(int num) {
        return switch (num) {
            case 0 -> DIAMOND;
            case 1 -> CLUB;
            case 2 -> HEART;
            case 3 -> SPADE;
            default -> null;
        };
    }
}
