package grafungi.graph.bn;

import grafungi.graph.Edge;
import grafungi.graph.Vertex;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author fschmidt
 */
public class Entity extends Vertex {

    private String name;
    private final List<State> states;
    private State observation;
    private final ConditionalProbabilityTable cpt; //TODO make to string and parse back when getting from database
    private final Map<State, Double> beliefs;

    public Entity(String name) {
        this.states = new ArrayList<>();
        this.cpt = new ConditionalProbabilityTable();
        this.name = name;
        this.beliefs = new HashMap<>();
        this.addProperty("name", this.name);
        this.addProperty("states", this.states);
        this.addProperty("observation", this.observation);
        this.addProperty("cpt", this.cpt);
        this.addProperty("beliefs", this.beliefs);
    }

    public Entity(Vertex v, List<Edge> parentEdges) {
        for(String propertyKey : v.getProperties().keySet()){
            if(!propertyKey.equals("name")&&!propertyKey.equals("states")&&!propertyKey.equals("observation")&&!propertyKey.equals("cpt")&&!propertyKey.equals("beliefs")){
                this.getProperties().put(propertyKey, v.getProperty(propertyKey));
            }
        }
        this.setId(v.getId());
        this.setName((String) v.getProperty("name"));
        this.states = new ArrayList<>();
        this.cpt = new ConditionalProbabilityTable();
        this.beliefs = new HashMap<>();
        this.getStates().addAll((List<State>) v.getProperty("states"));
//beliefs
        if (v.getProperties().keySet().contains("observation")) {
            this.setObservation((State) v.getProperty("observation"));
        }
        this.getBeliefs().putAll((Map<State, Double>) v.getProperty("beliefs"));
//            e.getCpt().getProbabilities().addAll(((ConditionalProbabilityTable) v.getProperty("cpt")).getProbabilities());
        ConditionalProbabilityTable cpt = ((ConditionalProbabilityTable) v.getProperty("cpt"));
        for (ConditionalProbability cp : cpt.getProbabilities()) {
            double prob = cp.getProbability();
            State influencedState = cp.getInfluencedState();
            State infState = this.getState(influencedState.getName());
            List<State> conditions = cp.getConditions();
            State[] conditionStates = new State[cp.getConditions().size()];
            int counter = 0;
            for (State s : conditions) {
                String entityName = s.getEntityName();
                String stateName = s.getName();
                State srcState = null;
                for (Edge edge : parentEdges) {
                    Vertex src = edge.getSource();
                    String srcName = (String) src.getProperty("name");
                    List<State> srcStates = (List<State>) src.getProperty("states");
                    if (srcName.equals(entityName)) {
                        for(State srcVState : srcStates){
                            if(srcVState.getName().equals(stateName)){
                                srcState = srcVState;
                            }
                        }
//                        srcState = src.getState(stateName);
                    }
                }
                if (srcState == null) {
                    System.out.println("NULLLLLL" + entityName + " , " + stateName);
                }
                conditionStates[counter] = srcState;
                counter++;
            }
            this.addConditionalProbability(prob, infState, conditionStates);
        }
        this.addProperty("name", this.name);
        this.addProperty("states", this.states);
        this.addProperty("observation", this.observation);
        this.addProperty("cpt", this.cpt);
        this.addProperty("beliefs", this.beliefs);
    }

    public void addStates(String... states) {
        for (String stateName : states) {
            State state = new State(stateName);
            state.setEntityName(this.name);
            this.states.add(state);
        }
//        this.getProperties().put("states", new Gson().toJson(states));
    }

    public State getState(String stateName) {
        for (State s : states) {
            if (stateName.equals(s.getName())) {
                return s;
            }
        }
        return null;
    }

    //Bei conditions immer über die parent id gehen! Kondierung der States noch nicht gut
    public void addConditionalProbability(double probability, State influencedState, State... conditions) {
        //TODO: Check if states exists in predecessors and in this entity
        this.cpt.addConditionProbability(probability, influencedState, conditions);
//        this.getProperties().put("cpt", new Gson().toJson(cpt));
    }

    public void setObservation(State observation) {
        if (observation == null) {
            this.observation = null;
            return;
        }
        for (State s : states) {
            if (s.getName().equals(observation.getName()) && s.getEntityName().equals(observation.getEntityName())) {
                this.observation = observation;
                this.addProperty("observation", this.observation);
                return;
            }
        }
    }

    public State getObservation() {
        return observation;
    }

    public List<State> getStates() {
        return states;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ConditionalProbabilityTable getCpt() {
        return this.cpt;
    }

    //TODO: Check for missing entries in probability table
    //TODO: Check for updates in vertex's CPTs
    public Map<State, Double> getBeliefs() {
        return beliefs;
    }
}
