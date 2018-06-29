package grafungi.graph.io.binary;

import grafungi.graph.Graph;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fschmidt
 */
public class ReadWriteBinary {

    public static void write(Graph graph, File file) {
        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(graph);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReadWriteBinary.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReadWriteBinary.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Graph read(File file) {
        Graph graph = null;
        try {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            graph = (Graph) in.readObject();
            in.close();
            fileIn.close();
        }  catch (ClassNotFoundException | FileNotFoundException ex) {
            Logger.getLogger(ReadWriteBinary.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReadWriteBinary.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return graph;
    }
}
