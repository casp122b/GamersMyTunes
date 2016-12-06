/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import BE.Playlist;
import DAL.PlaylistPersistentsManager;
import java.io.IOException;
import java.util.List;
import javax.management.openmbean.KeyAlreadyExistsException;

/**
 *
 * @author Casper & Jens
 */
public class PlaylistManager
{

    private static final String FILE_NAME = "playlist.dat";
    private static PlaylistManager instance = null;
    private final PlaylistPersistentsManager ppm;

    /**
     * When a MusicManager is made, a MusicPersistentManager, with the filename as a parameter, is also made
     */
    private PlaylistManager()
    {
        ppm = new PlaylistPersistentsManager(FILE_NAME);
    }

    /**
     * If there is no instance, then instance becomes a new PlaylistManager object, whitch is then returned
     * @return instance
     */
    public static PlaylistManager getInstance()
    {
        if (instance == null)
        {
            instance = new PlaylistManager();
        }
        return instance;
    }

    /**
     * Uses the methord in PlaylistPersistentManager of the same name, if it fails it sends an error message
     * It updates the .dat file, inputting a new PlayList object into it
     * @param updatePlaylist 
     */
    public void updatePlaylist(Playlist updatePlaylist)
    {
        try
        {
            ppm.updatePlaylist(updatePlaylist);
        }
        catch (IOException ex)
        {
            throw new PlaylistAdminException("Unable to update playlist with id: " + updatePlaylist.getId());
        }
    }

    /**
     * Uses the methord in PlaylistPersistentManager of the same name, if it fails it sends an error message
     * It adds the PlayList object to the .dat file and to an index 
     * @param aPlaylist
     * @return the PlayList object with the next id 
     */
    public Playlist addPlaylist(Playlist aPlaylist)
    {
        try
        {
            return ppm.addPlaylist(aPlaylist);
        }
        catch (IOException ex)
        {
            throw new PlaylistAdminException("Unable to add Playlist with id: " + aPlaylist.getId());
        }
        catch (KeyAlreadyExistsException ex)
        {
            throw new PlaylistAdminException(ex.getMessage());
        }
    }

    /**
     * Uses the methord in PlaylistPersistentManager of the same name, if it fails it sends an error message
     * Writes/Saves to the external files 
     */
    public void close()
    {
        try
        {
            ppm.close();
        }
        catch (IOException ex)
        {
            throw new PlaylistAdminException("Unable to close ressource");
        }
    }

    /**
     * Uses the methord in PlaylistPersistentManager of the same name, if it fails it sends an error message
     * It gets all of the PlayList objects in the .dat file
     * @return the list of PlayList objects
     */
    public List<Playlist> getAll()
    {
        try
        {
            return ppm.getAll();
        }
        catch (IOException ex)
        {
            throw new PlaylistAdminException("Unable to fetch playlist");
        }
    }

    /**
     * Uses the methord in PlaylistPersistentManager of the same name, if it fails it sends an error message
     * Removes a PlayList object with a matching id from the .dat file and from the index
     * @param playlistId 
     */
    public void removePlaylist(int playlistId)
    {
        try
        {
            ppm.removePlaylist(playlistId);
        }
        catch (IOException ex)
        {
            throw new PlaylistAdminException("Unable to remove playlist with id: " + playlistId);
        }
    }
}
