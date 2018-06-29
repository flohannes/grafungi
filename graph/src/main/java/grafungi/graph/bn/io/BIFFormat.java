package grafungi.graph.bn.io;

import grafungi.graph.bn.BayesianNetwork;
import grafungi.graph.bn.Entity;
import grafungi.graph.bn.State;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author fschmidt
 */
public class BIFFormat {

    private static final String NETWORK = "network";
    private static final String VARIABLE = "variable";
    private static final String PROBABILITY = "probability";

    public static BayesianNetwork parse(InputStream in) {
        BayesianNetwork bn = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        try {
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(NETWORK)) {
                    String[] splitLine = line.split(" ");
                    String bnName = splitLine[1];
                    bn = new BayesianNetwork(bnName);
                    while (!(line = br.readLine()).equals("}")) {
                    }
                    System.out.println("New BN: " + bn.getName());
                } else if (line.startsWith(VARIABLE)) {
                    String[] splitLine = line.split(" ");
                    String variableName = splitLine[1];
                    String variableType = splitLine[2];
                    Entity e = new Entity(variableName);
                    e.addProperty("type", variableType);
                    while (!(line = br.readLine()).equals("}")) {
                        final Pattern pattern = Pattern.compile("\\{(.*?)\\}");
                        Matcher matcher = pattern.matcher(line);
                        if (matcher.find()) {
                            String match = matcher.group(1);
                            match = match.trim().replace(" ", "");
                            String[] m = match.split(",");
                            e.addStates(m);
                        }
                    }
                    bn.addVertex(e);
                } else if (line.startsWith(PROBABILITY)) {
                    final Pattern pattern = Pattern.compile("\\((.*?)\\)");
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        String match = matcher.group(1);
                        match = match.trim().replace(" ", "");
                        if (match.contains("|")) {
                            String[] dependencies = match.split(Pattern.quote("|"));
                            String influencedEntity = dependencies[0];
                            String[] dependentEntities = dependencies[1].split(",");
                            Entity e = bn.getEntity(influencedEntity);
                            //addEdges
                            for (String depEnt : dependentEntities) {
                                bn.addDependency(bn.getEntity(depEnt), e);
                            }
                            //addProbs
                            while (!(line = br.readLine()).equals("}")) {
                                //  (yes, yes) 1.0, 0.0;
                                line = line.substring(3, line.length() - 1);
                                line = line.trim().replace(" ", "");
                                String[] subLine = line.split("\\)");

                                String[] states = subLine[0].split(",");
                                String[] probs = subLine[1].split(",");
                                State[] depStates = new State[states.length];
                                for (int i = 0; i < dependentEntities.length; i++) {
                                    Entity dE = bn.getEntity(dependentEntities[i]);
                                    State dS = dE.getState(states[i]);
                                    depStates[i] = dS;
                                }

                                for (int i = 0; i < probs.length; i++) {
                                    e.addConditionalProbability(Double.valueOf(probs[i]), e.getStates().get(i), depStates);
                                }

                            }
                        } else {
                            Entity e = bn.getEntity(match);
                            while (!(line = br.readLine()).equals("}")) {
                                //  table 0.5, 0.5;
                                String subStringTable = line.substring(7, line.length() - 1);
                                subStringTable = subStringTable.trim().replace(" ", "");
                                String[] probs = subStringTable.split(",");
                                for (int i = 0; i < probs.length; i++) {
                                    e.addConditionalProbability(Double.valueOf(probs[i]), e.getStates().get(i));
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(BIFFormat.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(BIFFormat.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return bn;
    }
}
