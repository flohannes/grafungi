package grafungi.graph.db;

import com.google.common.collect.Lists;
import com.thinkaurelius.titan.core.PropertyKey;
import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import com.thinkaurelius.titan.core.schema.TitanGraphIndex;
import com.thinkaurelius.titan.core.schema.TitanManagement;
import grafungi.graph.Edge;
import grafungi.graph.Graph;
import grafungi.graph.Vertex;
import grafungi.graph.db.util.Blob;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.logging.Logger;
import org.apache.commons.configuration.BaseConfiguration;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.both;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.otherV;
import org.apache.tinkerpop.gremlin.process.traversal.step.util.ImmutablePath;
import org.apache.tinkerpop.gremlin.structure.Direction;
import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.is;
import org.apache.tinkerpop.gremlin.structure.Property;

/**
 *
 * @author fschmidt
 */
public class TitanConnector implements GraphDatabaseAdapter {

    private static final Logger LOG = Logger.getLogger(TitanConnector.class.getName());

    private final String DEFAULT_LABEL = "to";
    private final String TITAN_DB_ID = "titan-db-id";
    private final String BLOB = "BLOB";
    private TitanGraph g;
    private boolean autoIndexing = true;
    private GraphDBAutoIndexing indexing;

    @Override
    public void connect(String path) {
        BaseConfiguration conf = new BaseConfiguration();
        conf.setProperty("storage.backend", "berkeleyje");
        conf.setProperty("storage.transactions", false);
//        conf.setProperty("storage.berkeleydb.lock-mode", LockMode.RMW);
//        conf.setProperty("storage.berkeleydb.isolation-level", BerkeleyJEStoreManager.IsolationLevel.SERIALIZABLE);
        conf.setProperty("storage.directory", path);
//        conf.setProperty("storage.parallel-backend-ops", false);
//        conf.setProperty("storage.lock.wait-time", 2000);
        this.g = TitanFactory.open(conf);
        //TODO: when start copy existing idexies from db to GraphDBAutoIndexing.
        Map<Collection<String>, Class> indecies = getIndecies();
        this.indexing = new GraphDBAutoIndexing(indecies);

        checkForIndexing(Arrays.asList("id"), Vertex.class);
//        createNewIndex(Arrays.asList("id"), Edge.class);
    }

    @Override
    public void connect(BaseConfiguration config) {
        this.g = TitanFactory.open(config);
        //TODO: when start copy existing idexies from db to GraphDBAutoIndexing.
        this.indexing = new GraphDBAutoIndexing();
    }

    @Override
    public void checkOpen(String path) {
        if (this.g.isClosed()) {
            System.out.println("DB closed. Reconnect! " + path);
            connect(path);
            if(!this.g.tx().isOpen()){
                this.g.tx().open();
            }
        }
    }

    @Override
    public void close() {
        this.g.close();
        this.indexing = null;
    }

    @Override
    public Graph cyphre(Graph graph, String query, Map<String, Object> params) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Graph gremlin(Graph graph, String query, Map<String, Object> params) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        try {
//            GremlinGroovyScriptEngine engine = new GremlinGroovyScriptEngine();
//
//            Bindings bindings = engine.createBindings();
//            bindings.put("g", this.g);
//            bindings.putAll(params);
//
//            Object results = engine.eval(
//                    "g.V('name', 'a').next().query().has('b', Compare.GREATER_THAN_EQUAL, value).orderBy('timestamp', DESC).edges()",
//                    bindings);
//        } catch (ScriptException ex) {
//            Logger.getLogger(TitanConnector.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
    }

    @Override
    public Graph sparql(Graph graph, String query) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(Vertex v) {
        this.g.tx().rollback();
        List<org.apache.tinkerpop.gremlin.structure.Vertex> vertices = this.g.traversal().V().has("id", v.getId()).toList();
        if (!vertices.isEmpty()) {
            org.apache.tinkerpop.gremlin.structure.Vertex vertex = vertices.get(0);
            vertex.remove();
            this.g.tx().commit();
        }
    }

    @Override
    public void remove(Edge a) {
        this.g.tx().rollback();
        List<org.apache.tinkerpop.gremlin.structure.Edge> edges = this.g.traversal().V().has("id", a.getSource().getId()).outE().has("id", a
                .getId()).toList();
        if (!edges.isEmpty()) {
            org.apache.tinkerpop.gremlin.structure.Edge edge = edges.get(0);
            edge.remove();
            this.g.tx().commit();
        }
    }

    @Override
    public void addVertex(Vertex v) {
        this.g.tx().rollback();
        org.apache.tinkerpop.gremlin.structure.Vertex sourceVertex = this.g.addVertex();
        sourceVertex.property("id", v.getId());
        v.getProperties().keySet().stream()
                .forEach((property) -> {
                    if (isTitanPrimitave(v.getProperty(property))) {
                        sourceVertex.property(property, v.getProperty(property));
                    } else {
                        sourceVertex.property(property, BLOB + Blob.writeString((Serializable) v.getProperty(property)));
                    }
                });
        this.g.tx().commit();
    }

    @Override
    public Edge addArc(Vertex s, Vertex d, Map<String, Object> properties) {
        this.g.tx().rollback();
        org.apache.tinkerpop.gremlin.structure.Vertex sourceVertex = this.g.traversal().V().has("id", s.getId()).toList().get(0);
        org.apache.tinkerpop.gremlin.structure.Vertex destVertex = this.g.traversal().V().has("id", d.getId()).toList().get(0);
//        System.out.println("src: "+sourceVertex.id() + " , dest: "+destVertex.id());
        org.apache.tinkerpop.gremlin.structure.Edge e = sourceVertex.addEdge(DEFAULT_LABEL, destVertex);
//        System.out.println("e: "+e.id());
        e.property("id", UUID.randomUUID().toString());
        for (String key : properties.keySet()) {
            e.property(key, properties.get(key));
        }
        this.g.tx().commit();
        return parse(s, d, e);
    }

    @Override
    public void addArc(Edge a) {
        this.g.tx().rollback();
        Vertex s = a.getSource();
        Vertex d = a.getDestination();
        Map<String, Object> properties = a.getProperties();
        org.apache.tinkerpop.gremlin.structure.Vertex sourceVertex = this.g.traversal().V().has("id", s.getId()).toList().get(0);
        org.apache.tinkerpop.gremlin.structure.Vertex destVertex = this.g.traversal().V().has("id", d.getId()).toList().get(0);
        org.apache.tinkerpop.gremlin.structure.Edge e = sourceVertex.addEdge(DEFAULT_LABEL, destVertex);
        e.property("id", a.getId());
        for (String key : properties.keySet()) {
            e.property(key, properties.get(key));
        }
        this.g.tx().commit();
    }

    @Override
    public void remove(List<Edge> as) {
        for (Edge a : as) {
            remove(a);
        }
    }

    @Override
    public boolean existsVertex(String id) {
        List<org.apache.tinkerpop.gremlin.structure.Vertex> sourceVertices = this.g.traversal().V().has("id", id).toList();
        return !sourceVertices.isEmpty();
    }

    @Override
    public boolean existsVertex(Map<String, Object> properties) {
        List<Vertex> sourceVertices = getVertex(properties);
        return !sourceVertices.isEmpty();
    }

    @Override
    public boolean existsArc(String id) {
        List<org.apache.tinkerpop.gremlin.structure.Edge> edges = this.g.traversal().E().has("id", id).toList();
        return !edges.isEmpty();
    }

    @Override
    public boolean existsArc(Vertex s, Vertex d, Map<String, Object> properties) {
        List<Edge> arcs = getArcs(s, d, properties);
        return !arcs.isEmpty();
    }

    @Override
    public List<Vertex> getVertex(Map<String, Object> properties) {
        checkForIndexing(properties.keySet(), Vertex.class);
        GraphTraversal<org.apache.tinkerpop.gremlin.structure.Vertex, org.apache.tinkerpop.gremlin.structure.Vertex> gt = this.g.traversal()
                .V();
        for (String property : properties.keySet()) {
            gt = gt.has(property, properties.get(property));
        }
        List<org.apache.tinkerpop.gremlin.structure.Vertex> sourceVertices = gt.toList();
        List<Vertex> vertices = new ArrayList<>();
        for (org.apache.tinkerpop.gremlin.structure.Vertex sv : sourceVertices) {
            Vertex v = parse(sv);
            vertices.add(v);
        }
        return vertices;
    }

    @Override
    public Vertex getVertex(String id) {
        List<org.apache.tinkerpop.gremlin.structure.Vertex> sourceVertices = this.g.traversal().V().has("id", id).toList();
        if (sourceVertices.isEmpty()) {
            return null;
        }
        return parse(sourceVertices.get(0));
    }

    @Override
    public List<Edge> getArcs(Vertex s, Vertex d, Map<String, Object> properties) {
        List<org.apache.tinkerpop.gremlin.structure.Edge> edges = g.traversal().V().has("id", s.getId()).outE().where(otherV().has("id", d
                .getId())).toList();

        List<Edge> arcs = new ArrayList<>();
        for (org.apache.tinkerpop.gremlin.structure.Edge e : edges) {
            if (e.outVertex().property("id").value().equals(s.getId()) && e.inVertex().property("id").value().equals(d.getId())) {
                Edge a = parse(parse(e.outVertex()), parse(e.inVertex()), e);
                arcs.add(a);
            }
        }
        return arcs;
    }

    @Override
    public List<Edge> getArcs(Map<String, Object> properties) {
        checkForIndexing(properties.keySet(), Edge.class);
        GraphTraversal<org.apache.tinkerpop.gremlin.structure.Edge, org.apache.tinkerpop.gremlin.structure.Edge> gt = this.g.traversal()
                .E();
        for (String property : properties.keySet()) {
            gt = gt.has(property, properties.get(property));
        }
        List<org.apache.tinkerpop.gremlin.structure.Edge> edges = gt.toList();
        List<Edge> arcs = new ArrayList<>();
        for (org.apache.tinkerpop.gremlin.structure.Edge e : edges) {
            Edge a = parse(parse(e.outVertex()), parse(e.inVertex()), e);
            arcs.add(a);
        }
        return arcs;
    }

    @Override
    public void update(Edge a) {
        this.g.tx().rollback();
        List<org.apache.tinkerpop.gremlin.structure.Edge> edges = this.g.traversal().V().has("id", a.getSource().getId()).outE().has(
                "id", a.getId()).toList();
        org.apache.tinkerpop.gremlin.structure.Edge e = edges.get(0);
        for (String eKey : e.keys()) {
            e.property(eKey).remove();
        }
        for (String aKey : a.getProperties().keySet()) {
            if (!aKey.equals(TITAN_DB_ID)) {
                if (isTitanPrimitave(a.getProperty(aKey))) {
                    e.property(aKey, a.getProperty(aKey));
                } else {
                    e.property(aKey, BLOB + Blob.writeString((Serializable) a.getProperty(aKey)));
                }

            }
        }
        e.property(TITAN_DB_ID, e.id().toString());
        this.g.tx().commit();
    }

    @Override
    public void update(Vertex v) {
        this.g.tx().rollback();
        List<org.apache.tinkerpop.gremlin.structure.Vertex> sourceVertices = this.g.traversal().V().has("id", v.getId()).toList();
        org.apache.tinkerpop.gremlin.structure.Vertex sv = sourceVertices.get(0);
        for (String svKey : sv.keys()) {
            sv.property(svKey).remove();
        }
        for (String vKey : v.getProperties().keySet()) {
            if (!vKey.equals(TITAN_DB_ID)) {
                if (isTitanPrimitave(v.getProperty(vKey))) {
                    sv.property(vKey, v.getProperty(vKey));
                } else {
                    sv.property(vKey, BLOB + Blob.writeString((Serializable) v.getProperty(vKey)));
                }
            }
        }
        sv.property(TITAN_DB_ID, sv.id().toString());
        this.g.tx().commit();
    }

    private Vertex parse(org.apache.tinkerpop.gremlin.structure.Vertex vertex) {
        Vertex v = new Vertex();
        v.setId((String) vertex.property("id").value());
        for (String key : vertex.keys()) {
            if (vertex.property(key).value() instanceof String) {
                String propertyString = (String) vertex.property(key).value();
                if (propertyString.startsWith(BLOB)) {
                    v.addProperty(key, Blob.fromString(propertyString.substring(BLOB.length(), propertyString.length())));
                } else {
                    v.addProperty(key, propertyString);
                }
            } else {
                v.addProperty(key, vertex.property(key).value());
            }
        }
        v.addProperty(TITAN_DB_ID, vertex.id());
        //TODO: add edges
        return v;
    }

    private Edge parse(Vertex s, Vertex d, org.apache.tinkerpop.gremlin.structure.Edge edge) {
        Graph graph = new Graph("");
        Edge a = graph.addEdge(s, d);
        a.setId((String) edge.property("id").value());
        for (String key : edge.keys()) {
            a.addProperty(key, edge.property(key).value());
        }
        a.addProperty(TITAN_DB_ID, edge.id());
        return a;
    }

    private Map<String, Object> parseProperties(org.apache.tinkerpop.gremlin.structure.Element graphElement) {
        List<Property> myList = Lists.newArrayList(graphElement.properties());
        Map<String, Object> properties = new HashMap<>();
        for (Property p : myList) {
            properties.put(p.key(), p.value());
        }
        properties.put(TITAN_DB_ID, graphElement.id());
        return properties;
    }

    @Override
    public boolean hasIncommingArcs(Vertex v) {
        List<org.apache.tinkerpop.gremlin.structure.Vertex> sourceVertex = this.g.traversal().V().has("id", v.getId()).toList();
        return sourceVertex.get(0).edges(Direction.IN).hasNext();
    }

    @Override
    public List<Edge> getIncommingArcs(Vertex v) {
        List<Edge> arcs = new ArrayList<>();
        List<org.apache.tinkerpop.gremlin.structure.Vertex> sourceVertex = this.g.traversal().V().has("id", v.getId()).toList();
        Iterator<org.apache.tinkerpop.gremlin.structure.Edge> edges = sourceVertex.get(0).edges(Direction.IN);
        while (edges.hasNext()) {
            org.apache.tinkerpop.gremlin.structure.Edge edge = edges.next();
            org.apache.tinkerpop.gremlin.structure.Vertex out = edge.outVertex();
            Vertex u = this.parse(out);
            Edge arc = this.parse(u, v, edge);
            arcs.add(arc);
        }
        return arcs;
    }

    @Override
    public boolean hasOutgoingArcs(Vertex v) {
        List<org.apache.tinkerpop.gremlin.structure.Vertex> sourceVertex = this.g.traversal().V().has("id", v.getId()).toList();
        return sourceVertex.get(0).edges(Direction.OUT).hasNext();
    }

    @Override
    public List<Edge> getOutgoingArcs(Vertex v) {
        List<Edge> arcs = new ArrayList<>();
        List<org.apache.tinkerpop.gremlin.structure.Vertex> sourceVertex = this.g.traversal().V().has("id", v.getId()).toList();
        Iterator<org.apache.tinkerpop.gremlin.structure.Edge> edges = sourceVertex.get(0).edges(Direction.OUT);
        while (edges.hasNext()) {
            org.apache.tinkerpop.gremlin.structure.Edge edge = edges.next();
            org.apache.tinkerpop.gremlin.structure.Vertex in = edge.inVertex();
            Vertex u = this.parse(in);
            Edge arc = this.parse(v, u, edge);
            arcs.add(arc);
        }
        return arcs;
    }

    @Override
    public Graph getWholeGraph(String graphName) {
        Graph graph = new Graph(graphName);
        List<Vertex> vertices = this.getVertex(new HashMap<>());
        vertices.stream()
                .forEach((v) -> {
                    graph.addVertex(v);
                });
        List<Edge> arcs = this.getArcs(new HashMap<>());
        for (Edge a : arcs) {
            Edge newA = graph.addEdge(a.getSource(), a.getDestination());
            newA.getProperties().putAll(a.getProperties());
        }
        return graph;
    }

    @Override
    public Graph getSubgraph(String graphName, Vertex root, int hops) {
        Graph subgraph = new Graph(graphName);
        subgraph.addVertex(root);
        Queue<Vertex> queue = new LinkedList<>();
        queue.add(root);
        for (int i = 0; i < hops; i++) {
            Queue<Vertex> newQueue = new LinkedList<>();
            while (!queue.isEmpty()) {
                Vertex vOut = queue.poll();
//                subgraph.addVertex(vOut);
                List<org.apache.tinkerpop.gremlin.structure.Edge> outE = this.g.traversal().V().has("id", vOut.getId()).outE().toList();
                for (org.apache.tinkerpop.gremlin.structure.Edge e : outE) {
                    if (e.outVertex().value("id").equals(vOut.getId())) {
                        Vertex vIn;
                        if (subgraph.getVertex(e.inVertex().value("id")) != null) {
                            vIn = subgraph.getVertex(e.inVertex().value("id"));
                        } else {
                            vIn = parse(e.inVertex());
                            subgraph.addVertex(vIn);
                            newQueue.add(vIn);
                        }
                        boolean edgeExists = false;
                        for (Edge eOutV : vOut.getArcs(Edge.Direction.OUTGOING)) {
                            if (eOutV.getDestination().getId().equals(vIn.getId())) {
                                edgeExists = true;
                                break;
                            }
                        }
                        if (!edgeExists) {
//                            Edge pE = parse(vOut, vIn, e);
                            Edge a = subgraph.addEdge(vOut, vIn);
                            a.getProperties().putAll(parseProperties(e));
                        }
                    }
                }
            }
            queue = newQueue;
        }
        return subgraph;
    }

    @Override
    public List<Vertex> getOutgoingArcVertices(Vertex v, Map<String, Object> nextVertexProperties) {
        checkForIndexing(nextVertexProperties.keySet(), Vertex.class);
        GraphTraversal<org.apache.tinkerpop.gremlin.structure.Vertex, org.apache.tinkerpop.gremlin.structure.Vertex> gt = this.g.traversal()
                .V().has("id", v.getId()).outE().inV();
        for (String property : nextVertexProperties.keySet()) {
            gt = gt.has(property, nextVertexProperties.get(property));
        }
        List<org.apache.tinkerpop.gremlin.structure.Vertex> outVertices = gt.toList();

        List<Vertex> vertices = new ArrayList<>();
        for (org.apache.tinkerpop.gremlin.structure.Vertex nextV : outVertices) {
            Vertex v2 = parse(nextV);
            vertices.add(v2);
        }
        return vertices;
    }

    @Override
    public List<List<Vertex>> getPaths(Vertex s, Vertex t, int k) {
        List<List<Vertex>> paths = new ArrayList<>();
        List list = new ArrayList();
        org.apache.tinkerpop.gremlin.structure.Vertex v1 = this.g.traversal().V().has("id", s.getId()).toList().get(0);
        org.apache.tinkerpop.gremlin.structure.Vertex v2 = this.g.traversal().V().has("id", t.getId()).toList().get(0);

        this.g.traversal().V(v1).repeat(both().simplePath()).until(is(v2)).limit(k).path().fill(list);
//        System.out.println(list);

        for (Object o : list) {
            List<Vertex> path = new LinkedList<>();
            ImmutablePath l = (ImmutablePath) o;
            for (Object ov : l) {
                org.apache.tinkerpop.gremlin.structure.Vertex v = (org.apache.tinkerpop.gremlin.structure.Vertex) ov;
                Vertex v_i = parse(v);
                path.add(v_i);
            }
            paths.add(path);
        }
        return paths;
    }

    private void checkForIndexing(Collection<String> propertyKeys, Class objectClass) {
        if (!propertyKeys.isEmpty()) {
            if (indexing.isNewIndex(propertyKeys, objectClass)) {
                this.createNewIndex(propertyKeys, objectClass);
            }
        }
    }

    private void createNewIndex(Collection<String> propertyKeys, Class objectClass) {
        this.g.tx().rollback(); //Never create new indexes while a transaction is active
        TitanManagement mgmt = this.g.openManagement();

        TitanManagement.IndexBuilder nameIndexBuilder;
        if (objectClass == Vertex.class) {
            nameIndexBuilder = mgmt.buildIndex("index-" + objectClass.getCanonicalName() + "" + propertyKeys.toString(),
                    org.apache.tinkerpop.gremlin.structure.Vertex.class);
        } else if (objectClass == Edge.class) {
            nameIndexBuilder = mgmt.buildIndex("index-" + objectClass.getCanonicalName() + "" + propertyKeys.toString(),
                    org.apache.tinkerpop.gremlin.structure.Edge.class);
        } else {
            mgmt.commit();
            return;
        }

        for (String propertyKey : propertyKeys) {
            PropertyKey property;
            if (mgmt.containsPropertyKey(propertyKey)) {
                property = mgmt.getPropertyKey(propertyKey);
            } else {
                property = mgmt.makePropertyKey(propertyKey).dataType(Object.class).make();
            }
            nameIndexBuilder.addKey(property);
        }
        nameIndexBuilder.buildCompositeIndex();
        mgmt.commit(); //Wait for the index to become available
        indexing.createIndex("index-" + objectClass.getCanonicalName() + "" + propertyKeys.toString(), propertyKeys, objectClass);
    }

    @Override
    public void createIndex(Collection<String> propertyKeys, Class objectClass) {
        this.g.tx().rollback(); //Never create new indexes while a transaction is active
        TitanManagement mgmt = this.g.openManagement();

        if (!mgmt.containsGraphIndex("index-" + objectClass.getCanonicalName() + "" + propertyKeys.toString())) {
            TitanManagement.IndexBuilder nameIndexBuilder;
            if (objectClass == Vertex.class) {
                nameIndexBuilder = mgmt.buildIndex("index-" + objectClass.getCanonicalName() + "" + propertyKeys.toString(),
                        org.apache.tinkerpop.gremlin.structure.Vertex.class);
            } else if (objectClass == Edge.class) {
                nameIndexBuilder = mgmt.buildIndex("index-" + objectClass.getCanonicalName() + "" + propertyKeys.toString(),
                        org.apache.tinkerpop.gremlin.structure.Edge.class);
            } else {
                mgmt.commit();
                return;
            }

            for (String propertyKey : propertyKeys) {
                PropertyKey property;
                if (mgmt.containsPropertyKey(propertyKey)) {
                    property = mgmt.getPropertyKey(propertyKey);
                } else {
                    property = mgmt.makePropertyKey(propertyKey).dataType(Object.class).make();
                }
                nameIndexBuilder.addKey(property);
            }
            TitanGraphIndex namei = nameIndexBuilder.buildCompositeIndex();
        }

        mgmt.commit(); //Wait for the index to become available
    }

    private Map<Collection<String>, Class> getIndecies() {
        Map<Collection<String>, Class> indecies = new HashMap<>();
        TitanManagement mgmt = this.g.openManagement();
        for (TitanGraphIndex index : mgmt.getGraphIndexes(org.apache.tinkerpop.gremlin.structure.Vertex.class)) {
            Collection<String> properties = new ArrayList<>();
            for (PropertyKey property : index.getFieldKeys()) {
                String propertyKeyName = property.name();
//                LOG.log(Level.INFO, "VertexIndex: {0}", propertyKeyName);
                properties.add(propertyKeyName);
                indecies.put(properties, Vertex.class);
            }
        }
        for (TitanGraphIndex index : mgmt.getGraphIndexes(org.apache.tinkerpop.gremlin.structure.Edge.class)) {
            Collection<String> properties = new ArrayList<>();
            for (PropertyKey property : index.getFieldKeys()) {
                String propertyKeyName = property.name();
//                LOG.log(Level.INFO, "EdgeIndex: {0}", propertyKeyName);
                properties.add(propertyKeyName);
                indecies.put(properties, Edge.class);
            }
        }
        return indecies;
    }

    @Override
    public void addGraph(Graph graph) {
        for (Vertex v : graph.getVertices()) {
            this.addVertex(v);
//            System.out.println("add v");
        }
        for (Edge e : graph.getEdges()) {
            this.addArc(e);
//            System.out.println("add e");
        }
    }

    private boolean isTitanPrimitave(Object o) {
        return o instanceof String || o instanceof Character
                || o instanceof Boolean || o instanceof Byte || o instanceof Short || o instanceof Integer
                || o instanceof Long || o instanceof Float || o instanceof Double;
    }
}
