/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

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

    @FXML
    public void btnCancelActionPerformed(ActionEvent event) 
    {
       
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    void setMainWindow(PlayerUIController aThis) {
        main = aThis;
    }
    
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
    
    public Playlist getPlaylist()
    {
        return playlist;
    }
}

