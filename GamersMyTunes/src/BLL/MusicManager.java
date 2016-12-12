/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import BE.Music;
import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author Casper & Jens
 */
public class MusicManager
{
    private static MusicManager instance = null;
    private Music currentSong;
    private MediaPlayer player;

    /**
     * If there is no instance, then instance becomes a new MusicManager object, whitch is then returned
     * @return instance
     */
    public static MusicManager getInstance()
    {
        if (instance == null)
        {
            instance = new MusicManager();
        }
        return instance;
        
    }
    
    /**
     * Plays the specified song if any is found.
     *
     * @param m
     * @param overwrite
     */
    public void playSong(Music m, boolean overwrite)
    {
        if (currentSong == null || overwrite)
        {
            currentSong = m;
            File soundFile = new File(currentSong.getFile());
            Media media = new Media(soundFile.toURI().toString());
            player = new MediaPlayer(media);
        }
        player.play();

    }
    
    /**
     * Pauses the currently playing song.
     */
    public void pauseSong()
    {
        if (currentSong != null)
        {
            player.pause();
        }
    }
    
    public MediaPlayer getMediaPlayer()
    {
        return player;
    }
    
    /**
     * Gets the currently playing song.
     *
     * @return Returns a song object representing the song currently playing.
     */
    public Music getCurrentlyPlayingSong()
    {
        return this.currentSong;
    }
    
    public void stopSong()
    {
        if (currentSong != null)
        {
            player.stop();
        }
    }

}
