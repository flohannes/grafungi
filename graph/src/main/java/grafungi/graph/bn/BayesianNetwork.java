package grafungi.graph.bn;

import grafungi.graph.Edge;
import grafungi.graph.Graph;
import grafungi.graph.Vertex;
import java.util.List;
import java.util.Map;

/**
 *
 * @author fschmidt
 */
public class BayesianNetwork extends Graph {
    
    public BayesianNetwork(String name) {
        super(name);
    }
    
    public BayesianNetwork(Graph g) {
        super(g.getName());
        for (Vertex v : g.getVertices()) {
            Entity e = new Entity((String) v.getProperty("name"));
            e.getStates().addAll((List<State>) v.getProperty("states"));
//beliefs
            if (v.getProperties().keySet().contains("observation")) {
                e.setObservation((State) v.getProperty("observation"));
                System.out.println("O: " + e.getObservation() + "     V: " + v.getProperty("observation"));//+ " PARSE:" + ((State) v
//                        .getProperty("observation")).getEntityName());
            }
            e.getBeliefs().putAll((Map<State, Double>) v.getProperty("beliefs"));
//            e.getCpt().getProbabilities().addAll(((ConditionalProbabilityTable) v.getProperty("cpt")).getProbabilities());
            e.setId(v.getId());
            this.addVertex(e);
        }
        for (Edge e : g.getEdges()) {
            this.addDependency(this.getEntity((String) e.getSource().getProperty("name")), this.getEntity((String) e.getDestination()
                    .getProperty("name")));
            System.out.println("e::" + this.getEntity((String) e.getSource().getProperty("name")).getName() + " , " + this.getEntity(
                    (String) e.getDestination().getProperty("name")).getName());
        }
        for (Vertex v : g.getVertices()) {
            Entity e = this.getEntity((String) v.getProperty("name"));
            ConditionalProbabilityTable cpt = ((ConditionalProbabilityTable) v.getProperty("cpt"));
            for (ConditionalProbability cp : cpt.getProbabilities()) {
                double prob = cp.getProbability();
                State influencedState = cp.getInfluencedState();
                State infState = e.getState(influencedState.getName());
                List<State> conditions = cp.getConditions();
                State[] conditionStates = new State[cp.getConditions().size()];
                int counter = 0;
                for (State s : conditions) {
                    String entityName = s.getEntityName();
                    String stateName = s.getName();
                    State srcState = null;
                    for (Edge edge : e.getArcs(Edge.Direction.INCOMING)) {
                        Entity src = (Entity) edge.getSource();
                        if (src.getName().equals(entityName)) {
                            srcState = src.getState(stateName);
                        }
                    }
                    if (srcState == null) {
                        System.out.println("NULLLLLL" + entityName + " , " + stateName);
                    }
                    conditionStates[counter] = srcState;
                    counter++;
                }
                e.addConditionalProbability(prob, infState, conditionStates);
            }
        }
    }
    
    public void addDependency(Entity condition, Entity influenced) {
        //TODO: Check for no cycles
        this.addEdge(condition, influenced);
        //TODO: No parallel arcs allowed
    }
    
    public Entity getEntity(String name) {
        for (Vertex v : this.getVertices()) {
            if (v.getProperty("name").equals(name)) {
                return (Entity) v;
            }
        }
        return null;
    }
}
