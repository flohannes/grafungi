package grafungi.graph.io.tgf;

import grafungi.graph.Edge;
import grafungi.graph.Graph;
import grafungi.graph.Vertex;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fschmidt
 */
public class TGFConverter {

    public static void convert(Graph graph, File tgfFile) {
        try {
            FileWriter fw = new FileWriter(tgfFile);

            Map<Vertex, Integer> idTable = new HashMap<>();
            int i = 1;

            for (Vertex v : graph.getVertices()) {
                idTable.put(v, i);
                fw.write(i + " " + "\n");
                i++;
            }

            fw.write("#\n");

            for (Edge a : graph.getEdges()) {
                fw.write(idTable.get(a.getSource()) + " " + idTable.get(a.getDestination())
                        + " " + "\n");
            }

            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(TGFConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
