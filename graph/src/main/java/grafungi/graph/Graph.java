package grafungi.graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 *
 * @author fschmidt
 */
public class Graph implements Serializable{

    private final Collection<Vertex> vertices;
    private final Collection<Edge> arcs;
    private final String name;

    public Graph(String name) {
        this.vertices = new ArrayList<>();
        this.arcs = new ArrayList<>();
        this.name = name;
    }
    
    public Graph() {
        this.vertices = new ArrayList<>();
        this.arcs = new ArrayList<>();
        this.name = UUID.randomUUID().toString();
    }

    public void addVertex(Vertex v) {
        this.vertices.add(v);
    }

    public void removeVertex(Vertex v) {
        this.vertices.remove(v);
    }

    public Collection<Vertex> getVertices() {
        return vertices;
    }

    public Edge addEdge(Vertex src, Vertex dest) {
        Edge a = new Edge(src, dest);
        this.arcs.add((Edge) a);
        src.addArc(a);
        dest.addArc(a);
        return a;
    }

    public void removeEdge(Edge a) {
        a.getSource().removeArc(a);
        a.getDestination().removeArc(a);
        this.arcs.remove(a);
    }

    public Collection<Edge> getEdges() {
        return arcs;
    }
    
    public Vertex getVertex(String id){
        for(Vertex v : vertices){
            if(id.equals(v.getId())){
                return v;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }
    
}
