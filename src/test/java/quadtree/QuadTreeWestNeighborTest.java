package quadtree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;

public class QuadTreeWestNeighborTest {

  /**
   *
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
   * 1 should give 3 <br>
   * 2 should give 4 <br>
   * 3 should give root <br>
   * 4 should give root <br>
   */
  @Test
  public void test1() {
    QuadTree quadTree = new QuadTree();
    assertTrue(quadTree, "1", "3");
    assertTrue(quadTree, "2", "4");
    assertTrue(quadTree, "3", "root");
    assertTrue(quadTree, "4", "root");
  }

  /**
   *
   *
   * <pre>
   *  ---------------------
   * |        | 1.3 |  1.1 |
   * |    3   |-----|------|
   * |        | 1.4 |  1.2 |
   * |--------|------------|
   * |   4    |     2      |
   * |        |            |
   *  ---------------------
   *
   * </pre>
   *
   * 1.1 should give 1.3 <br>
   * 1.2 should give 1.4 <br>
   * 1.3 should give 3 <br>
   * 1.4 should give 3 <br>
   * 2 should give 4 <br>
   */
  @Test
  public void test2() {
    QuadTree quadTree = new QuadTree();
    quadTree.createSubTree("1");
    assertTrue(quadTree, "1.1", "1.3");
    assertTrue(quadTree, "1.2", "1.4");
    assertTrue(quadTree, "1.3", "3");
    assertTrue(quadTree, "1.4", "3");
    assertTrue(quadTree, "2", "4");
  }

  /**
   *
   *
   * <pre>
   *  --------------------------
   * | 3.3  | 3.1  | 1.3 |  1.1 |
   * |------|------|-----|------|
   * | 3.4  | 3.2  | 1.4 |  1.2 |
   * |-------------|------------|
   * |   4         |     2      |
   * |             |            |
   *  ---------------------------
   *
   * </pre>
   *
   * 1.1 should give 1.3 <br>
   * 1.2 should give 1.4 <br>
   * 1.3 should give 3.1 <br>
   * 1.4 should give 3.2 <br>
   * 2 should give 4 <br>
   */
  @Test
  public void test3() {
    QuadTree quadTree = new QuadTree();
    quadTree.createSubTree("1", "3");
    assertTrue(quadTree, "1.1", "1.3");
    assertTrue(quadTree, "1.2", "1.4");
    assertTrue(quadTree, "1.3", "3.1");
    assertTrue(quadTree, "1.4", "3.2");
    assertTrue(quadTree, "2", "4");
  }

  /**
   *
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
   * 2 should give [4.1,4.2] <br>
   * 4.1 should give 4.3 <br>
   * 4.2 should give 4.4 <br>
   */
  @Test
  public void test4() {
    QuadTree quadTree = new QuadTree();
    quadTree.createSubTree("4");
    assertTrue(quadTree, "2", "4.1,4.2");
    assertTrue(quadTree, "4.1", "4.3");
    assertTrue(quadTree, "4.2", "4.4");
  }

  /**
   *
   *
   * <pre>
   *  -------------------------------------
   * |                      |              |
   * |    3                 |     1        |
   * |                      |              |
   * |----------------------|--------------|
   * |     |       |        |     |        |
   * | 4.3 | 4.1.3 | 4.1.1  |     |        |
   * |     |-------|--------| 2.3 |  2.1   |
   * |     | 4.1.4 | 4.1.2  |     |        |
   * |----------------------|--------------|
   * |     |                |     |        |
   * | 4.4 |     4.2        | 2.4 |  2.2   |
   * |     |                |     |        |
   *  --------------------------------------
   *
   * </pre>
   *
   * 1 should give 3 <br>
   * 2.1 should give 2.3 <br>
   * 2.2 should give 2.4 <br>
   * 2.4 should give 4.2 <br>
   * 4.2 should give 4.4 <br>
   * 2.3 should give [4.1.1,4.1.2] <br>
   * 4.1.1 should give 4.1.3 <br>
   * 4.1.3 should give 4.3 <br>
   */
  @Test
  public void test5() {
    QuadTree quadTree = new QuadTree();
    quadTree.createSubTree("2", "4", "4.1");
    assertTrue(quadTree, "1", "3");
    assertTrue(quadTree, "2.1", "2.3");
    assertTrue(quadTree, "2.2", "2.4");
    assertTrue(quadTree, "2.4", "4.2");
    assertTrue(quadTree, "4.2", "4.4");
    assertTrue(quadTree, "2.3", "4.1.1,4.1.2");
    assertTrue(quadTree, "4.1.1", "4.1.3");
    assertTrue(quadTree, "4.1.3", "4.3");
  }

  /**
   *
   *
   * <pre>
   * -------------------|--------------------------------
   * |                  |       |        |              |
   * |                  | 1.3.3 | 1.3.1  |              |
   * |                  |-------|--------|     1.1      |
   * |                  | 1.3.4 | 1.3.2  |              |
   * |                  |       |        |              |
   * |         3        |----------------|--------------|
   * |                  |                |              |
   * |                  |                |              |
   * |                  |      1.4       |      1.2     |
   * |                  |                |              |
   * |                  |                |              |
   * |                  |                |              |
   * |--------------------------------------------------|
   * |                  |                               |
   * |        4         |             2                 |
   * |                  |                               |
   * -----------------------------------------------------
   *
   * </pre>
   *
   * 1.1 should give [1.3.1,1.3.2] <br>
   * 1.3.1 should give 1.3.3 <br>
   * 1.3.2 should give 1.3.4 <br>
   * 1.3.3 should give 3 <br>
   * 1.3.4 should give 3 <br>
   * 1.4 should give 3 <br>
   * 1.2 should give 1.4 <br>
   * 2 should give 4 <br>
   * 4 should give root <br>
   */
  @Test
  public void test6() {
    QuadTree quadTree = new QuadTree();
    quadTree.createSubTree("1", "1.3");
    assertTrue(quadTree, "1.1", "1.3.1,1.3.2");
    assertTrue(quadTree, "1.3.1", "1.3.3");
    assertTrue(quadTree, "1.3.2", "1.3.4");
    assertTrue(quadTree, "1.3.3", "3");
    assertTrue(quadTree, "1.3.4", "3");
    assertTrue(quadTree, "1.4", "3");
    assertTrue(quadTree, "1.2", "1.4");
    assertTrue(quadTree, "2", "4");
    assertTrue(quadTree, "4", "root");
  }

  private void assertTrue(QuadTree quadTree, String nodeId, String expectedNeighbour) {
    List<QuadTreeNode> node = quadTree.getWestNeighbour(nodeId);
    assertNotNull(node);
    String neighbourNodeIds =
        node.stream().map(QuadTreeNode::getNodeId).collect(Collectors.joining(","));
    assertEquals(expectedNeighbour, neighbourNodeIds);
  }
}
