package chapter1;

import java.io.File;

public class Exercises {

    // 2. Using the listFiles(FileFilter) and isDirectory methods of the java.io.File class,
    //    write a method that returns all subdirectories of a given directory. Use a lambda
    //    expression instead of a FileFilter object. Repeat with a method expression.

    public File[] getSubdirectories(String directory, boolean withLambda) {
        File parentDirectory = new File(directory);
        if (withLambda) return getSubDirectoriesWithLambda(parentDirectory);
        else return getSubdirectoriesWithMethodReference(parentDirectory);
    }

    private File[] getSubDirectoriesWithLambda(File directory) {
        return directory.listFiles( (File file) -> file.isDirectory() );
    }

    private File[] getSubdirectoriesWithMethodReference(File directory) {
        return null;
    }
}
