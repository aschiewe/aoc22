package day6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

public class Main {
    private static final int PACKAGE_MARKER_SIZE = 4;
    private static final int MESSAGE_MARKER_SIZE = 14;


    public static void main(String[] args) throws IOException {

//        String fileName = "in/6-example.txt";
        String fileName = "in/6.txt";
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line = reader.readLine();
        char[] array = line.toCharArray();
        HashSet<Character> controlSet = new HashSet<>();
        boolean foundPackageStarter = false;
        for (int index = PACKAGE_MARKER_SIZE-1; index < array.length; index++) {
            // Parse for part 1, the package marker
            if (!foundPackageStarter) {
                controlSet.clear();
                for (int subIndex = index; subIndex > index - PACKAGE_MARKER_SIZE; subIndex--) {
                    controlSet.add(array[subIndex]);
                }
                if (controlSet.size() == PACKAGE_MARKER_SIZE) {
                    System.out.println("Found start of package marker " + (index+1));
                    foundPackageStarter = true;
                }
            }
            // Parse for part 2, the message marker
            controlSet.clear();
            if (index >= MESSAGE_MARKER_SIZE) {
                for (int subIndex = index; subIndex > index-MESSAGE_MARKER_SIZE; subIndex--) {
                    controlSet.add(array[subIndex]);
                }
                if (controlSet.size() == MESSAGE_MARKER_SIZE) {
                    System.out.println("Found start of message marker: " + (index+1));
                    break;
                }
            }
        }
    }
}
