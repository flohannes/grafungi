package grafungi.graph.io.d3;

/**
 *
 * @author fschmidt
 */
public class ArcD3 {

    private String name;
    private int source;
    private int target;
    private int value;

    public void setName(String name) {
        this.name = name;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getSource() {
        return source;
    }

    public int getTarget() {
        return target;
    }

    public int getValue() {
        return value;
    }

}
