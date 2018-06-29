package grafungi.graph.io.d3;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import grafungi.graph.Edge;
import grafungi.graph.Graph;
import grafungi.graph.Vertex;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 *
 * @author fschmidt
 */
public class D3Converter {

    public static String getD3TreeJson(Graph graph, Vertex root) {
        TreeD3 tree = convert(graph, root);
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        String json = gson.toJson(tree.getRootNodes());
        return json;
    }

    public static String getD3TreeJson(Graph graph, Vertex root, int hops) {
        TreeD3 tree = convert(graph, root, hops);
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        String json = gson.toJson(tree.getRootNodes());
        return json;
    }

    /*
    Only use with DAG (directed acyclic graph)
     */
    private static TreeD3 convert(Graph graph, Vertex root) {
        TreeD3 tree = new TreeD3();
        Vertex vRoot = graph.getVertex(root.getId());
        String name = vRoot.getId();
        Map<String, String> rootproperties = new HashMap<>();
        if (vRoot.getProperties().containsKey("name")) {
            name = (String) vRoot.getProperty("name");
        }
        VertexTreeD3 vtRoot = new VertexTreeD3(name, null);
        for (String key : vRoot.getProperties().keySet()) {
            if (key.equals("type")) {
                vtRoot.setType((String) vRoot.getProperty(key));
            }
            if (key.equals("value")) {
                vtRoot.setValue((Integer) vRoot.getProperty(key));
            }
            if (key.equals("level")) {
                vtRoot.setLevel((String) vRoot.getProperty(key));
            }
            if (!key.equals("name") && !key.equals("type") && !key.equals("value") && !key.equals("level")) {
                rootproperties.put(key, vRoot.getProperty(key).toString());
            }
        }
        vtRoot.setProperties(rootproperties);
        LinkedList<VertexTreeD3> queueTree = new LinkedList<>();
        queueTree.add(vtRoot);
        LinkedList<Vertex> queueOrig = new LinkedList<>();
        queueOrig.add(root);
        while (!queueTree.isEmpty()) {
            VertexTreeD3 vTree = queueTree.poll();
            Vertex vGraph = queueOrig.poll();
            for (Edge a : vGraph.getArcs(Edge.Direction.OUTGOING)) {
                Vertex childV = a.getDestination();
                String nameV = childV.getId();
                Map<String, String> properties = new HashMap<>();
                if (childV.getProperties().containsKey("name")) {
                    nameV = (String) childV.getProperty("name");
                }
                VertexTreeD3 vtChild = new VertexTreeD3(nameV, vTree);
                for (String key : childV.getProperties().keySet()) {
                    if (key.equals("type")) {
                        vtChild.setType((String) childV.getProperty(key));
                    }
                    if (key.equals("value")) {
                        vtChild.setValue((Integer) childV.getProperty(key));
                    }
                    if (key.equals("level")) {
                        vtChild.setLevel((String) childV.getProperty(key));
                    }
                    if (!key.equals("name") && !key.equals("type") && !key.equals("value") && !key.equals("level")) {
                        properties.put(key, childV.getProperty(key).toString());
                    }

                }
                if (a.getProperties().containsKey("prob")) {
                    properties.put("prob", String.valueOf(a.getProperty("prob")));
//                    vTree.getProperties().put("prob-" + childV.getId(), String.valueOf(a.getProperty("prob")));
                }
                vtChild.setProperties(properties);
                vTree.addChild(vtChild);
                queueTree.add(vtChild);
                queueOrig.add(childV);
            }
        }

        //build tree
        tree.addRootNode(vtRoot);
        return tree;
    }

    /*
    Use for non-cyclic and cyclic graphs
     */
    private static TreeD3 convert(Graph graph, Vertex root, int hops) {
        TreeD3 tree = new TreeD3();
        Vertex vRoot = graph.getVertex(root.getId());
        String name = vRoot.getId();
        Map<String, String> rootproperties = new HashMap<>();
        if (vRoot.getProperties().containsKey("name")) {
            name = (String) vRoot.getProperty("name");
        }
        VertexTreeD3 vtRoot = new VertexTreeD3(name, null);
        for (String key : vRoot.getProperties().keySet()) {
            if (key.equals("type")) {
                vtRoot.setType((String) vRoot.getProperty(key));
            }
            if (key.equals("value")) {
                vtRoot.setValue((Integer) vRoot.getProperty(key));
            }
            if (key.equals("level")) {
                vtRoot.setLevel((String) vRoot.getProperty(key));
            }
            if (!key.equals("name") && !key.equals("type") && !key.equals("value") && !key.equals("level")) {
                rootproperties.put(key, vRoot.getProperty(key).toString());
            }
        }
        vtRoot.setProperties(rootproperties);
        Queue<VertexTreeD3> queueTree = new LinkedList<>();
        queueTree.add(vtRoot);
        //ONLY FOR max num of hops
        Queue<Vertex> queueOrig = new LinkedList<>();
        queueOrig.add(root);
        for (int i = 0; i < hops; i++) {
            Queue<Vertex> newQueue = new LinkedList<>();
            Queue<VertexTreeD3> newQueueTree = new LinkedList<>();
            while (!queueOrig.isEmpty()) {
                Vertex vOut = queueOrig.poll();
                VertexTreeD3 vOutTree = queueTree.poll();
                for (Edge a : vOut.getArcs(Edge.Direction.OUTGOING)) {
                    Vertex childV = a.getDestination();
                    String nameV = childV.getId();
                    Map<String, String> properties = new HashMap<>();
                    if (childV.getProperties().containsKey("name")) {
                        nameV = (String) childV.getProperty("name");
                    }
                    VertexTreeD3 vtChild = new VertexTreeD3(nameV, vOutTree);
                    for (String key : childV.getProperties().keySet()) {
                        if (key.equals("type")) {
                            vtChild.setType((String) childV.getProperty(key));
                        }
                        if (key.equals("value")) {
                            vtChild.setValue((Integer) childV.getProperty(key));
                        }
                        if (key.equals("level")) {
                            vtChild.setLevel((String) childV.getProperty(key));
                        }
                        if (!key.equals("name") && !key.equals("type") && !key.equals("value") && !key.equals("level")) {
                            properties.put(key, childV.getProperty(key).toString());
                        }

                    }
                    if (a.getProperties().containsKey("prob")) {
                        properties.put("prob", String.valueOf(a.getProperty("prob")));
//                        vOutTree.getProperties().put("prob-" + childV.getId(), String.valueOf(a.getProperty("prob")));
                    }
                    vtChild.setProperties(properties);
                    vOutTree.addChild(vtChild);
                    newQueueTree.add(vtChild);
                    newQueue.add(childV);
                }
            }
            queueOrig = newQueue;
            queueTree = newQueueTree;
        }
        //build tree
        tree.addRootNode(vtRoot);
        return tree;
    }

    public static String getD3Json(Graph graph) {
        GraphD3 graphD3 = convert(graph);
        String json = JSON.toJSONString(graphD3, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.BrowserCompatible);
        return json;
    }

    public static GraphD3 convert(Graph graph) {
        GraphD3 graphD3 = new GraphD3();
        Map<String, Integer> vertexD3IdMap = new HashMap<>();
        Map<String, Vertex> vertexIdMap = new HashMap<>();
        int i = 0;
        for (Vertex v : graph.getVertices()) {
            VertexD3 vD3 = new VertexD3();
            vertexD3IdMap.put(v.getId(), i);
            vertexIdMap.put(v.getId(), v);
            if (v.getProperties().containsKey("name")) {
                vD3.setName((String) v.getProperties().get("name"));
            } else {
                vD3.setName("Vertex " + String.valueOf(i));
            }
            if (v.getProperties().containsKey("group")) {
                vD3.setGroup((int) v.getProperties().get("group"));
            } else {
                vD3.setGroup(1);
            }
            Map<Object, Object> properties = new HashMap<>();
            properties.put("id", v.getId());
            for (String key : v.getProperties().keySet()) {
                if (key != null) {
                    if (!key.equals("name") && !key.equals("group") && v.getProperty(key) != null) {
//                    String jsonInString = new Gson().toJson(v.getProperty(key));
                        properties.put(key, (Serializable) v.getProperty(key));
                    }
                }
            }
            vD3.setProperties(properties);

            graphD3.addVertex(vD3);
            i++;
        }
        for (Edge a : graph.getEdges()) {
            ArcD3 aD3 = new ArcD3();
            aD3.setSource(vertexD3IdMap.get(a.getSource().getId()));
            aD3.setTarget(vertexD3IdMap.get(a.getDestination().getId()));
            if (a.getProperties().containsKey("name")) {
                aD3.setName((String) a.getProperties().get("name"));
            } else {
                aD3.setName("Arc " + vertexD3IdMap.get(a.getSource().getId()) + " to " + vertexD3IdMap.get(a.getDestination().getId()));
            }
            if (a.getProperties().containsKey("value")) {
                aD3.setValue((int) a.getProperties().get("value"));
            } else {
                aD3.setValue(1);
            }
            graphD3.addArc(aD3);
        }
        return graphD3;
    }
}
