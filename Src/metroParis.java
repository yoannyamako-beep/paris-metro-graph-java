import java.util.Collections;
import java.io.*;
import java.nio.charset.*;
import java.util.Scanner;

public class metroParis {

	public static void main(String[] args) {

		Graph g = new Graph();
		readFile(g);

		System.out.println("Lecture terminée !");
		System.out.println("Stations chargées : " + g.getStations().size());

		if (g.getStations().isEmpty()) {
			System.out.println("Aucune station chargée -> arrêt");
			return;
		}

		Trajet t = dijkstra(g, 0, 10);
		if (t != null) t.print();
	}

	// LECTURE FICHIER
	public static void readFile(Graph g) {

		File f = new File("Metro.txt");

		try (BufferedInputStream bis =
					 new BufferedInputStream(new FileInputStream(f))) {

			Charset charset = detectCharset(bis);

			Scanner sc = new Scanner(new InputStreamReader(new FileInputStream(f), charset));

			// première ligne
			if (!sc.hasNextLine()) return;
			String[] first = sc.nextLine().trim().split(" ");
			int nbStation = Integer.parseInt(first[0]);
			int nbArc = Integer.parseInt(first[1]);

			// stations
			while (sc.hasNextLine()) {
				String line = sc.nextLine().trim();
				if (line.equals("$")) break;
				if (line.isEmpty()) continue;

				String[] parts = line.split(" ");
				int id = Integer.parseInt(parts[0]);
				String nom = String.join(" ",
						java.util.Arrays.copyOfRange(parts, 1, parts.length));

				g.ajouterStations(new Station(id, nom));
			}

			// positions
			while (sc.hasNextLine()) {
				String line = sc.nextLine().trim();
				if (line.equals("$")) break;
				if (line.isEmpty()) continue;

				String[] parts = line.split(" ");
				int id = Integer.parseInt(parts[0]);
				int x = Integer.parseInt(parts[1]);
				int y = Integer.parseInt(parts[2]);

				g.getStations().get(id).setPosition(x, y);
			}

			// arcs
			while (sc.hasNextLine()) {
				String line = sc.nextLine().trim();
				if (line.isEmpty()) continue;

				String[] p = line.split(" ");
				g.ajouterArc(
						Integer.parseInt(p[0]),
						Integer.parseInt(p[1]),
						Integer.parseInt(p[2])
				);
			}

			sc.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// DETECTION ENCODAGE
	private static Charset detectCharset(BufferedInputStream in)
			throws IOException {

		in.mark(3);
		int b1 = in.read();
		int b2 = in.read();
		int b3 = in.read();
		in.reset();

		if (b1 == 0xEF && b2 == 0xBB && b3 == 0xBF)
			return StandardCharsets.UTF_8;
		if (b1 == 0xFF && b2 == 0xFE)
			return StandardCharsets.UTF_16LE;
		if (b1 == 0xFE && b2 == 0xFF)
			return StandardCharsets.UTF_16BE;

		return StandardCharsets.UTF_8;
	}

	// DIJKSTRA
	public static Trajet dijkstra(Graph g, int start, int end) {

		int n = g.getStations().size();
		int[] dist = new int[n];
		boolean[] visited = new boolean[n];
		int[] parent = new int[n];

		for (int i = 0; i < n; i++) {
			dist[i] = Integer.MAX_VALUE;
			parent[i] = -1;
		}
		dist[start] = 0;

		for (int i = 0; i < n; i++) {

			int u = -1, best = Integer.MAX_VALUE;
			for (int j = 0; j < n; j++)
				if (!visited[j] && dist[j] < best) {
					best = dist[j];
					u = j;
				}

			if (u == -1 || u == end) break;
			visited[u] = true;

			for (Arc a : g.getAdj().get(u)) {
				int v = a.getDestination();
				int w = a.getTemps();
				if (!visited[v] && dist[u] + w < dist[v]) {
					dist[v] = dist[u] + w;
					parent[v] = u;
				}
			}
		}

		if (dist[end] == Integer.MAX_VALUE) return null;

		Trajet t = new Trajet();
		for (int v = end; v != -1; v = parent[v])
			t.ajouterStation(g.getStations().get(v));

		Collections.reverse(t.getList());
		t.setTempsTotal(dist[end]);
		return t;
	}
}
