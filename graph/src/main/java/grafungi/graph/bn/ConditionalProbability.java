package grafungi.graph.bn;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author fschmidt
 */
public class ConditionalProbability implements Serializable {

    public final double probability;
    public final List<State> conditions;
    public final State influencedState;

    public ConditionalProbability(double probability, State influencedState, State... conditions) {
        this.probability = probability;
        this.conditions = new ArrayList<>(Arrays.asList(conditions));
        this.influencedState = influencedState;
    }

    public double getProbability() {
        return probability;
    }

    public List<State> getConditions() {
        return conditions;
    }

    public State getInfluencedState() {
        return influencedState;
    }

    @Override
    public String toString() {
        return "ConditionalProbability{" + "probability=" + probability + ", conditions=" + conditions + ", influencedState="
                + influencedState + '}';
    }
}
