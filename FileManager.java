import java.io.*;
import java.util.StringTokenizer;

public class FileManager  {

    private String indexFilePath;

    public FileManager(String indexFilePath) {
        this.indexFilePath = indexFilePath;
    }

    public void createFile(int mWay) throws IOException {
        PrintWriter printWriter = new PrintWriter(new FileWriter(indexFilePath));
        BTree bTree = new BTree(mWay);
        printWriter.println(mWay);
        System.out.println("The file \""+ indexFilePath +"\" created.");
        printWriter.close();
    }

    public void saveFile(BTree bTree) throws IOException {
        PrintWriter printWriter = new PrintWriter(new FileWriter(indexFilePath, true));

        Node leftMostNode = bTree.root;
        while (leftMostNode.getNodeType() == TreeNodeType.InternalNode) {
            leftMostNode = ((InternalNode)leftMostNode).getChild(0);
        }

        Node startNode = leftMostNode;

        while (startNode != null) {
            for (int i = 0; i < startNode.getKeyCount(); i++) {
                printWriter.println(startNode.keys.get(i) + "," + ((LeafNode) startNode).values.get(i));
            }
            startNode = ((LeafNode) startNode).rightSibling;
        }
        printWriter.close();
    }

    public BTree loadFile() throws IOException, ClassNotFoundException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(indexFilePath));
        String line = bufferedReader.readLine();
        BTree bTree = new BTree(Integer.parseInt(line));

        while ((line = bufferedReader.readLine()) != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(line, ",");
            int key = Integer.parseInt(stringTokenizer.nextToken());
            int value = Integer.parseInt(stringTokenizer.nextToken());
            Node node = bTree.findRightLeafNode(key, 'i');
            bTree.root = ((LeafNode) node).insert(key, value);
        }

        bufferedReader.close();
        return bTree;
    }
}
