package tour;

import city.City;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Tour {
    private City[] cities;
    Integer[] bestTour;
    Integer[] worstTour;
    double bestLength;
    double worstLength;

    public Tour(City[] cities, Integer[] bestTour, Integer[] worstTour, double bestLength, double worstLength) {
        this.cities = cities;
        this.bestTour = bestTour;
        this.worstTour = worstTour;
        this.bestLength = bestLength;
        this.worstLength = worstLength;
    }

    public Tour(Integer[] bestTour, double bestLength) {
        this.bestTour = bestTour;
        this.bestLength = bestLength;
    }

    public Integer[] getBestTour() {
        return bestTour;
    }

    public void setBestTour(Integer[] bestTour) {
        this.bestTour = bestTour;
    }

    public Integer[] getWorstTour() {
        return worstTour;
    }

    public void setWorstTour(Integer[] worstTour) {
        this.worstTour = worstTour;
    }

    public double getBestLength() {
        return bestLength;
    }

    public void setBestLength(double bestLength) {
        this.bestLength = bestLength;
    }

    public double getWorstLength() {
        return worstLength;
    }

    public void setWorstLength(double worstLength) {
        this.worstLength = worstLength;
    }

    public double showBest() {
        System.out.println(bestLength);
        return bestLength;
    }

    public String getBestOrder() {
        List<Integer> ints = Arrays.asList(bestTour);
        return ints.stream().map(integer -> cities[integer].getName()).collect(Collectors.joining(" -> "));
    }
}
