package grafungi.graph.io.dot;

/**
 * This class represents an edge in the graph
 *
 * @author Ashraf M. Kibriya (amk14@cs.waikato.ac.nz)
 * @version $Revision: 8034 $ - 23 Apr 2003 - Initial version (Ashraf M. Kibriya)
 */
public class GraphEdge extends Object {

    /**
     * The index of source node in Nodes vector
     */
    public int src;
    /**
     * The index of target node in Nodes vector
     */
    public int dest;
    /**
     * The type of Edge
     */
    public int type;
    /**
     * Label of source node
     */
    public String srcLbl;
    /**
     * Label of target node
     */
    public String destLbl;

    public GraphEdge(int s, int d, int t) {
        src = s;
        dest = d;
        type = t;
        srcLbl = null;
        destLbl = null;
    }

//  public GraphEdge(int s, int d, int t, String sLbl, String dLbl) {
//    src=s; dest=d; type=t;
//    srcLbl = sLbl; destLbl = dLbl;
//  }
    @Override
    public String toString() {
        return ("(" + src + "," + dest + "," + type + ")");
    }

    @Override
    public boolean equals(Object e) {
        return e instanceof GraphEdge && ((GraphEdge) e).src == this.src && ((GraphEdge) e).dest == this.dest && ((GraphEdge) e).type
                == this.type;
    }

} // GraphEdge
