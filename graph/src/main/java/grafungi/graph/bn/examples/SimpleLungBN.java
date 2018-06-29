package grafungi.graph.bn.examples;

import grafungi.graph.Graph;
import grafungi.graph.bn.BayesianNetwork;
import grafungi.graph.bn.Entity;
import grafungi.graph.db.GraphDatabaseAdapter;
import grafungi.graph.db.TitanConnector;
import grafungi.graph.io.d3.D3Converter;

/**
 *
 * @author fschmidt
 */
public class SimpleLungBN {

    public static void main(String... args) {
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
        db.connect("/Users/fschmidt/graphws/" + keyspace);
        db.addGraph(bn);
        db.close();

        GraphDatabaseAdapter db2 = new TitanConnector();
        db2.connect("/Users/fschmidt/graphws/" + keyspace);
        Graph dbGraph = db2.getWholeGraph("Simple Lung Disease");
        BayesianNetwork bn2 = new BayesianNetwork(dbGraph);

        String json2 = D3Converter.getD3Json(bn2);
        System.out.println(json2);
        db2.close();

    }
}
