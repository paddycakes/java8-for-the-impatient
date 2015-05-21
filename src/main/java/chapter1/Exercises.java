package chapter1;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Comparator;

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
}
