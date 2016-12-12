/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BE;

import java.io.Serializable;

/**
 *
 * @author Casper & Jens
 */
public class Music implements Serializable
{

    private String title;
    private String category;
    private String artist;
    private String time;
    private String file;
    private int id;
    private int rating;

    /**
     * Constructor for creating a new Music object, where it takes its private variables and sets them to the parameters
     * @param id
     * @param title
     * @param artist
     * @param category
     * @param time
     * @param file 
     */
    public Music(int id, String title, String artist, String category, String time, String file)
    {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.category = category;
        this.time = time;
        this.file = file;
        this.rating = rating;
    }

    /**
     * If there wasn't given an id, the id is set to -1
     * @param title
     * @param artist
     * @param category
     * @param time
     * @param file 
     */
    public Music(String title, String artist, String category, String time, String file)
    {

        this(-1, title, artist, category, time, file);
    }

    /**
     * Creates an object with an id and uses an already existing Music object variables
     * @param id
     * @param m 
     */
    public Music(int id, Music m)
    {
        this(id, m.getTitle(), m.getArtist(), m.getGenre(), m.getTime(), m.getFile());
    }

    /**
     * @return the name
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * @return the genre
     */
    public String getGenre()
    {
        return category;
    }

    /**
     * @return the artist
     */
    public String getArtist()
    {
        return artist;
    }

    /**
     * @return the time
     */
    public String getTime()
    {
        return time;
    }

    /**
     * @return the file
     */
    public String getFile()
    {
        return file;
    }

    /**
     * @return the id
     */
    public int getId()
    {
        return id;
    }

    /**
     * Checks wheter the object in the parameter is an Music Object, if not, it returns false, 
     * else it returns wheter or not the Music object and the parameter object has the same id
     * @param o
     * @return true or false based on wheter or the two ids are identical
     */
    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof Music))
        {
            return false;
        }
        Music other = (Music) o;
        return (this.id == other.id);
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    /**
     * @param id
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * @param genre the genre to set
     */
    public void setGenre(String genre)
    {
        this.category = genre;
    }

    /**
     * @param artist the artist to set
     */
    public void setArtist(String artist)
    {
        this.artist = artist;
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time)
    {
        this.time = time;
    }

    /**
     * @param file the file to set
     */
    public void setFile(String file)
    {
        this.file = file;
    }
    
    /**
     * Returns the entire object
     * @return 
     */
    public Music getMusic()
    {
        return this;
    }
    
    public int getRating()
    {
        return rating;
    }
    
    public void setRating(int rating)
    {
        this.rating = rating;
    }

}
