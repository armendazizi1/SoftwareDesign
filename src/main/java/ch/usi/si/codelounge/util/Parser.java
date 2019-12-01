package ch.usi.si.codelounge.util;

/** A simple Parser interface with one method. */
public interface Parser<T> {
  /**
   * Tries to do the parsing action. Does not throw any exception.
   *
   * @return an instance of the generic type T.
   */
  T tryParse();
}
