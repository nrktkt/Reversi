package reversi;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * A generic Tree of type T
 *
 * @param <T>
 */
public class Tree<T> {

  private T head;

  //All leaves in the tree
  private ArrayList<Tree<T>> leafs = new ArrayList<Tree<T>>();

  //Parent of a tree
  private Tree<T> parent = null;

  //Hash table used for quickly grabbing nodes.
  private HashMap<T, Tree<T>> locate = new HashMap<T, Tree<T>>();

  //Creates a tree with a head node
  public Tree(T head) {
    this.head = head;
    locate.put(head, this);
  }

  //Inserts a leaf node on a given tree
  public void addLeaf(T root, T leaf) {
    if (locate.containsKey(root)) {
      locate.get(root).addLeaf(leaf);
    } else {
      addLeaf(root).addLeaf(leaf);
    }
  }

  //Creates a leaf node
  public Tree<T> addLeaf(T leaf) {
    Tree<T> t = new Tree<T>(leaf);
    leafs.add(t);
    t.parent = this;
    t.locate = this.locate;
    locate.put(leaf, t);
    return t;
  }

  //Sets the parent of this tree
  public Tree<T> setAsParent(T parentRoot) {
    Tree<T> t = new Tree<T>(parentRoot);
    t.leafs.add(this);
    this.parent = t;
    t.locate = this.locate;
    t.locate.put(head, this);
    t.locate.put(parentRoot, t);
    return t;
  }

  //Gets the root of this tree
  public T getHead() {
    return head;
  }

  //Returns a node within this tree
  public Tree<T> getTree(T element) {
    return locate.get(element);
  }

  //Gets the parent of this tree
  public Tree<T> getParent() {
    return parent;
  }

  //Get all children of this root
  public Collection<T> getSuccessors(T root) {
    Collection<T> successors = new ArrayList<T>();
    Tree<T> tree = getTree(root);
    if (null != tree) {
      for (Tree<T> leaf : tree.leafs) {
        successors.add(leaf.head);
      }
    }
    return successors;
  }

  //Returns all subtrees
  public Collection<Tree<T>> getSubTrees() {
    return leafs;
  }

  //Returns all subtrees of a given subtree
  public static <T> Collection<T> getSuccessors(T of, Collection<Tree<T>> in) {
    for (Tree<T> tree : in) {
      if (tree.locate.containsKey(of)) {
        return tree.getSuccessors(of);
      }
    }
    return new ArrayList<T>();
  }

  @Override
  public String toString() {
    return printTree(0);
  }

  //Used for pretty printing the tree
  private static final int indent = 2;

  //Pretty prints the tree
  private String printTree(int increment) {
    String s = "";
    String inc = "";
    for (int i = 0; i < increment; ++i) {
      inc = inc + " ";
    }
    s = inc + head;
    for (Tree<T> child : leafs) {
      s += "\n" + child.printTree(increment + indent);
    }
    return s;
  }
}