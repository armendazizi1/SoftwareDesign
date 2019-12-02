package ch.usi.si.codelounge;

import ch.usi.si.codelounge.excel.ExcelParser;
import org.apache.logging.log4j.LogManager;
import org.junit.Rule;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTests {

  @Rule
  public LogAppenderResource appender =
      new LogAppenderResource(LogManager.getLogger(ExcelParser.class.getName()));

  @Test
  public void shouldOutputText() {
    // arrange
    String[] input = InputTestHelper.generate("F45");

    // act
    App.main(input);

    // assert
    assertThat(appender.getOutput()).contains("F45= Total Returning Customers");
  }

  @Test
  public void shouldOutputSum() {
    // arrange
    String[] input = InputTestHelper.generate("Q42");

    // act
    App.main(input);

    // assert
    assertThat(appender.getOutput()).contains("Q42= Q39+Q40+Q41");
  }

  @Test
  public void shouldOutputFormula() {
    // arrange
    String[] input = InputTestHelper.generate("G35");

    // act
    App.main(input);

    // assert
    assertThat(appender.getOutput()).contains("G35= SUM(G10:G34)");
  }

  @Test
  public void shouldOutputComplexFormula() {
    // arrange
    String[] input = InputTestHelper.generate("G10");

    // act
    App.main(input);

    // assert
    assertThat(appender.getOutput())
        .contains("G10= IF(AND(G$8>=$C10,G$8<=$C10+$E10-1),$D10*$F10,0)");
  }

  @Test
  public void shouldOutputReference() {
    // arrange
    String[] input = InputTestHelper.generate("I39");

    // act
    App.main(input);

    // assert
    assertThat(appender.getOutput()).contains("I39= H42");
  }
}
