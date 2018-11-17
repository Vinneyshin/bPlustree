import java.io.*;
import java.security.Key;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BTree implements Serializable {

    private int mWay;
    private int startKey;
    private int endKey;
    public Node root;


    public BTree(int mWay) {
        this.mWay = mWay;
        this.root = new LeafNode(mWay, true);
    }

    //Command: program -i index_file data_file
    //Using pre-order traversal
    public void insert(String dataFile) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(dataFile));
        String line = null;

        while ((line = bufferedReader.readLine()) != null) {
            //using StringTokenizer to parse the key and value.
            StringTokenizer stringTokenizer = new StringTokenizer(line, ",");
            int key = Integer.parseInt(stringTokenizer.nextToken());
            int value = Integer.parseInt(stringTokenizer.nextToken());
            Node node = findRightLeafNode(key, 'i');
            root = ((LeafNode) node).insert(key, value);
        }
    }

    //Command: program -d index_file delete_file
    public void delete(String dataFile) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(dataFile));
        String line = null;

        while ((line = bufferedReader.readLine()) != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(line, ",");
            int key = Integer.parseInt(stringTokenizer.nextToken());
            int value = Integer.parseInt(stringTokenizer.nextToken());
        }
    }

    //Command: program -s index_file key
    public void search(int key){
        Node node = findRightLeafNode(key, 's');

        int index = node.search(key);

        if (node.keys.get(index) != key) {
            System.out.println("NOT FOUND");
        } else {
            System.out.println(((LeafNode) node).values.get(index));
        }
    }

    //Command: program -r index_file start_key end_key
    public void search(int startKey, int endKey) {
        Node startNode = findRightLeafNode(startKey, 'r');
        Node endNode = findRightLeafNode(endKey, 'r');
        int index = startNode.search(startKey);
        for (int i = index; i < startNode.getKeyCount(); i++) {
            System.out.println(startNode.keys.get(i) + ","  + ((LeafNode) startNode).values.get(i));
        }
        startNode = ((LeafNode) startNode).rightSibling;
        while (startNode != endNode) {
            for (int i = 0; i < startNode.getKeyCount(); i++) {
                System.out.println(startNode.keys.get(i) + "," + ((LeafNode) startNode).values.get(i));
            }
            startNode = ((LeafNode) startNode).rightSibling;
        }
        index = startNode.search(endKey);
        for (int i = 0; i < index + 1; i++) {
            System.out.println(startNode.keys.get(i) + "," + ((LeafNode) startNode).values.get(i));
        }
    }

    public LeafNode findRightLeafNode(int key, char mode) {
        Node node = root;
        if (mode == 's') {
            while (node.getNodeType() == TreeNodeType.InternalNode) {
                for (int i = 0; i < node.getKeyCount(); i++) {
                    if (i == node.getKeyCount() -1){
                        System.out.print(node.keys.get(i));
                        break;
                    }
                    System.out.print(node.keys.get(i) + ",");
                }
                System.out.println();
                node = ((InternalNode) node).getChild(node.search(key));
            }
        } else {
            while (node.getNodeType() == TreeNodeType.InternalNode) {
                node = ((InternalNode) node).getChild(node.search(key));
            }
        }

        return (LeafNode) node;
    }

    @Override
    public String toString() {
        return "BTree{" +
                "mWay=" + mWay +
                ", startKey=" + startKey +
                ", endKey=" + endKey +
                '}';
    }
}
