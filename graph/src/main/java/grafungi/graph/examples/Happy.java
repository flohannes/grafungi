package grafungi.graph.examples;

import grafungi.graph.Graph;
import grafungi.graph.Vertex;
import grafungi.graph.db.GraphDatabaseAdapter;
import grafungi.graph.db.TitanConnector;


/**
 *
 * @author fschmidt
 */
public class Happy {

    public static void main(String... args) {
        Graph graph = new Graph("Happy");

        Vertex root = new Vertex();
        root.addProperty("name", "Are you happy?");
        root.addProperty("description", "State your moment feeling.");
        root.addProperty("type", "examination");
        root.addProperty("root", true);
        root.addProperty("source", "Internet");

        Vertex v1 = new Vertex();
        v1.addProperty("name", "Yes, I am happy!");
        v1.addProperty("description", "Klick it when you feel to be happy.");
        v1.addProperty("type", "symptom");
        Vertex v2 = new Vertex();
        v2.addProperty("name", "No, I am not happy");
        v2.addProperty("description", "Does not feel happy.");
        v2.addProperty("type", "symptom");

        Vertex v3 = new Vertex();
        v3.addProperty("name", "Keep doing whatever you are doing!");
        v3.addProperty("description", "Nothing has to change.");
        v3.addProperty("type", "diagnosis");

        Vertex v4 = new Vertex();
        v4.addProperty("name", "Do you want to be happy?");
        v4.addProperty("description",
                "");
        v4.addProperty("type", "examination");
        
        Vertex v5 = new Vertex();
        v5.addProperty("name", "No");
        v5.addProperty("description",
                "You feel fine to be unhappy.");
        v5.addProperty("type", "symptom");
        Vertex v6 = new Vertex();
        v6.addProperty("name", "Yes");
        v6.addProperty("description",
                "I do not like to be unhappy anymore.");
        v6.addProperty("type", "symptom");

        Vertex v7 = new Vertex();
        v7.addProperty("name", "Change something!");
        v7.addProperty("description", "Change something in your live or situation.");
        v7.addProperty("type", "therapy");

        //TODO: Als Graph zusammenfuegen
        graph.addVertex(root);
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);
        graph.addVertex(v5);
        graph.addVertex(v6);
        graph.addVertex(v7);
        
        graph.addEdge(root, v1);
        graph.addEdge(root, v2);
        graph.addEdge(v1, v3);
        graph.addEdge(v2, v4);
        graph.addEdge(v4, v5);
        graph.addEdge(v4, v6);
        graph.addEdge(v5, v3);
        graph.addEdge(v6, v7);
        
        for(Vertex v : graph.getVertices()){
            v.addProperty("in-degree", v.getInDegree());
            v.addProperty("out-degree", v.getOutDegree());
        }
        
        String keyspace = "graphhappy1";
        GraphDatabaseAdapter db = new TitanConnector();
//        BaseConfiguration config = new BaseConfiguration();
//        config.setProperty("storage.backend", "cassandra");
//        config.setProperty("storage.hostname", "10.200.1.56");
//        config.setProperty("storage.cassandra.keyspace", keyspace);
//        db.connect(config);
        db.connect("/Users/fschmidt/graphws/"+keyspace);
        db.addGraph(graph);
        db.close();
    }
}
