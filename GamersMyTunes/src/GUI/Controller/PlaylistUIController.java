/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controller;

import BE.Playlist;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author Casper & Jens
 */
public class PlaylistUIController implements Initializable {
    
    @FXML
    private Button btnCancel;
    @FXML TextField txtPlaylistName;
    private PlayerUIController main;
    private Playlist playlist;
    @FXML
    private AnchorPane root;
    @FXML
    private Label lblName;
    @FXML
    private Button btnSave;
    @FXML
    private TextField txtName;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    /**
     * makes the close button works
     * @param event 
     */
    @FXML
    public void btnCancelActionPerformed(ActionEvent event) 
    {
       
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
    /**
     * sets main window
     * @param aThis 
     */
    void setMainWindow(PlayerUIController aThis) {
        main = aThis;
    }
    /**
     * makes the save button in playlist work
     * @param event 
     */
    @FXML
    public void btnSaveActionPerformed(ActionEvent event)
    {
        String name = txtPlaylistName.getText().trim();
        if (!name.equals(""))
        {
            playlist = new Playlist(name);
            main.addToPlaylist(playlist);
            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.close();
        }
        else
        {
            new Alert(Alert.AlertType.ERROR, "You must input a name for the playlist!").showAndWait();
        }
    }
    /**
     * return playlist
     * @return 
     */
    public Playlist getPlaylist()
    {
        return playlist;
    }
}

