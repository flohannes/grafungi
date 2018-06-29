package grafungi.graph;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The Graphelement is an abstract representation for vertices and edges in a given property-graph.
 * It captures a map of properties as key-value store and an internal applied id.
 * @author Florian
 */
public abstract class GraphElement implements Serializable {

    private String id;
    private Map<String, Object> properties;

    public GraphElement() {
        properties = new HashMap<>();
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
    
    public void addProperty(String key, Object value){
        this.properties.put(key, value);
    }
    
    public Object getProperty(String key){
        return this.properties.get(key);
    }

}
