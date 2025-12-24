import java.util.ArrayList;

public class Graph {
    private ArrayList<Station> stations = new ArrayList<>();
    private ArrayList<ArrayList<Arc>> adj = new ArrayList<>();

    public void ajouterStations(Station s) {
        stations.add(s);
        adj.add(new ArrayList<Arc>());
    }

    public void ajouterArc(int depart, int destination, int temps) {
        adj.get(depart).add(new Arc(destination, temps));
    }

    public ArrayList<Station> getStations() {
        return stations;
    }

    public ArrayList<ArrayList<Arc>> getAdj() {
        return adj;
    }
}
