/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Model;

import BE.Music;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import BE.Playlist;
import DAL.PlaylistDAO;
import java.util.List;

/**
 *
 * @author James
 */
public class PlaylistModel {

    private final PlaylistDAO pDAO;
    private static PlaylistModel instance;

    final ObservableList<Playlist> playlists = FXCollections.observableArrayList();
    final ObservableList<Music> songsOnPlaylist = FXCollections.observableArrayList();

    public static PlaylistModel getInstance()
    {
        if (instance == null)
        {
            instance = new PlaylistModel();
        }
        return instance;
    }

    private PlaylistModel()
    {
        pDAO = new PlaylistDAO();
    }

    public void addPlaylist(Playlist playlist)
    {
        playlists.add(playlist);
    }

    public ObservableList<Playlist> getPlaylists()
    {
        return playlists;
    }

//    public void setPlaylistNames()
//    {
//        playlistTitles.clear();
//        for (Playlist playlist : playlists)
//        {
//            playlistTitles.add(playlist.getName());
//        }
//
//    }

    public ObservableList<Music> getSongsOnPlaylist()
    {
        return songsOnPlaylist;
    }

    public void updatePlaylistView(List<Music> songsOnPlaylist)
    {
        this.songsOnPlaylist.clear();
        this.songsOnPlaylist.addAll(songsOnPlaylist);
    }

    public void loadPlaylistData() throws FileNotFoundException
    {
        playlists.clear();
        playlists.addAll(pDAO.readObjectData("PlaylistTest.dat"));
    }

    public void savePlaylistData()
    {
        try
        {
            ArrayList<Playlist> playlistToSave = new ArrayList<>();
            for (Playlist playlist : playlists)
            {
                playlistToSave.add(playlist);

            }
            pDAO.writeObjectData(playlistToSave, "PlaylistTest.dat");
        }
        catch (IOException ex)
        {
            // TODO: exception handling.
        }
    }

}
