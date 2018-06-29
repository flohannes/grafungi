package grafungi.graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 *
 * @author fschmidt
 */
public class Vertex extends GraphElement implements Serializable{

    private final Collection<Edge> arcs;

    public Vertex() {
        arcs = new ArrayList<>();
    }

    public void addArc(Edge a) {
        this.arcs.add(a);
    }

    public void removeArc(Edge a) {
        this.arcs.remove(a);
    }

    public Collection<Edge> getArcs() {
        return Collections.unmodifiableCollection(arcs);
    }

    public Collection<Edge> getArcs(Edge.Direction arcDirection) {
        switch (arcDirection) {
            case BOTH:
                return this.getArcs();
            case INCOMING:
                Collection incomingArcs = new ArrayList<>();
                for (Edge arc : arcs) {
                    if (arc.getDestination()== this) {
                        incomingArcs.add(arc);
                    }
                }
                return incomingArcs;
            case OUTGOING:
                Collection outgoingArcs = new ArrayList<>();
                for (Edge arc : arcs) {
                    if (arc.getSource().getId().equals(this.getId())) {
                        outgoingArcs.add(arc);
                    }
                }
                return outgoingArcs;
            case LOOP:
                Collection loops = new ArrayList<>();
                for (Edge arc : arcs) {
                    if (arc.getSource() == this && arc.getDestination() == this) {
                        loops.add(arc);
                    }
                }
                return loops;
            default:
                return null;
        }
    }

    public int getDegree() {
        int numOfLoops = 0;
        for (Edge a : arcs) {
            if (a.getSource() == this && a.getDestination() == this) {
                numOfLoops++;
            }
        }
        return this.arcs.size() + numOfLoops;
    }

    public int getOutDegree() {
        int degreeCounter = 0;
        for (Edge a : arcs) {
            if (a.getSource().getId().equals(this.getId())) {
                degreeCounter++;
            }
        }
        return degreeCounter;
    }

    public int getInDegree() {
        int degreeCounter = 0;
        for (Edge a : arcs) {
            if (a.getDestination() == this) {
                degreeCounter++;
            }
        }
        return degreeCounter;
    }
}
