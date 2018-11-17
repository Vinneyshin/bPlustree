import java.io.*;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) {
        String command = args[0];
        String index_file = args[1];

        FileManager fileManager = new FileManager(index_file);

        switch (command){

            case"-c":
                try {
                    // args[2] == b (which is # of child nodes, in a word, mway
                    fileManager.createFile(Integer.parseInt(args[2]));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case"-i":
                try {
                    // args[1] == index_file
                    // args[2] == data_file.csv
                    BTree bTree = fileManager.loadFile();
                    bTree.insert(args[2]);
                    fileManager.saveFile(bTree);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;

            case"-d":
                //Command: program -d index_file delete_file
                // args[1] == index_file
                // args[2] == delete_file.csv
                try {
                    BTree bTree = fileManager.loadFile();
                    bTree.delete(args[2]);
                    fileManager.saveFile(bTree);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;

            case"-s":
                //Command: program -s index_file key
                // args[1] == index_file
                // args[2] == key
                try {
                    BTree bTree = fileManager.loadFile();
                    bTree.search(Integer.parseInt(args[2]));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;

            case "-r":
                //Command: program -r index_file start_key end_key
                // args[1] == index_file
                // args[2] == start_key
                // args[3] == end_key
                try {
                    BTree bTree = fileManager.loadFile();
                    bTree.search(Integer.parseInt(args[2]), Integer.parseInt(args[3]));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            default:
                break;

        }
    }
}
