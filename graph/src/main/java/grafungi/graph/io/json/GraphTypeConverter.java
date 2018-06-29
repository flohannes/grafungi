package grafungi.graph.io.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import grafungi.graph.Edge;
import grafungi.graph.Graph;
import grafungi.graph.Vertex;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author fschmidt
 */
public class GraphTypeConverter implements JsonSerializer<Graph>, JsonDeserializer<Graph> {

    @Override
    public JsonElement serialize(Graph graph, Type type, JsonSerializationContext jsc) {
        final JsonObject jsonObject = new JsonObject();

        //Vertices
        JsonArray jsonVertexArray = new JsonArray();
        for (Vertex v : graph.getVertices()) {
            JsonObject jsonVertex = new JsonObject();
            String vId = v.getId();
            jsonVertex.addProperty("id", vId);

            JsonObject propertyMap = new JsonObject();
            for (Map.Entry<String, Object> entry : v.getProperties().entrySet()) {
                String keyName = entry.getKey();
                Object obj = entry.getValue();
                JsonElement valueElement = jsc.serialize(obj, obj.getClass());
                propertyMap.add(keyName, valueElement);
            }
            jsonVertex.add("properties", propertyMap);
            jsonVertexArray.add(jsonVertex);
        }
        jsonObject.add("vertices", jsonVertexArray);

        //Edges
        JsonArray jsonEdgeArray = new JsonArray();
        for (Edge a : graph.getEdges()) {
            JsonObject jsonEdge = new JsonObject();
            String vId = a.getId();
            jsonEdge.addProperty("id", vId);
            String srcId = a.getSource().getId();
            jsonEdge.addProperty("source", srcId);
            String dstId = a.getDestination().getId();
            jsonEdge.addProperty("destination", dstId);

            JsonObject propertyMap = new JsonObject();
            for (Map.Entry<String, Object> entry : a.getProperties().entrySet()) {
                String keyName = entry.getKey();
                Object obj = entry.getValue();
                JsonElement valueElement = jsc.serialize(obj, obj.getClass());
                propertyMap.add(keyName, valueElement);
            }
            jsonEdge.add("properties", propertyMap);
            jsonEdgeArray.add(jsonEdge);
        }
        jsonObject.add("edges", jsonEdgeArray);

        return jsonObject;
    }

    @Override
    public Graph deserialize(JsonElement jsonGraph, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        Graph graph = new Graph("");
        //Vertices
        JsonArray verticesJson = jsonGraph.getAsJsonObject().get("vertices").getAsJsonArray();
        for (int i = 0; i < verticesJson.size(); i++) {
            Vertex v = new Vertex();
            JsonObject graphElementObject = verticesJson.get(i).getAsJsonObject();
            String id = graphElementObject.get("id").getAsString();
            v.setId(id);
            Map<String, Object> propertyMap = new HashMap<>();
            JsonObject propertyJson = graphElementObject.get("properties").getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : propertyJson.entrySet()) {
                propertyMap.put(entry.getKey(), jdc.deserialize(entry.getValue(), Object.class));
            }
            v.setProperties(propertyMap);
            graph.addVertex(v);
        }
        //Edges
        JsonArray edgesJson = jsonGraph.getAsJsonObject().get("edges").getAsJsonArray();
        for (int i = 0; i < edgesJson.size(); i++) {
            JsonObject graphElementObject = edgesJson.get(i).getAsJsonObject();
            String id = graphElementObject.get("id").getAsString();
            String sourceId = graphElementObject.get("source").getAsString();
            String destinationId = graphElementObject.get("destination").getAsString();
            
            Map<String, Object> propertyMap = new HashMap<>();
            JsonObject propertyJson = graphElementObject.get("properties").getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : propertyJson.entrySet()) {
                propertyMap.put(entry.getKey(), jdc.deserialize(entry.getValue(), Object.class));
            }

            Edge a = graph.addEdge(graph.getVertex(sourceId), graph.getVertex(destinationId));
            a.setId(id);
            a.setProperties(propertyMap);
        }

        return graph;
    }

}
