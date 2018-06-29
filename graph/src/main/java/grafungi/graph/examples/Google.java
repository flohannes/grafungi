package grafungi.graph.examples;

import grafungi.graph.Graph;
import grafungi.graph.Vertex;
import grafungi.graph.db.GraphDatabaseAdapter;
import grafungi.graph.db.TitanConnector;


/**
 *
 * @author fschmidt
 */
public class Google {

    public static void main(String... args) {
        Graph graph = new Graph("Google");

        Vertex root = new Vertex();
        root.addProperty("name", "Should I Google it or not?");
        root.addProperty("description", "A guide for search addicts.");
        root.addProperty("type", "examination");
        root.addProperty("root", true);
        root.addProperty("source", "Internet");

        Vertex v1 = new Vertex();
        v1.addProperty("name", "Do you have access to the internet?");
        v1.addProperty("description", "Do you have a device with internet access?");
        v1.addProperty("type", "examination");
        
        Vertex v2 = new Vertex();
        v2.addProperty("name", "Yes");
        v2.addProperty("description", "Internet is working.");
        v2.addProperty("type", "symptom");
        Vertex v3 = new Vertex();
        v3.addProperty("name", "No");
        v3.addProperty("description", "");
        v3.addProperty("type", "symptom");

        Vertex v4 = new Vertex();
        v4.addProperty("name", "Does anyone neaby have access?");
        v4.addProperty("description",
                "Check if anyone around you might have a device with internet connection available.");
        v4.addProperty("type", "examination");
        
        Vertex v5 = new Vertex();
        v5.addProperty("name", "Yes");
        v5.addProperty("description",
                "Detected a device with internet connection.");
        v5.addProperty("type", "symptom");
        Vertex v6 = new Vertex();
        v6.addProperty("name", "No");
        v6.addProperty("description", "");
        v6.addProperty("type", "symptom");

        Vertex v7 = new Vertex();
        v7.addProperty("name", "Where are you? 1995?");
        v7.addProperty("description", "Are you a time traveler?");
        v7.addProperty("type", "diagnosis");
        
        Vertex v8 = new Vertex();
        v8.addProperty("name", "Are you willing to ask to use it?");
        v8.addProperty("description", "");
        v8.addProperty("type", "examination");

        Vertex v9 = new Vertex();
        v9.addProperty("name", "Yes");
        v9.addProperty("description", "I am willing!");
        v9.addProperty("type", "symptom");
        Vertex v10 = new Vertex();
        v10.addProperty("name", "No");
        v10.addProperty("description", "No chance that I ask for it.");
        v10.addProperty("type", "symptom");
        
        Vertex v11 = new Vertex();
        v11.addProperty("name", "You will never know!");
        v11.addProperty("description", "You will never know if you do not try.");
        v11.addProperty("type", "diagnosis");

        Vertex v12 = new Vertex();
        v12.addProperty("name", "Work or school related?");
        v12.addProperty("description", "Is your question work or school related?");
        v12.addProperty("type", "examination");

        Vertex v13 = new Vertex();
        v13.addProperty("name", "Yes");
        v13.addProperty("description", "It is for school or work.");
        v13.addProperty("type", "diagnosis");
        Vertex v14 = new Vertex();
        v14.addProperty("name", "No");
        v14.addProperty("description", "For something else.");
        v14.addProperty("type", "examination");

        Vertex v15 = new Vertex();
        v15.addProperty("name", "Google it!");
        v15.addProperty("description", "Give it a try.");
        v15.addProperty("type", "therapy");
        
        Vertex v16 = new Vertex();
        v16.addProperty("name", "For boss or teacher?");
        v16.addProperty("description", "Is it for your boss or teacher?");
        v16.addProperty("type", "examination");

        Vertex v17 = new Vertex();
        v17.addProperty("name", "Yes");
        v17.addProperty("description", "It is for my teacher or boss.");
        v17.addProperty("type", "symptom");
        Vertex v18 = new Vertex();
        v18.addProperty("name", "No");
        v18.addProperty("description", "For me or someone else.");
        v18.addProperty("type", "symptom");
        
        Vertex v19 = new Vertex();
        v19.addProperty("name", "For a group project?");
        v19.addProperty("description", "Is it for a group project?");
        v19.addProperty("type", "examination");

        Vertex v20 = new Vertex();
        v20.addProperty("name", "Yes");
        v20.addProperty("description", "For the group!");
        v20.addProperty("type", "symptom");
        Vertex v21 = new Vertex();
        v21.addProperty("name", "No");
        v21.addProperty("description", "");
        v21.addProperty("type", "symptom");

        Vertex v22 = new Vertex();
        v22.addProperty("name", "Important question?");
        v22.addProperty("description", "Is it an important question?");
        v22.addProperty("type", "examination");

        Vertex v23 = new Vertex();
        v23.addProperty("name", "Yes");
        v23.addProperty("description", "Definetly important!");
        v23.addProperty("type", "symptom");
        Vertex v24 = new Vertex();
        v24.addProperty("name", "No");
        v24.addProperty("description", "Not important.");
        v24.addProperty("type", "symptom");
        
        Vertex v25 = new Vertex();
        v25.addProperty("name", "Do something productive");
        v25.addProperty("description", "Do something productive instead.");
        v25.addProperty("type", "therapy");
        
        Vertex v26 = new Vertex();
        v26.addProperty("name", "Do you need the answer right now?");
        v26.addProperty("description", "");
        v26.addProperty("type", "examination");
        
        Vertex v27 = new Vertex();
        v27.addProperty("name", "Yes");
        v27.addProperty("description", "Need quick answer!");
        v27.addProperty("type", "symptom");
        Vertex v28 = new Vertex();
        v28.addProperty("name", "No");
        v28.addProperty("description", "I have time.");
        v28.addProperty("type", "symptom");
        
        Vertex v29 = new Vertex();
        v29.addProperty("name", "Google it later");
        v29.addProperty("description", "");
        v29.addProperty("type", "therapy");
        
        Vertex v30 = new Vertex();
        v30.addProperty("name", "Are you alone?");
        v30.addProperty("description", "");
        v30.addProperty("type", "examination");
        
        Vertex v31 = new Vertex();
        v31.addProperty("name", "Yes");
        v31.addProperty("description", "");
        v31.addProperty("type", "symptom");
        Vertex v32 = new Vertex();
        v32.addProperty("name", "No");
        v32.addProperty("description", "");
        v32.addProperty("type", "symptom");

        Vertex v33 = new Vertex();
        v33.addProperty("name", "Contrary answers?");
        v33.addProperty("description", "Is there a disagreement on the answer to the question?");
        v33.addProperty("type", "examination");
        
        Vertex v34 = new Vertex();
        v34.addProperty("name", "Yes");
        v34.addProperty("description", "");
        v34.addProperty("type", "symptom");
        Vertex v35 = new Vertex();
        v35.addProperty("name", "No");
        v35.addProperty("description", "");
        v35.addProperty("type", "symptom");
        
        Vertex v36 = new Vertex();
        v36.addProperty("name", "Wager on the answer?");
        v36.addProperty("description", "Is there a wager on the answer?");
        v36.addProperty("type", "examination");
        
        Vertex v37 = new Vertex();
        v37.addProperty("name", "Yes");
        v37.addProperty("description", "");
        v37.addProperty("type", "symptom");
        Vertex v38 = new Vertex();
        v38.addProperty("name", "No");
        v38.addProperty("description", "Nothing to loose.");
        v38.addProperty("type", "symptom");
        
        Vertex v39 = new Vertex();
        v39.addProperty("name", "Do you think you will win?");
        v39.addProperty("description", "");
        v39.addProperty("type", "examination");
        
        Vertex v40 = new Vertex();
        v40.addProperty("name", "Yes");
        v40.addProperty("description", "I can!");
        v40.addProperty("type", "symptom");
        Vertex v41 = new Vertex();
        v41.addProperty("name", "No");
        v41.addProperty("description", "");
        v41.addProperty("type", "symptom");
        
        Vertex v42 = new Vertex();
        v39.addProperty("name", "Do not let them Google it!");
        v39.addProperty("description", "");
        v39.addProperty("type", "therapy");
        
        Vertex v43 = new Vertex();
        v43.addProperty("name", "Deadline?");
        v43.addProperty("description", "Is there a deadline for the answer");
        v43.addProperty("type", "examination");
        
        Vertex v44 = new Vertex();
        v44.addProperty("name", "Yes");
        v44.addProperty("description", "");
        v44.addProperty("type", "symptom");
        Vertex v45 = new Vertex();
        v45.addProperty("name", "No");
        v45.addProperty("description", "");
        v45.addProperty("type", "symptom");
        
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
        graph.addVertex(v24);
        graph.addVertex(v25);
        graph.addVertex(v26);
        graph.addVertex(v27);
        graph.addVertex(v28);
        graph.addVertex(v29);
        graph.addVertex(v30);
        graph.addVertex(v31);
        graph.addVertex(v32);
        graph.addVertex(v33);
        graph.addVertex(v34);
        graph.addVertex(v35);
        graph.addVertex(v36);
        graph.addVertex(v37);
        graph.addVertex(v38);
        graph.addVertex(v39);
        graph.addVertex(v40);
        graph.addVertex(v40);
        graph.addVertex(v41);
        graph.addVertex(v42);
        graph.addVertex(v43);
        graph.addVertex(v44);
        graph.addVertex(v45);

        graph.addEdge(root, v1);
        graph.addEdge(v1, v2);
        graph.addEdge(v1, v3);
        graph.addEdge(v3, v4);
        graph.addEdge(v4, v5);
        graph.addEdge(v4, v6);
        graph.addEdge(v6, v7);
        graph.addEdge(v5, v8);
        graph.addEdge(v8, v9);
        graph.addEdge(v8, v10);
        graph.addEdge(v8, v10);
        graph.addEdge(v10, v11);
        graph.addEdge(v9, v12);
        graph.addEdge(v2, v12);
        graph.addEdge(v12, v13);
        graph.addEdge(v12, v14);
        graph.addEdge(v14, v30);
        graph.addEdge(v13, v43);
        graph.addEdge(v43, v44);
        graph.addEdge(v43, v45);
        graph.addEdge(v44, v15);
        graph.addEdge(v45, v16);
        graph.addEdge(v16, v17);
        graph.addEdge(v16, v18);
        graph.addEdge(v17, v15);
        graph.addEdge(v18, v19);
        graph.addEdge(v19, v20);
        graph.addEdge(v19, v21);
        graph.addEdge(v20, v15);
        graph.addEdge(v21, v22);
        graph.addEdge(v22, v23);
        graph.addEdge(v22, v24);
        graph.addEdge(v24, v25);
        graph.addEdge(v23, v26);
        graph.addEdge(v26, v27);
        graph.addEdge(v26, v28);
        graph.addEdge(v27, v15);
        graph.addEdge(v28, v29);
        graph.addEdge(v30, v31);
        graph.addEdge(v30, v32);
        graph.addEdge(v31, v22);
        graph.addEdge(v32, v33);
        graph.addEdge(v33, v34);
        graph.addEdge(v33, v35);
        graph.addEdge(v35, v22);
        graph.addEdge(v34, v36);
        graph.addEdge(v36, v37);
        graph.addEdge(v36, v38);
        graph.addEdge(v38, v22);
        graph.addEdge(v37, v39);
        graph.addEdge(v39, v40);
        graph.addEdge(v39, v41);
        graph.addEdge(v40, v15);
        graph.addEdge(v41, v42);
        
        for(Vertex v : graph.getVertices()){
            v.addProperty("in-degree", v.getInDegree());
            v.addProperty("out-degree", v.getOutDegree());
        }

        String keyspace = "graphgoogle1";
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
