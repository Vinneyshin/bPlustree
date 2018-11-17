import java.util.ArrayList;

public class InternalNode extends Node {

    protected ArrayList<Node> children;


    public InternalNode(int m_way) {
        super(m_way);
        children = new ArrayList<>();
    }

    @Override
    public Node split() {
        int mid = this.getKeyCount()/2;
        int keyCount = getKeyCount();
        InternalNode rightInternerNode = new InternalNode(getM_way());

        for (int i = mid + 1; i < keyCount; i++) {
            rightInternerNode.keys.add(this.keys.get(mid+1));
            this.keys.remove(mid + 1);
        }

        int tmp = 0;
        for (int i = mid + 1; i < keyCount + 1; i++) {
            rightInternerNode.children.add(this.children.get(mid + 1));
            rightInternerNode.children.get(tmp).setParent(rightInternerNode);
            this.children.remove(mid + 1);
            tmp++;
        }

        if (this.getRightSibling() != null) {
            this.getRightSibling().setLeftSibling(rightInternerNode);
        }

        rightInternerNode.setLeftSibling(this);
        rightInternerNode.setRightSibling(this.rightSibling);
        this.setRightSibling(rightInternerNode);

        InternalNode parentNode = new InternalNode(getM_way());
        parentNode.keys.add(this.keys.get(mid));
        this.keys.remove(mid);

        return rightInternerNode;

    }

    /*@Override
    public void dealUnderFlow() {

    }*/

    /*@Override
    public void dealwithOverFlow() {


    }*/

    @Override
    public int search(int key) {
        int i = 0;
        while (i < getKeyCount() && key > keys.get(i)){
            i++;
        }
        return i;
    }

    @Override
    public TreeNodeType getNodeType() {
        return TreeNodeType.InternalNode;
    }

    public Node getChild(int index) {
        return children.get(index);
    }

    public void setChild(int index,Node child) {
        if (children.contains(child)) {
            return;
        }
        this.children.add(index, child);
    }

    public Node pushUp(Integer key, Node leftChild, Node rightChild) {
        //the index that we have to insert
        int index = this.search(key);
        this.insertAt(index, key, leftChild, rightChild);
        if (this.isOverFlowed()) {
            return this.dealwithOverFlow();
        }
        if (this.getParent() == null) {
            return this;
        } else {
            return this.getParent();
        }
    }

    private void insertAt(int index, int key, Node leftChild, Node rightChild) {
        this.setKey(index, key);
        this.setChild(index, leftChild);
        this.setChild(index + 1, rightChild);
    }
}
