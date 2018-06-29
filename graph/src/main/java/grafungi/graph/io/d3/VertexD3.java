package grafungi.graph.io.d3;

import java.util.Map;

/**
 *
 * @author fschmidt
 */
public class VertexD3 {

    private String name;
    private int group;
    private Map<Object, Object> properties;

    public String getName() {
        return name;
    }

    public int getGroup() {
        return group;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public Map<Object, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<Object, Object> properties) {
        this.properties = properties;
    }
}
