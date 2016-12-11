/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BE;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Casper & Jens
 */
public class Playlist
{

    private String name;
    private int songs;
    private String time;
    private int id;
    private List<Music> musiclist;

    /**
     * Takes the parameter object and gives it a new id while keeping the name
     * @param id
     * @param p
     */
    public Playlist(int id, Playlist p)
    {
        this(id, p.getName());
    }

    /**
     *  Creates a Playlist object with an id of -1
     * @param name
     */
    public Playlist(String name)
    {
        this(-1, name);
    }

    /**
     * Creates a Playlist object and sets the variables to the ones in the parameters, and sets musiclist to be an arraylist
     * @param id
     * @param name
     */
    public Playlist(int id, String name)
    {
        this.id = id;
        this.name = name;
        musiclist = new ArrayList<>();
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return the songs
     */
    public int getSongs()
    {
        return musiclist.size();
    }

    /**
     * @return the time
     */
    public String getTime()
    {
        return time;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the id
     */
    public int getId()
    {
        return id;
    }

    /**
     * If the musiclist already contains the parameters music object, it won't do anything otherwise it will add the object to the arraylist musiclist
     * @param music 
     */
    public void addMusic(Music music)
    {
        if (!musiclist.contains(music))
        {
            try
            {
                musiclist.add(music);
            }
            catch (NullPointerException ex)
            {
                ex.printStackTrace();
                System.err.println("Didn't make id \n" + ex.getMessage() + "\n Couldn't add music file: " + music);
            }
        }
    }

    /**
     *  The music object in the parameter gets removed
     * @param music
     */
    public void removeMusic(Music music)
    {
        musiclist.remove(music);
    }

    /**
     * @return the musiclist
     */
    public List<Music> getMusiclist()
    {
        return musiclist;
    }
}

//    /**
//     * Checks wheter the object in the parameter is an PlayList object, if not, it returns false, 
//     * else it returns wheter or not the PlayList object and the parameter object has the same id
//     * @param o
//     * @return true or false based on wheter or the two ids are identical
//     */
//    @Override
//    public boolean equals(Object o)
//    {
//        if (!(o instanceof Playlist))
//        {
//            return false;
//        }
//        Playlist other = (Playlist) o;
//        return (this.id == other.id);
//    }
//}
