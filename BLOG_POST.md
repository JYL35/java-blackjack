# [기술 블로그] Gemini CLI와 함께하는 도메인 주도 설계: 블랙잭 미션 완수하기

## 1. 서론
우아한테크코스 레벨 1 미션인 '블랙잭'을 진행하며, 단순한 코드 작성을 넘어 **AI 에이전트(Gemini CLI)**와 협업하는 새로운 개발 방식을 시도해 보았습니다. 핵심 도메인 로직을 직접 설계하고, AI가 이를 바탕으로 단위 테스트 뼈대 작성과 PR 생성 같은 반복적인 작업을 자동화하며 느낀 기술적 통찰을 공유합니다.

---

## 2. 주요 구현 기술 및 도구

### 🛠️ 도메인 설계 (Domain-Driven Design)
`blackjack.domain` 패키지 내에 객체지향 원칙을 준수하여 핵심 로직을 구축했습니다.
- **`Card`, `CardNumber`, `CardPattern`**: 카드의 속성을 상수가 아닌 Enum과 Value Object로 분리하여 타입 안정성을 확보했습니다.
- **`Deck`**: 52장의 카드를 생성하고 셔플(Shuffle)하는 책임을 명확히 부여했습니다.
- **`Player`**: 카드 합계를 계산하고 추가 드로우 여부를 결정하는 상태와 행위를 캡슐화했습니다.

### 🤖 AI Skills & MCP (Model Context Protocol)
이번 프로젝트에서 가장 인상적이었던 점은 **AI Skills**의 활용입니다.
- **Context Awareness**: `GEMINI.md`와 같은 프로젝트 컨텍스트를 통해 AI가 개발자의 설계 의도를 사전에 파악하고, 일관된 코딩 컨벤션을 유지할 수 있었습니다.
- **Custom Skills**: `skill-creator`를 통해 반복되는 DTO 생성이나 테스트 패턴을 스킬화하여 생산성을 극대화했습니다.

### 💻 Gemini CLI: 터미널에서의 심리스한 협업
명령줄 인터페이스(CLI) 환경에서 AI와 대화하며 소스 코드를 수정하고 바로 `gh` CLI와 연동하여 Pull Request를 생성했습니다.
- **자동화된 워크플로우**: `git status` 확인부터 `gh pr create`까지, 번거로운 CLI 명령어 조합을 AI가 대신 수행하여 개발 흐름(Flow)이 끊기지 않도록 도왔습니다.
- **Smart Git Management**: 제목에 `(by gemini-cli)`를 포함하는 등, 커밋 메시지와 PR 템플릿을 자동으로 준수하며 협업의 정석을 보여주었습니다.

---

## 3. 핵심 코드 분석 (`blackjack.domain`)

```java
// 예시: Deck 클래스의 셔플 및 드로우 로직
public class Deck {
    private final List<Card> cards;

    public Deck() {
        this.cards = generateAllCards();
        Collections.shuffle(this.cards);
    }

    public Card draw() {
        return cards.remove(0);
    }
}
```
위와 같이 도메인 로직은 간결하게 유지하되, 이에 대응하는 **`DeckTest.java`**와 같은 테스트 코드는 Gemini CLI가 제 설계를 분석하여 자동으로 뼈대를 작성해주었습니다. 이를 통해 저는 복잡한 엣지 케이스 로직에만 더 집중할 수 있었습니다.

---

## 4. 느낀 점 (Reflections)

AI는 이제 단순한 '질의응답'의 도구가 아니라, **'동료 개발자(Peer Programmer)'**로서의 역할을 수행합니다. 특히 이번 경험을 통해 느낀 핵심적인 두 가지는 다음과 같습니다.

1. **설계의 중요성**: AI에게 명확한 도메인 모델을 제시할수록 AI가 생성해내는 보조 코드(테스트, DTO 등)의 품질이 비약적으로 향상됩니다. 결국 개발자의 핵심 역량은 **'좋은 구조를 설계하는 능력'**에 있음을 다시 한번 확인했습니다.
2. **자동화의 가치**: PR 생성이나 반복적인 단위 테스트 작성 같은 '운영적 오버헤드'를 AI에게 맡김으로써, 프로젝트 본연의 가치인 비즈니스 로직 고도화에 더 많은 에너지를 쏟을 수 있었습니다.

---

> **"AI와 함께 성장하는 개발자가 되는 법, 그것은 AI를 도구로 쓰는 것을 넘어 협업의 파트너로 인정하는 것에서 시작됩니다."**
