package grafungi.graph;

import java.io.Serializable;

/**
 *
 * @author fschmidt
 */
public class Edge extends GraphElement implements Serializable{

    private final Vertex source;
    private final Vertex destination;

    public enum Direction {
        BOTH, INCOMING, OUTGOING, LOOP;
    }
    
    protected Edge(Vertex src, Vertex dest){
        this.source = src;
        this.destination = dest;
    }

    public Vertex getSource() {
        return source;
    }

    public Vertex getDestination() {
        return destination;
    }
    
}
