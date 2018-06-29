package grafungi.query.ws.data.util;

import grafungi.graph.Graph;
import grafungi.graph.Vertex;
import grafungi.graph.bn.BayesianNetwork;
import grafungi.graph.bn.Entity;
import grafungi.graph.bn.io.BIFFormat;
import grafungi.graph.db.GraphDatabaseAdapter;
import grafungi.graph.db.TitanConnector;
import grafungi.graph.io.d3.D3Converter;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fschmidt
 */
public class DummyGraphs {

    public static void create(String dbPath) {
        includeDiarrhea(dbPath);
        includeDyspepsia(dbPath);
        includeGoogle(dbPath);
        includeHCC(dbPath);
        includeHappy(dbPath);
        includeKreditwuerdigkeit(dbPath);
        includeLungBN(dbPath);
        includeCancerBN(dbPath);
        
        try {
            includeBifGraphs(dbPath);
        } catch (URISyntaxException ex) {
            Logger.getLogger(DummyGraphs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void includeDiarrhea(String dbPath) {
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

        for (Vertex v : graph.getVertices()) {
            v.addProperty("in-degree", v.getInDegree());
            v.addProperty("out-degree", v.getOutDegree());
        }

        String keyspace = "graphdiarrhea1";
        GraphDatabaseAdapter db = new TitanConnector();
        db.connect(dbPath + "" + keyspace);
        db.addGraph(graph);
        db.close();

    }

    private static void includeDyspepsia(String dbPath) {
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

        for (Vertex v : graph.getVertices()) {
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
        db.connect(dbPath + "" + keyspace);
        db.addGraph(graph);
        db.close();
    }

    private static void includeGoogle(String dbPath) {
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

        for (Vertex v : graph.getVertices()) {
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
        db.connect(dbPath + "" + keyspace);
        db.addGraph(graph);
        db.close();
    }

    private static void includeHCC(String dbPath) {
        Graph graph = new Graph("HCC");

        Vertex root = new Vertex();
        root.addProperty("name", "HCC");
        root.addProperty("description",
                "Hepatocellular carcinoma (HCC), also called malignant hepatoma, is the most common type of liver cancer.");
        root.addProperty("type", "diagnosis");
        root.addProperty("root", true);
        root.addProperty("source", "Hartmut Schmidt, Klinik für Transplantationsmedizin, UKM");
        root.addProperty("concept", UUID.randomUUID());

        Vertex v0 = new Vertex();
        v0.addProperty("name", "Treatment of underlying liver disease");
        v0.addProperty("description", "Treatment of underlying liver disease (HCV, HBV, etc.).");
        v0.addProperty("type", "therapy");
        v0.addProperty("concept", UUID.randomUUID());

        Vertex v1 = new Vertex();
        v1.addProperty("name", "Surgical resectabile");
        v1.addProperty("description", "Can be the region of interest in the liver be removed.");
        v1.addProperty("type", "examination");
        v1.addProperty("concept", UUID.randomUUID());
        Vertex v2 = new Vertex();
        v2.addProperty("name", "Not surgical resectabile");
        v2.addProperty("description", "Check if the region of interest in the liver can be removed.");
        v2.addProperty("type", "examination");
        v2.addProperty("concept", UUID.randomUUID());

        Vertex v3 = new Vertex();
        v3.addProperty("name", "Imaging biomarker: 1 lesion and < 8cm");
        v3.addProperty("description", "Image as biomarker to check for 1 lesion, which is <8cm.");
        v3.addProperty("type", "examination");
        v3.addProperty("concept", UUID.randomUUID());
        Vertex v4 = new Vertex();
        v4.addProperty("name", "Imaging biomarker: 2-8 lesions any < 8 cm");
        v4.addProperty("description", "Image as biomarker to check for 2-8 lesions, where any < 8 cm.");
        v4.addProperty("type", "examination");
        v4.addProperty("concept", UUID.randomUUID());
        Vertex v5 = new Vertex();
        v5.addProperty("name", "Imaging biomarker: >8 lesions or diffuse or any ≥ 8cm");
        v5.addProperty("description", "Image as biomarker to check for >8 lesions or diffuse or any ≥ 8cm.");
        v5.addProperty("type", "examination");
        v5.addProperty("concept", UUID.randomUUID());

        Vertex v6 = new Vertex();
        v6.addProperty("name", "has hyperperfusion");
        v6.addProperty("description", "Check for hyperperfusion using CT/MRI/ultrasound.");
        v6.addProperty("type", "symptom");
        v6.addProperty("concept", UUID.randomUUID());
        Vertex v7 = new Vertex();
        v7.addProperty("name", "has not hyperperfusion");
        v7.addProperty("description", "Check for hyperperfusion using CT/MRI/ultrasound.");
        v7.addProperty("type", "symptom");
        v7.addProperty("concept", UUID.randomUUID());

        Vertex v8 = new Vertex();
        v8.addProperty("name", "has hyperperfusion");
        v8.addProperty("description", "Check for hyperperfusion using CT/MRI/ultrasound.");
        v8.addProperty("type", "symptom");
        v8.addProperty("concept", UUID.randomUUID());
        Vertex v9 = new Vertex();
        v9.addProperty("name", "has not hyperperfusion");
        v9.addProperty("description", "Check for hyperperfusion using CT/MRI/ultrasound.");
        v9.addProperty("type", "symptom");
        v9.addProperty("concept", UUID.randomUUID());

        Vertex v10 = new Vertex();
        v10.addProperty("name", "has hyperperfusion");
        v10.addProperty("description", "Check for hyperperfusion using CT/MRI/ultrasound.");
        v10.addProperty("type", "symptom");
        v10.addProperty("concept", UUID.randomUUID());
        Vertex v11 = new Vertex();
        v11.addProperty("name", "has not hyperperfusion");
        v11.addProperty("description", "Check for hyperperfusion using CT/MRI/ultrasound.");
        v11.addProperty("type", "symptom");
        v11.addProperty("concept", UUID.randomUUID());

        Vertex v12 = new Vertex();
        v12.addProperty("name", "DEB-TACE");
        v12.addProperty("description",
                "Doxorubicin-eluting bead transarterial chemoembolization (DEB-TACE) is a novel locoregional treatment for unresectable hepatocellular carcinoma (HCC).");
        v12.addProperty("type", "therapy");
        v12.addProperty("concept", UUID.randomUUID());
        Vertex v13 = new Vertex();
        v13.addProperty("name", "RFA");
        v13.addProperty("description",
                "Percutaneous radiofrequency ablation (RFA) is an exciting approach to destroying inoperable primary tumors or metastases in the liver.");
        v13.addProperty("type", "therapy");
        v13.addProperty("concept", UUID.randomUUID());

        Vertex v14 = new Vertex();
        v14.addProperty("name", "DEB-TACE");
        v14.addProperty("description",
                "Doxorubicin-eluting bead transarterial chemoembolization (DEB-TACE) is a novel locoregional treatment for unresectable hepatocellular carcinoma (HCC).");
        v14.addProperty("type", "therapy");
        v14.addProperty("concept", UUID.randomUUID());
        Vertex v15 = new Vertex();
        v15.addProperty("name", "RFA");
        v15.addProperty("description",
                "Percutaneous radiofrequency ablation (RFA) is an exciting approach to destroying inoperable primary tumors or metastases in the liver.");
        v15.addProperty("type", "therapy");
        v15.addProperty("concept", UUID.randomUUID());

        Vertex v16 = new Vertex();
        v16.addProperty("name", "SIRT");
        v16.addProperty("description",
                "Selective internal radiation therapy (SIRT) is a form of radiation therapy used in interventional radiology to treat cancer. Survival < 6 months.");
        v16.addProperty("type", "therapy");
        v16.addProperty("concept", UUID.randomUUID());
        Vertex v17 = new Vertex();
        v17.addProperty("name", "Palliative");
        v17.addProperty("description", "Survival < 3 months.");
        v17.addProperty("type", "therapy");
        v17.addProperty("concept", UUID.randomUUID());

        Vertex v18 = new Vertex();
        v18.addProperty("name", "Has residual hyperperfusion");
        v18.addProperty("description", "Increased perfusion of blood through an organ.");
        v18.addProperty("type", "symptom");
        v18.addProperty("concept", UUID.randomUUID());
        Vertex v19 = new Vertex();
        v19.addProperty("name", "No residual hyperperfusion");
        v19.addProperty("description", "No increased perfusion of blood through an organ.");
        v19.addProperty("type", "symptom");
        v19.addProperty("concept", UUID.randomUUID());

        Vertex v20 = new Vertex();
        v20.addProperty("name", "RFA +- Sorafenib");
        v20.addProperty("description",
                "Percutaneous radiofrequency ablation (RFA) is an exciting approach to destroying inoperable primary tumors or metastases in the liver. Sorafenib (co-developed and co-marketed by Bayer and Onyx Pharmaceuticals as Nexavar), is a kinase inhibitor drug approved for the treatment of primary kidney cancer (advanced renal cell carcinoma), advanced primary liver cancer (hepatocellular carcinoma), and radioactive iodine resistant advanced thyroid carcinoma.");
        v20.addProperty("type", "therapy");
        v20.addProperty("concept", UUID.randomUUID());

        Vertex v21 = new Vertex();
        v21.addProperty("name", "CT/MRI reevaluation");
        v21.addProperty("description", "CT/MRI reevaluation each 3 months.");
        v21.addProperty("type", "examination");
        v21.addProperty("concept", UUID.randomUUID());

        Vertex v22 = new Vertex();
        v22.addProperty("name", "Positive response");
        v22.addProperty("description", "Positive response to therapy.");
        v22.addProperty("type", "examination");
        v22.addProperty("concept", UUID.randomUUID());
        Vertex v23 = new Vertex();
        v23.addProperty("name", "Non-response");
        v23.addProperty("description", "Negative response to therapy");
        v23.addProperty("type", "examination");
        v23.addProperty("concept", UUID.randomUUID());

        Vertex v24 = new Vertex();
        v24.addProperty("name", "CT/MRI reevaluation");
        v24.addProperty("description", "CT/MRI reevaluation each 3 months.");
        v24.addProperty("type", "examination");
        v24.addProperty("concept", UUID.randomUUID());
        Vertex v25 = new Vertex();
        v25.addProperty("name", "Positive response");
        v25.addProperty("description", "Positive response to therapy.");
        v25.addProperty("type", "examination");
        v25.addProperty("concept", UUID.randomUUID());

        Vertex v26 = new Vertex();
        v26.addProperty("name", "lesion <= 5cm");
        v26.addProperty("description", "Lesion is smaller (or equal) than 5cm.");
        v26.addProperty("type", "examination");
        v26.addProperty("concept", UUID.randomUUID());
        Vertex v27 = new Vertex();
        v27.addProperty("name", "lesion > 5cm");
        v27.addProperty("description", "Lesion is bigger than 5cm.");
        v27.addProperty("type", "examination");
        v27.addProperty("concept", UUID.randomUUID());

        Vertex v28 = new Vertex();
        v28.addProperty("name", "SBRT");
        v28.addProperty("description", "Stereotactic body radiation therapy (SBRT). Disease-free: 0%%, 2-yr-survival 18-53%%.");
        v28.addProperty("type", "therapy");
        v28.addProperty("concept", UUID.randomUUID());

        //stripe 2
        Vertex v29 = new Vertex();
        v29.addProperty("name", "Has residual hyperperfusion");
        v29.addProperty("description", "Increased perfusion of blood through an organ.");
        v29.addProperty("type", "symptom");
        v29.addProperty("concept", UUID.randomUUID());
        Vertex v30 = new Vertex();
        v30.addProperty("name", "No residual hyperperfusion");
        v30.addProperty("description", "No increased perfusion of blood through an organ.");
        v30.addProperty("type", "symptom");
        v30.addProperty("concept", UUID.randomUUID());

        Vertex v31 = new Vertex();
        v31.addProperty("name", "RFA +- Sorafenib");
        v31.addProperty("description",
                "Percutaneous radiofrequency ablation (RFA) is an exciting approach to destroying inoperable primary tumors or metastases in the liver. Sorafenib (co-developed and co-marketed by Bayer and Onyx Pharmaceuticals as Nexavar), is a kinase inhibitor drug approved for the treatment of primary kidney cancer (advanced renal cell carcinoma), advanced primary liver cancer (hepatocellular carcinoma), and radioactive iodine resistant advanced thyroid carcinoma.");
        v31.addProperty("type", "therapy");
        v31.addProperty("concept", UUID.randomUUID());

        Vertex v32 = new Vertex();
        v32.addProperty("name", "CT/MRI reevaluation");
        v32.addProperty("description", "CT/MRI reevaluation each 3 months.");
        v32.addProperty("type", "examination");
        v32.addProperty("concept", UUID.randomUUID());

        Vertex v33 = new Vertex();
        v33.addProperty("name", "Positive response");
        v33.addProperty("description", "Positive response to therapy.");
        v33.addProperty("type", "examination");
        v33.addProperty("concept", UUID.randomUUID());
        Vertex v34 = new Vertex();
        v34.addProperty("name", "Non-response");
        v34.addProperty("description", "Negative response to therapy");
        v34.addProperty("type", "examination");
        v34.addProperty("concept", UUID.randomUUID());

        Vertex v35 = new Vertex();
        v35.addProperty("name", "SIRT");
        v35.addProperty("description",
                "Selective internal radiation therapy (SIRT) is a form of radiation therapy used in interventional radiology to treat cancer. Survival < 9 months.");
        v35.addProperty("type", "therapy");
        v35.addProperty("concept", UUID.randomUUID());

        Vertex v36 = new Vertex();
        v36.addProperty("name", "CT/MRI reevaluation");
        v36.addProperty("description", "CT/MRI reevaluation each 3 months.");
        v36.addProperty("type", "examination");
        v36.addProperty("concept", UUID.randomUUID());
        Vertex v37 = new Vertex();
        v37.addProperty("name", "Positive response");
        v37.addProperty("description", "Positive response to therapy.");
        v37.addProperty("type", "examination");
        v37.addProperty("concept", UUID.randomUUID());
        Vertex v38 = new Vertex();
        v38.addProperty("name", "Non-response");
        v38.addProperty("description", "Negative response to therapy");
        v38.addProperty("type", "examination");
        v38.addProperty("concept", UUID.randomUUID());

        Vertex v39 = new Vertex();
        v39.addProperty("name", "<= 3 lesions, each <= 5cm");
        v39.addProperty("description", "At most 3 lesions, where each is smaller (or equal) than 5cm.");
        v39.addProperty("type", "examination");
        v39.addProperty("concept", UUID.randomUUID());
        Vertex v40 = new Vertex();
        v40.addProperty("name", "> 3 lesions");
        v40.addProperty("description", "More than 3 lesions present.");
        v40.addProperty("type", "examination");
        v40.addProperty("concept", UUID.randomUUID());
        Vertex v41 = new Vertex();
        v41.addProperty("name", "any lesion > 5cm");
        v41.addProperty("description", "Any lesion is bigger than 5cm.");
        v41.addProperty("type", "examination");
        v41.addProperty("concept", UUID.randomUUID());

        Vertex v42 = new Vertex();
        v42.addProperty("name", "SBRT");
        v42.addProperty("description", "Stereotactic body radiation therapy (SBRT). Survival < 6 months.");
        v42.addProperty("type", "therapy");
        v42.addProperty("concept", UUID.randomUUID());

        //FIRST PART
        Vertex v43 = new Vertex();
        v43.addProperty("name", "Fulfils criteria");
        v43.addProperty("description",
                "Check criteria: i) portal hypertension, ii) LVVD > 10 mmHg, iii) > 2 lesions, iv) gastroesophageal varices, v) splenomegaly > 12 cm, vi) bilirubin > 3 mg/dl, vii) Fibroscan > 8.5 kPa, viii) > 3 cm.");
        v43.addProperty("type", "examination");
        v43.addProperty("concept", UUID.randomUUID());
        Vertex v44 = new Vertex();
        v44.addProperty("name", "Does not fulfil criteria");
        v44.addProperty("description",
                "Check if any criteria is not fulfiled: i) portal hypertension, ii) LVVD > 10 mmHg, iii) > 2 lesions, iv) gastroesophageal varices, v) splenomegaly > 12 cm, vi) bilirubin > 3 mg/dl, vii) Fibroscan > 8.5 kPa, viii) > 3 cm.");
        v44.addProperty("type", "examination");
        v44.addProperty("concept", UUID.randomUUID());

        Vertex v45 = new Vertex();
        v45.addProperty("name", "Surgical resection");
        v45.addProperty("description", "5-yr-disease-free: 16-34 %%, 5-yr-survival 40-53%%.");
        v45.addProperty("type", "therapy");
        v45.addProperty("concept", UUID.randomUUID());

        Vertex v46 = new Vertex();
        v46.addProperty("name", "Fulfils transplant criteria");
        v46.addProperty("description", "Check current transplant criteria.");
        v46.addProperty("type", "examination");
        v46.addProperty("concept", UUID.randomUUID());
        Vertex v47 = new Vertex();
        v47.addProperty("name", "Does not fulfil transplant criteria");
        v47.addProperty("description", "Check current transplant criteria.");
        v47.addProperty("type", "examination");
        v47.addProperty("concept", UUID.randomUUID());

        Vertex v48 = new Vertex();
        v48.addProperty("name", "Transplant waiting list");
        v48.addProperty("description", "Put patient on transplant waiting list.");
        v48.addProperty("type", "therapy");
        v48.addProperty("concept", UUID.randomUUID());

        Vertex v49 = new Vertex();
        v49.addProperty("name", "Fulfils CT/MRI 3D Milano criteria");
        v49.addProperty("description", "Check current CT/MRI 3D Milano criteria.");
        v49.addProperty("type", "examination");
        v49.addProperty("concept", UUID.randomUUID());
        Vertex v50 = new Vertex();
        v50.addProperty("name", "Does not fulfil CT/MRI 3D Milano criteria");
        v50.addProperty("description", "Check current CT/MRI 3D Milano criteria.");
        v50.addProperty("type", "examination");
        v50.addProperty("concept", UUID.randomUUID());

        Vertex v51 = new Vertex();
        v51.addProperty("name", "Bridging therapy");
        v51.addProperty("description", "Find bridging therapy.");
        v51.addProperty("type", "therapy");
        v51.addProperty("concept", UUID.randomUUID());

        Vertex v52 = new Vertex();
        v52.addProperty("name", "CT/MRI reevaluation");
        v52.addProperty("description", "CT/MRI reevaluation each 3 months.");
        v52.addProperty("type", "examination");
        v52.addProperty("concept", UUID.randomUUID());

        Vertex v53 = new Vertex();
        v53.addProperty("name", "Fulfils transplant criteria");
        v53.addProperty("description", "Check current transplant criteria.");
        v53.addProperty("type", "examination");
        v53.addProperty("concept", UUID.randomUUID());
        Vertex v54 = new Vertex();
        v54.addProperty("name", "Does not fulfil transplant criteria");
        v54.addProperty("description", "Check current transplant criteria.");
        v54.addProperty("type", "examination");
        v54.addProperty("concept", UUID.randomUUID());

        Vertex v55 = new Vertex();
        v55.addProperty("name", "Transplantation");
        v55.addProperty("description", "5-yr-disease-free: 77 %%, 5-yr-survival 57-75%%.");
        v55.addProperty("type", "therapy");
        v55.addProperty("concept", UUID.randomUUID());

        graph.addVertex(root);
        graph.addVertex(v0);
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
        graph.addVertex(v41);
        graph.addVertex(v42);
        graph.addVertex(v43);
        graph.addVertex(v44);
        graph.addVertex(v45);
        graph.addVertex(v46);
        graph.addVertex(v47);
        graph.addVertex(v48);
        graph.addVertex(v49);
        graph.addVertex(v50);
        graph.addVertex(v51);
        graph.addVertex(v52);
        graph.addVertex(v53);
        graph.addVertex(v54);
        graph.addVertex(v55);

        graph.addEdge(root, v1);
        graph.addEdge(root, v2);
        graph.addEdge(v2, v3);
        graph.addEdge(v2, v4);
        graph.addEdge(v2, v5);
        graph.addEdge(v3, v6);
        graph.addEdge(v3, v7);
        graph.addEdge(v4, v8);
        graph.addEdge(v4, v9);
        graph.addEdge(v5, v10);
        graph.addEdge(v5, v11);
        graph.addEdge(v6, v12);
        graph.addEdge(v7, v13);
        graph.addEdge(v8, v14);
        graph.addEdge(v9, v15);
        graph.addEdge(v10, v16);
        graph.addEdge(v11, v17);
        graph.addEdge(v12, v18);
        graph.addEdge(v12, v19);
        graph.addEdge(v18, v20);
        graph.addEdge(v19, v21);
        graph.addEdge(v20, v21);
        graph.addEdge(v21, v22);
        graph.addEdge(v21, v23);
        graph.addEdge(v22, v12);
        graph.addEdge(v13, v24);
        graph.addEdge(v24, v25);
        graph.addEdge(v24, v23);
        graph.addEdge(v25, v13);
        graph.addEdge(v23, v26);
        graph.addEdge(v23, v27);
        graph.addEdge(v26, v28);
        graph.addEdge(v27, v10);
        graph.addEdge(v27, v11);

        graph.addEdge(v14, v29);
        graph.addEdge(v14, v30);
        graph.addEdge(v29, v31);
        graph.addEdge(v31, v32);
        graph.addEdge(v30, v32);
        graph.addEdge(v32, v33);
        graph.addEdge(v32, v34);
        graph.addEdge(v33, v14);
        graph.addEdge(v34, v35);

        graph.addEdge(v15, v36);
        graph.addEdge(v36, v37);
        graph.addEdge(v36, v38);
        graph.addEdge(v37, v15);
        graph.addEdge(v38, v39);
        graph.addEdge(v38, v40);
        graph.addEdge(v38, v41);
        graph.addEdge(v39, v42);
        graph.addEdge(v40, v10);
        graph.addEdge(v40, v11);
        graph.addEdge(v41, v10);
        graph.addEdge(v41, v11);

        graph.addEdge(v1, v43);
        graph.addEdge(v1, v44);
        graph.addEdge(v44, v45);
        graph.addEdge(v43, v46);
        graph.addEdge(v43, v47);
        graph.addEdge(v47, v3);
        graph.addEdge(v47, v4);
        graph.addEdge(v47, v5);
        graph.addEdge(v46, v48);
        graph.addEdge(v48, v49);
        graph.addEdge(v48, v50);
        graph.addEdge(v49, v51);
        graph.addEdge(v50, v51);
        graph.addEdge(v51, v52);
        graph.addEdge(v52, v53);
        graph.addEdge(v52, v54);
        graph.addEdge(v54, v3);
        graph.addEdge(v54, v4);
        graph.addEdge(v54, v5);
        graph.addEdge(v53, v55);

        for (Vertex v : graph.getVertices()) {
            v.addProperty("in-degree", v.getInDegree());
            v.addProperty("out-degree", v.getOutDegree());
        }

        String keyspace = "graphhcc1";
        GraphDatabaseAdapter db = new TitanConnector();
//        BaseConfiguration config = new BaseConfiguration();
//        config.setProperty("storage.backend", "cassandra");
//        config.setProperty("storage.hostname", "10.200.1.56");
//        config.setProperty("storage.cassandra.keyspace", keyspace);
//        db.connect(config);
        db.connect(dbPath + "" + keyspace);
        db.addGraph(graph);
        db.close();
    }

    private static void includeHappy(String dbPath) {
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

        for (Vertex v : graph.getVertices()) {
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
        db.connect(dbPath + "" + keyspace);
        db.addGraph(graph);
        db.close();
    }

    private static void includeKreditwuerdigkeit(String dbPath) {
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

        for (Vertex v : graph.getVertices()) {
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
        db.connect(dbPath + "" + keyspace);
        db.addGraph(graph);
        db.close();
    }

    private static void includeLungBN(String dbPath) {
        BayesianNetwork bn = new BayesianNetwork("Simple Lung Disease");

        Entity smoking = new Entity("Smoking");
        smoking.addStates("smoking", "nonsmoking");
        smoking.addProperty("type", "symptom");

        Entity lungDisease = new Entity("Lung Disease");
        lungDisease.addStates("positive", "negative");
        lungDisease.addProperty("type", "diagnosis");

        Entity breath = new Entity("Shortness of Breath");
        breath.addStates("positive", "negative");
        breath.addProperty("type", "symptom");

        Entity chestPain = new Entity("Chest Pain");
        chestPain.addStates("positive", "negative");
        chestPain.addProperty("type", "symptom");

        Entity cough = new Entity("Cough");
        cough.addStates("positive", "negative");
        cough.addProperty("type", "symptom");

        Entity cold = new Entity("Cold");
        cold.addStates("positive", "negative");
        cold.addProperty("type", "diagnosis");

        Entity fever = new Entity("Fever");
        fever.addStates("positive", "negative");
        fever.addProperty("type", "symptom");

        bn.addVertex(smoking);
        bn.addVertex(lungDisease);
        bn.addVertex(breath);
        bn.addVertex(chestPain);
        bn.addVertex(cough);
        bn.addVertex(cold);
        bn.addVertex(fever);

        //Links
        bn.addDependency(smoking, lungDisease);
        bn.addDependency(lungDisease, breath);
        bn.addDependency(lungDisease, chestPain);
        bn.addDependency(lungDisease, cough);
        bn.addDependency(cold, cough);
        bn.addDependency(cold, fever);

        //CPT
        smoking.addConditionalProbability(0.2, smoking.getState("smoking"));
        smoking.addConditionalProbability(0.8, smoking.getState("nonsmoking"));
        cold.addConditionalProbability(0.02, cold.getState("positive"));
        cold.addConditionalProbability(0.98, cold.getState("negative"));

        lungDisease.addConditionalProbability(0.1009, lungDisease.getState("positive"), smoking.getState("smoking"));
        lungDisease.addConditionalProbability(0.8991, lungDisease.getState("negative"), smoking.getState("smoking"));
        lungDisease.addConditionalProbability(0.001, lungDisease.getState("positive"), smoking.getState("nonsmoking"));
        lungDisease.addConditionalProbability(0.999, lungDisease.getState("negative"), smoking.getState("nonsmoking"));

        breath.addConditionalProbability(0.208, breath.getState("positive"), lungDisease.getState("positive"));
        breath.addConditionalProbability(0.792, breath.getState("negative"), lungDisease.getState("positive"));
        breath.addConditionalProbability(0.01, breath.getState("positive"), lungDisease.getState("negative"));
        breath.addConditionalProbability(0.99, breath.getState("negative"), lungDisease.getState("negative"));
        chestPain.addConditionalProbability(0.208, chestPain.getState("positive"), lungDisease.getState("positive"));
        chestPain.addConditionalProbability(0.792, chestPain.getState("negative"), lungDisease.getState("positive"));
        chestPain.addConditionalProbability(0.01, chestPain.getState("positive"), lungDisease.getState("negative"));
        chestPain.addConditionalProbability(0.99, chestPain.getState("negative"), lungDisease.getState("negative"));
        cough.addConditionalProbability(0.7525, cough.getState("positive"), lungDisease.getState("positive"), cold.getState("positive"));
        cough.addConditionalProbability(0.2475, cough.getState("negative"), lungDisease.getState("positive"), cold.getState("positive"));
        cough.addConditionalProbability(0.505, cough.getState("positive"), lungDisease.getState("positive"), cold.getState("negative"));
        cough.addConditionalProbability(0.495, cough.getState("negative"), lungDisease.getState("positive"), cold.getState("negative"));
        cough.addConditionalProbability(0.505, cough.getState("positive"), lungDisease.getState("negative"), cold.getState("positive"));
        cough.addConditionalProbability(0.495, cough.getState("negative"), lungDisease.getState("negative"), cold.getState("positive"));
        cough.addConditionalProbability(0.01, cough.getState("positive"), lungDisease.getState("negative"), cold.getState("negative"));
        cough.addConditionalProbability(0.99, cough.getState("negative"), lungDisease.getState("negative"), cold.getState("negative"));
        fever.addConditionalProbability(0.307, fever.getState("positive"), cold.getState("positive"));
        fever.addConditionalProbability(0.693, fever.getState("negative"), cold.getState("positive"));
        fever.addConditionalProbability(0.01, fever.getState("positive"), cold.getState("negative"));
        fever.addConditionalProbability(0.99, fever.getState("negative"), cold.getState("negative"));

//        smoking.setObservation(smoking.getState("smoking"));
        String json = D3Converter.getD3Json(bn);
        System.out.println(json);
        String keyspace = "bnlung1";
        GraphDatabaseAdapter db = new TitanConnector();
//        BaseConfiguration config = new BaseConfiguration();
//        config.setProperty("storage.backend", "cassandra");
//        config.setProperty("storage.hostname", "10.200.1.56");
//        config.setProperty("storage.cassandra.keyspace", keyspace);
//        db.connect(config);
        db.connect(dbPath + "" + keyspace);
        db.addGraph(bn);
        db.close();

        GraphDatabaseAdapter db2 = new TitanConnector();
        db2.connect(dbPath + "" + keyspace);
        Graph dbGraph = db2.getWholeGraph("Simple Lung Disease");
        BayesianNetwork bn2 = new BayesianNetwork(dbGraph);

        String json2 = D3Converter.getD3Json(bn2);
        System.out.println(json2);
        db2.close();
    }

    private static void includeCancerBN(String dbPath) {
        BayesianNetwork bn = new BayesianNetwork("Simple Cancer");

        Entity smoking = new Entity("Smoking");
        smoking.addStates("smoking", "nonsmoking");
        smoking.addProperty("type", "symptom");

        Entity pollution = new Entity("Pollution");
        pollution.addStates("low", "medium", "high");
        pollution.addProperty("type", "symptom");

        Entity cancer = new Entity("Cancer");
        cancer.addStates("positive", "negative");
        cancer.addProperty("type", "diagnosis");

        Entity xray = new Entity("X-Ray");
        xray.addStates("positive", "negative");
        xray.addProperty("type", "examination");

        bn.addVertex(smoking);
        bn.addVertex(pollution);
        bn.addVertex(cancer);
        bn.addVertex(xray);

        //Links
        bn.addDependency(smoking, cancer);
        bn.addDependency(pollution, cancer);
        bn.addDependency(cancer, xray);

        //CPT
        smoking.addConditionalProbability(0.3, smoking.getState("smoking"));
        smoking.addConditionalProbability(0.7, smoking.getState("nonsmoking"));
        pollution.addConditionalProbability(0.9, pollution.getState("low"));
        pollution.addConditionalProbability(0.0, pollution.getState("medium"));
        pollution.addConditionalProbability(0.1, pollution.getState("high"));

        cancer.addConditionalProbability(0.05, cancer.getState("positive"), pollution.getState("high"), smoking.getState("smoking"));
        cancer.addConditionalProbability(0.95, cancer.getState("negative"), pollution.getState("high"), smoking.getState("smoking"));
        cancer.addConditionalProbability(0.02, cancer.getState("positive"), pollution.getState("high"), smoking.getState("nonsmoking"));
        cancer.addConditionalProbability(0.98, cancer.getState("negative"), pollution.getState("high"), smoking.getState("nonsmoking"));
        cancer.addConditionalProbability(0.5, cancer.getState("positive"), pollution.getState("medium"), smoking.getState("smoking"));
        cancer.addConditionalProbability(0.5, cancer.getState("negative"), pollution.getState("medium"), smoking.getState("smoking"));
        cancer.addConditionalProbability(0.5, cancer.getState("positive"), pollution.getState("medium"), smoking.getState("nonsmoking"));
        cancer.addConditionalProbability(0.5, cancer.getState("negative"), pollution.getState("medium"), smoking.getState("nonsmoking"));
        cancer.addConditionalProbability(0.03, cancer.getState("positive"), pollution.getState("low"), smoking.getState("smoking"));
        cancer.addConditionalProbability(0.97, cancer.getState("negative"), pollution.getState("low"), smoking.getState("smoking"));
        cancer.addConditionalProbability(0.001, cancer.getState("positive"), pollution.getState("low"), smoking.getState("nonsmoking"));
        cancer.addConditionalProbability(0.999, cancer.getState("negative"), pollution.getState("low"), smoking.getState("nonsmoking"));

        xray.addConditionalProbability(0.9, xray.getState("positive"), cancer.getState("positive"));
        xray.addConditionalProbability(0.1, xray.getState("negative"), cancer.getState("positive"));
        xray.addConditionalProbability(0.2, xray.getState("positive"), cancer.getState("negative"));
        xray.addConditionalProbability(0.8, xray.getState("negative"), cancer.getState("negative"));

        //TODO: fill up rest of variables;
        String keyspace = "bncancer1";
        GraphDatabaseAdapter db = new TitanConnector();
//        BaseConfiguration config = new BaseConfiguration();
//        config.setProperty("storage.backend", "cassandra");
//        config.setProperty("storage.hostname", "10.200.1.56");
//        config.setProperty("storage.cassandra.keyspace", keyspace);
//        db.connect(config);
        db.connect(dbPath + "" + keyspace);
        db.addGraph(bn);
        db.close();
    }

    private static void includeBifGraphs(String dbPath) throws URISyntaxException {
//        System.out.println("######R#######");
//        System.out.println(DummyGraphs.class.getResource("src/resources/asia.bif"));
//        System.out.println(DummyGraphs.class.getResource("/src/resources/asia.bif"));
//        System.out.println(DummyGraphs.class.getResource("/resources/asia.bif"));
//        System.out.println(DummyGraphs.class.getResource("asia.bif"));
//        System.out.println(DummyGraphs.class.getResource("/asia.bif"));
//        System.out.println(DummyGraphs.class.getResource("asia"));
//        System.out.println(DummyGraphs.class.getResource("**/asia.bif"));
//        String p1 = DummyGraphs.class.getResource("/asia.bif").toExternalForm();
//        Path path1 = Paths.get(DummyGraphs.class.getResource("/asia.bif").toURI());
//
//        System.out.println("P1: " + p1);
        BayesianNetwork bn = BIFFormat.parse(DummyGraphs.class.getResourceAsStream("/asia.bif"));
        String keyspace = "bnasia1";
        GraphDatabaseAdapter db = new TitanConnector();
        db.connect(dbPath + "" + keyspace);
        db.addGraph(bn);
        db.close();

//        Path path2 = Paths.get(DummyGraphs.class.getResourceAsStream("/alarm.bif").toURI());
        BayesianNetwork bn2 = BIFFormat.parse(DummyGraphs.class.getResourceAsStream("/alarm.bif"));
        String keyspace2 = "bnalarm1";
        GraphDatabaseAdapter db2 = new TitanConnector();
        db2.connect(dbPath + "" + keyspace2);
        db2.addGraph(bn2);
        db2.close();

//        Path path3 = Paths.get(DummyGraphs.class.getResourceAsStream("/hepar2.bif").toURI());
        BayesianNetwork bn3 = BIFFormat.parse(DummyGraphs.class.getResourceAsStream("/hepar2.bif"));
        String keyspace3 = "bnhepar1";
        GraphDatabaseAdapter db3 = new TitanConnector();
        db3.connect(dbPath + "" + keyspace3);
        db3.addGraph(bn3);
        db3.close();
    }
}
