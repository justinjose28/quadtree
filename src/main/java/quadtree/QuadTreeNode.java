package quadtree;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.ToString;

/**
 * Represents a node in quad tree. A node can further have 4 more nodes.
 *
 * <pre>
 *  -------------------
 * |        |          |
 * |    3   |    1     |
 * |        |          |
 * |--------|----------|
 * |   4    |   2      |
 * |        |          |
 *  -------------------
 *
 * </pre>
 *
 * @author Justin.J
 */
@Data
@ToString(exclude = "parent")
public class QuadTreeNode {
  private QuadTreeNode[] quads;
  private QuadTreeNode parent;
  private int position; // position of the node with in the current hierarchy
  private String nodeId;

  QuadTreeNode() {
    this.nodeId = "root";
  }

  public QuadTreeNode(QuadTreeNode parent, int position) {
    this.parent = parent;
    this.position = position;
    this.nodeId =
        parent.parent == null ? String.valueOf(position) : parent.getNodeId() + "." + position;
  }

  /**
   * Find the west neighbor of this node. West neighbor can be of same, greater or smaller in size.
   */
  public List<QuadTreeNode> getWestNeighbors() {
    // First find the neighbor which is equal or greater in size.
    QuadTreeNode westernNeighborGreaterOrEqualSize = getWestNeighborGreaterOrEqualSize();
    // It is possible that the above neighbor has sub-trees. In that case,traverse through the
    // sub-trees and find possible neighbors.
    return getNeighboursOfSmallSize(westernNeighborGreaterOrEqualSize);
  }

  /** Find west neighbor which is greater or equal in size */
  public QuadTreeNode getWestNeighborGreaterOrEqualSize() {
    // 1 is stored at 0, 2 is stored at 1 and so on
    int indexForPosition = getIndexForPosition(position);
    if (indexForPosition < 2) {
      // For nodeIds ending with 1 & 2, there will be always a west neighbor node which is at
      // least of the same size.
      // If parent is null, we have reached the root node. In that case just return the root node.
      return parent != null ? parent.getQuads()[indexForPosition + 2] : this;
    } else {
      // For nodeIds ending with 3 & 4, west neighbor node can be of same size or greater size.
      // First find the west neighbor of the parent. This will give the west neighbor of greater
      // size
      QuadTreeNode parentsWestNeighbour = this.parent.getWestNeighborGreaterOrEqualSize();
      if (parentsWestNeighbour.parent != null && parentsWestNeighbour.getQuads() != null) {
        // If parentsWestNeighbour has sub-trees, it means that we will have a west neighbor node
        // of the same size.
        // For eg: For nodeId ending with 3, west node always end with 1. For nodeId ending with
        // 4, west node always end with 2
        return parentsWestNeighbour.getQuads()[indexForPosition - 2];
      } else {
        return parentsWestNeighbour;
      }
    }
  }

  /**
   * If a west neighbor node of same or greater size has sub-trees, we need to further drill down
   * and find the neighbors from the sub trees.
   *
   * <pre>
   *  -----------------------
   * |           |          |
   * |    3      |    1     |
   * |           |          |
   * |-----------|----------|
   * |     |     |          |
   * | 4.3 | 4.1 |          |
   * |---- |-----|   2      |
   * | 4.4 | 4.2 |          |
   * |     |     |          |
   *  ----------------------
   *
   * </pre>
   *
   * In the above tree, for node 2, west neighbor of same size is 4. But, 4 has sub-trees and the
   * most apt west neighbors are 4.1 & 4.2 which are at a smaller level.
   */
  public List<QuadTreeNode> getNeighboursOfSmallSize(QuadTreeNode westernNeighbourNode) {
    List<QuadTreeNode> neighbourList = new ArrayList<>();
    addWesternNeighboursToList(neighbourList, westernNeighbourNode);
    return neighbourList;
  }

  /** Get the child-node at the specified position. Return null if none exists. */
  public QuadTreeNode getChildNodeAt(int position) {
    if (quads == null) {
      return null;
    }
    return quads[getIndexForPosition(position)];
  }

  /** Create a sub-tree(quad-tree) inside the current node. */
  public void createSubTree() {
    this.quads =
        new QuadTreeNode[] {
          new QuadTreeNode(this, 1),
          new QuadTreeNode(this, 2),
          new QuadTreeNode(this, 3),
          new QuadTreeNode(this, 4)
        };
  }

  /** Get the child-node at the specified position if it already exists. Else, create one. */
  public QuadTreeNode getOrCreateChildNode(int position) {
    QuadTreeNode nodeAtRequestedPos = getChildNodeAt(position);
    if (nodeAtRequestedPos == null) {
      createSubTree();
      nodeAtRequestedPos = getChildNodeAt(position);
    }
    return nodeAtRequestedPos;
  }

  private void addWesternNeighboursToList(
      List<QuadTreeNode> neighbourList, QuadTreeNode westernNeighbourNode) {
    if (westernNeighbourNode.getParent() != null && westernNeighbourNode.getQuads() != null) {
      // West neighbors will be always from the position 1 & 2.
      addWesternNeighboursToList(neighbourList, westernNeighbourNode.getQuads()[0]);
      addWesternNeighboursToList(neighbourList, westernNeighbourNode.getQuads()[1]);
    } else {
      neighbourList.add(westernNeighbourNode);
    }
  }

  private int getIndexForPosition(int position) {
    return position - 1;
  }
}
