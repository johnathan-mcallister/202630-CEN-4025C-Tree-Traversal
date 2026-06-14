/**
 * Author: Johnathan McAllister
 * Date: 06-12-26
 * Course: 202630-CEN-4025C
 * Professor: Dr. Mary Walauskis
 *
 * Purpose:
 * - Scan a folder and all subfolders
 * - Build a tree representation of the directory structure
 * - Store folder name, file count, and total file size per node
 * - Represent parent-child folder relationships
 * - Print the directory tree in a readable format
 *
 * Constraints:
 * - Must use recursion for folder traversal
 * - Must use recursion for output formatting
 * - Each node represents a folder only
 * - Must aggregate file counts and sizes per folder
 * - Must handle nested folders of any depth
 */

package cen4025;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class Main {
    public static void main(String[] args) throws Exception{
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        listDir(currentPath, 0);
    }

    public static void listDir(Path path, int depth) throws Exception {
        BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);

        if(attr.isDirectory()) {
            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path);
            if(depth == 0) {
                System.out.println("├─ " + path.getFileName().toString());
            } else {
                System.out.println(spacesForDepth(depth) + "└─ " + path.getFileName().toString() + " <Count:" + Files.list(path).count() + " Size:" + Files.size(path) + "kb>");
            }
            for (Path child : directoryStream) {
                listDir(child, depth + 1);
            }
        } else {
            System.out.println(spacesForDepth(depth) + " ─ " + path.getFileName().toString() + " <Size:" + Files.size(path) + "kb>");
        }
    }

    public static String spacesForDepth(int depth) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            sb.append("  ");
        }
        return sb.toString();
    }
}