package grafungi.graph.bn;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fschmidt
 */
public class ConditionalProbabilityTable implements Serializable{
    private static final long serialVersionUID = 1L;
    private final List<ConditionalProbability> probabilities;
    public ConditionalProbabilityTable(){
        this.probabilities = new ArrayList<>();
    }
    public void addConditionProbability(double probability,State influencedState, State... conditions){
        //TODO: Check if conditions plus influenced state already exists. if yes, remove before adding
        this.probabilities.add(new ConditionalProbability(probability, influencedState, conditions));
    }
    public void remove(){
        //TODO: add remove methods
    }

    public List<ConditionalProbability> getProbabilities() {
        return probabilities;
    }

    @Override
    public String toString() {
        return "ConditionalProbabilityTable{" + "probabilities=" + probabilities + '}';
    }
}
