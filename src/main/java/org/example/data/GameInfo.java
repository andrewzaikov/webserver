package org.example.data;

import java.util.List;

public record GameInfo(List<Card> myCards, List<Card> opponentCards, PlayerState status) {
}
