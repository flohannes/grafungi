package grafungi.graph.examples;

import grafungi.graph.Graph;
import grafungi.graph.Vertex;
import grafungi.graph.db.GraphDatabaseAdapter;
import grafungi.graph.db.TitanConnector;


/**
 *
 * @author fschmidt
 */
public class Kreditwuerdigkeit {

    public static void main(String... args) {
        Graph graph = new Graph("Kreditwürdigkeit");

        Vertex root = new Vertex();
        root.addProperty("name", "Einkommen");
        root.addProperty("description", "Jahreseinkommen des Bewerbers");
        root.addProperty("type", "examination");
        root.addProperty("root", true);
        root.addProperty("source", "Internet");

        Vertex v1 = new Vertex();
        v1.addProperty("name", "<30k");
        v1.addProperty("description", "");
        v1.addProperty("type", "symptom");
        Vertex v2 = new Vertex();
        v2.addProperty("name", "30k-70k");
        v2.addProperty("description", "");
        v2.addProperty("type", "symptom");
        Vertex v3 = new Vertex();
        v3.addProperty("name", ">70k");
        v3.addProperty("description", "");
        v3.addProperty("type", "symptom");

        Vertex v4 = new Vertex();
        v4.addProperty("name", "Ist vorbestraft?");
        v4.addProperty("description",
                "Anfrage an das Vorstrafenregister, ob die Person bereits Einträge besitzt.");
        v4.addProperty("type", "examination");
        
        Vertex v5 = new Vertex();
        v5.addProperty("name", "Ja");
        v5.addProperty("description",
                "Es existiert mindest ein eintrag im Vorstrafenregister.");
        v5.addProperty("type", "symptom");
        Vertex v6 = new Vertex();
        v6.addProperty("name", "Nein");
        v6.addProperty("description",
                "Keinen Eintrag im Vorstrafenregister gefunden.");
        v6.addProperty("type", "symptom");

        Vertex v7 = new Vertex();
        v7.addProperty("name", "Kreditwürdig");
        v7.addProperty("description", "Antragsteller kann einen Kredit bekommen.");
        v7.addProperty("type", "diagnosis");
        Vertex v8 = new Vertex();
        v8.addProperty("name", "Nicht kreditwürdig");
        v8.addProperty("description", "Der Antragsteller bekommt keinen Kredit.");
        v8.addProperty("type", "diagnosis");

        Vertex v9 = new Vertex();
        v9.addProperty("name", "Ist vorbestraft?");
        v9.addProperty("description",
                "Anfrage an das Vorstrafenregister, ob die Person bereits Einträge besitzt.");
        v9.addProperty("type", "examination");

        Vertex v10 = new Vertex();
        v10.addProperty("name", "Beschäftigungszeitraum");
        v10.addProperty("description", "Prüfung wie lange die Person bereits in dem Job angestellt ist.");
        v10.addProperty("type", "examination");
        
        Vertex v11 = new Vertex();
        v11.addProperty("name", "< 1");
        v11.addProperty("description", "Weniger als ein Jahr in dem momentanen Job.");
        v11.addProperty("type", "symptom");
        Vertex v12 = new Vertex();
        v12.addProperty("name", "1 - 5");
        v12.addProperty("description", "Zwischen 1 bis 5 Jahren bereits im Job.");
        v12.addProperty("type", "diagnosis");
        Vertex v13 = new Vertex();
        v13.addProperty("name", "> 5");
        v13.addProperty("description", "Länger als 5 Jahre im Job.");
        v13.addProperty("type", "diagnosis");

        Vertex v14 = new Vertex();
        v14.addProperty("name", "Verwendet eine Kreditkarte");
        v14.addProperty("description", "");
        v14.addProperty("type", "examination");

        Vertex v15 = new Vertex();
        v15.addProperty("name", "Ja");
        v15.addProperty("description", "Verwendet eine Kreditkarte.");
        v15.addProperty("type", "symptom");
        Vertex v16 = new Vertex();
        v16.addProperty("name", "Nein");
        v16.addProperty("description", "Besitzt oder verwendet keine Kreditkarte.");
        v16.addProperty("type", "symptom");

        Vertex v17 = new Vertex();
        v17.addProperty("name", "Ja");
        v17.addProperty("description",
                "Es existiert mindest ein eintrag im Vorstrafenregister.");
        v17.addProperty("type", "symptom");
        Vertex v18 = new Vertex();
        v18.addProperty("name", "Nein");
        v18.addProperty("description",
                "Keinen Eintrag im Vorstrafenregister gefunden.");
        v18.addProperty("type", "symptom");

        //TODO: Als Graph zusammenfuegen
        graph.addVertex(root);
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);
        graph.addVertex(v5);
        graph.addVertex(v6);
        graph.addVertex(v7);
        graph.addVertex(v8);
        graph.addVertex(v9);
        graph.addVertex(v10);
        graph.addVertex(v11);
        graph.addVertex(v12);
        graph.addVertex(v13);
        graph.addVertex(v14);
        graph.addVertex(v15);
        graph.addVertex(v16);
        graph.addVertex(v17);
        graph.addVertex(v18);
        
        graph.addEdge(root, v1);
        graph.addEdge(root, v2);
        graph.addEdge(root, v3);
        graph.addEdge(v1, v4);
        graph.addEdge(v4, v5);
        graph.addEdge(v4, v6);
        graph.addEdge(v5, v7);
        graph.addEdge(v6, v8);
        
        graph.addEdge(v3, v9);
        graph.addEdge(v9, v17);
        graph.addEdge(v9, v18);
        graph.addEdge(v17, v8);
        graph.addEdge(v18, v7);
        
        graph.addEdge(v2, v10);
        graph.addEdge(v10, v11);
        graph.addEdge(v10, v12);
        graph.addEdge(v10, v13);
        graph.addEdge(v11, v8);
        graph.addEdge(v13, v7);
        graph.addEdge(v12, v14);
        graph.addEdge(v14, v15);
        graph.addEdge(v14, v16);
        graph.addEdge(v15, v7);
        graph.addEdge(v16, v8);
        
        for(Vertex v : graph.getVertices()){
            v.addProperty("in-degree", v.getInDegree());
            v.addProperty("out-degree", v.getOutDegree());
        }

        String keyspace = "graphcredit1";
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
