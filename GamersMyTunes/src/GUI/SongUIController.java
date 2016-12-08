/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BE.Music;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author Casper & Jens
 */
public class SongUIController implements Initializable {

    private AnchorPane root;
    private Music music;
    private FileChooser fc;
    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtArtist;
    @FXML
    private TextField txtGenre;
    @FXML
    private TextField txtDuration;
    @FXML
    private TextField txtPath;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnBrowse;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //To be able to choose a file
        fc = new FileChooser();
        fc.setInitialDirectory(new java.io.File("."));
        fc.setTitle("Add music");
    }

    @FXML
    public void btnBrowseActionPerformed(ActionEvent event) {
        //To open a file using FileChooser first we create a new FileChooser 
        FileChooser fileChooser = new FileChooser();
    //TO set a title to the fileChooser 
        fileChooser.setTitle("Choose a File");
    //TO show the popup window for opening file.
        fileChooser.showOpenDialog(btnBrowse.getScene().getWindow());
    }

    @FXML
    private void btnCancelActionPerformed(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
