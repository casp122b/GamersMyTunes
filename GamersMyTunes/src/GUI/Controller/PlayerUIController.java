/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controller;

import BE.Music;
import BE.Playlist;
import BLL.MusicManager;
import BLL.PlaylistManager;
import GUI.Model.PlaylistModel;
import GUI.Model.SongModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.util.Duration;

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
    private TableView<Music> tblSOP;
    @FXML
    private TableView<Playlist> tblPlaylists;
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
    private TableColumn<Playlist, String> playlistsColName;
    @FXML
    private TableColumn<Playlist, Integer> playlistsColSongs;
    @FXML
    private TableColumn<Music, String> songsColTitle;
    @FXML
    private TableColumn<Music, String> songsColArtist;
    @FXML
    private TableColumn<Music, String> songsColCategory;
    @FXML
    private TableColumn<Music, String> songsColTime;
    @FXML
    private TableColumn<Music, String> SOPColTitle;
    @FXML
    private TableColumn<Music, String> SOPColArtist;
    
    private Music selectedSong;
    private boolean isPlaying;
    private boolean isMuted;
    private MusicManager musicManager;
    private final ObservableList<Music> songsLibrary;
    private final ObservableList<Music> currentSongsInView;
    private final ObservableList<Playlist> playlists;
    private PlaylistManager playlistManager;
    private SongModel songModel;
    private PlaylistModel playlistModel;
    
    /**
     * The default contructor for this class.
     */
    public PlayerUIController()
    {
        this.songsLibrary = FXCollections.observableArrayList();
        this.currentSongsInView = FXCollections.observableArrayList();
        this.playlists = FXCollections.observableArrayList();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
            tblSongs.setItems(FXCollections.observableArrayList());
            songsColTitle.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getTitle()));
            songsColArtist.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getArtist()));
            songsColCategory.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getGenre()));
            songsColTime.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getTime()));
            
            tblPlaylists.setItems(FXCollections.observableArrayList());
            playlistsColName.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getName()));
            playlistsColSongs.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getSongs()));
            isPlaying = false;  
            
            musicManager = new MusicManager();
            playlistManager = new PlaylistManager();
            songModel = SongModel.getInstance();
            playlistModel = PlaylistModel.getInstance();
    }
    /**
     * @param event
     */
    
    @FXML
    public void btnPlaylistUINew(ActionEvent event) throws Exception 
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/PlaylistUI.fxml"));
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
    
    public void addToSongs(Music m)
    {
        tblSongs.getItems().add(m);
    }
    
    public void addToPlaylist(Playlist pl)
    {
        tblPlaylists.getItems().add(pl);
        
    }
    
    @FXML
    public void btnSongUINew(ActionEvent event) throws Exception
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/SongUI.fxml"));
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
    public void btnCloseActionPerformed(ActionEvent event) 
    {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    public void btnPlayActionPerformed(ActionEvent event)
    {
        // Making sure the song is never null before trying to play a song.
        if (selectedSong == null)
        {
            tblSongs.selectionModelProperty().get().select(0);
        }
        
        selectedSong = tblSongs.selectionModelProperty().getValue().getSelectedItem();
        //Play button pressed
        if (!isPlaying)
        {
            musicManager.playSong(selectedSong, false);
        }
        else
        {
            musicManager.pauseSong();
        }

        processMediaInfo();
    }
    
    private void processMediaInfo()
    {
        try
        {
            musicManager.getMediaPlayer().currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> listener, Duration oldVal, Duration newVal) {
                    ;
                }
            });
            if (!isPlaying)
            {
                lblNP.setText(musicManager.getCurrentlyPlayingSong().getTitle());
            }
            else
            {
                lblNP.setText(musicManager.getCurrentlyPlayingSong().getTitle());
            }
        }
        finally
        {

        }
    }
}
