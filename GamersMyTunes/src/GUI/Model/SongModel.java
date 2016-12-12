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
import java.io.File;

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
    
    /**
     * Constructer for SongModel.
     */
    private SongModel()
    {
        musicDAO = new MusicDAO();
    }
    
    //creates an arraylist of Music.
    ObservableList<Music> songs = FXCollections.observableArrayList();

    /**
     * Method for adding songs to the Music ArrayList. 
     * @param m
     */
    public void addSong(Music m)
    {
        songs.add(m);
    }
    
//    /**
//     * This method makes it possible to edit song info.
//     * @param contextSong 
//     */
//    public void editSong(Music contextSong)
//    {
//
//        for (int i = 0; i < songs.size(); i++)
//        {
//
//            Music m = songs.get(i);
//            if (m.getId() == contextSong.getId())
//            {
//
//                m.setTitle(contextSong.getTitle());
//                m.setArtist(contextSong.getArtist());
//                m.setGenre(contextSong.getGenre());
//                m.setTime(contextSong.getTime());
//                //Replace the song
//                songs.set(i, m);
//
//            }
//
//        }
//
//    }

    /**
     * Gets the Music ArrayList.
     * @return 
     */
    public ObservableList<Music> getSongs()
    {
        return songs;
    }

    public void setSongs()
    {

    }

    /**
     * Returns the value of contextSong.
     * @return 
     */
    public Music getContextSong()
    {
        return contextSong;

    }

    /**
     * Method for setting the contextSong. 
     * @param m
     */
    public void setContextSong(Music m)
    {
        this.contextSong = m;
    }

    /**
     * Clears the 
     * @throws FileNotFoundException 
     */
    public void loadSongData() throws FileNotFoundException, IOException, ClassNotFoundException
    {
        songs.clear();
        try {
            songs.addAll(musicDAO.readObjectData("SongsData.dat"));
        } catch (FileNotFoundException ex) {
            File songDataFile = new File("SongsData.dat");
            if(!songDataFile.exists())
            {
                songDataFile.createNewFile();
            }
        }
    }
    /**
     * saves the date that have been enter in SongUI
     */
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
