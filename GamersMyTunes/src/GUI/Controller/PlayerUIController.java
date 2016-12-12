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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Casper & Jens
 */
public class PlayerUIController implements Initializable {

    @FXML
    public Button btnClose;
    @FXML
    public AnchorPane root;
    @FXML
    public Button btnPlay;
    @FXML
    public Button btnStop;
    @FXML
    public Button btnPlaylistNew;
    @FXML
    public Button btnTblSongsNew;
    @FXML
    public Button btnTblSongsDelete;
    @FXML
    public TableView<Music> tblSOP;
    @FXML
    public TableView<Playlist> tblPlaylists;
    @FXML
    public TableView<Music> tblSongs;
    @FXML
    public Label lblSOP;
    @FXML
    public Label lblPlaylists;
    @FXML
    public Label lblSongs;
    @FXML
    public TextField txtFilter;
    @FXML
    public Slider sliderVolume;
    @FXML
    public Label lblNP;
    @FXML
    public TableColumn<Playlist, String> playlistsColName;
    @FXML
    public TableColumn<Playlist, Integer> playlistsColSongs;
    @FXML
    private TableColumn<Music, String> songsColTitle;
    @FXML
    private TableColumn<Music, String> songsColArtist;
    @FXML
    private TableColumn<Music, String> songsColCategory;
    @FXML
    private TableColumn<Music, String> songsColTime;
    @FXML
    public TableColumn<Music, String> SOPColTitle;
    @FXML
    public TableColumn<Music, String> SOPColArtist;
    @FXML
    private Button btnPlaylistDelete;
    @FXML
    private Button btnAddToPlaylist;
    @FXML
    private Label lblNowPlaying;
    
    private Music selectedSong;
    private Playlist selectedPlaylist;
    private boolean isPlaying;
    private MusicManager musicManager;
    private final ObservableList<Music> songsLibrary;
    private ObservableList<Music> currentSongsInView;
    private ObservableList<Playlist> playlists;
    private PlaylistManager playlistManager;
    private SongModel songModel;
    private PlaylistModel playlistModel;

    /**
     * The default contructor for this class.
     */
    public PlayerUIController() {
        this.songsLibrary = FXCollections.observableArrayList();
        this.currentSongsInView = FXCollections.observableArrayList();
        this.playlists = FXCollections.observableArrayList();
    }

    /**
     * This method initializes all its content, when PlayerUIController is
     * launched.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        songsColTitle.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getTitle()));
        songsColArtist.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getArtist()));
        songsColCategory.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getGenre()));
        songsColTime.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getTime()));
        playlistsColName.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getName()));
        playlistsColSongs.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getSongs()));
        SOPColTitle.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getTitle()));
        SOPColArtist.setCellValueFactory(value -> new SimpleObjectProperty<>(value.getValue().getArtist()));

        musicManager = new MusicManager();
        playlistManager = new PlaylistManager();
        songModel = SongModel.getInstance();
        playlistModel = PlaylistModel.getInstance();
        isPlaying = false;
        tblSongs.setItems(songModel.getSongs());
        tblPlaylists.setItems(playlistModel.getPlaylists());
        tblSOP.setItems(playlistModel.getSongsOnPlaylist());
        initialLoad();
        setPlaylists();
        currentSongsInView = songsLibrary;
        searchOnUpdate();
    }

    /**
     * @param event
     */
    private void initialLoad() {
        try {
            songModel.loadSongData();
            playlistModel.loadPlaylistData();
        } catch (Exception e) {
            Logger.getLogger(PlayerUIController.class.getName()).log(Level.SEVERE, null, e);
        }
        tblSongs.setItems(songModel.getSongs());
    }

    @FXML
    public void btnPlaylistUINew(ActionEvent event) throws Exception {
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

    @FXML
    public void handleOnMousePressed(MouseEvent event) {
        selectedSong = tblSongs.selectionModelProperty().getValue().getSelectedItem();
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
            if (selectedSong != null) {
                musicManager.pauseSong();
                musicManager.playSong(selectedSong, true);
                processMediaInfo();
            }
        }
    }

    @FXML
    public void btnCloseActionPerformed(ActionEvent event) {
        songModel.saveSongData();
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void btnPlayActionPerformed(ActionEvent event) {
        // Making sure the song is never null before trying to play a song.
        if (selectedSong == null) {
            tblSongs.selectionModelProperty().get().select(0);
        }

        selectedSong = tblSongs.selectionModelProperty().getValue().getSelectedItem();
        //Play button pressed
        if (!isPlaying) {
            musicManager.playSong(selectedSong, false);
        } else {
            musicManager.pauseSong();
        }

        processMediaInfo();
    }

    /**
     * Deletes the selected song from tblSongs.
     */
    @FXML
    public void btnTblSongsDeleteActionPerformed() {
        deleteSong();

    }

    /**
     * Deletes the selected playlist from tblPlaylists.
     */
    @FXML
    public void btnPlaylistDeleteActionPerformed() {

        deletePlaylist();

    }

    @FXML
    public void btnAddToPlaylistActionPerformed(ActionEvent event) {
        Music mIndex = tblSongs.selectionModelProperty().getValue().getSelectedItem();
        Playlist pIndex = tblPlaylists.selectionModelProperty().getValue().getSelectedItem();
        if (mIndex != null && pIndex != null) {
            pIndex.addMusic(mIndex);

            playlistModel.updatePlaylistView(pIndex.getMusiclist());
        }
    }

    @FXML
    public void tblChangePlaylistActionPerformed(MouseEvent event) {
        Playlist pIndex = tblPlaylists.selectionModelProperty().getValue().getSelectedItem();
        playlistModel.updatePlaylistView(pIndex.getMusiclist());

    }

    @FXML
    public void btnStopActionPerformed(ActionEvent event) {
        musicManager.stopSong();
    }

    @FXML
    public void handleOnVolumeChanged() {
        musicManager.getMediaPlayer().setVolume(sliderVolume.getValue() / 100);
    }

    public void addToSongs(Music m) {
        tblSongs.getItems().add(m);
    }

    public void addToPlaylist(Playlist pl) {
        tblPlaylists.getItems().add(pl);

    }

    public void btnTblSongsNewActionPerformed(ActionEvent event) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/SongUI.fxml"));
            Parent root = loader.load();
            SongUIController controller = loader.getController();
            controller.setMainWindow(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * If there is a song currently playing, it will be stopped, and the
     * selected song is deleted from tblSongs.
     */
    private void deleteSong() {
        for (Music m : tblSongs.getItems()) {
            if (m.getTitle() == selectedSong.getTitle()) {
                tblSongs.getItems().remove(selectedSong);
                musicManager.stopSong();
            }
        }
    }

    private void deletePlaylist() 
    {
        for (Playlist pl : tblPlaylists.getItems()) {
            if (pl.getName() == selectedPlaylist.getName()) {
                tblPlaylists.getItems().remove(selectedPlaylist);
            }
        }
    }
    
    public void setSongs() {
        songsLibrary.addAll(songModel.getSongs());
    }

    public void setPlaylists() {
        playlists = playlistModel.getPlaylists();
    }

    private void searchOnUpdate() {
        txtFilter.textProperty().addListener((listener, oldVal, newVal)
                -> {
            ObservableList<Music> searchedSongs = FXCollections.observableArrayList();
            searchedSongs.clear();
            ObservableList<Music> allSongsInCurrentView = currentSongsInView;
            if (selectedPlaylist == null) {
                allSongsInCurrentView.setAll(songModel.getSongs());
            } else {
                allSongsInCurrentView.setAll(selectedPlaylist.getSongList());
            }

            for (Music m : allSongsInCurrentView) {
                if (m.getTitle().trim().toLowerCase().contains(newVal.trim().toLowerCase())
                        || m.getArtist().trim().toLowerCase().contains(newVal.trim().toLowerCase())
                        || m.getGenre().trim().toLowerCase().contains(newVal.trim().toLowerCase())
                        || String.valueOf(m.getRating()).trim().toLowerCase().contains(newVal.trim().toLowerCase())
                        && !searchedSongs.contains(m)) {
                    searchedSongs.add(m);
                }
            }

            tblSongs.setItems(searchedSongs);
        });
    }

    private void processMediaInfo() {
        try {
            musicManager.getMediaPlayer().currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> listener, Duration oldVal, Duration newVal) {
                    ;
                }
            });
            if (!isPlaying) {
                lblNP.setText(musicManager.getCurrentlyPlayingSong().getTitle());
            } else {
                lblNP.setText(musicManager.getCurrentlyPlayingSong().getTitle());
            }
        } finally {

        }
    }
}
