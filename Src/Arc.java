public class Arc {
    private int destination;
    private int temps;

    public Arc(int destination, int temps) {
        this.destination = destination;
        this.temps = temps;
    }

    public int getDestination() {
        return destination;
    }

    public int getTemps() {
        return temps;
    }
}
