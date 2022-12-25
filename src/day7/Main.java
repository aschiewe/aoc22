package day7;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final int MAX_SIZE_PART_1 = 100000;
    private static final int FILESYSTEM_SIZE = 70000000;
    private static final int NEEDED_SPACE = 30000000;

    public static void main(String[] args) throws IOException {
//        FileSystem system = FileSystem.parse("in/7-example.txt");
        FileSystem system = FileSystem.parse("in/7.txt");

        int sumOfSizes = 0;
        for (FileSystem.FileSystemObject object: system.getObjects()) {
            if (object.isDir() && object.getSize() <= MAX_SIZE_PART_1) {
                sumOfSizes += object.getSize();
            }
        }
        System.out.println("Sum of sizes of dirs with max size " + MAX_SIZE_PART_1 + ": " + sumOfSizes);

        // Onto part two
        int usedSpace = system.getObject("/").getSize();
        int minSizeToDelete = NEEDED_SPACE - (FILESYSTEM_SIZE - usedSpace);
        List<Integer> objectsPossibleToDelete = new ArrayList<>();

        for (FileSystem.FileSystemObject object: system.getObjects()) {
            if (object.isDir() && object.getSize() >= minSizeToDelete) {
                objectsPossibleToDelete.add(object.getSize());
            }
        }

        System.out.println("Dir size of directory to delete: " + objectsPossibleToDelete.stream().mapToInt(Integer::intValue).min().getAsInt());
    }
}
