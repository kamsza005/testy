package aco;

import ant.Ant;
import city.City;
import city.CityUtils;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class AcoTest {
    //system
    private int maxIterations;
    private double c = 1.0; // startowa wartość fermonów
    private double alpha = 1.0; // parametr kontrolujący wpływ feromonów
    private double beta = 5.0; // parametr kontrolujący wpływ długości pomiędzy miastami
    private double ro = 0.5; // współczynnik wyparowywania feromonów
    private double Q = 500.0; // stała
    private double randomFactor = 0.1; // szansa na losowe miasto
    private Random rand = new Random();
    private int attempts = 1;
    // iteration

    //cities
    private static int numberOfCities;
    private City[] cities;
    private CityUtils cityUtils = new CityUtils();
    private double lengths[][];

    //ants
    private double antFactory = 0.75; // wpółczynnik stosunku mrówek do miast
    private int numberOfAnts;
    private Ant[] ants;

    private double[][] pheromones;
    private double[] probabilities;

    private int currentIndex = 0; // wskaźnik do poruszania się po miastach

    //tours
    private double bestTourLength = Double.MAX_VALUE;

    public AcoTest(AcoProperty acoProperty, Double value) throws Exception {
        if (!acoProperty.equals(AcoProperty.MAX_ITERATIONS)) {
            this.maxIterations = 1000;
        }
        switch (acoProperty) {
            case ALPHA:
                alpha = value;
                break;
            case BETA:
                beta = value;
                break;
            case RO:
                ro = value;
                break;
            case ANT_FACTOR:
                antFactory = value;
                break;
            case RANDOM_FACTOR:
                randomFactor = value;
                break;
            case ATTEMPTS:
                attempts = value.intValue();
                break;
            case MAX_ITERATIONS:
                maxIterations = value.intValue();
                break;

                default:
                    throw new Exception("Złe acoProperty");
        }
        cities = new City[]{
                new City(0, 53.1745115, 22.1228459, "Kalinowo", "Piątnica", true),
                new City(1, 54.05625, 17.43582, "Wojsk", "Lipnica", true),
                new City(2, 54.02539, 16.24581, "Czaple", "Świeszyno", true),
                new City(3, 51.70945, 17.47852, "Durzyn", "Krotoszyn", true),
                new City(4, 53.67358, 22.34982, "Długochorzele", "Prostki", true),
                new City(5, 52.78982, 14.63671, "Smolnica", "Dębno", true),
                new City(6, 52.80546, 17.39941, "Stępuchowo", "Damasławek", true),
                new City(7, 52.8563889, 18.6758333, "Stara Wieś", "Aleksandrów Kujawski", true),
                new City(8, 54.0362068, 16.0571939, "Pomianowo", "Białogard", true),
                new City(9, 52.9714052, 14.6715424, "Góralice", "Trzcińsko-Zdrój", true),
                new City(10, 54.68024, 17.93986, "Mierzynko", "Gniewino", true),
                new City(11, 51.5586965, 19.9278667, "Dębniak", "Ujazd", true),
                new City(12, 52.1536111, 20.6783333, "Kotowice", "Brwinów", true),
                new City(13, 51.8333333, 18.8833333, "Bratków Dolny", "Zadzim", true),
                new City(14, 50.6954674, 22.3080568, "Ciechocin", "Modliborzyce", true),
                new City(15, 51.8972364, 21.1900672, "Rososzka", "Chynów", true),
                new City(16, 50.5527471, 16.5825752651107, "Chudzów", "Nowa Ruda", true),
                new City(17, 53.7851137, 16.9260143, "Koleśnik", "Biały Bór", true),
                new City(18, 52.1469444, 17.9538889, "Trąbczyn Dworski", "Zagórów", true),
                new City(19, 53.583799, 17.1121929, "Uniechówek", "Debrzno", true),
                new City(20, 53.94633, 22.64396, "Szeszki", "Wieliczki", true),
                new City(21, 49.9951444, 22.9963849, "Chałupki Chotynieckie", "Radymno", true),
                new City(22, 50.5579208, 18.1502056, "Otmice", "Izbicko", true),
                new City(23, 53.1363126, 16.9224314, "Śmiłowo", "Kaczory", true),
                new City(24, 54.1121393, 19.9076434, "Kwitajny Małe", "Godkowo", true),
                new City(25, 51.1627081, 23.0089622, "Dorohucza", "Trawniki", true),
                new City(26, 52.465085, 18.3630421, "Zygmuntowo", "Skulsk", true),
                new City(27, .4224633, 22.5803852, "Majdan Kozłowiecki", "Lubartów", true)
        };
        numberOfCities = cities.length;
        afterConstructorSetup();
    }

    private void afterConstructorSetup() {
        lengths = new double[numberOfCities][numberOfCities];
        setupLengths();
        numberOfAnts = (int) (numberOfCities * antFactory);
        ants = new Ant[numberOfAnts];
        pheromones = new double[numberOfCities][numberOfCities];
        probabilities = new double[numberOfCities];
        generateAnts();
    }


    public double solve(){
        IntStream.range(0, attempts).forEach(i -> {
            clearTrails();
            for (int j = 0; j < maxIterations; j++) {
                currentIndex = 0;
                setupAnts();
                moveAnts();
                updatePheromones();
                updateBest();
            }
        });


        return bestTourLength;
    }

    private void setupLengths(){
        for (int i = 0; i < numberOfCities; i++){
            for (int j = 0; j < numberOfCities; j++){
                lengths[i][j] = cityUtils.distance(cities[i], cities[j]);
            }
        }
    }

    private void generateAnts(){
        IntStream.range(0, numberOfAnts).forEach(i -> {
            ants[i] = new Ant(numberOfCities);
        });
    }

    private void setupAnts(){
        Stream.of(ants).forEach(ant -> {
            ant.clear();
            ant.visitCity(-1 , rand.nextInt(numberOfCities));
        });
    }

    private void moveAnts(){
        IntStream.range(currentIndex, numberOfCities - 1)
                .forEach(i -> {
                    Stream.of(ants).forEach(ant -> ant.visitCity(currentIndex, selectNextCity(ant)));
                    currentIndex++;
                });
    }

    private int selectNextCity(Ant ant){
        if (rand.nextDouble() < randomFactor) {
            return selectRandomCity(ant);
        }

        calculateProbabilities(ant);

        double r = rand.nextDouble();
        double total = 0;
        for (int i = 0; i < numberOfCities; i++) {
            total += probabilities[i];
            if (total >= r) {
                return i;
            }
        }

        throw new RuntimeException("Nie ma więcej miast");
    }

    private int selectRandomCity(Ant a) {
        ArrayList<Integer> cities = new ArrayList<>();
        for (int i = 0; i < numberOfCities; i++) {
            if (!a.visited(i)) {
                cities.add(i);
            }
        }
        return cities.get(rand.nextInt(cities.size()));
    }

    private void calculateProbabilities(Ant ant) {
        int x = ant.tourOrder[currentIndex];
        double tau;
        double eta;
        double denominator = 0.0;
        double numerator;

        for (int z = 0; z < numberOfCities; z++) {
            if (!ant.visited(z)) {
                tau = Math.pow(pheromones[x][z], alpha);
                eta = Math.pow(1.0 / lengths[x][z], beta);
                denominator += tau * eta;
            }
        }
        for (int y = 0; y < numberOfCities; y++) {
            if (ant.visited(y)) {
                probabilities[y] = 0.0;
            } else {
                tau = Math.pow(pheromones[x][y], alpha);
                eta = Math.pow(1.0 / lengths[x][y], beta);
                numerator = tau * eta;
                probabilities[y] = numerator / denominator;
            }
        }
    }

    private void updatePheromones() {
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = 0; j < numberOfCities; j++) {
                pheromones[i][j] *= ro;
            }
        }

        for (Ant a : ants) {
            double pheromoneAmount = Q / a.getTrailLength(lengths);
            for (int i = 0; i < numberOfCities - 1; i++) {
                pheromones[a.tourOrder[i]][a.tourOrder[i + 1]] += pheromoneAmount;
            }
            pheromones[a.tourOrder[numberOfCities - 1]][a.tourOrder[0]] += pheromoneAmount;
        }
    }

    private void clearTrails(){
        for (int i = 0; i < numberOfCities; i++){
            for (int j = 0; j < numberOfCities; j++){
                pheromones[i][j] = c;
            }
        }
    }

    private void updateBest() {
        for (Ant a : ants) {
            if (a.getTrailLength(lengths) < bestTourLength) {
                bestTourLength = a.getTrailLength(lengths);
            }
        }
    }
}
