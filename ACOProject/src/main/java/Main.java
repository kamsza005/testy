import aco.*;


//###########

import aco.AcoProperty;
import aco.AcoTester;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tour.Tour;

/*
public class Main {

    public static void main(String[] args) {
        //Aco aco = new Aco(8,100);
        //Tour tour = aco.solve(8);
        //tour.showBest();
        //System.out.println(tour.getBestOrder());
        //CityUtils cityUtils = new CityUtils();
        //List<City> list = cityUtils.getListOfCities("2", "Gruta");
        //for (City c : list) {
        //    System.out.println(c.getDetails());
        //}
        //AcoTester acoTester = new AcoTester(AcoProperty.ANT_FACTOR, 0.1, 0.1, 2.0);
        //acoTester.test();
    }
}
*/

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent loginViewParent = FXMLLoader.load(getClass().getResource("gui.fxml"));
        Scene loginScene = new Scene(loginViewParent);
        primaryStage.setTitle("GUI");
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}


