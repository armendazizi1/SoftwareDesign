package ch.usi.si.codelounge.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * adapted from
 * https://github.com/stlanger/Docear/blob/master/freeplane_plugin_script/src/org/freeplane/plugin/script/UniqueStack.java
 */
public class UniqueStack<T> implements Iterable<T> {

  private ArrayList<T> stack = new ArrayList<>();
  private HashSet<T> set = new HashSet<>();

  /** returns true only if the element was actually added. */
  public boolean push(T t) {
    if (set.add(t)) {
      stack.add(t);
      return true;
    }
    return false;
  }

  public Iterator<T> iterator() {
    return stack.iterator();
  }

  public int size() {
    return stack.size();
  }

  public T peek() {
    return stack.isEmpty() ? null : stack.get(stack.size() - 1);
  }

  public boolean remove(T element) {
    if (stack.isEmpty()) {
      return false;
    } else {
      boolean result = stack.remove(element);
      set.remove(element);
      return result;
    }
  }

  public boolean isEmpty() {
    return size() == 0;
  }

  public String toString() {
    return stack.toString();
  }
}
