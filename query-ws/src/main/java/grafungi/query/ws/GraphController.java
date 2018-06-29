package grafungi.query.ws;

import grafungi.query.ws.data.GraphMetadata;
import grafungi.query.ws.data.GraphMetadataDBRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author fschmidt
 */
@RestController
@RequestMapping("/graphs")
public class GraphController {

    @Value("${app.createdata}")
    private boolean createDummyData;

    @Autowired
    private GraphMetadataDBRepository repository;

    public GraphController() {
//        System.out.println("G: "+createDummyData);
//        if (createDummyData) {
//            add();
//        }
    }

    @PostConstruct
    public void init() {
        System.out.println("G: " + createDummyData);
        if (createDummyData) {
            add();
        }
    }

    @CrossOrigin
    @RequestMapping("/all")
    public List<GraphMetadata> getAll() {
        Iterable<GraphMetadata> patientsIterator = repository.findAll();
        Iterator<GraphMetadata> iter = patientsIterator.iterator();
        List<GraphMetadata> graphs = new ArrayList<>();
        while (iter.hasNext()) {
            GraphMetadata graph = iter.next();
            graphs.add(graph);
        }
        return graphs;
    }

    @CrossOrigin
    @RequestMapping("/all-trees")
    public List<GraphMetadata> getAllTrees() {
        Iterable<GraphMetadata> patientsIterator = repository.findAll();
        Iterator<GraphMetadata> iter = patientsIterator.iterator();
        List<GraphMetadata> graphs = new ArrayList<>();
        while (iter.hasNext()) {
            GraphMetadata graph = iter.next();
            if (graph.getType().equals("tree")) {
                graphs.add(graph);
            }
        }
        return graphs;
    }

    @CrossOrigin
    @RequestMapping("/all-bns")
    public List<GraphMetadata> getAllBNs() {
        Iterable<GraphMetadata> patientsIterator = repository.findAll();
        Iterator<GraphMetadata> iter = patientsIterator.iterator();
        List<GraphMetadata> graphs = new ArrayList<>();
        while (iter.hasNext()) {
            GraphMetadata graph = iter.next();
            if (graph.getType().equals("bn")) {
                graphs.add(graph);
            }
        }
        return graphs;
    }

    @CrossOrigin
    @RequestMapping("/id/{id}")
    public GraphMetadata getById(@PathVariable("id") Long id) {
        GraphMetadata graph = repository.findById(id).get();
        return graph;
    }

    @RequestMapping("/new")
    public void add(@RequestParam(value = "name", required = true) String name, @RequestParam(value = "id", required = true) String id,
            @RequestParam(value = "type", defaultValue = "tree") String type) {
        GraphMetadata graph = new GraphMetadata();
        graph.setId(new Date().getTime());
        graph.setGraphDBId(id);
        graph.setGraphName(name);
        graph.setType(type);
        repository.save(graph);
    }

//    @RequestMapping("/add")
    private void add() {
        GraphMetadata graph1 = new GraphMetadata();
        graph1.setId(new Date().getTime());
        graph1.setGraphDBId("graphdiarrhea1");
        graph1.setGraphName("Diarrhea");
        graph1.setType("tree");
        repository.save(graph1);

        GraphMetadata graph2 = new GraphMetadata();
        graph2.setId(new Date().getTime());
        graph2.setGraphDBId("graphdyspepsia1");
        graph2.setGraphName("Dyspepsia");
        graph2.setType("tree");
        repository.save(graph2);

        GraphMetadata graph5 = new GraphMetadata();
        graph5.setId(new Date().getTime());
        graph5.setGraphDBId("graphhcc1");
        graph5.setGraphName("HCC");
        graph5.setType("tree");
        repository.save(graph5);

        GraphMetadata graph9 = new GraphMetadata();
        graph9.setId(new Date().getTime());
        graph9.setGraphDBId("graphgoogle1");
        graph9.setGraphName("Google");
        graph9.setType("tree");
        repository.save(graph9);

        GraphMetadata graph10 = new GraphMetadata();
        graph10.setId(new Date().getTime());
        graph10.setGraphDBId("graphhappy1");
        graph10.setGraphName("Happy");
        graph10.setType("tree");
        repository.save(graph10);

        GraphMetadata graph11 = new GraphMetadata();
        graph11.setId(new Date().getTime());
        graph11.setGraphDBId("graphcredit1");
        graph11.setGraphName("Credit");
        graph11.setType("tree");
        repository.save(graph11);

        GraphMetadata graph3 = new GraphMetadata();
        graph3.setId(new Date().getTime());
        graph3.setGraphDBId("bncancer1");
        graph3.setGraphName("Simple Cancer");
        graph3.setType("bn");
        repository.save(graph3);

        GraphMetadata graph4 = new GraphMetadata();
        graph4.setId(new Date().getTime());
        graph4.setGraphDBId("bnlung1");
        graph4.setGraphName("Simple Lung Disease");
        graph4.setType("bn");
        repository.save(graph4);

        GraphMetadata graph6 = new GraphMetadata();
        graph6.setId(new Date().getTime());
        graph6.setGraphDBId("bnasia1");
        graph6.setGraphName("Asia");
        graph6.setType("bn");
        repository.save(graph6);

        GraphMetadata graph7 = new GraphMetadata();
        graph7.setId(new Date().getTime());
        graph7.setGraphDBId("bnalarm1");
        graph7.setGraphName("Alarm");
        graph7.setType("bn");
        repository.save(graph7);

        GraphMetadata graph8 = new GraphMetadata();
        graph8.setId(new Date().getTime());
        graph8.setGraphDBId("bnhepar1");
        graph8.setGraphName("Hepar");
        graph8.setType("bn");
        repository.save(graph8);
    }
}
