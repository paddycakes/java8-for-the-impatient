package chapter1;

import org.junit.Test;

import java.io.File;
import java.util.List;
import java.io.IOException;

import static chapter1.Exercises.andThen;
import static chapter1.Exercises.uncheck;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;


public class ExercisesTest {

    // 2. Using the listFiles(FileFilter) and isDirectory methods of the java.io.File class,
    //    write a method that returns all subdirectories of a given directory. Use a lambda
    //    expression instead of a FileFilter object. Repeat with a method expression.


    // 4. Given an array of File objects, sort it so that the directories come before the files, and
    //    within each group, elements are sorted by path name. Use a lambda expression, not a Comparator.


    @Test
    public void testLambdaCapture() {
        List<Runnable> runnableList = Exercises.getRunnables();
        for (Runnable runnable : runnableList) {
            new Thread(runnable).start();
        }
    }


    /*  6.
         Didn’t you always hate it that you had to deal with checked exceptions in a Runnable? Write a method uncheck that catches all checked exceptions and turns them into unchecked exceptions. For example,
         new Thread(uncheck(
            () -> { System.out.println("Zzz"); Thread.sleep(1000); })).start();
                   // Look, no catch (InterruptedException)!
          Hint: Define an interface RunnableEx whose run method may throw any excep- tions. Then implement public static Runnable uncheck(RunnableEx runner). Use a lambda expression inside the uncheck function.
    */
    @Test
    public void shouldConvertCheckedToUncheckedExceptionWhenCallingUncheck()  {
        new Thread(uncheck(
                () -> { System.out.println("Zzz"); Thread.sleep(1000); })).start();
            // Look, no catch (InterruptedException)!

        try {
            new Thread(uncheck(
                    () -> {
                        throw new IOException("BANG!");
                    })).start();
        } catch (Exception result){
          assertThat(result, instanceOf(RuntimeException.class));
        }

    }


    // 7. Write a static method andThen that takes as parameters two Runnable instances and returns a Runnable that runs the first,
    // then the second. In the main method, pass two lambda expressions into a call to andThen, and run the returned instance.
    @Test
    public void shouldCombine2RunnablesWhenCallingAndThen() {
        Runnable result = andThen(() -> {
                    System.out.println("runnable First");
                },
                () -> {
                    System.out.println("runnable Second");
                });

        assertThat(result, is(notNullValue()));
        assertThat(result, instanceOf(Runnable.class));

        new Thread(result).start();
    }




}
