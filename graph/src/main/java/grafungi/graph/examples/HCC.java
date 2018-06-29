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
public class HCC {
    public static void main(String... args){
        Graph graph = new Graph("HCC");

        Vertex root = new Vertex();
        root.addProperty("name", "HCC");
        root.addProperty("description", "Hepatocellular carcinoma (HCC), also called malignant hepatoma, is the most common type of liver cancer.");
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
        v12.addProperty("description", "Doxorubicin-eluting bead transarterial chemoembolization (DEB-TACE) is a novel locoregional treatment for unresectable hepatocellular carcinoma (HCC).");
        v12.addProperty("type", "therapy");
        v12.addProperty("concept", UUID.randomUUID());
        Vertex v13 = new Vertex();
        v13.addProperty("name", "RFA");
        v13.addProperty("description", "Percutaneous radiofrequency ablation (RFA) is an exciting approach to destroying inoperable primary tumors or metastases in the liver.");
        v13.addProperty("type", "therapy");
        v13.addProperty("concept", UUID.randomUUID());
        
        Vertex v14 = new Vertex();
        v14.addProperty("name", "DEB-TACE");
        v14.addProperty("description", "Doxorubicin-eluting bead transarterial chemoembolization (DEB-TACE) is a novel locoregional treatment for unresectable hepatocellular carcinoma (HCC).");
        v14.addProperty("type", "therapy");
        v14.addProperty("concept", UUID.randomUUID());
        Vertex v15 = new Vertex();
        v15.addProperty("name", "RFA");
        v15.addProperty("description", "Percutaneous radiofrequency ablation (RFA) is an exciting approach to destroying inoperable primary tumors or metastases in the liver.");
        v15.addProperty("type", "therapy");
        v15.addProperty("concept", UUID.randomUUID());
        
        Vertex v16 = new Vertex();
        v16.addProperty("name", "SIRT");
        v16.addProperty("description", "Selective internal radiation therapy (SIRT) is a form of radiation therapy used in interventional radiology to treat cancer. Survival < 6 months.");
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
        v20.addProperty("description", "Percutaneous radiofrequency ablation (RFA) is an exciting approach to destroying inoperable primary tumors or metastases in the liver. Sorafenib (co-developed and co-marketed by Bayer and Onyx Pharmaceuticals as Nexavar), is a kinase inhibitor drug approved for the treatment of primary kidney cancer (advanced renal cell carcinoma), advanced primary liver cancer (hepatocellular carcinoma), and radioactive iodine resistant advanced thyroid carcinoma.");
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
        v31.addProperty("description", "Percutaneous radiofrequency ablation (RFA) is an exciting approach to destroying inoperable primary tumors or metastases in the liver. Sorafenib (co-developed and co-marketed by Bayer and Onyx Pharmaceuticals as Nexavar), is a kinase inhibitor drug approved for the treatment of primary kidney cancer (advanced renal cell carcinoma), advanced primary liver cancer (hepatocellular carcinoma), and radioactive iodine resistant advanced thyroid carcinoma.");
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
        v35.addProperty("description", "Selective internal radiation therapy (SIRT) is a form of radiation therapy used in interventional radiology to treat cancer. Survival < 9 months.");
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
        v43.addProperty("description", "Check criteria: i) portal hypertension, ii) LVVD > 10 mmHg, iii) > 2 lesions, iv) gastroesophageal varices, v) splenomegaly > 12 cm, vi) bilirubin > 3 mg/dl, vii) Fibroscan > 8.5 kPa, viii) > 3 cm.");
        v43.addProperty("type", "examination");
        v43.addProperty("concept", UUID.randomUUID());
        Vertex v44 = new Vertex();
        v44.addProperty("name", "Does not fulfil criteria");
        v44.addProperty("description", "Check if any criteria is not fulfiled: i) portal hypertension, ii) LVVD > 10 mmHg, iii) > 2 lesions, iv) gastroesophageal varices, v) splenomegaly > 12 cm, vi) bilirubin > 3 mg/dl, vii) Fibroscan > 8.5 kPa, viii) > 3 cm.");
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
        
        for(Vertex v : graph.getVertices()){
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
        db.connect("/Users/fschmidt/graphws/"+keyspace);
        db.addGraph(graph);
        db.close();
    }
}
