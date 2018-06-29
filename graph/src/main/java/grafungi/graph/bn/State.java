package grafungi.graph.bn;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;

/**
 *
 * @author fschmidt
 */
@JsonSerialize
public class State implements Serializable {

    private static final long serialVersionUID = 1L;

    private String entityName;
    private String name;

    public State() {
    }

    public State(String name) {
        this.name = name;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "entityName = " + this.entityName + ", name = " + this.name;
    }

}
