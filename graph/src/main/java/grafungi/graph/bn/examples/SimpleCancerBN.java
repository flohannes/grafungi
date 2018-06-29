package grafungi.graph.bn.examples;

import grafungi.graph.bn.BayesianNetwork;
import grafungi.graph.bn.Entity;
import grafungi.graph.db.GraphDatabaseAdapter;
import grafungi.graph.db.TitanConnector;


/**
 *
 * @author fschmidt
 */
public class SimpleCancerBN {

    public static void main(String... args) {
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
        db.connect("/Users/fschmidt/graphws/"+keyspace);
        db.addGraph(bn);
        db.close();
    }
}
