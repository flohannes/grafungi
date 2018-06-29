package grafungi.graph.db;

import grafungi.graph.Edge;
import grafungi.graph.Vertex;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author fschmidt
 */
public final class GraphDBAutoIndexing {

    //TODO: Vertex and Edge
    //TODO: Add start times of queries
    private final Map<Collection<String>, String> idVertexIndexProperties;
    private final Map<Collection<String>, String> idEdgeIndexProperties;
    //TIME
    private final Map<Collection<String>, List<Long>> idSearchQueryTimelapses;

    private final Set<String> indexIds;
//    private Map<Collection<String>, List<Long>> idAddQueryTimelapses;
//    private Map<Collection<String>, List<Long>> idIndexCreationTime;
//    private Map<Collection<String>, List<Long>> idIndexCreationTimelapses;
//    private Map<Collection<String>, List<Long>> idIndexRemovedTime;

    public GraphDBAutoIndexing() {
        idVertexIndexProperties = new HashMap<>();
        idEdgeIndexProperties = new HashMap<>();
        idSearchQueryTimelapses = new HashMap<>();
        indexIds = new HashSet<>();
    }

    public GraphDBAutoIndexing(Map<Collection<String>, Class> indecies) {
        idVertexIndexProperties = new HashMap<>();
        idEdgeIndexProperties = new HashMap<>();
        idSearchQueryTimelapses = new HashMap<>();
        indexIds = new HashSet<>();
        for (Map.Entry<Collection<String>, Class> index : indecies.entrySet()) {
            String id = "index-" + index.getValue().getCanonicalName() + "" + index.getKey().toString();
//            createIndex(id, index.getKey(), index.getValue());
            indexIds.add(id);
        }

    }

    public void startSearchQuery(Collection<String> properties) {
        long startTime = System.nanoTime();
        long estimatedTime = System.nanoTime() - startTime;
    }

    public void createIndex(String id, Collection<String> properties, Class objClass) {
        indexIds.add(id);
        if (objClass == Vertex.class) {
            idVertexIndexProperties.put(properties, id);
        } else if (objClass == Edge.class) {
            idEdgeIndexProperties.put(properties, id);
        }
    }

    //TODO: checkForIndexCreationOrRemoval
    public boolean isNewIndex(Collection<String> properties, Class objClass) {
        if(properties.isEmpty())
            return false;
        String id = "index-" + objClass.getCanonicalName() + "" + properties.toString();
        return !indexIds.contains(id);
//        if (objClass == Vertex.class) {
//            return !idVertexIndexProperties.containsKey(properties);
//        } else if (objClass == Edge.class) {
//            return !idEdgeIndexProperties.containsKey(properties);
//        }
//        return true;
    }
}
