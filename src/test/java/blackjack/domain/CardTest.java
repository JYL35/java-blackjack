package blackjack.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CardTest {
    @Test
    @DisplayName("카드의 점수를 반환한다.")
    void getScore() {
        Card card = new Card(CardNumber.JACK, CardPattern.SPADE);
        assertThat(card.getScore()).isEqualTo(10);
    }
}
