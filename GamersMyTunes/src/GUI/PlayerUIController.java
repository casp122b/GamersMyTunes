/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BE.Music;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Casper & Jens
 */
public class PlayerUIController implements Initializable {

    @FXML
    private Button btnClose;
    @FXML
    private AnchorPane root;
    @FXML
    private Button btnPrev;
    @FXML
    private Button btnPlay;
    @FXML
    private Button btnNext;
    @FXML
    private Button btnStop;
    @FXML
    private Button btnPlaylistNew;
    @FXML
    private Button btnPlaylistEdit;
    @FXML
    private Button btnPlaylistDel;
    @FXML
    private Button btnUp;
    @FXML
    private Button btnDown;
    @FXML
    private Button btnDel;
    @FXML
    private Button btnSongUINew;
    @FXML
    private Button btnSongUIEdit;
    @FXML
    private Button btnSongUIDel;
    @FXML
    private TableView<?> tblSOP;
    @FXML
    private TableView<?> tblPlaylists;
    @FXML
    private TableView<Music> tblSongs;
    @FXML
    private Label lblSOP;
    @FXML
    private Label lblPlaylists;
    @FXML
    private Label lblSongs;
    @FXML
    private TextField txtFilter;
    @FXML
    private Slider sliderVolume;
    @FXML
    private Label lblNP;
    @FXML
    private TableColumn<?, ?> sopColName;
    @FXML
    private TableColumn<?, ?> sopColSongs;
    @FXML
    private TableColumn<?, ?> sopColTime;
    @FXML
    private TableColumn<?, ?> playlistsColName;
    @FXML
    private TableColumn<Music, String> songsColTitle;
    @FXML
    private TableColumn<Music, String> songsColArtist;
    @FXML
    private TableColumn<Music, String> songsColCategory;
    @FXML
    private TableColumn<Music, String> songsColTime;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            tblSongs.setItems(FXCollections.observableArrayList());
            songsColTitle.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getTitle()));
            songsColArtist.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getArtist()));
            songsColCategory.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getGenre()));
            songsColTime.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getTime()));
            
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
            PlaylistUIController controller = loader.getController();
            controller.setMainWindow(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void addToPlaylist(Music m)
    {
        tblSongs.getItems().add(m);
    }
    
    @FXML
    public void btnSongUINew(ActionEvent event) throws Exception
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/SongUI.fxml"));
            Parent root = loader.load();
            SongUIController controller = loader.getController();
            controller.setMainWindow(this);
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
