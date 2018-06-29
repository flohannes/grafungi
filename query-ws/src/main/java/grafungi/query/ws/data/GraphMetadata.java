package grafungi.query.ws.data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author fschmidt
 */
@javax.persistence.Entity
//@javax.persistence.Table(name = "elastic_sample", schema = "graph")
public class GraphMetadata {
    @Id 
    @GeneratedValue
    private Long id;
    private String graphName;
    private String graphDBId;
    private String type;

    public GraphMetadata(){
        
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGraphName() {
        return graphName;
    }

    public void setGraphName(String graphName) {
        this.graphName = graphName;
    }

    public String getGraphDBId() {
        return graphDBId;
    }

    public void setGraphDBId(String graphDBId) {
        this.graphDBId = graphDBId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
