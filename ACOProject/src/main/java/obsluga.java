import aco.Aco;
import aco.AcoProperty;
import aco.AcoTester;
import city.City;
import city.CityUtils;
import geocoder.Geocoder;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import city.CityDAO;
import tour.Tour;



import javax.persistence.criteria.Selection;


public class obsluga {

    public TextField wykres_start;
    public TextField wykres_step;
    public TextField wykres_stop;

    public TextField ustawienia_c;
    public TextField ustawienia_alfa;
    public TextField ustawienia_beta;
    public TextField ustawienia_ro;
    public TextField ustawienia_q;
    public TextField ustawienia_randomfactor;
    public TextField ustawienia_antfactory;
    public TextField ustawienia_iteracje;
    public TextField ustawienia_miasta;

    public Button generuj_wykres;
    public Button parametry;

    public TextField ustawienia_info;

    public TextField aco_miasto;


    public Button aco_dodaj;
    public Button aco_szukaj;
    public Button aco_usun;
    public Button aco_wyszukaj;

    public ListView list1;
    public ListView list2;

    public TextArea aco_info;

    private ObservableList<City> pierwszalista;
    private ObservableList<City> drugalista;

    private CityDAO city = new CityDAO();
    private Geocoder geocoder = new Geocoder();

    public void initialize(){

        pierwszalista=FXCollections.observableArrayList(city.getCitiesByName(""));
        drugalista=FXCollections.observableArrayList();

        list1.setItems(pierwszalista);
    }


    public void removeFromList(javafx.scene.input.MouseEvent event) {

        Node aNode = (Node)event.getSource();
        int selected;

        if(event.getClickCount()==2){

            if(aNode.equals(list1)){
                selected=list1.getSelectionModel().getSelectedIndex();
                drugalista.add((City) list1.getSelectionModel().getSelectedItem());
                list2.setItems(drugalista);
                pierwszalista.remove(selected);

            }else
            {
                selected=list2.getSelectionModel().getSelectedIndex();
                pierwszalista.add((City) list2.getSelectionModel().getSelectedItem());
                list1.setItems(pierwszalista);
                drugalista.remove(selected);

            }



        }

    }



    public void generuj(ActionEvent actionEvent) {

        String start1 = wykres_start.getText();
        Double start = Double.parseDouble(start1);

        String step1 = wykres_step.getText();
        Double step = Double.parseDouble(step1);

        String stop1 = wykres_stop.getText();
        Double stop = Double.parseDouble(stop1);

        AcoTester acoTester = new AcoTester(AcoProperty.ANT_FACTOR, start,step,stop);
        acoTester.test();
    }


    public void ustaw_parametry(ActionEvent actionEvent) {

        String c1 = ustawienia_c.getText();
        Double c = Double.parseDouble(c1);

        String alfa1 = ustawienia_alfa.getText();
        Double alfa = Double.parseDouble(alfa1);

        String beta1 = ustawienia_beta.getText();
        Double beta = Double.parseDouble(beta1);

        String ro1 = ustawienia_ro.getText();
        Double ro = Double.parseDouble(ro1);

        String q1 = ustawienia_q.getText();
        Double q = Double.parseDouble(q1);

        String randomfactor1 = ustawienia_randomfactor.getText();
        Double randomfactor = Double.parseDouble(randomfactor1);

        String antfactory1 = ustawienia_antfactory.getText();
        Double antfactory = Double.parseDouble(antfactory1);


        Aco aco= new Aco(c,alfa,beta,ro,q,randomfactor,antfactory);
        //aco.ustawienia(c,alfa,beta,ro,q,randomfactor,antfactory);

        ustawienia_info.setText("Zmieniono ustawienia");



    }


    public void aco_szukanie(ActionEvent actionEvent) {

        String miasto = aco_miasto.getText();

        pierwszalista=FXCollections.observableArrayList(city.getCitiesByName(miasto));

        list1.setItems(pierwszalista);



        /*
        final ObservableList<City> pierwszalista =
                FXCollections.observableArrayList(city.getCitiesByName(miasto));


         */


    }


    public void aco_wyszukanie(ActionEvent actionEvent) {

        City[] cities = new City[drugalista.size()];

        int index = 0;
        for (City c : drugalista) {
            cities[index] = geocoder.getCity(c.getName(), c.getGmina());
            index++;
        }
        geocoder.setZeroIndex();

        Aco aco = new Aco(cities,1000);
        Tour tour = aco.solve(4);
        tour.showBest();
        System.out.println(tour.getBestOrder());






/*
        for (City a : drugalista) {

            System.out.println(a);

            aco_info.setText(String.valueOf(drugalista));


        }



 */


    }




}
