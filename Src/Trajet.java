import java.util.ArrayList;

public class Trajet {
	private ArrayList<Station> list = new ArrayList<>();
	private int tempsTotal;

	public void ajouterStation(Station s) {
		list.add(s);
	}

	public void setTempsTotal(int t) {
		this.tempsTotal = t;
	}

	public ArrayList<Station> getList() {
		return list;
	}

	public void print() {
		System.out.println("Trajet :");
		for (Station s : list) {
			System.out.println(" -> " + s);
		}
		System.out.println("Temps total = " + tempsTotal + " secondes");
	}
}

