import com.dto.MatchDto;
import com.dto.MatchScoreModel;
import com.dto.PlayerDto;
import com.services.MatchScoreCalculationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class ScoreTest {
    MatchScoreCalculationService service = new MatchScoreCalculationService();
    MatchDto matchDto = new MatchDto(new PlayerDto("A"), new PlayerDto("B"), null);

    @Test
    public  void testGameWin() {
        MatchScoreModel score = new MatchScoreModel(matchDto, 40, 0,
                null, null, null,
                java.util.Arrays.asList(0, 0), java.util.Arrays.asList(0, 0)
        );
        service.incrementPlayerScore(score, score.getMatchDto().getFirstPlayer());

        Assertions.assertNull(score.getAdvantage());
        Assertions.assertEquals(0, score.getFirstPlayerScore());
        Assertions.assertEquals(0, score.getSecondPlayerScore());
        Assertions.assertEquals(java.util.Arrays.asList(1, 0), score.getGames());
    }
    @Test
    public void testTieBreak() {
        MatchScoreModel score = new MatchScoreModel(matchDto, 40, 0,
                null, null, null,
                java.util.Arrays.asList(6, 6), java.util.Arrays.asList(0, 0)
        );
        service.incrementPlayerScore(score, score.getMatchDto().getFirstPlayer());

        Assertions.assertEquals(0, score.getFirstPlayerScore());
        Assertions.assertEquals(0, score.getSecondPlayerScore());
        Assertions.assertEquals((Integer) 1, score.getTieBreakScoreFirst());
        Assertions.assertEquals(java.util.Arrays.asList(6, 6), score.getGames());
    }

    @Test
    public void testSimpleScoreIncrement() {
        MatchScoreModel score = new MatchScoreModel(matchDto, 0, 0,
                null, null, null,
                java.util.Arrays.asList(0, 0), java.util.Arrays.asList(0, 0));
        service.incrementPlayerScore(score, matchDto.getFirstPlayer());

        Assertions.assertEquals(15, score.getFirstPlayerScore());
        Assertions.assertEquals(0, score.getSecondPlayerScore());
    }

    @Test
    public void testScoreToForty() {
        MatchScoreModel score = new MatchScoreModel(matchDto, 30, 15,
                null, null, null,
                java.util.Arrays.asList(0, 0), java.util.Arrays.asList(0, 0));
        service.incrementPlayerScore(score, matchDto.getFirstPlayer());

        Assertions.assertEquals(40, score.getFirstPlayerScore());
        Assertions.assertEquals(15, score.getSecondPlayerScore());
    }

    @Test
    public void testWinGameWithoutDeuce() {
        MatchScoreModel score = new MatchScoreModel(matchDto, 40, 15,
                null, null, null,
                java.util.Arrays.asList(0, 0), java.util.Arrays.asList(0, 0));
        service.incrementPlayerScore(score, matchDto.getFirstPlayer());

        Assertions.assertEquals(0, score.getFirstPlayerScore());
        Assertions.assertEquals(0, score.getSecondPlayerScore());
        Assertions.assertEquals(java.util.Arrays.asList(1, 0), score.getGames());
    }

    @Test
    public void testDeuceToAdvantage() {
        MatchScoreModel score = new MatchScoreModel(matchDto, 40, 40,
                null, null, null,
                java.util.Arrays.asList(0, 0), java.util.Arrays.asList(0, 0));
        service.incrementPlayerScore(score, matchDto.getFirstPlayer());

        Assertions.assertEquals("1", score.getAdvantage());
        Assertions.assertEquals(40, score.getFirstPlayerScore());
        Assertions.assertEquals(40, score.getSecondPlayerScore());
    }

    @Test
    public void testAdvantageToGameWin() {
        MatchScoreModel score = new MatchScoreModel(matchDto, 40, 40,
                null, null, "1",
                java.util.Arrays.asList(0, 0), java.util.Arrays.asList(0, 0));
        service.incrementPlayerScore(score, matchDto.getFirstPlayer());
        Assertions.assertEquals(0, score.getFirstPlayerScore());
        Assertions.assertEquals(0, score.getSecondPlayerScore());
        Assertions.assertEquals(java.util.Arrays.asList(1, 0), score.getGames());
        Assertions.assertNull(score.getAdvantage());
    }

    @Test
    public void testAdvantageLost() {
        MatchScoreModel score = new MatchScoreModel(matchDto, 40, 40,
                null, null, "1",
                java.util.Arrays.asList(0, 0), java.util.Arrays.asList(0, 0));
        service.incrementPlayerScore(score, matchDto.getSecondPlayer());
        Assertions.assertNull(score.getAdvantage());
        Assertions.assertEquals(40, score.getFirstPlayerScore());
        Assertions.assertEquals(40, score.getSecondPlayerScore());
    }

    @Test
    public void testTieBreakStart() {
        MatchScoreModel score = new MatchScoreModel(matchDto, 40, 0,
                null, null, null,
                java.util.Arrays.asList(6, 6), java.util.Arrays.asList(0, 0));
        service.incrementPlayerScore(score, matchDto.getFirstPlayer());
        Assertions.assertEquals(0, score.getFirstPlayerScore());
        Assertions.assertEquals(0, score.getSecondPlayerScore());
        Assertions.assertEquals((Integer) 1, score.getTieBreakScoreFirst());
        Assertions.assertEquals(java.util.Arrays.asList(6, 6), score.getGames());
    }

    @Test
    public void testTieBreakWin() {
        MatchScoreModel score = new MatchScoreModel(matchDto, 40, 0,
                null, null, null,
                java.util.Arrays.asList(6, 6), java.util.Arrays.asList(0, 0));
        score.setTieBreakScoreFirst(6);
        score.setTieBreakScoreSecond(5);
        service.incrementPlayerScore(score, matchDto.getFirstPlayer());
        Assertions.assertNull(score.getTieBreakScoreFirst());
        Assertions.assertNull(score.getTieBreakScoreSecond());
        Assertions.assertEquals(java.util.Arrays.asList(0, 0), score.getGames());
        Assertions.assertEquals(java.util.Arrays.asList(1, 0), score.getSets());
    }
}
