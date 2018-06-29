package grafungi.graph.io.d3;

import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author fschmidt
 */
public class VertexTreeD3 {
    @Expose
    private String name;
    @Expose
    private String parent;
    private VertexTreeD3 parentNode;
    @Expose
    private List<VertexTreeD3> children;

    @Expose
    private int value = 10;
    @Expose
    private String type;
    @Expose
    private String level;
    @Expose
    private String uniqueId;
    @Expose
    private Map<String,String> properties;

    public VertexTreeD3(String name, VertexTreeD3 parentNode) {
        this.name = name;
        this.parentNode = parentNode;
        if (parentNode == null) {
            this.parent = "null";
        } else {
            this.parent = parentNode.getName();
        }
        this.children = new ArrayList<>();
        this.level = "white";
        this.type = "gray";
        this.uniqueId = UUID.randomUUID().toString();
        this.properties = new HashMap<>();
    }

    public void addChild(VertexTreeD3 childNode) {
        this.level = "steelblue";
        this.children.add(childNode);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public List<VertexTreeD3> getChildren() {
        return children;
    }

    public void setChildren(List<VertexTreeD3> children) {
        this.children = children;
    }

    public VertexTreeD3 getParentNode() {
        return parentNode;
    }

    public void setParentNode(VertexTreeD3 parentNode) {
        this.parentNode = parentNode;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        if (!children.isEmpty()) {
            return "steelblue";
        }
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }    
}
