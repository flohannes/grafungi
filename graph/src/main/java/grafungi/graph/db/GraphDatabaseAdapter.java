package grafungi.graph.db;

import grafungi.graph.Edge;
import grafungi.graph.Graph;
import grafungi.graph.Vertex;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.commons.configuration.BaseConfiguration;

/**
 *
 * @author fschmidt
 */
public interface GraphDatabaseAdapter {

    void connect(String path);
    void connect(BaseConfiguration config);
    void close();
    void checkOpen(String path);

    public Graph cyphre(Graph graph, String query, Map<String, Object> params);

    public Graph gremlin(Graph graph, String query, Map<String, Object> params);

    public Graph sparql(Graph graph, String query);
    
    public void createIndex(Collection<String> propertyKeys, Class objectClass);

    //Simple queries
    public void addVertex(Vertex v);

    public Edge addArc(Vertex s, Vertex d, Map<String,Object> properties);
    public void addArc(Edge a);

    public void remove(Vertex v);

    public void remove(Edge a);
    public void remove(List<Edge> a);

    public boolean existsVertex(String id);
    public boolean existsVertex(Map<String,Object> properties);

    public boolean existsArc(String id);
    public boolean existsArc(Vertex s, Vertex d, Map<String,Object> properties);
    
    public List<Vertex> getVertex(Map<String,Object> properties);
    public Vertex getVertex(String id);
    
    public List<Edge> getArcs(Vertex s, Vertex d, Map<String,Object> properties);
    public List<Edge> getArcs(Map<String,Object> properties);
    
    public void update(Edge a);
    public void update(Vertex v);
    
    public boolean hasIncommingArcs(Vertex v);
    public boolean hasOutgoingArcs(Vertex v);

    public List<Edge> getIncommingArcs(Vertex v);
    public List<Edge> getOutgoingArcs(Vertex v);
    public List<Vertex> getOutgoingArcVertices(Vertex v, Map<String, Object> nextVertexProperties);
    
    public Graph getWholeGraph(String graphName);
    public Graph getSubgraph(String graphName, Vertex root, int hops);
    
    public List<List<Vertex>> getPaths(Vertex s, Vertex t, int k);
    
    public void addGraph(Graph graph);
}
