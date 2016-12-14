/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Model;

import BE.Music;
import BE.Playlist;
import DAL.PlaylistDAO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Casper & Jens
 */
public class PlaylistModel {

    private final PlaylistDAO pDAO;
    private static PlaylistModel instance;

    final ObservableList<Playlist> playlists = FXCollections.observableArrayList();
    final ObservableList<Music> songsOnPlaylist = FXCollections.observableArrayList();

    public static PlaylistModel getInstance() {
        if (instance == null) {
            instance = new PlaylistModel();
        }
        return instance;
    }
    /**
     * makes pDAO = new playlistDAO
     */
    private PlaylistModel() {
        pDAO = new PlaylistDAO();
    }
    /**
     * add a playlist
     * @param playlist 
     */
    public void addPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }
    /**
     * makes a observablelist called Playlist
     * @return 
     */
    public ObservableList<Playlist> getPlaylists() {
        return playlists;
    }

    /**
     * makes a observablelist called Music
     * @return 
     */
    public ObservableList<Music> getSongsOnPlaylist() {
        return songsOnPlaylist;
    }
    /**
     * updates the playlist view
     * @param songsOnPlaylist 
     */
    public void updatePlaylistView(List<Music> songsOnPlaylist) {
        this.songsOnPlaylist.clear();
        this.songsOnPlaylist.addAll(songsOnPlaylist);
        playlists.set(0, playlists.get(0));
    }
    /**
     * loads the playlists data
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void loadPlaylistData() throws FileNotFoundException, IOException {
        playlists.clear();
        try {
            playlists.addAll(pDAO.readObjectData("PlaylistTest.dat"));
        } catch (FileNotFoundException e) {
            File playlistDataFile = new File("PlaylistTest.dat");
            if (!playlistDataFile.exists()) {
                playlistDataFile.createNewFile();
            }
        }
    }
    /**
     * saves the playlist date that have been enter
     */
    public void savePlaylistData() {
        try {
            ArrayList<Playlist> playlistToSave = new ArrayList<>();
            for (Playlist playlist : playlists) {
                playlistToSave.add(playlist);
            }
            pDAO.writeObjectData(playlistToSave, "PlaylistTest.dat");
        } catch (IOException ex) {
        }
    }

    public void removeSongFromAllPlaylists(Music selectedSong) 
    {
        for(Playlist playlist : playlists)
        {
            for(Music m : playlist.getMusiclist())
            {
                if (m.getTitle().equals(selectedSong.getTitle()))
                {
                    playlist.removeMusic(m);
                    instance.updatePlaylistView(playlist.getMusiclist());
                }
            }
        }
                
    }

}
