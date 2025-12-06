package Lab.Pair;

public class Pair implements PairInterface {

    private double first;
    private double second;

    public Pair(double first, double second){
        this.first = first;
        this.second = second;
    }

    @Override
    public void incrementFirst(double x) {
        this.first += x;
    }

    @Override
    public void incrementSecond(double x) {
        this.second += x;
    }

    @Override
    public void setFirst(double first) {
        this.first = first;
    }

    @Override
    public void setSecond(double second) {
        this.second = second;
    }

    @Override
    public double getFirst() {
        return first;
    }

    @Override
    public double getSecond() {
        return second;
    }
}
