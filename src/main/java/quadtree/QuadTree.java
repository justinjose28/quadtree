package quadtree;

import static java.lang.Integer.parseInt;

import java.util.List;
import lombok.Data;

@Data
public class QuadTree {

  private QuadTreeNode root = new QuadTreeNode();

  public QuadTree() {
    root.createSubTree();
  }

  /**
   * Create a new tree inside each of the specified nodeIds.
   *
   * <ul>
   *   <li>If nodeId is 1, create 1.1,1.2,1.3,1.4.
   *   <li>If nodeId is 1.1, create 1.1.1,1.1.2,1.1.3,1.1.4.
   * </ul>
   */
  public void createSubTree(String... nodeIds) {
    for (String nodeId : nodeIds) {
      String[] nodeIdSplitArr = nodeId.split("\\.");
      QuadTreeNode tempRoot = root;
      for (String nodeIdSplit : nodeIdSplitArr) {
        // Get the child node specified by nodeIdSplit. If nodeIdSplit is 1, return child at
        // position 1 of the root.
        tempRoot = tempRoot.getOrCreateChildNode(parseInt(nodeIdSplit));
        // If there is no tree defined for tempRoot, create a new tree. For eg: If nodeIdSplit is
        // 1 and there are no trees already defined for 1, create a new tree.
        if (tempRoot.getQuads() == null) {
          tempRoot.createSubTree();
        }
      }
    }
  }

  /**
   * Find the west neighbor of the specified nodeId. Basically, it is the node which is to the
   * immediate left of the specified nodeId. The size of the neighbor can be equal, greater or
   * smaller than the specified node.
   */
  public List<QuadTreeNode> getWestNeighbour(String nodeId) {
    // Find the specified node by traversing from root
    String[] nodeIdSplitArr = nodeId.split("\\.");
    QuadTreeNode tempRoot = root;
    for (String nodeIdSplit : nodeIdSplitArr) {
      tempRoot = tempRoot.getChildNodeAt(parseInt(nodeIdSplit));
    }
    return tempRoot.getWestNeighbors();
  }

  /** Print all nodeIds with in the quad tree. */
  public void printNodeIds() {
    System.out.println(root.getNodeId());
    printNodeIds(root.getQuads());
  }

  private void printNodeIds(QuadTreeNode[] nodeArr) {
    if (nodeArr != null) {
      for (QuadTreeNode node : nodeArr) {
        System.out.println(node.getNodeId());
        printNodeIds(node.getQuads());
      }
    }
  }
}
