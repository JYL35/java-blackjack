package blackjack.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlayerTest {
    @Test
    @DisplayName("플레이어는 카드를 추가하고 총 점수를 계산한다.")
    void calculateScore() {
        Player player = new Player("pobi");
        player.addCard(new Card(CardNumber.TWO, CardPattern.SPADE));
        player.addCard(new Card(CardNumber.THREE, CardPattern.HEART));

        assertThat(player.calculateScore()).isEqualTo(5);
    }
}
