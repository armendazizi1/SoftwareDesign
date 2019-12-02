package ch.usi.si.codelounge;

import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.StringLayout;
import org.apache.logging.log4j.core.appender.WriterAppender;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.junit.rules.ExternalResource;

import java.io.CharArrayWriter;

/**
 * JUnit rule for testing output to Log4j. Handy for verifying logging. This sets up and tears down
 * an Appender resource on a given Logger.
 * adapted from https://www.dontpanicblog.co.uk/2018/04/29/test-log4j2-with-junit/
 */
public class LogAppenderResource extends ExternalResource {

  private static final String APPENDER_NAME = "log4jRuleAppender";

  /**
   * Logged messages contains level and message only. This allows us to test that level and message
   * are set.
   */
  private static final String PATTERN = "%-5level %msg";
  private final CharArrayWriter outContent = new CharArrayWriter();
  private Logger logger;
  private Appender appender;

  public LogAppenderResource(org.apache.logging.log4j.Logger logger) {
    this.logger = (org.apache.logging.log4j.core.Logger) logger;
  }

  @Override
  protected void before() {
    StringLayout layout = PatternLayout.newBuilder().withPattern(PATTERN).build();
    appender =
        WriterAppender.newBuilder()
            .setTarget(outContent)
            .setLayout(layout)
            .setName(APPENDER_NAME)
            .build();
    appender.start();
    logger.addAppender(appender);
  }

  @Override
  protected void after() {
    logger.removeAppender(appender);
  }

  public String getOutput() {
    return outContent.toString();
  }
}
