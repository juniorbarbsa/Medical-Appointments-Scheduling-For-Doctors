package sample;

import database.DataBaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuController implements Initializable {
    public TableView<Rdv> tableRdv;
    @FXML
    public TableColumn<Rdv,Integer> id;
    @FXML
    public TableColumn<Rdv,Integer> idPatient;
    @FXML
    public TableColumn<Rdv,String> nomEtPrenomP;
    @FXML
    public TableColumn<Rdv, LocalDate> date;
    @FXML
    public TableColumn<Rdv, LocalTime> heure;
    @FXML
    public TableColumn<Rdv,String> objet;
    public TableView<Rdv> tableRdvA;
    @FXML
    public TableColumn<Rdv,Integer> idA;
    @FXML
    public TableColumn<Rdv,Integer> idPatientA;
    @FXML
    public TableColumn<Rdv,String> nomEtPrenomPA;
    @FXML
    public TableColumn<Rdv, LocalDate> dateA;
    @FXML
    public TableColumn<Rdv, LocalTime> heureA;
    @FXML
    public TableColumn<Rdv,String> objetA;
    @FXML
    public DatePicker autreJour;
    @FXML
    public TableView<Rdv> tableRdvP;
    @FXML
    public TableColumn<Rdv,Integer> idP;
    @FXML
    public TableColumn<Rdv,Integer> idPatientP;
    @FXML
    public TableColumn<Rdv,String> nomEtPrenomPP;
    @FXML
    public TableColumn<Rdv, LocalDate> dateP;
    @FXML
    public TableColumn<Rdv, LocalTime> heureP;
    @FXML
    public TableColumn<Rdv,String> objetP;
    @FXML
    public TextField lepatient;

    ObservableList<Rdv> list = FXCollections.observableArrayList();
    ObservableList<Rdv> listA = FXCollections.observableArrayList();
    ObservableList<Rdv> listP = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initCol();
        initCol1();
        initCol2();
        loadTodayRdv();
    }
    @FXML
    public void loadAddPatient(){
        loadWindow("addPatient.fxml", "Ajouter un nouveau patient");
    }
    @FXML
    public void loadAddRdv(){
        loadWindow("addRdv.fxml", "Ajouter un rendez-vous");
    }
    @FXML
    public void loadListPatients(){
        loadWindow("patientList.fxml", "Liste des patients");
    }
    @FXML
    public void loadListRdv(){
        loadWindow("rdvList.fxml", "Liste des rendez-vous");
    }
    void loadWindow(String loc, String title) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    public void loadTodayRdv() {
        list.clear();
        DataBaseHandler handler=DataBaseHandler.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calobj = Calendar.getInstance();
        String qu = "SELECT * FROM rdv where date='"+df.format(calobj.getTime())+"'";
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                int id=rs.getInt("id");
                int idPatient  = rs.getInt("idPatient");
                LocalDate date = rs.getDate("date").toLocalDate();
                LocalTime heure = rs.getTime("heure").toLocalTime();
                String objet = rs.getString("objet");
                String nomEtPrenomP=rs.getString("nomEtPrenomP");

                list.add(new Rdv(id,idPatient, nomEtPrenomP, date,heure,objet));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PatientAddController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tableRdv.setItems(list);
    }


    private void initCol() {
        idA.setCellValueFactory(new PropertyValueFactory("id"));
        idPatientA.setCellValueFactory(new PropertyValueFactory("idPatient"));
        nomEtPrenomPA.setCellValueFactory(new PropertyValueFactory("nomEtPrenomP"));
        dateA.setCellValueFactory(new PropertyValueFactory("date"));
        heureA.setCellValueFactory(new PropertyValueFactory("heure"));
        objetA.setCellValueFactory(new PropertyValueFactory("objet"));

    }
    private void initCol1() {
        id.setCellValueFactory(new PropertyValueFactory("id"));
        idPatient.setCellValueFactory(new PropertyValueFactory("idPatient"));
        nomEtPrenomP.setCellValueFactory(new PropertyValueFactory("nomEtPrenomP"));
        date.setCellValueFactory(new PropertyValueFactory("date"));
        heure.setCellValueFactory(new PropertyValueFactory("heure"));
        objet.setCellValueFactory(new PropertyValueFactory("objet"));

    }
    private void initCol2() {
        idP.setCellValueFactory(new PropertyValueFactory("id"));
        idPatientP.setCellValueFactory(new PropertyValueFactory("idPatient"));
        nomEtPrenomPP.setCellValueFactory(new PropertyValueFactory("nomEtPrenomP"));
        dateP.setCellValueFactory(new PropertyValueFactory("date"));
        heureP.setCellValueFactory(new PropertyValueFactory("heure"));
        objetP.setCellValueFactory(new PropertyValueFactory("objet"));

    }
    @FXML
    public void loadPatient(){
        initCol1();
        listP.clear();
        DataBaseHandler handler=DataBaseHandler.getInstance();
        String patient = lepatient.getText();
        if (patient.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Choisissez l'id du patient");
            alert.showAndWait();
            return;
        }
        String qu = "SELECT * FROM rdv where idPatient="+patient;
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                int id=rs.getInt("id");
                int idPatient  = rs.getInt("idPatient");
                LocalDate date = rs.getDate("date").toLocalDate();
                LocalTime heure = rs.getTime("heure").toLocalTime();
                String objet = rs.getString("objet");
                String nomEtPrenomP=rs.getString("nomEtPrenomP");
                listP.add(new Rdv(id,idPatient, nomEtPrenomP, date,heure,objet));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PatientAddController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tableRdvP.setItems(listP);
    }
    @FXML
    public void loadOtherDay()

    {   initCol1();
        listA.clear();
        DataBaseHandler handler=DataBaseHandler.getInstance();
        LocalDate dateR = autreJour.getValue();
        if (dateR.toString().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Choisissez la date");
            alert.showAndWait();
            return;
        }
        String qu = "SELECT * FROM rdv where date='"+dateR+"'";
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                int id=rs.getInt("id");
                int idPatient  = rs.getInt("idPatient");
                LocalDate date = rs.getDate("date").toLocalDate();
                LocalTime heure = rs.getTime("heure").toLocalTime();
                String objet = rs.getString("objet");
                String nomEtPrenomP=rs.getString("nomEtPrenomP");

                listA.add(new Rdv(id,idPatient, nomEtPrenomP, date,heure,objet));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PatientAddController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tableRdvA.setItems(listA);

    }
}