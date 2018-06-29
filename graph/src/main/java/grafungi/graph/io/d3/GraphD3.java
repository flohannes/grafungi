package grafungi.graph.io.d3;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fschmidt
 */
public class GraphD3 implements Serializable{

    private final List<VertexD3> nodes;
    private final List<ArcD3> links;

    public GraphD3() {
        this.nodes = new ArrayList<>();
        this.links = new ArrayList<>();
    }

    public void addVertex(VertexD3 node) {
        this.nodes.add(node);
    }

    public void addArc(ArcD3 arc) {
        this.links.add(arc);
    }

    public List<VertexD3> getNodes() {
        return nodes;
    }

    public List<ArcD3> getLinks() {
        return links;
    }
    
}
