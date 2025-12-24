public class Station {
	private int id, x, y;
	private String nom;

	public Station(int id, String nom, int x, int y) {
		this.id = id;
		this.nom = nom;
		this.x = x;
		this.y = y;
	}

	public Station(int id, String nom) {
		this.id = id;
		this.nom = nom;
		this.x = 0;
		this.y = 0;
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	@Override
	public String toString() {
		return id + " - " + nom + " (" + x + "," + y + ")";
	}
}
