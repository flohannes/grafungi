package grafungi.graph.io.d3;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fschmidt
 */
public class TreeD3 {
    private final List<VertexTreeD3> rootNodes;
    
    public TreeD3(){
        rootNodes = new ArrayList<>();
    }

    public List<VertexTreeD3> getRootNodes() {
        return rootNodes;
    }
    
    public void addRootNode(VertexTreeD3 root){
        this.rootNodes.add(root);
    }
}
