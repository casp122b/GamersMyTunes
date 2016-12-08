/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author Casper & Jens
 */
public class PlayerUIController implements Initializable {

    @FXML
    private Button btnClose;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    /**
     * Clears all ratings.
     *
     * @param event
     */
    @FXML
    public void btnPlaylistUINew(ActionEvent event) throws Exception 
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/PlaylistUI.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void btnSongUINew(ActionEvent event) throws Exception
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/SongUI.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void btnCloseActionPerformed(ActionEvent event) 
    {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
}
