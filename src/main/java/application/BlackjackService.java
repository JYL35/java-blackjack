package application;

import domain.card.Card;
import domain.card.Deck;
import domain.member.Members;
import domain.member.Money;
import dto.RoundResult;
import dto.GameResult;
import dto.MemberStatus;

import java.util.*;

public class BlackjackService {

    private final Members members;
    private final Deck deck;

    public BlackjackService(Map<String, Integer> playerBetAmounts, Deck deck) {
        this.members = new Members(setUpMembers(playerBetAmounts));
        this.deck = deck;
        distributeInitCard();
    }

    private Map<String, Money> setUpMembers(Map<String, Integer> playerBetAmounts) {
        Map<String, Money> playerBets = new HashMap<>();
        for (String name : playerBetAmounts.keySet()) {
            Money betMoney = new Money(playerBetAmounts.get(name));
            playerBets.put(name, betMoney);
        }
        return playerBets;
    }

    private void distributeInitCard() {
        members.provideCardToDealer(deck.draw());
        members.provideCardToDealer(deck.draw());
        for (String playerName : members.getAllPlayerName()) {
            members.provideCardToPlayer(playerName, deck.draw());
            members.provideCardToPlayer(playerName, deck.draw());
        }
    }

    public boolean isFinishedByName(String playerName) {
        return members.isPlayerFinishedByName(playerName);
    }

    public RoundResult startOneRound(String memberName) {
        List<Card> playerCards = drawForMember(memberName);

        boolean isBust = isPlayerBust(memberName);

        return new RoundResult(playerCards, isBust);
    }

    private List<Card> drawForMember(String playerName) {
        members.provideCardToPlayer(playerName, deck.draw());
        return members.findCardByName(playerName);
    }

    private boolean isPlayerBust(String playerName) {
        return members.isPlayerBust(playerName);
    }

    public void endPlayerRound(String playerName) {
        members.changePlayerStateToStay(playerName);
    }

    public boolean checkDealerDrawable() {
        if (members.canTheDealerDraw()) {
            members.provideCardToDealer(deck.draw());
            members.changeDealerStateToStay();
            return true;
        }
        members.changeDealerStateToStay();
        return false;
    }

    public List<MemberStatus> getMemberStatuses() {
        List<MemberStatus> memberStatuses = new ArrayList<>();
        memberStatuses.add(
                new MemberStatus(members.getDealerName(), members.findDealerCards(), members.getDealerScore()));

        members.getAllPlayerName().stream()
                .map(name -> new MemberStatus(name, members.findCardByName(name), members.getPlayerScore(name)))
                .forEach(memberStatuses::add);
        return Collections.unmodifiableList(memberStatuses);
    }

    public List<GameResult> getGameResults() {
        Map<String, Integer> profits = members.calculateFinalProfits();
        return profits.entrySet().stream()
                .map(entry -> new GameResult(entry.getKey(), entry.getValue()))
                .toList();
    }
}
