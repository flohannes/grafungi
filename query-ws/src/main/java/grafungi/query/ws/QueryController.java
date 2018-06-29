package grafungi.query.ws;

import com.google.gson.Gson;
import grafungi.graph.Edge;
import grafungi.graph.Graph;
import grafungi.graph.Vertex;
import grafungi.graph.bn.BayesianNetwork;
import grafungi.graph.bn.ConditionalProbability;
import grafungi.graph.bn.Entity;
import grafungi.graph.bn.State;
import grafungi.graph.bn.jayes.Inference;
import grafungi.graph.db.GraphDatabaseAdapter;
import grafungi.graph.db.TitanConnector;
import grafungi.graph.io.d3.D3Converter;
import grafungi.query.ws.data.GraphMetadata;
import grafungi.query.ws.data.GraphMetadataDBRepository;
import grafungi.query.ws.data.util.DummyGraphs;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueryController {

    @Value("${app.dbpath}")
    private String dbPath;

    @Value("${app.createdata}")
    private String createDummyData;

    @Autowired
    private GraphMetadataDBRepository graphRepro;
    private static final Logger LOG = Logger.getLogger(QueryController.class.getName());

    private String basePath;// = "/home/ubuntu/medgraphs/";
    private final String localBasePath = "/Users/fschmidt/graphws/";
//    private final GraphDatabaseAdapter graphdb;
    private Map<String, GraphDatabaseAdapter> graphDBs;

    public QueryController() {
//        graphDBs = new HashMap<>();
//        this.basePath = dbPath;
////        graphdb = new TitanConnector();
//System.out.println(createDummyData);
//        if (Boolean.parseBoolean(createDummyData)) {
//            DummyGraphs.create(dbPath);
//        }
    }

    @PostConstruct
    public void init() {
        graphDBs = new HashMap<>();
        this.basePath = dbPath;
//        graphdb = new TitanConnector();
        System.out.println(createDummyData);
        if (Boolean.parseBoolean(createDummyData)) {
            DummyGraphs.create(dbPath);
        }
    }

//    @PreDestroy
//    public void closeGraphDBs() {
//        System.out.println("PRE DESTROY ENTRED!!!");
//        for (String key : graphDBs.keySet()) {
//            graphDBs.get(key).close();
//        }
//    }
    private GraphDatabaseAdapter getConnectedDB(String graphName) {
        if (graphDBs.keySet().contains(graphName)) {
            System.out.println("DB get " + graphName);
            return graphDBs.get(graphName);
        } else {
            GraphDatabaseAdapter graphdb = new TitanConnector();
            graphdb.connect(basePath + graphName);
//            graphdb.connect(localBasePath + graphName);
            graphDBs.put(graphName, graphdb);
            System.out.println("DB register " + graphName);
            return graphdb;
        }
    }

    private void reconnect(GraphDatabaseAdapter graphdb, String graphName) {
        graphdb.close();
        graphdb.connect(basePath + graphName);
    }

    @CrossOrigin
    @RequestMapping("/tree")
    public String getTree(@RequestParam(value = "name", defaultValue = "graphdiarrhea1") String graphName, @RequestParam(value
            = "rootNodeId",
            defaultValue = "-1") String currentNode, @RequestParam(value = "hops", defaultValue = "1") Integer numberOfHops) {
        //open database
        GraphDatabaseAdapter graphdb = getConnectedDB(graphName);

        Vertex root;
        if (currentNode.equals("-1")) {
            Map<String, Object> properties = new HashMap<>();
            properties.put("root", true);
            root = graphdb.getVertex(properties).get(0);
        } else {
            root = graphdb.getVertex(currentNode);
        }

        //get tree from graph parameters given
        Graph subgraph = graphdb.getSubgraph(graphName, root, numberOfHops);
//        graphdb.close();
        //return tree
        return D3Converter.getD3TreeJson(subgraph, root, numberOfHops);
    }

    @CrossOrigin
    @RequestMapping("/tree/vertex/{id}")
    public Vertex getVertexById(@RequestParam(value = "name", defaultValue = "graphdiarrhea1") String graphName,
            @PathVariable("id") String nodeId) {
        GraphDatabaseAdapter graphdb = getConnectedDB(graphName);
        Vertex v = graphdb.getVertex(nodeId);
//        graphdb.close();
        return v;
    }

    @CrossOrigin
    @RequestMapping("/tree/vertex/edit/{id}")
    public ResponseEntity updateTreeVertex(@PathVariable("id") String id,
            @RequestParam(value = "name", defaultValue = "graphdiarrhea1") String graphName,
            @RequestParam(value = "properties", required = true) String properties) {

        Gson gson = new Gson();
        Map<String, Object> objclass = new HashMap<>();
        final Map<String, Object> map = (Map<String, Object>) gson.fromJson(properties, objclass.getClass());
        if (!map.containsKey("id") && !map.containsKey("titan-db-id")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Does not contain id or titan-db-id.");
        }
        //open database
        GraphDatabaseAdapter graphdb = getConnectedDB(graphName);
        //get tree from graph parameters given
        Vertex v = graphdb.getVertex(id);
        if (!map.containsKey("root") && v.getProperties().containsKey("root")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Does not contain root.");
        }
        v.getProperties().clear();
        map.keySet().stream()
                .forEach((key) -> {
                    v.addProperty(key, map.get(key));
                });
        graphdb.update(v);
//        graphdb.close();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }

    /*
    BAYESIAN NETWORK FUNCTIONS
     */
    @CrossOrigin
    @RequestMapping("/bn")
    public String getBayesianNetworkD3(@RequestParam(value = "name", defaultValue = "bnlung1") String graphName) {
        GraphDatabaseAdapter graphdb = getConnectedDB(graphName);
        Graph graph = graphdb.getWholeGraph(graphName);
//        graphdb.close();
        return D3Converter.getD3Json(graph);
    }

    @CrossOrigin
    @RequestMapping("/bn/inference")
    public String getInference(@RequestParam(value = "name", defaultValue = "bnlung1") String graphName) {
        //open database
        GraphDatabaseAdapter graphdb = getConnectedDB(graphName);
        Graph graph = graphdb.getWholeGraph(graphName);
//        graphdb.close();
        BayesianNetwork bn = new BayesianNetwork(graph);
        Inference inf = new Inference();
        BayesianNetwork resultBn = inf.calc(bn);
        //return json
        return D3Converter.getD3Json(resultBn);
    }

    /*
    http://10.200.1.75:8012/bn/probability?
    name=bnlung1&
    id=0ab4586b-7e6d-4126-84af-7b5bcc2a2e15&
    probability=[0.02,0.98]&
    influencedState=[positive,negative]&
    condition-states=[{%22entityName%22:%22Cold%22,%22name%22:%22negative%22}]
     */
    @CrossOrigin
    @RequestMapping("/bn/probability")
    public ResponseEntity changeProbability(
            @RequestParam(value = "name", defaultValue = "bnlung1") String graphName,
            @RequestParam(value = "id", required = true) String id,
            @RequestParam(value = "probability", defaultValue = "[]") String probability,
            @RequestParam(value = "influenced-state", defaultValue = "[]") String influencedState,
            @RequestParam(value = "condition-states", defaultValue = "[]") String conditionStatesAsString) {
        //open database
        Gson gson = new Gson();
        State[] conditionStates = gson.fromJson(conditionStatesAsString, State[].class);
        String[] influencedStates = gson.fromJson(influencedState, String[].class);
        double[] probabilities = gson.fromJson(probability, double[].class);
        GraphDatabaseAdapter graphdb = getConnectedDB(graphName);
        Vertex v = graphdb.getVertex(id);
        List<Edge> parentEdges = graphdb.getIncommingArcs(v);

        if (v == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vertex Id not known.");
        }
        Entity e = new Entity(v, parentEdges);
        //remove old cp
        int cpSizeBefore = e.getCpt().getProbabilities().size();
        Iterator<ConditionalProbability> cpIter = e.getCpt().getProbabilities().iterator();
        while (cpIter.hasNext()) {
            ConditionalProbability cp = cpIter.next();
            boolean remove = true;
            if (!Arrays.stream(influencedStates).parallel().anyMatch(cp.getInfluencedState().getName()::contains)) {
                remove = false;
                continue;
            }
            for (State condition : cp.getConditions()) {
                boolean found = false;
                for (State newConditionName : conditionStates) {
                    if (condition.getName().equals(newConditionName.getName()) && condition.getEntityName().equals(newConditionName
                            .getEntityName())) {
                        found = true;
                    }
                }
                if (!found) {
                    remove = false;
                }
            }
            //remove
            if (remove) {
                cpIter.remove();
            }
        }
        int cpSizeAfter = e.getCpt().getProbabilities().size();
        if (cpSizeAfter >= cpSizeBefore) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPs not found.");
        }

        //insert new cp
        for (int i = 0; i < influencedStates.length; i++) {
            String infState = influencedStates[i];
            double prob = probabilities[i];
            State newInfluencedState = e.getState(infState);
            if (newInfluencedState == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("States for update not found in vertex.");
            }
            e.getCpt()
                    .addConditionProbability(prob, newInfluencedState, conditionStates);
        }
        graphdb.update(e);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }

    @CrossOrigin
    @RequestMapping("/bn/edit/vertex/{id}")
    public ResponseEntity setObservation(@PathVariable(value = "id") String id,
            @RequestParam(value = "name", defaultValue = "bnlung1") String graphName,
            @RequestParam(value = "observation") String observation) {
        //open database
        GraphDatabaseAdapter graphdb = getConnectedDB(graphName);
        Vertex vertex = graphdb.getVertex(id);
        if (vertex == null) {
//            graphdb.close();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vertex Id not known.");
        }
        if (observation == null) {
            vertex.getProperties().remove("observation");
        } else if (observation.isEmpty()) {
            vertex.getProperties().remove("observation");
        } else {
            List<State> states = (List<State>) vertex.getProperty("states");
            for (State s : states) {
                if (observation.equals(s.getName())) {
                    vertex.getProperties().put("observation", s);
                }
            }
        }
        graphdb.update(vertex);
        Graph graph = graphdb.getWholeGraph(graphName);
//        reconnect(graphdb, graphName);
        BayesianNetwork bn = new BayesianNetwork(graph);
        Inference inf = new Inference();
        BayesianNetwork resultBn = inf.calc(bn);
        //return json
        String newInferenceResult = D3Converter.getD3Json(resultBn);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(newInferenceResult);
    }

    /*
    GRAPHS FUNCTIONS: TODO! CHECK ROOT FOR TREES!!!
     */
    @CrossOrigin
    @RequestMapping("/graph/vertex/remove/{id}")
    public void removeVertex(@PathVariable("id") String id, @RequestParam(value = "name", defaultValue = "graphdiarrhea1") String graphName) {
        //TODO not remove root!
        GraphDatabaseAdapter graphdb = getConnectedDB(graphName);
        Vertex v = graphdb.getVertex(id);
        if (v.getProperties().containsKey("in-degree")) {
            if ((int) v.getProperty("in-degree") > 0) {
                for (Edge e : graphdb.getIncommingArcs(v)) {
                    Vertex v2 = e.getSource();
                    v2.addProperty("out-degree", (int) v2.getProperty("out-degree") - 1);
                    graphdb.update(v2);
                }
            }
        }
        if (v.getProperties().containsKey("out-degree")) {
            if ((int) v.getProperty("out-degree") > 0) {
                for (Edge e : graphdb.getOutgoingArcs(v)) {
                    Vertex v2 = e.getDestination();
                    v2.addProperty("in-degree", (int) v2.getProperty("in-degree") - 1);
                    graphdb.update(v2);
                }
            }
        }
        if (v.getProperties().containsKey("root")) {
            if ((boolean) v.getProperty("root")) {
                if ((int) v.getProperty("out-degree") > 0) {
                    for (Edge e : graphdb.getOutgoingArcs(v)) {
                        graphdb.remove(e);
                    }
                }
                Map<String, Object> newProperties = new HashMap<>();
                newProperties.put("id", v.getProperty("id"));
                newProperties.put("titan-db-id", v.getProperty("titan-db-id"));
                newProperties.put("root", true);
                newProperties.put("in-degree", 0);
                newProperties.put("out-degree", 0);
                newProperties.put("name", graphName);
                v.getProperties().clear();
                v.getProperties().putAll(newProperties);
                graphdb.update(v);
            }
        } else {
            graphdb.remove(v);
        }
//        graphdb.close();
    }

//    @CrossOrigin
//    @RequestMapping("/tree/vertex/remove/{id}")
//    public void removeTreeVertex(@PathVariable("id") String id, @RequestParam(value = "name", defaultValue = "graphdiarrhea1") String graphName) {
//        GraphDatabaseAdapter graphdb = getConnectedDB(graphName);
//        Vertex v = graphdb.getVertex(id);
//        //TODO: ADD REMOVE FUNCTIONS FOR TREES IN ORDER TO REMOVE SUBTREES.
//        graphdb.remove(v);
//        graphdb.close();
//    }
    @CrossOrigin
    @RequestMapping("/graph/edge/remove")
    public void removeEdge(@RequestParam("src") String src, @RequestParam("dst") String dst, @RequestParam(value = "name", defaultValue
            = "graphdiarrhea1") String graphName) {
        //open database
        GraphDatabaseAdapter graphdb = getConnectedDB(graphName);
        //get tree from graph parameters given
        Vertex srcV = graphdb.getVertex(src);
        Vertex dstV = graphdb.getVertex(dst);
        List<Edge> e = graphdb.getArcs(srcV, dstV, new HashMap<>());
        if (e.size() == 1) {
            graphdb.remove(e.get(0));
            //update properties
            srcV.addProperty("out-degree", (int) srcV.getProperty("out-degree") - 1);
            dstV.addProperty("in-degree", (int) dstV.getProperty("in-degree") - 1);
            graphdb.update(srcV);
            graphdb.update(dstV);
        }
//        graphdb.close();
    }

    @CrossOrigin
    @RequestMapping("/graph/vertex/add")
    public Vertex addVertex(@RequestParam(value = "name", defaultValue = "graphdiarrhea1") String graphName) {
        GraphDatabaseAdapter graphdb = getConnectedDB(graphName);
        Vertex v = new Vertex();
        v.addProperty("in-degree", 0);
        v.addProperty("out-degree", 0);
        graphdb.addVertex(v);
        Vertex newV = graphdb.getVertex(v.getId());
//        graphdb.close();
        return newV;
    }

    @CrossOrigin
    @RequestMapping("/graph/edge/add")
    public boolean addEdge(@RequestParam("src") String src, @RequestParam("dst") String dst, @RequestParam(value = "name", defaultValue
            = "graphdiarrhea1") String graphName) {
        GraphDatabaseAdapter graphdb = getConnectedDB(graphName);
        Vertex srcV = graphdb.getVertex(src);
        Vertex dstV = graphdb.getVertex(dst);
        srcV.addProperty("out-degree", (int) srcV.getProperty("out-degree") + 1);
        dstV.addProperty("in-degree", (int) dstV.getProperty("in-degree") + 1);
        graphdb.update(srcV);
        graphdb.update(dstV);
        Edge e = graphdb.addArc(srcV, dstV, new HashMap<>());
//        graphdb.close();
        return true;
    }

//    @CrossOrigin
//    @RequestMapping("/graph/vertex/update/{id}")
//    public void updateVertex(@PathVariable("id") String id, @RequestParam(value = "name", defaultValue = "graphdiarrhea1") String graphName,
//            @RequestParam(value = "properties", required = true) String properties) {
//
//        //TODO: CHECK FOR AT LEAST SPECIFIC PROPERTIES (ID,TITAN-DB-ID,ROOT)
//        Gson gson = new Gson();
//        Map<String, Object> objclass = new HashMap<>();
//        final Map<String, Object> map = (Map<String, Object>) gson.fromJson(properties, objclass.getClass());
//        //open database
//        GraphDatabaseAdapter graphdb = getConnectedDB(graphName);
//        //get tree from graph parameters given
//        Vertex v = graphdb.getVertex(id);
//        v.getProperties().clear();
//        map.keySet().stream()
//                .forEach((key) -> {
//                    v.addProperty(key, map.get(key));
//                });
//        graphdb.update(v);
//        graphdb.close();
//    }
//    @CrossOrigin
//    @RequestMapping("/graph/edge/update")
//    public void updateEdge(@RequestParam(value = "name", defaultValue = "graphdiarrhea1") String graphName, @RequestBody(required = true) Edge e) {
//        //open database
//        GraphDatabaseAdapter graphdb = new TitanConnector();
//        graphdb.connect("/home/ubuntu/medgraphs/" + graphName);
//        //get tree from graph parameters given
//        graphdb.update(e);
//        graphdb.close();
//    }
    @CrossOrigin
    @RequestMapping("/graph/path")
    public List<List<String>> getPath(@RequestParam(value = "name", defaultValue = "graphdiarrhea1") String graphName, @RequestParam(value
            = "vertex1", required = true) String vertexId1, @RequestParam(value = "vertex2", required = true) String vertexId2,
            @RequestParam(value = "paths", defaultValue = "1") String numPaths) {
        //open database
        GraphDatabaseAdapter graphdb = getConnectedDB(graphName);
        //get tree from graph parameters given
        List<List<Vertex>> paths = graphdb.getPaths(graphdb.getVertex(vertexId1), graphdb.getVertex(vertexId2), Integer.valueOf(numPaths));

        List<List<String>> pathIds = new ArrayList<>();
        for (List<Vertex> path : paths) {
            List<String> pathId = new ArrayList<>();
            for (Vertex v : path) {
                pathId.add(v.getId());
            }
            pathIds.add(pathId);
        }
//        graphdb.close();
        return pathIds;
    }

    @CrossOrigin
    @RequestMapping("/graph/copy")
    public String copyGraph(@RequestParam(value = "name", defaultValue = "graphdiarrhea1") String graphName, @RequestParam(name = "target",
            required = true) String renamedGraph, @RequestParam(value = "type", defaultValue = "tree") String type) {

        try {
            String newId = java.net.URLEncoder.encode(renamedGraph + new Date().getTime(), "UTF-8");

            try {
                File srcDir = new File(basePath + graphName);
                File destDir = new File(basePath + newId);
                FileUtils.copyDirectory(srcDir, destDir);
            } catch (IOException ex) {
                Logger.getLogger(QueryController.class.getName()).log(Level.SEVERE, null, ex);
            }

//            String urlString = "http://localhost:8016/graphs/new?name=" + java.net.URLEncoder.encode(graphName, "UTF-8") + "&id="
//                    + newId + "&type=" + java.net.URLEncoder.encode(type, "UTF-8");
//            URL url;
//            try {
//                url = new URL(urlString);
//                URLConnection conn = url.openConnection();
//                InputStream is = conn.getInputStream();
//                is.close();
//            } catch (MalformedURLException ex) {
//                Logger.getLogger(QueryController.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IOException ex) {
//                Logger.getLogger(QueryController.class.getName()).log(Level.SEVERE, null, ex);
//            }
            GraphMetadata graphDataPoint = new GraphMetadata();
            graphDataPoint.setGraphDBId(newId);
            graphDataPoint.setGraphName(java.net.URLEncoder.encode(graphName, "UTF-8"));
            graphDataPoint.setType(java.net.URLEncoder.encode(type, "UTF-8"));
            graphRepro.save(graphDataPoint);

            return newId;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(QueryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @CrossOrigin
    @RequestMapping("/graph/add")
    public String copyGraph(@RequestParam(value = "name", required = true) String graphName, @RequestParam(value = "type", defaultValue
            = "tree") String type) {
        try {
            String newId = java.net.URLEncoder.encode(graphName + new Date().getTime(), "UTF-8");
            GraphDatabaseAdapter graphdb = getConnectedDB(newId);
            Graph g = new Graph();
            Vertex v = new Vertex();
            v.addProperty("name", graphName);
            v.addProperty("root", true);
            v.addProperty("description", "");
            v.addProperty("type", "diagnosis");
            v.addProperty("source", "unknown");
            v.addProperty("in-degree", 0);
            v.addProperty("out-degree", 0);
            g.addVertex(v);
            graphdb.addGraph(g);
//            graphdb.close();

//            String urlString = "http://localhost:8016/graphs/new?name=" + java.net.URLEncoder.encode(graphName, "UTF-8") + "&id="
//                    + newId + "&type=" + java.net.URLEncoder.encode(type,
//                            "UTF-8");
//            URL url;
//            try {
//                url = new URL(urlString);
//                URLConnection conn = url.openConnection();
//                InputStream is = conn.getInputStream();
//                is.close();
//            } catch (MalformedURLException ex) {
//                Logger.getLogger(QueryController.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IOException ex) {
//                Logger.getLogger(QueryController.class.getName()).log(Level.SEVERE, null, ex);
//            }
            GraphMetadata graphDataPoint = new GraphMetadata();
            graphDataPoint.setGraphDBId(newId);
            graphDataPoint.setGraphName(java.net.URLEncoder.encode(graphName, "UTF-8"));
            graphDataPoint.setType(java.net.URLEncoder.encode(type, "UTF-8"));
            graphRepro.save(graphDataPoint);

            return newId;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(QueryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @CrossOrigin
    @RequestMapping("/graph")
    public String getGraphD3(@RequestParam(value = "name", defaultValue = "graphdiarrhea1") String graphName) {
        GraphDatabaseAdapter graphdb = getConnectedDB(graphName);
        Graph graph = graphdb.getWholeGraph(graphName);
//        graphdb.close();
        return D3Converter.getD3Json(graph);
    }
}
