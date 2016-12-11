/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import BE.Music;
import DAL.MusicDAO;

/**
 *
 * @author Casper & Jens
 */
public class SongModel {

    private Music contextSong;

    private final MusicDAO musicDAO;

    private static SongModel instance;

    public static SongModel getInstance()
    {
        if (instance == null)
        {
            instance = new SongModel();
        }
        return instance;
    }

    private SongModel()
    {
        musicDAO = new MusicDAO();
    }

    ObservableList<Music> songs = FXCollections.observableArrayList();

    public void addSong(Music song)
    {
        songs.add(song);

    }

    public void editSong(Music contextSong)
    {

        for (int i = 0; i < songs.size(); i++)
        {

            Music m = songs.get(i);
            if (m.getId() == contextSong.getId())
            {

                m.setTitle(contextSong.getTitle());
                m.setArtist(contextSong.getArtist());
                m.setGenre(contextSong.getGenre());
                m.setTime(contextSong.getTime());
                //Replace the song
                songs.set(i, m);

            }

        }

    }

    public ObservableList<Music> getSongs()
    {
        return songs;
    }

    public void setSongs()
    {

    }

    public Music getContextSong()
    {
        return contextSong;

    }

    public void setContextSong(Music contextSong)
    {
        this.contextSong = contextSong;
    }

    public void loadSongData() throws FileNotFoundException
    {
        songs.clear();
        songs.addAll(musicDAO.readObjectData("SongsData.dat"));
    }

    public void saveSongData()
    {
        try
        {
            ArrayList<Music> songsToSave = new ArrayList<>();
            for (Music m : songs)
            {
                songsToSave.add(m);
            }
            musicDAO.writeObjectData(songsToSave, "SongsData.dat");
        }
        catch (IOException ex)
        {
            // TODO: exception handling.
        }
    }

}
