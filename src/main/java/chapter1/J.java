package chapter1;

/**
 * Created by paddy on 08/06/2015.
 */
public interface J {

    void f();

    default void g() {
        System.out.println("This is the default method of J");
    }

    public static void h() {
        System.out.println("This is the static method of J");
    }
}
