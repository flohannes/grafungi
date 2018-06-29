package grafungi.graph.io.graphml;

import grafungi.graph.Edge;
import grafungi.graph.Graph;
import grafungi.graph.Vertex;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author fschmidt
 */
public class GraphMLConverter {

    private final static String NODE_DESCRIPTION_KEY = "d0";
    private final static String NODE_GRAPHICS_KEY = "d1";
    private final static String EDGE_GRAPHICS_KEY = "d2";

    private final static String DEFAULT_COLOR = "#FFCC00";

    private final static double DEG_240_BLUE = 240.0 / 360.0;

    /**
     *
     * @param graph
     * @param graphmlFile
     */
    public static void convert(Graph graph, File graphmlFile) {

        try {
//            Map<Long, Long> idTable = new HashMap<>();

            Collection<Vertex> coll = graph.getVertices();

            // INIT GRAPHML
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("graphml");
            rootElement.setAttribute("xmlns",
                    "http://graphml.graphdrawing.org/xmlns");
            rootElement.setAttribute("xmlns:java",
                    "http://www.yworks.com/xml/yfiles-common/1.0/java");
            rootElement
                    .setAttribute("xmlns:sys",
                            "http://www.yworks.com/xml/yfiles-common/markup/primitives/2.0");
            rootElement.setAttribute("xmlns:x",
                    "http://www.yworks.com/xml/yfiles-common/markup/2.0");
            rootElement.setAttribute("xmlns:xsi",
                    "http://www.w3.org/2001/XMLSchema-instance");
            rootElement.setAttribute("xmlns:y",
                    "http://www.yworks.com/xml/graphml");
            rootElement.setAttribute("xmlns:yed",
                    "http://www.yworks.com/xml/yed/3");
            rootElement
                    .setAttribute(
                            "xsi:schemaLocation",
                            "http://graphml.graphdrawing.org/xmlns http://www.yworks.com/xml/schema/graphml/1.1/ygraphml.xsd");
            doc.appendChild(rootElement);

            Element graphElement = doc.createElement("graph");
            graphElement.setAttribute("id", "G");
            graphElement.setAttribute("edgedefault", "directed");
            rootElement.appendChild(graphElement);

            Element keyElement = doc.createElement("key");
            keyElement.setAttribute("id", NODE_DESCRIPTION_KEY);
            keyElement.setAttribute("for", "node");
            keyElement.setAttribute("attr.name", "description");
            keyElement.setAttribute("attr.type", "string");
            rootElement.appendChild(keyElement);

            keyElement = doc.createElement("key");
            keyElement.setAttribute("id", NODE_GRAPHICS_KEY);
            keyElement.setAttribute("for", "node");
            keyElement.setAttribute("yfiles.type", "nodegraphics");
            rootElement.appendChild(keyElement);

            keyElement = doc.createElement("key");
            keyElement.setAttribute("id", EDGE_GRAPHICS_KEY);
            keyElement.setAttribute("for", "edge");
            keyElement.setAttribute("yfiles.type", "edgegraphics");
            rootElement.appendChild(keyElement);

            int i = 0;
            Map<String, Integer> vertexIdMap = new HashMap<>();
            for (Vertex v : coll) {
                Map<String, Object> table = v.getProperties();

                String vertexId = v.getId();
                String vertexLabel = "";
                if (table.containsKey("label")) {
                    vertexLabel = (String) table.get("label");
                }
                String vertexValue = "";
                if (table.containsKey("value")) {
                    vertexValue = (String) table.get("value");
                }

                Element nodeElement = doc.createElement("node");
                vertexIdMap.put(v.getId(), i);
                nodeElement.setAttribute("id", "n" + i);
                graphElement.appendChild(nodeElement);

                Element dataElement = doc.createElement("data");
                dataElement.setAttribute("key", NODE_DESCRIPTION_KEY);
                dataElement.setTextContent(vertexId + "; " + "\n" + vertexValue);
                nodeElement.appendChild(dataElement);

                dataElement = doc.createElement("data");
                dataElement.setAttribute("key", NODE_GRAPHICS_KEY);
                nodeElement.appendChild(dataElement);

                Element shapeNodeElement = doc.createElement("y:ShapeNode");
                dataElement.appendChild(shapeNodeElement);

                // set node color
                String nodeColor = DEFAULT_COLOR;

                Element fillElement = doc.createElement("y:Fill");
                fillElement.setAttribute("color", nodeColor);
                fillElement.setAttribute("transparent", "false");
                shapeNodeElement.appendChild(fillElement);

                Element nodeLabelElement = doc.createElement("y:NodeLabel");
                nodeLabelElement.setTextContent(vertexLabel);

                shapeNodeElement.appendChild(nodeLabelElement);
                i++;
            }

            for (Edge a : graph.getEdges()) {
                Element edgeElement = doc.createElement("edge");
                edgeElement
                        .setAttribute("source", "n" + vertexIdMap.get(a.getSource().getId()));
                edgeElement
                        .setAttribute("target", "n" + vertexIdMap.get(a.getDestination().getId()));
                graphElement.appendChild(edgeElement);

                Element dataElement = doc.createElement("data");
                dataElement.setAttribute("key", EDGE_GRAPHICS_KEY);
                edgeElement.appendChild(dataElement);

                Element polyLineEdgeElement = doc
                        .createElement("y:PolyLineEdge");
                dataElement.appendChild(polyLineEdgeElement);

                Element arrowsElement = doc.createElement("y:Arrows");
                arrowsElement.setAttribute("source", "none");
                arrowsElement.setAttribute("target", "standard");
                polyLineEdgeElement.appendChild(arrowsElement);

                Element edgeLabelElement = doc.createElement("y:EdgeLabel");
                edgeLabelElement.setAttribute("alignment", "center");
                polyLineEdgeElement.appendChild(edgeLabelElement);

                Element edgeLineStyleElement = doc
                        .createElement("y:LineStyle");

                edgeLineStyleElement.setAttribute("width", "0.1");
                edgeLineStyleElement.setAttribute("type", "dotted");

                polyLineEdgeElement.appendChild(edgeLineStyleElement);
            }

            TransformerFactory transformerFactory = TransformerFactory
                    .newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(graphmlFile);
            transformer.transform(source, result);
        } catch (ParserConfigurationException | TransformerConfigurationException ex) {
            Logger.getLogger(GraphMLConverter.class.getName()).log(Level.SEVERE, null, ex);

        } catch (TransformerException ex) {
            Logger.getLogger(GraphMLConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
