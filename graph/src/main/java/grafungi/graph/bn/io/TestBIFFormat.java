package grafungi.graph.bn.io;

import grafungi.graph.bn.BayesianNetwork;
import grafungi.graph.db.GraphDatabaseAdapter;
import grafungi.graph.db.TitanConnector;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


/**
 *
 * @author fschmidt
 */
public class TestBIFFormat {

    public static void main(String... args) throws FileNotFoundException {
        BayesianNetwork bn = BIFFormat.parse(new FileInputStream("/Users/fschmidt/asia.bif"));
        String keyspace = "bnasia1";
        GraphDatabaseAdapter db = new TitanConnector();
        db.connect("/Users/fschmidt/graphws/"+keyspace);
        db.addGraph(bn);
        db.close();
        
        BayesianNetwork bn2 = BIFFormat.parse(new FileInputStream("/Users/fschmidt/alarm.bif"));
        String keyspace2 = "bnalarm1";
        GraphDatabaseAdapter db2 = new TitanConnector();
        db2.connect("/Users/fschmidt/graphws/"+keyspace2);
        db2.addGraph(bn2);
        db2.close();
        
        BayesianNetwork bn3 = BIFFormat.parse(new FileInputStream("/Users/fschmidt/hepar2.bif"));
        String keyspace3 = "bnhepar1";
        GraphDatabaseAdapter db3 = new TitanConnector();
        db3.connect("/Users/fschmidt/graphws/"+keyspace3);
        db3.addGraph(bn3);
        db3.close();        
    }
}
