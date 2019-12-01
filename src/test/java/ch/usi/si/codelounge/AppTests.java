package ch.usi.si.codelounge;

import org.apache.commons.cli.ParseException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AppTests {

  private IOTestHelper ioTestHelper;

  @Before
  public void setupOutput() {
    ioTestHelper = new IOTestHelper();
  }

  @Test
  public void shouldOutputText() throws ParseException, InvalidFormatException, IOException {
    // arrange
    String[] input = InputTestHelper.generate("F45");

    // act
    App.main(input);

    // assert
    assertThat(ioTestHelper.getOutput()).contains("F45= Total Returning Customers");
  }

  @Test
  public void shouldOutputSum() throws ParseException, InvalidFormatException, IOException {
    // arrange
    String[] input = InputTestHelper.generate("Q42");

    // act
    App.main(input);

    // assert
    assertThat(ioTestHelper.getOutput()).contains("Q42= Q39+Q40+Q41");
  }

  //
  @Test
  public void shouldOutputFormula() throws ParseException, InvalidFormatException, IOException {
    // arrange
    String[] input = InputTestHelper.generate("G35");

    // act
    App.main(input);

    // assert
    assertThat(ioTestHelper.getOutput()).contains("G35= SUM(G10:G34)");
  }

  @Test
  public void shouldOutputComplexFormula()
      throws ParseException, InvalidFormatException, IOException {
    // arrange
    String[] input = InputTestHelper.generate("G10");

    // act
    App.main(input);

    // assert
    assertThat(ioTestHelper.getOutput())
        .contains("G10= IF(AND(G$8>=$C10,G$8<=$C10+$E10-1),$D10*$F10,0)");
  }

  @Test
  public void shouldOutputReference() throws ParseException, InvalidFormatException, IOException {
    // arrange
    String[] input = InputTestHelper.generate("I39");

    // act
    App.main(input);

    // assert
    assertThat(ioTestHelper.getOutput()).contains("I39= H42");
  }
}
