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
public class Diarrhea {

    public static void main(String... args) {
        Graph graph = new Graph("Diarrhea");

        Vertex root = new Vertex();
        root.addProperty("name", "Diarrhea");
        root.addProperty("description", "Nonspecific chronic diarrhea (>4 weeks)");
        root.addProperty("type", "diagnosis");
        root.addProperty("root", true);
        root.addProperty("source", "Gastro Book");
        root.addProperty("concept", UUID.randomUUID());

        Vertex v1 = new Vertex();
        v1.addProperty("name", "Has red flags");
        v1.addProperty("description", "Has any of these symptoms: Age >50 years, blood in stool, postexposure to antibiotics, anaemia.");
        v1.addProperty("type", "symptom");
        v1.addProperty("concept", UUID.randomUUID());
        Vertex v2 = new Vertex();
        v2.addProperty("name", "No red flags");
        v2.addProperty("description", "Has non of these symptoms: Age >50 years, blood in stool, postexposure to antibiotics, anaemia.");
        v2.addProperty("type", "symptom");
        v2.addProperty("concept", UUID.randomUUID());

        Vertex v3 = new Vertex();
        v3.addProperty("name", "Endoscopy");
        v3.addProperty("description", "Endoscopy is a nonsurgical procedure used to examine a person's digestive tract.");
        v3.addProperty("type", "examination");
        v3.addProperty("concept", UUID.randomUUID());

        Vertex v4 = new Vertex();
        v4.addProperty("name", "Has calprotectin in stool");
        v4.addProperty("description",
                "Faecal calprotectin is a biochemical measurement of the protein calprotectin in the stool. Elevated faecal calprotectin indicates the migration of neutrophils to the intestinal mucosa, which occurs during intestinal inflammation, including inflammation caused by inflammatory bowel disease.");
        v4.addProperty("type", "symptom");
        v4.addProperty("concept", UUID.randomUUID());
        Vertex v5 = new Vertex();
        v5.addProperty("name", "No calprotectin in stool");
        v5.addProperty("description",
                "Faecal calprotectin is a biochemical measurement of the protein calprotectin in the stool. Elevated faecal calprotectin indicates the migration of neutrophils to the intestinal mucosa, which occurs during intestinal inflammation, including inflammation caused by inflammatory bowel disease.");
        v5.addProperty("type", "symptom");
        v5.addProperty("concept", UUID.randomUUID());

        Vertex v6 = new Vertex();
        v6.addProperty("name", "Inflammatory diarrhea");
        v6.addProperty("description",
                "Inflammatory diarrhea occurs when there is damage to the mucosal lining or brush border, which leads to a passive loss of protein-rich fluids and a decreased ability to absorb these lost fluids.");
        v6.addProperty("type", "diagnosis");
        v6.addProperty("concept", UUID.randomUUID());

        Vertex v7 = new Vertex();
        v7.addProperty("name", "Positive stool bacteriology");
        v7.addProperty("description", "Check stool for parasites 3 times, small bowel biopsy, small bowel fluid aspiration.");
        v7.addProperty("type", "symptom");
        v7.addProperty("concept", UUID.randomUUID());
        Vertex v8 = new Vertex();
        v8.addProperty("name", "Negative stool bacteriology");
        v8.addProperty("description", "Check stool for parasites 3 times, small bowel biopsy, small bowel fluid aspiration.");
        v8.addProperty("type", "symptom");
        v8.addProperty("concept", UUID.randomUUID());

        Vertex v9 = new Vertex();
        v9.addProperty("name", "Infectious diarrhea");
        v9.addProperty("description",
                "Gastroenteritis, also known as infectious diarrhea, is inflammation of the gastrointestinal tract that involves the stomach and small intestine.");
        v9.addProperty("type", "diagnosis");
        v9.addProperty("concept", UUID.randomUUID());

        Vertex v10 = new Vertex();
        v10.addProperty("name", "Stool weight >300g");
        v10.addProperty("description", "Check 24h stool weight.");
        v10.addProperty("type", "symptom");
        v10.addProperty("concept", UUID.randomUUID());
        Vertex v11 = new Vertex();
        v11.addProperty("name", "Stool weight <300g");
        v11.addProperty("description", "Check 24h stool weight.");
        v11.addProperty("type", "symptom");
        v11.addProperty("concept", UUID.randomUUID());

        Vertex v12 = new Vertex();
        v12.addProperty("name", "Pseudo diarrhea");
        v12.addProperty("description", "Incontinence, irritable bowel syndrome.");
        v12.addProperty("type", "diagnosis");
        v12.addProperty("concept", UUID.randomUUID());

        Vertex v13 = new Vertex();
        v13.addProperty("name", "Genuine diarrhea");
        v13.addProperty("description", "");
        v13.addProperty("type", "diagnosis");
        v13.addProperty("concept", UUID.randomUUID());

        Vertex v14 = new Vertex();
        v14.addProperty("name", "Pancreas MR/CT-enteroclysis");
        v14.addProperty("description", "");
        v14.addProperty("type", "examination");
        v14.addProperty("concept", UUID.randomUUID());

        Vertex v15 = new Vertex();
        v15.addProperty("name", "Stool fat <7g");
        v15.addProperty("description", "Check 24h stool fat.");
        v15.addProperty("type", "symptom");
        v15.addProperty("concept", UUID.randomUUID());
        Vertex v16 = new Vertex();
        v16.addProperty("name", "Stool fat >7g");
        v16.addProperty("description", "Check 24h stool fat.");
        v16.addProperty("type", "symptom");
        v16.addProperty("concept", UUID.randomUUID());

        Vertex v17 = new Vertex();
        v17.addProperty("name", "Steatorrhoea");
        v17.addProperty("description",
                "Steatorrhea (or steatorrhoea) is the presence of excess fat in feces. Stools may be bulky and difficult to flush, have a pale and oily appearance and can be especially foul-smelling.");
        v17.addProperty("type", "diagnosis");
        v17.addProperty("concept", UUID.randomUUID());

        Vertex v18 = new Vertex();
        v18.addProperty("name", "Osmotic gap >50 mOsmol/kg");
        v18.addProperty("description", "Stool osmotic gap is a calculation performed to distinguish among different causes of diarrhea.");
        v18.addProperty("type", "symptom");
        v18.addProperty("concept", UUID.randomUUID());
        Vertex v19 = new Vertex();
        v19.addProperty("name", "Osmotic gap <50 mOsmol/kg");
        v19.addProperty("description", "Stool osmotic gap is a calculation performed to distinguish among different causes of diarrhea.");
        v19.addProperty("type", "symptom");
        v19.addProperty("concept", UUID.randomUUID());

        Vertex v20 = new Vertex();
        v20.addProperty("name", "Fasting");
        v20.addProperty("description", "Test if fasting is applicable.");
        v20.addProperty("type", "therapy");
        v20.addProperty("concept", UUID.randomUUID());

        Vertex v21 = new Vertex();
        v21.addProperty("name", "Secretory diarrhea");
        v21.addProperty("description",
                "Secretory diarrhea means that there is an increase in the active secretion, or there is an inhibition of absorption.");
        v21.addProperty("type", "diagnosis");
        v21.addProperty("concept", UUID.randomUUID());

        Vertex v22 = new Vertex();
        v22.addProperty("name", "Osmotic diarrhea");
        v22.addProperty("description", "Osmotic diarrhea occurs when too much water is drawn into the bowels.");
        v22.addProperty("type", "diagnosis");
        v22.addProperty("concept", UUID.randomUUID());

        Vertex v23 = new Vertex();
        v23.addProperty("name", "Check differential diagnosis");
        v23.addProperty("description", "Special tests according to differential diagnosis.");
        v23.addProperty("type", "examination");
        v23.addProperty("concept", UUID.randomUUID());

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
        graph.addVertex(v19);
        graph.addVertex(v20);
        graph.addVertex(v21);
        graph.addVertex(v22);
        graph.addVertex(v23);

        graph.addEdge(root, v1);
        graph.addEdge(root, v2);
        graph.addEdge(v1, v3);
        graph.addEdge(v2, v4);
        graph.addEdge(v2, v5);
        graph.addEdge(v4, v6);
        graph.addEdge(v6, v3);
        graph.addEdge(v4, v8);
        graph.addEdge(v4, v7);
        graph.addEdge(v7, v9);
        graph.addEdge(v8, v10);
        graph.addEdge(v8, v11);
        graph.addEdge(v11, v12);
        graph.addEdge(v10, v13);
        graph.addEdge(v13, v15);
        graph.addEdge(v13, v16);
        graph.addEdge(v16, v17);
        graph.addEdge(v17, v14);
        graph.addEdge(v15, v18);
        graph.addEdge(v15, v19);
        graph.addEdge(v18, v22);
        graph.addEdge(v22, v23);
        graph.addEdge(v19, v20);
        graph.addEdge(v20, v21);
        graph.addEdge(v21, v23);
        
        for(Vertex v : graph.getVertices()){
            v.addProperty("in-degree", v.getInDegree());
            v.addProperty("out-degree", v.getOutDegree());
        }

        String keyspace = "graphdiarrhea1";
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
