import javafx.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

enum TreeNodeType {
    InternalNode,
    LeafNode
}

public abstract class Node implements Serializable {
    //private static final long serialVersionUID = 1L;

    protected int m_way;
    protected ArrayList<Integer> keys;
    protected Node parent;
    protected Node leftSibling;
    protected Node rightSibling;
    protected boolean isRoot;


    public Node(int m_way) {
        this.m_way = m_way;
        this.keys = new ArrayList<>();
        this.parent = null;
        this.leftSibling = null;
        this.rightSibling = null;
        this.isRoot = false;
    }

    //it returns the index of node to be inserted
    abstract public Node split();
    abstract public int search(int key);
    abstract public TreeNodeType getNodeType();

    public int getKey(int index) {
        return keys.get(index);
    }

    // i have to sort whenever i insert
    public void setKey(int index, Integer key) {
        keys.add(index, key);
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public ArrayList<Integer> getKeys() {
        return keys;
    }

    public void setKeys(ArrayList<Integer> keys) {
        this.keys = keys;
    }

    public int getKeyCount() {
        return keys.size();
    }

    public Node getLeftSibling() {
        return leftSibling;
    }

    public void setLeftSibling(Node leftSibling) {
        this.leftSibling = leftSibling;
    }

    public Node getRightSibling() {
        return rightSibling;
    }

    public void setRightSibling(Node rightSibling) {
        this.rightSibling = rightSibling;
    }

    public int getM_way() {
        return m_way;
    }

    public void setM_way(int m_way) {
        this.m_way = m_way;
    }

    //m_way - 1 == Maximum # of nodes
    public boolean isOverFlowed() {
        return (m_way - 1) < keys.size();
    }

    //m_way/2 - 1 == Minimum # of nodes
    public boolean isUnderFlowed() {
        return (m_way / 2 - 1) > keys.size();
    }

    //for distribution
    public boolean canLendAKey() {
        return (m_way / 2 - 1) < (keys.size() - 1);
    }


    //abstract public void dealUnderFlow();
    public Node dealwithOverFlow(){
        //todo when we are dealing with an InternerNode overflow, there's a problem in the split().
        int mid = this.getKeyCount()/2;
        int key = this.getKey(mid);

        Node newRightNode = this.split();

        if (this.getParent() == null) {
            this.setParent(new InternalNode(getM_way()));
            this.getParent().isRoot = true;
            this.isRoot = false;
        }
        newRightNode.setParent(this.getParent());

        //setting siblings

        return ((InternalNode)this.getParent()).pushUp(key, this, newRightNode);
    }

    protected void sort(ArrayList<Integer> keys) {
        Collections.sort(keys, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1> o2) {
                    return -1;
                } else if (o1.equals(o2)) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
    }
}
