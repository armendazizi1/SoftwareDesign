package ch.usi.si.codelounge;


public class App {
  public static void main(String[] args) {

    ParserManager manager = new ParserManager(args);
    boolean success = manager.tryParse();

    if (!success) {
      System.exit(-1);
    }
  }
}
