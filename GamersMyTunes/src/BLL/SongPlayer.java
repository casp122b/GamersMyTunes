/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import DAL.SongOpener;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javazoom.jlgui.basicplayer.BasicPlayerException;

/**
 *
 * @author Casper & Jens
 */
public class SongPlayer
{
    private final SongOpener so;
    private final String filePath;
    
    /**
     * 
     * @param filePath
     * @throws FileNotFoundException
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws BasicPlayerException 
     */
    public SongPlayer(String filePath) throws FileNotFoundException, UnsupportedAudioFileException, IOException, BasicPlayerException
    {
        so = new SongOpener(filePath);
        so.openFromFile();
        this.filePath = filePath;
    }
    
    /**
     * 
     * @return duration in seconds
     */
    public long getDurationInSeconds()
    {
        return so.getDurationInSeconds();
    }
    
    /**
     * 
     * @return duration as string
     */
    public String getDurationToString()
    {
        return so.getDurationToString();
    }
    
    /**
     * 
     * @return title
     */
    public String getTitle()
    {
        return so.getTitle();
    }
    
    /**
     * 
     * @return  author
     */
    public String getAuthor()
    {
        return so.getAuthor();
    }
    
    /**
     * Makes BasicPlayer play
     * @throws BasicPlayerException 
     */
    public void play() throws BasicPlayerException
    {
        so.play();
    }
    
    /**
     * Makes BasicPlayer pause
     * @throws BasicPlayerException 
     */
    public void pause() throws BasicPlayerException
    {
        so.pause();
    }
    
    /**
     * Makes BasicPlayer resume
     * @throws BasicPlayerException 
     */
    public void resume() throws BasicPlayerException
    {
        so.resume();
    }
    
    /**
     * Makes BasicPlayer stop
     * @throws BasicPlayerException 
     */
    public void stop() throws BasicPlayerException
    {
        so.stop();
    }
    
}
