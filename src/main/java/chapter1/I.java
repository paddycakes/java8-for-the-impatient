package chapter1;

/**
 * Created by paddy on 08/06/2015.
 */
public interface I {

    void f();

    default void g() {
        System.out.println("This is the default method of I");
    }

    public static void h() {
        System.out.println("This is the static method of I");
    }
}
