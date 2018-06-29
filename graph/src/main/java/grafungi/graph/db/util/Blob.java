package grafungi.graph.db.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;

/**
 *
 * @author fschmidt
 */
public class Blob {

    /**
     * Serialize any object
     *
     * @param obj
     * @return
     */
    public static String writeString(Object obj) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream so = new ObjectOutputStream(bo);
            so.writeObject(obj);
            so.flush();
            // This encoding induces a bijection between byte[] and String (unlike UTF-8)
            String s = bo.toString("ISO-8859-1");
            so.close();
            bo.close();
            return s;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(Blob.class.getName()).log(Level.SEVERE, null, e);
        }
        return "";
    }

    /**
     * Deserialize any object
     *
     * @param str
     * @return
     */
    public static Object fromString(String str) {
        // deserialize the object
        try {
            // This encoding induces a bijection between byte[] and String (unlike UTF-8)
            byte b[] = str.getBytes("ISO-8859-1");
            ByteArrayInputStream bi = new ByteArrayInputStream(b);
            ObjectInputStream si = new ObjectInputStream(bi);
            si.close();
            bi.close();
            Object o = si.readObject();
            return o;
        } catch (IOException | ClassNotFoundException e) {
            java.util.logging.Logger.getLogger(Blob.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}
