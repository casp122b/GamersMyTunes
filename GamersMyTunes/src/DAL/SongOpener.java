/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.UnsupportedAudioFileException;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;

/**
 *
 * @author Casper & Jens
 */
public class SongOpener
{

    private String filePath;
    private BasicPlayer bp;
    private AudioFileFormat aff;
    private File file;
    private Map properties;
    
    /**
     * @param filePath 
     */

    public SongOpener(String filePath)
    {
        this.filePath = filePath;
        bp = new BasicPlayer();
        file = new File(filePath);
    }
    
    /**
     * If there is a file, it will open it with BasicPlayer lib, get the audio file format.
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws BasicPlayerException 
     */
    public void openFromFile() throws UnsupportedAudioFileException, IOException, BasicPlayerException
    {
        if (file.exists())
        {
            bp.open(file);
            aff = new MpegAudioFileReader().getAudioFileFormat(file);
            properties = aff.properties();
        }
        else
        {
            throw new FileNotFoundException("ERROR - File not found");
        }
    }
    
    /**
     * Will get the duration of a selected song, in microseconds.
     * @return (long) duration 
     */
    public long getDuration()
    {
        if (properties.get("duration") != null)
        {
            long duration = (long) properties.get("duration");
            System.out.println(duration + "");
            return duration;
        }
        else
        {
            return 0;
        }
    }
    
    /**
     * Will convert from microseconds to seconds.
     * @return duration in seconds
     */
    public long getDurationInSeconds()
    {
        long duration = (long) properties.get("duration");
        long durationInSeconds = (duration / 1000000);
        return durationInSeconds;
    }
    
    /**
     * Will convert from seconds, to minutes and seconds.
     * @return duration as a string
     */
    public String getDurationToString()
    {
        int seconds = (int) getDurationInSeconds();
        int remainder = seconds % 60;
        int minutes = (seconds - remainder) / 60;

        if (remainder < 10)
        {
            return minutes + ":" + "0" + remainder;
        }
        else
        {
            return minutes + ":" + remainder;
        }
    }
    
    /**
     * Reads the title from the audio file.
     * @return title
     */
    public String getTitle()
    {
            String title = (String) properties.get("title");
            return title;
    }
    
    /**
     * Reads the author from the audio file
     * @return  author
     */
    public String getAuthor()
    {
            String author = (String) properties.get("author");
            return author;
    }
    
    /**
     * Makes BasicPlayer play songs.
     * @throws BasicPlayerException 
     */
    public void play() throws BasicPlayerException
    {
        bp.play();
    }
    
    /**
     * Makes BasicPlayer pause songs.
     * @throws BasicPlayerException 
     */
    public void pause() throws BasicPlayerException
    {
        bp.pause();
    }
    
    /**
     * Makes BasicPlayer resume songs.
     * @throws BasicPlayerException 
     */
    public void resume() throws BasicPlayerException
    {
        bp.resume();
    }
    
    /**
     * Makes BasicPlayer stop songs.
     * @throws BasicPlayerException 
     */
    public void stop() throws BasicPlayerException
    {
        bp.stop();
    }
    
}
