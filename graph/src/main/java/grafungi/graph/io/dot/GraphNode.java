package grafungi.graph.io.dot;

/**
 * This class represents a node in the Graph.
 *
 * @author Ashraf M. Kibriya (amk14@cs.waikato.ac.nz)
 * @version $Revision: 8034 $ - 23 Apr 2003 - Initial version (Ashraf M. Kibriya)
 */
public class GraphNode {

    /**
     * Types of Edges
     */
    int DIRECTED = 1, REVERSED = 2, DOUBLE = 3;

    //Node types
    /**
     * SINGULAR_DUMMY node - node with only one outgoing edge i.e. one which represents a single edge and is inserted to close a gap
     */
    int SINGULAR_DUMMY = 1;
    /**
     * PLURAL_DUMMY node - node with more than one outgoing edge i.e. which represents an edge split and is inserted to close a gap
     */
    int PLURAL_DUMMY = 2;
    /**
     * NORMAL node - node actually contained in graphs description
     */
    int NORMAL = 3;

    /**
     * ID and label for the node
     */
    public String ID, lbl;
    /**
     * The outcomes for the given node
     */
    public String[] outcomes;
    /**
     * probability table for each outcome given outcomes of parents, if any
     */
    public double[][] probs;   //probabilities
    /**
     * The x and y position of the node
     */
    public int x = 0, y = 0;
    /**
     * The indices of parent nodes
     */
    public int[] prnts;       //parent nodes
    /**
     * The indices of nodes to which there are edges from this node, plus the type of edge
     */
    public int[][] edges;
    /**
     * Type of node. Default is Normal node type
     */
    public int nodeType = NORMAL;

    /**
     * Constructor
     *
     * @param id
     * @param label
     */
    public GraphNode(String id, String label) {
        ID = id;
        lbl = label;
        nodeType = NORMAL;
    }

    /**
     * Constructor
     *
     */
//  public GraphNode(String id, String label, int type ) {
//    ID = id; lbl = label; nodeType = type;
//  }
    /**
     * Returns true if passed in argument is an instance of GraphNode and is equal to this node. Implemented to enable the use of contains
     * method in Vector/FastVector class.
     */
    @Override
    public boolean equals(Object n) {
        return n instanceof GraphNode && ((GraphNode) n).ID.equalsIgnoreCase(this.ID); //System.out.println("returning true, n.ID >"+((GraphNode)n).ID+
        //                   "< this.ID >"+this.ID+"<");
    }
} // GraphNode
