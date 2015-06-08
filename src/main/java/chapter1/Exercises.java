package chapter1;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Exercises {

    // 2. Using the listFiles(FileFilter) and isDirectory methods of the java.io.File class,
    //    write a method that returns all subdirectories of a given directory. Use a lambda
    //    expression instead of a FileFilter object. Repeat with a method expression.

    public static File[] getSubdirectories(String directory, boolean withLambda) {
        File parentDirectory = new File(directory);
        if (withLambda) return getSubDirectoriesWithLambda(parentDirectory);
        else return getSubdirectoriesWithMethodReference(parentDirectory);
    }

    private static File[] getSubDirectoriesWithLambda(File directory) {
        // FileFilter filter = (File file) -> file.isDirectory();
        return directory.listFiles( (File file) -> file.isDirectory() );
    }

    private static File[] getSubdirectoriesWithMethodReference(File directory) {
        return directory.listFiles(File::isDirectory);
    }

    // 3. Using the list(FilenameFilter) method of the java.io.File class, write a method that
    //    returns all files in a given directory with a given extension. Use a lambda expression,
    //    not a FilenameFilter. Which variables from the enclosing scope does it capture?

    public static String[] getAllFilesWithGivenExtension(String directory,  String fileType){
        File fileDirectory = new File(directory);
        return fileDirectory.list((File dir, String name) ->  name.endsWith(fileType));
    }

    // The lambda captures fileType from the enclosing scope.


    // 4. Given an array of File objects, sort it so that the directories come before the files, and
    //    within each group, elements are sorted by path name. Use a lambda expression, not a Comparator.

    public static File[] sortDirectoriesBeforeFilesAlphabetically(File[] files) {
        // If both parameters are directories, sort lexigrahically
        // If both parameters are files, sort lexigraphically
        // If different, sort directory first

        Comparator<File> comp = (File f1, File f2) -> {
            if (f1.isDirectory() && !f2.isDirectory()) {
                return -1;
            } else if (!f1.isDirectory() && f2.isDirectory()) {
                return 1;
            }
            return f1.getPath().compareTo(f2.getPath());
        };

        Arrays.sort(files, comp);
        return files;
    }


    // 8.

    public static List<Runnable> getRunnables() {
        String[] names = { "Peter", "Paul", "Mary" };
        List<Runnable> runners = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            runners.add(() -> System.out.println(names));
        }
        return runners;
    }

    // A traditional for loop won't work as the variable i would need to be final
    // and thus could not be mutated.


    // 9. Form a subclass Collection2 from Collection and add a default method
    // void forEachIf(Consumer<T> action, Predicate<T> filter) that applies action to each
    // element for which filter returns true. How could you use it?



    // 11.

    //5. Didn’t you always hate it that you had to deal with checked exceptions in a Runnable?
    // Write a method uncheck that catches all checked exceptions and turns them into unchecked exceptions. For example,
    //new Thread(uncheck(
    //        () -> { System.out.println("Zzz"); Thread.sleep(1000); })).start();
// Look, no catch (InterruptedException)!
  //  Hint: Define an interface RunnableEx whose run method may throw any excep- tions. Then implement public static Runnable uncheck(RunnableEx runner). Use a lambda expression inside the uncheck function.

    public static Runnable uncheck(RunnableEx runner) {
        return () -> {
            try {
                runner.run();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

  //  6. Write a static method andThen that takes as parameters two Runnable instances
  // and returns a Runnable that runs the first, then the second. In the main method,
  // pass two lambda expressions into a call to andThen, and run the returned instance.

    public static Runnable andThen(Runnable first, Runnable second){
        return () -> {
                first.run();
                second.run();
        };
    }


}
