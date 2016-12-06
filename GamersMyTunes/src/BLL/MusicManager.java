/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import BE.Music;
import DAL.MusicPersistentManager;
import java.io.IOException;
import java.util.List;
import javax.management.openmbean.KeyAlreadyExistsException;

/**
 *
 * @author Casper & Jens
 */
public class MusicManager
{

    private static final String FILE_NAME = "music.dat";
    private static MusicManager instance = null;

    /**
     * When a MusicManager is made, a MusicPersistentManager, with the filename as a parameter, is also made
     */
    private MusicManager()
    {
        mpm = new MusicPersistentManager(FILE_NAME);
    }

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
 * Uses the methord in MusicPersistentManager of the same name, if it fails it sends an error message
 * It gets an id, which then returns a Music object with that id
 * @param musicId
 * @return the MusicPersistentManagers getById
 */
    public Music getById(int musicId)
    {
        try
        {
            return mpm.getById(musicId);
        }
        catch (IOException ex)
        {
            throw new MusicAdminException("Unable to fetch music with id: " + musicId);
        }
    }

    /**
     * Uses the methord in MusicPersistentManager of the same name, if it fails it sends an error message
     * It updates the .dat file, inputting a new music object into it
     * @param updateMusic 
     */
    public void updateMusic(Music updateMusic)
    {
        try
        {
            mpm.updateMusic(updateMusic);
        }
        catch (IOException ex)
        {
            throw new PlaylistAdminException("Unable to update playlist with id: " + updateMusic.getId());
        }
    }

    private final MusicPersistentManager mpm;

    /**
     * Uses the methord in MusicPersistentManager of the same name, if it fails it sends an error message
     * It gets all of the objects in the .dat file
     * @return the list of Music objects
     */
    public List<Music> getAll()
    {
        try
        {
            return mpm.getAll();
        }
        catch (IOException ex)
        {
            throw new PlaylistAdminException("Unable to fetch music");
        }
    }

    /**
     *  Uses the methord in MusicPersistentManager of the same name, if it fails it sends an error message
     * It adds the Music object to the .dat file and to an index 
     * @param m
     * @return the Music object with the next id 
     */
    public Music addMusic(Music m)
    {
        try
        {
            return mpm.addMusic(m);
        }
        catch (IOException ex)
        {
            throw new MusicAdminException("Unable to add music with id: " + m.getId());
        }
        catch (KeyAlreadyExistsException ex)
        {
            throw new MusicAdminException(ex.getMessage());
        }
    }

    /**
     * Uses the methord in MusicPersistentManager of the same name, if it fails it sends an error message
     * Removes a Music object with a matching id from the .dat file and from the index
     * @param musicId 
     */
    public void removeMusic(int musicId)
    {
        try
        {
            mpm.removeMusic(musicId);
        }
        catch (IOException ex)
        {
            throw new MusicAdminException("Unable to remove music with id: " + musicId);
        }
    }

    /**
     * Uses the methord in MusicPersistentManager of the same name, if it fails it sends an error message
     * Writes/Saves to the external files 
     */
    public void close()
    {
        try
        {
            mpm.close();
        }
        catch (IOException ex)
        {
            throw new PlaylistAdminException("Unable to close ressource");
        }
    }
}
