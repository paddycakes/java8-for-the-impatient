package chapter1;

/**
 * Created by paddy on 08/06/2015.
 */
public class P implements I, J {

    @Override
    public void f() {
        System.out.println("Both interfaces have the same abstract method. But you " +
                "implement one method with the same name so therefore no conflict.");
    }

    // You need to explicitly resolve a default method clash by overriding
    @Override
    public void g() {
        I.super.g();;
    }

    // The identically named static methods from each interface are distinguished by the type used for invocation.
    public void testStatic() {
        J.h();
        I.h();
    }


}
