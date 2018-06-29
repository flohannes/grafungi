package grafungi.graph.bn.jayes;

import com.github.vangj.jbayes.inf.prob.Graph;
import com.github.vangj.jbayes.inf.prob.Node;
import grafungi.graph.Edge;
import grafungi.graph.Vertex;
import grafungi.graph.bn.BayesianNetwork;
import grafungi.graph.bn.ConditionalProbability;
import grafungi.graph.bn.ConditionalProbabilityTable;
import grafungi.graph.bn.State;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.recommenders.jayes.BayesNet;
import org.eclipse.recommenders.jayes.BayesNode;
import org.eclipse.recommenders.jayes.inference.jtree.JunctionTreeAlgorithm;
import org.eclipse.recommenders.jayes.inference.jtree.JunctionTreeBuilder;
import org.eclipse.recommenders.jayes.util.triangulation.MinFillIn;

/**
 *
 * @author fschmidt
 */
public class Inference {

    public BayesianNetwork calc(BayesianNetwork bn) {
        //bn to BayesNet; then add evidences; then calc inference; return BayesianNetwork
        BayesNet net = new BayesNet();
        Map<BayesNode, String> evidences = new HashMap<>();
        List<BayesNode> nodes = new ArrayList<>();

        //Sort nodes: topological sort using Kahn's algorithm
        List<Vertex> sortedNodes = new ArrayList<>();
        Queue<Vertex> queue = new LinkedList<>();
        List<Edge> removedEdges = new ArrayList<>();
        for (Vertex vertex : bn.getVertices()) {
            if (vertex.getInDegree() == 0) {
                System.out.println("0 in-degree: " + vertex.getProperty("name"));
                queue.add(vertex);
            }
        }
        while (!queue.isEmpty()) {
            Vertex n = queue.remove();
            sortedNodes.add(n);
            for (Edge e : n.getArcs(Edge.Direction.OUTGOING)) {
                Vertex m = e.getDestination();
                removedEdges.add(e);
                boolean noIncomingEdges = true;
                for (Edge em : m.getArcs(Edge.Direction.INCOMING)) {
                    if (!removedEdges.contains(em)) {
                        noIncomingEdges = false;
                        break;
                    }
                }
                if (noIncomingEdges) {
                    if (!sortedNodes.contains(m) && !queue.contains(m)) {
                        queue.add(m);
                    }
                }
            }
        }
        if (removedEdges.size() != bn.getEdges().size()) {
            try {
                throw new Exception();
            } catch (Exception ex) {
                Logger.getLogger(Inference.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        System.out.println("sortSize: " + sortedNodes.size());
        for (Vertex v : sortedNodes) {
            System.out.println(v.getProperty("name"));
        }
        
        //Create nodes
        for (Vertex vertex : sortedNodes) {
//            Entity e = (Entity) vertex;
            List<State> states = (List<State>) vertex.getProperty("states");//e.getStates();
            State observation = (State) vertex.getProperty("observation");//e.getObservation();
            if(observation!=null)
                System.out.println("O-Inf: "+observation.getEntityName()+"."+observation.getName());
            ConditionalProbabilityTable cpt = (ConditionalProbabilityTable) vertex.getProperty("cpt");//.getCpt();
            String name = (String) vertex.getProperty("name");//e.getName();

            BayesNode node = net.createNode(name);
            for (State s : states) {
                node.addOutcomes(s.getName());
            }
            if (vertex.getArcs(Edge.Direction.INCOMING).size() > 0) {
                List<BayesNode> parents = new ArrayList<>();
                for (Edge eParent : vertex.getArcs(Edge.Direction.INCOMING)) {
                    Vertex vParent = eParent.getSource();
                    for (BayesNode bnode : nodes) {
                        if (vParent.getProperty("name").equals(bnode.getName())) {
                            parents.add(bnode);
                            break;
                        }
                    }
                }
                node.setParents(parents);
            }
            nodes.add(node);

            //Add Probabilities!
            Queue<BayesNode> probNodeList = new LinkedList<>();
            probNodeList.add(node);
            probNodeList.addAll(node.getParents());
            int numOfProbabilities = node.getOutcomeCount();
            for (BayesNode parent : probNodeList) {
                if (!parent.equals(node)) {
                    numOfProbabilities *= parent.getOutcomeCount();
                }
            }
            double[] probs = new double[numOfProbabilities];

            List<List<State>> data = new ArrayList<>();
            for (BayesNode bnode : probNodeList) {
                for (Edge eParent : vertex.getArcs(Edge.Direction.INCOMING)) {
                    Vertex vParent = eParent.getSource();
                    if (bnode.getName().equals(vParent.getProperty("name"))) {
                        data.add((List<State>) vParent.getProperty("states"));
                        break;
                    }
                }
            }
            data.add((List<State>) vertex.getProperty("states"));
            List<List<State>> permutedStates = SetPermutations.<State>getSetPermutations(data);

            int probCounter = 0;
            for (List<State> stateSet : permutedStates) {
                for (ConditionalProbability probability : cpt.getProbabilities()) {
                    State influencedState = probability.getInfluencedState();
                    List<State> conditions = new ArrayList<>();
                    conditions.add(influencedState);
                    conditions.addAll(probability.getConditions());
                    double prob = probability.getProbability();
                    if (stateSet.containsAll(conditions)) {
                        System.out.println("p: " + stateSet + " " + prob);
                        probs[probCounter] = prob;
                        probCounter++;
                        break;
                    }
                }
            }

            System.out.println(node.getName() + " " + Arrays.toString(probs));
            node.setProbabilities(probs);

            //Set evidences: 
            if (observation != null) {
                evidences.put(node, observation.getName());
                System.out.println("evidence: " + node.getName() + " " + vertex.getProperty("observation"));
            }
        }

        for (BayesNode bnode : net.getNodes()) {
            System.out.println(bnode.getName() + " parents:" + bnode.getParents().size() + " outcomes:" + bnode.getOutcomes()
                    + " probabilities:" + Arrays.toString(bnode.getProbabilities()));
        }
//        JunctionTreeAlgorithm inferer = new JunctionTreeAlgorithm();
//        inferer.getFactory().setUseLogScale(true);
        JunctionTreeAlgorithm inferer = new JunctionTreeAlgorithm();
        JunctionTreeBuilder builder = JunctionTreeBuilder.forHeuristic(new MinFillIn());
        inferer.setJunctionTreeBuilder(builder);
//        IBayesInferer inferer = new JunctionTreeAlgorithm();
        inferer.setNetwork(net);
        inferer.setEvidence(evidences);

        for (Vertex v : bn.getVertices()) {
            for (BayesNode bnode : net.getNodes()) {
                if (v.getProperty("name").equals(bnode.getName())) {
                    double[] bs = inferer.getBeliefs(bnode);
                    Map<State, Double> beliefs = new HashMap<>();
                    for (int i = 0; i < bs.length; i++) {
                        double b = bs[i];
                        State state = ((List<State>) v.getProperty("states")).get(i);
                        beliefs.put(state, b);
                        ((Map<State, Double>) v.getProperty("beliefs")).putAll(beliefs);
                    }
                }
            }
        }
//        double[] beliefes = inferer.getBeliefs(node);
        return bn;
    }

    public BayesianNetwork calcNewInf(BayesianNetwork bn) {
        //TODO: bn to BayesNet; then add evidences; then calc inference; return BayesianNetwork
//        BayesNet net = new BayesNet();
        Graph g = new Graph();
        Map<Node, String> evidences = new HashMap<>();
//        List<BayesNode> nodes = new ArrayList<>();
        List<Node> ns = new ArrayList<>();

        //Sort nodes: topological sort using Kahn's algorithm
        List<Vertex> sortedNodes = new ArrayList<>();
        Queue<Vertex> queue = new LinkedList<>();
        List<Edge> removedEdges = new ArrayList<>();
        for (Vertex vertex : bn.getVertices()) {
            if (vertex.getInDegree() == 0) {
                System.out.println("0 in-degree: " + vertex.getProperty("name"));
                queue.add(vertex);
            }
        }
        while (!queue.isEmpty()) {
            Vertex n = queue.remove();
            sortedNodes.add(n);
            for (Edge e : n.getArcs(Edge.Direction.OUTGOING)) {
                Vertex m = e.getDestination();
                removedEdges.add(e);
                boolean noIncomingEdges = true;
                for (Edge em : m.getArcs(Edge.Direction.INCOMING)) {
                    if (!removedEdges.contains(em)) {
                        noIncomingEdges = false;
                        break;
                    }
                }
                if (noIncomingEdges) {
                    if (!sortedNodes.contains(m) && !queue.contains(m)) {
                        queue.add(m);
                    }
                }
            }
        }
        if (removedEdges.size() != bn.getEdges().size()) {
            try {
                throw new Exception();
            } catch (Exception ex) {
                Logger.getLogger(Inference.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        System.out.println("sortSize: " + sortedNodes.size());
        for (Vertex v : sortedNodes) {
            System.out.println(v.getProperty("name"));
        }
        //Create nodes
        for (Vertex vertex : sortedNodes) {
//            Entity e = (Entity) vertex;
            List<State> states = (List<State>) vertex.getProperty("states");//e.getStates();
            State observation = (State) vertex.getProperty("observation");//e.getObservation();
            ConditionalProbabilityTable cpt = (ConditionalProbabilityTable) vertex.getProperty("cpt");//.getCpt();
            String name = (String) vertex.getProperty("name");//e.getName();

            Node n = Node.newBuilder().name(name).build();
//            BayesNode node = net.createNode(name);
            for (State s : states) {
//                node.addOutcomes(s.getName());
                n.getValues().add(s.getName());
            }
            if (vertex.getArcs(Edge.Direction.INCOMING).size() > 0) {
//                List<BayesNode> parents = new ArrayList<>();
                for (Edge eParent : vertex.getArcs(Edge.Direction.INCOMING)) {
                    Vertex vParent = eParent.getSource();
//                    for (BayesNode bnode : nodes) {
//                        if (vParent.getProperty("name").equals(bnode.getName())) {
//                            parents.add(bnode);
//                            break;
//                        }
//                    }

                    for (Node np : ns) {
                        if (vParent.getProperty("name").equals(np.getName())) {
                            n.addParent(np);
                            break;
                        }
                    }
                }
//                node.setParents(parents);
            }
//            nodes.add(node);
            ns.add(n);

            //Add Probabilities!
//            Queue<BayesNode> probNodeList = new LinkedList<>();
            Queue<Node> probNodeList = new LinkedList<>();
//            probNodeList.add(n);
//            probNodeList.add(node);
            probNodeList.addAll(n.getParents());
//            probNodeList.addAll(node.getParents());
//            int numOfProbabilities = node.getOutcomeCount();
            int numOfProbabilities = 1;
//            if (probNodeList.isEmpty()) {
//                numOfProbabilities = 0;
//            }
            for (Node parent : probNodeList) {
                numOfProbabilities *= parent.getValues().size();
            }

            double[][] probs = new double[numOfProbabilities][n.getValues().size()];

            List<List<State>> data = new ArrayList<>();
            for (Node bnode : probNodeList) {
                for (Edge eParent : vertex.getArcs(Edge.Direction.INCOMING)) {
                    Vertex vParent = eParent.getSource();
                    if (bnode.getName().equals(vParent.getProperty("name"))) {
                        data.add((List<State>) vParent.getProperty("states"));
                        break;
                    }
                }
            }
//            data.add((List<State>) vertex.getProperty("states"));
            List<List<State>> permutedStates = SetPermutations.<State>getSetPermutations(data);

            int probCounter = 0;
            for (List<State> stateSet : permutedStates) {
                int infCounter = 0;
                for (State infState : states) {
                    List<State> stateSetCopy = new ArrayList<>(stateSet);
                    stateSetCopy.add(infState);
                    for (ConditionalProbability probability : cpt.getProbabilities()) {
                        State influencedState = probability.getInfluencedState();
                        List<State> conditions = new ArrayList<>();
                        conditions.add(influencedState);
                        conditions.addAll(probability.getConditions());
                        double prob = probability.getProbability();
                        if (stateSetCopy.containsAll(conditions)) {
                            System.out.println("p: " + stateSetCopy + " " + prob);
                            probs[probCounter][infCounter] = prob;
//                            probCounter++;
                            break;
                        }
                    }
                    infCounter++;
                }
                probCounter++;
            }

            System.out.println(n.getName() + " " + Arrays.deepToString(probs));
            n.setCpt(probs);//.setProbabilities(probs);

            //Set evidences: 
            if (observation != null) {
                evidences.put(n, observation.getName());
                System.out.println("evidence: " + n.getName() + " " + vertex.getProperty("observation"));
            }
        }

        for (Node n : ns) {
            g.addNode(n);
        }

        for (Node bnode : g.getNodes()) {
            System.out.println(bnode.getName() + " parents:" + bnode.getParents().size() + " outcomes:" + Arrays.toString(bnode.getValues().toArray())
                    + " probabilities:" + Arrays.deepToString(bnode.getCpt().getValues().toArray()));
        }

        for (Map.Entry<Node, String> evidenceEntry : evidences.entrySet()) {
            g.observe(evidenceEntry.getKey().getName(), evidenceEntry.getValue());
        }

        g.sample(10_000);

//        JunctionTreeAlgorithm inferer = new JunctionTreeAlgorithm();
//        inferer.getFactory().setUseLogScale(true);
//        JunctionTreeAlgorithm inferer = new JunctionTreeAlgorithm();
//        JunctionTreeBuilder builder = JunctionTreeBuilder.forHeuristic(new MinFillIn());
//        inferer.setJunctionTreeBuilder(builder);
////        IBayesInferer inferer = new JunctionTreeAlgorithm();
//        inferer.setNetwork(net);
//        inferer.setEvidence(evidences);
        for (Vertex v : bn.getVertices()) {
            for (Node bnode : g.getNodes()) {
                if (v.getProperty("name").equals(bnode.getName())) {
                    double[] bs = bnode.probs();
                    Map<State, Double> beliefs = new HashMap<>();
                    for (int i = 0; i < bs.length; i++) {
                        double b = bs[i];
                        State state = ((List<State>) v.getProperty("states")).get(i);
                        beliefs.put(state, b);
                        ((Map<State, Double>) v.getProperty("beliefs")).putAll(beliefs);
                    }
                }
            }
        }
        g.clearSamples();
//        double[] beliefes = inferer.getBeliefs(node);
        return bn;
    }

}
