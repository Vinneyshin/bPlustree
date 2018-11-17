import java.util.ArrayList;

public class LeafNode extends Node {

    protected LeafNode rightLeafNode;
    protected ArrayList<Integer> values;

    public LeafNode(int m_way) {
        super(m_way);
        values = new ArrayList<>();
        this.isRoot = false;
    }

    public LeafNode(int m_way, boolean isRoot) {
        super(m_way);
        values = new ArrayList<>();
        // TODO: 9/30/2018 how to deal with root
        this.isRoot = isRoot;
    }

    @Override
    public Node split() {
        //making new leaf node
        rightLeafNode = new LeafNode(this.getM_way());
        //connecting with leaf node

        if (this.getRightSibling() != null) {
            this.getRightSibling().setLeftSibling(rightLeafNode);
        }

        rightLeafNode.setLeftSibling(this);
        rightLeafNode.setRightSibling(this.rightSibling);
        this.setRightSibling(rightLeafNode);

        //parent setting
        //setting parent's node
        ArrayList<Integer> rightNodeKeys = new ArrayList<>();
        ArrayList<Integer> rightNodeValues = new ArrayList<>();

        int nextToMid = this.getM_way()/2 + 1;
        int nextToLast = this.getM_way();
        //i for right next to middle of keys
        //getM_way - 1 for maximum index of keys
        for (int i = nextToMid; i < nextToLast; i++){
            //i used for index getM_way()/2 + 1 because arraylist sorts the elements after removing its element.
            rightNodeKeys.add(this.keys.get(nextToMid));
            rightNodeValues.add(this.values.get(nextToMid));
            this.keys.remove(nextToMid);
            this.values.remove(nextToMid);
        }
        rightLeafNode.setKeys(rightNodeKeys);
        rightLeafNode.setValues(rightNodeValues);

        this.isRoot = false;
        return rightLeafNode;
    }

    public void setValues(ArrayList<Integer> values) {
        this.values = values;
    }

    public int getValue(int index) {
        return values.get(index);
    }

    public void setValue(int index ,int value) {
        this.values.add(index, value);
    }

    @Override
    public TreeNodeType getNodeType() {
        return TreeNodeType.LeafNode;
    }

    @Override
    public int search(int key) {
        int i = 0;
        while (i < getKeyCount() && key > keys.get(i)){
            i++;
        }
        return i;
    }


    //Always returns the root node
    public Node insert(int key, int value) {
        //special case of the root node
        if (isRoot && getKeyCount() == 0) {
            this.keys.add(0, key);
            this.values.add(0,value);
            return this;
        }

        int index = this.search(key);

        this.setKey(index, key);
        this.setValue(index, value);
        if (this.isOverFlowed()) {
            return findRootNode(this.dealwithOverFlow());
        }
        return findRootNode(this);
    }

    public Node findRootNode(Node node){
        while (!(node.getParent() == null)) {
            node = node.getParent();
        }
        return node;
    }
}
