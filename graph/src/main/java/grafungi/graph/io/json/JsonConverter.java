package grafungi.graph.io.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import grafungi.graph.Graph;

/**
 *
 * @author fschmidt
 */
public class JsonConverter {

    public static String getJson(Graph graph) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Graph.class, new GraphTypeConverter());
        Gson gson = gsonBuilder.create();
        String graphJson = gson.toJson(graph);
        return graphJson;
    }

    public static Graph getGraph(String json){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Graph.class, new GraphTypeConverter());
        Gson gson = gsonBuilder.create();
        Graph graphJson = gson.fromJson(json, Graph.class);
        return graphJson;
    }
}
