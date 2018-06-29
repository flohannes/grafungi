package grafungi.graph.examples;

import grafungi.graph.Graph;
import grafungi.graph.Vertex;
import grafungi.graph.db.GraphDatabaseAdapter;
import grafungi.graph.db.TitanConnector;
import java.util.UUID;

/**
 *
 * @author fschmidt
 */
public class Dyspepsia {

    public static void main(String... args) {
        Graph graph = new Graph("Dyspepsia");

        Vertex root = new Vertex();
        root.addProperty("name", "Dyspepsia");
        root.addProperty("description", "Dyspepsia, also known as indigestion, is a condition of impaired digestion.");
        root.addProperty("type", "diagnosis");
        root.addProperty("root", true);
        root.addProperty("source", "Gastro Book");
        root.addProperty("concept", UUID.randomUUID());

        Vertex v1 = new Vertex();
        v1.addProperty("name", "Age > 55 years");
        v1.addProperty("description", "Person is older than 55 years.");
        v1.addProperty("type", "symptom");
        v1.addProperty("concept", UUID.randomUUID());
        Vertex v2 = new Vertex();
        v2.addProperty("name", "Age < 55 years");
        v2.addProperty("description", "Person is younger than 55 years.");
        v2.addProperty("type", "symptom");
        v2.addProperty("concept", UUID.randomUUID());

        Vertex v3 = new Vertex();
        v3.addProperty("name", "New alarm signal");
        v3.addProperty("description",
                "Any of these new occurring symptoms:Family anamnesis for malignancy of the upper gastrointestinal tract, past history of peptic uicer disease, unintentional weight loss, gastrointestinal bleeding or iron deficiency anemia, increasing dysphagia or odynophagia, persistent vomiting, icterus, tumor in abdomen, lymphadenopathy.");
        v3.addProperty("type", "symptom");
        v3.addProperty("concept", UUID.randomUUID());
        Vertex v4 = new Vertex();
        v4.addProperty("name", "No alarm signal");
        v4.addProperty("description",
                "No new occuring symptoms: Family anamnesis for malignancy of the upper gastrointestinal tract, past history of peptic uicer disease, unintentional weight loss, gastrointestinal bleeding or iron deficiency anemia, increasing dysphagia or odynophagia, persistent vomiting, icterus, tumor in abdomen, lymphadenopathy.");
        v4.addProperty("type", "symptom");
        v4.addProperty("concept", UUID.randomUUID());

        Vertex v5 = new Vertex();
        v5.addProperty("name", "H. pylori prevalence < 10%");
        v5.addProperty("description",
                "H. pylori prevalence and incidence differs by geography and race. Prevalence refers to the total number of people that have a disease at a given time, while incidence refers to new infections of a disease.");
        v5.addProperty("type", "symptom");
        v5.addProperty("concept", UUID.randomUUID());
        Vertex v6 = new Vertex();
        v6.addProperty("name", "H. pylori prevalence >= 10%");
        v6.addProperty("description",
                "H. pylori prevalence and incidence differs by geography and race. Prevalence refers to the total number of people that have a disease at a given time, while incidence refers to new infections of a disease.");
        v6.addProperty("type", "symptom");
        v6.addProperty("concept", UUID.randomUUID());

        Vertex v7 = new Vertex();
        v7.addProperty("name", "PPI therapy trial");
        v7.addProperty("description",
                "Proton pump inhibitors (PPIs) are a group of drugs whose main action is a pronounced and long-lasting reduction of gastric acid production.");
        v7.addProperty("type", "therapy");
        v7.addProperty("concept", UUID.randomUUID());

        Vertex v8 = new Vertex();
        v8.addProperty("name", "H. pylori test");
        v8.addProperty("description", "If necessary therapy.");
        v8.addProperty("type", "examination");
        v8.addProperty("concept", UUID.randomUUID());

        Vertex v9 = new Vertex();
        v9.addProperty("name", "Endosycopy");
        v9.addProperty("description", "Endoscopy is a nonsurgical procedure used to examine a person's digestive tract.");
        v9.addProperty("type", "examination");
        v9.addProperty("concept", UUID.randomUUID());

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

        graph.addEdge(root, v1).addProperty("prob", 0.5);
        graph.addEdge(root, v2).addProperty("prob", 0.5);
        graph.addEdge(v1, v3).addProperty("prob", 0.5);
        graph.addEdge(v3, v9).addProperty("prob", 0.5);
        graph.addEdge(v2, v4).addProperty("prob", 0.5);
        graph.addEdge(v4, v5).addProperty("prob", 0.2);
        graph.addEdge(v4, v6).addProperty("prob", 0.3);
        graph.addEdge(v5, v7).addProperty("prob", 0.2);
        graph.addEdge(v7, v9).addProperty("prob", 0.5);
        graph.addEdge(v6, v8).addProperty("prob", 0.3);
        graph.addEdge(v8, v9).addProperty("prob", 0.3);
        
        for(Vertex v : graph.getVertices()){
            v.addProperty("in-degree", v.getInDegree());
            v.addProperty("out-degree", v.getOutDegree());
        }

        String keyspace = "graphdyspepsia1";
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
