package day7;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FileSystem {

    /**
     * Store each content by its fully qualified path
     */
    private final Map<String, FileSystemObject> system;

    public FileSystem() {
        system = new HashMap<>();
        system.put("/", new Directory("/"));
    }

    public Collection<FileSystemObject> getObjects() {
        return this.system.values();
    }

    public FileSystemObject getObject (String path) {
        return this.system.get(path);
    }


    public static FileSystem parse(String inputFile) throws IOException {
        FileSystem system = new FileSystem();
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String line;
        String currentPath = "/";
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(" ");
            if (values[0].equals("$")) {
                // Command
                // Only need to do something for `cd`
                if (values[1].equals("cd")) {
                    switch (values[2]) {
                        case "/" -> currentPath = "/";
                        case ".." -> currentPath = currentPath.substring(0, currentPath.lastIndexOf("/"));
                        default -> currentPath = getNewPath(currentPath, values[2]);
                    }
                }
            }
            else {
                // Listing of files
                // We should always be in a directory, otherwise something went wrong
                Directory currentDir = (Directory) system.system.get(currentPath);
                // This is only save if a directory is only listed once, otherwise this will duplicate things. But
                // this is the case for the input data
                FileSystemObject newObject;
                if (values[0].equals("dir")) {
                    newObject = new Directory(values[1]);
                }
                else {
                    newObject = new File(values[1], Integer.parseInt(values[0]));
                }
                currentDir.addEntry(newObject);
                system.system.put(getNewPath(currentPath, values[1]), newObject);
            }
        }
        return system;
    }

    private static String getNewPath(String currentPath, String newName) {
        if (currentPath.equals("/")) {
            return currentPath + newName;
        } else {
            return currentPath + "/" + newName;
        }
    }




    interface FileSystemObject {
        int getSize();
        String getName();

        boolean isDir();
    }

    public static class File implements FileSystemObject {
        private final String name;
        private final int size;

        public File (String name, int size) {
            this.name = name;
            this.size = size;
        }

        @Override
        public int getSize() {
            return size;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public boolean isDir() {
            return false;
        }
    }

    public static class Directory implements FileSystemObject{
        private final List<FileSystemObject> content;
        private final String name;
        // Cache the size
        private int size = -1;

        public Directory(String name) {
            this.content = new ArrayList<>();
            this.name = name;
        }

        public void addEntry(FileSystemObject object) {
            this.content.add(object);
            // Invalidate cache
            this.size = -1;
        }

        @Override
        public int getSize() {
            if (size != -1) {
                return size;
            }
            size = content.stream().mapToInt(FileSystemObject::getSize).sum();
            return size;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public boolean isDir() {
            return true;
        }
    }
}
