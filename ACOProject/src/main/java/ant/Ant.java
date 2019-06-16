package ant;

public class Ant {
    private int numberOfCities;
    public Integer[] tourOrder;
    private boolean[] visitedCities;

    public Ant(int numberOfCities){
        this.numberOfCities = numberOfCities;
        this.tourOrder = new Integer[numberOfCities];
        this.visitedCities = new boolean[numberOfCities];
    }

    public void visitCity(int currentIndex, int city){
        tourOrder[currentIndex + 1] = city;
        visitedCities[city] = true;
    }

    public boolean visited(int index){
        return visitedCities[index];
    }

    public double getTrailLength(double lengths[][]){
        double length = lengths[tourOrder[numberOfCities - 1]][tourOrder[0]];

        for (int i = 0; i < numberOfCities - 1; i++) {
            length += lengths[tourOrder[i]][tourOrder[i + 1]];
        }
        return length;
    }

    public void clear() {
        for (int i = 0; i < numberOfCities; i++)
            visitedCities[i] = false;
    }
}
