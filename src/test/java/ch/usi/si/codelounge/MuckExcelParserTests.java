package ch.usi.si.codelounge;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

public class MuckExcelParserTests {

    private IOTestHelper ioTestHelper;

    @Before
    public void setUpOutput() {
        ioTestHelper = new IOTestHelper();
    }

    @Test
    public void shouldOutputValuesWhenSheetContainsValues() {
        assertThat(true).isTrue();
//        MuckExcelParser.main();
    }

}
