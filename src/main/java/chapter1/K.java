package chapter1;

/**
 * Created by paddy on 08/06/2015.
 */
public class K extends S implements I {

    // This is no clash of method implementations of f() between interface I and
    // superclass S because in this case the superclass implementation always wins.
}
