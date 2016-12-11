/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import BE.Playlist;
import BE.Music;

/**
 * This class manages everything regarding the playlists; Creating a new
 * playlist, editing an existing playlist, adding and removing elements to/from
 * the playlist.
 *
 * @author Casper & Jens
 */
public class PlaylistManager 
{

    /**
     * Adds a specified song to a specified playlist.
     *
     * @param playlist A playlist to add the song to.
     * @param m
     */
    public void addSong(Playlist playlist, Music m)
    {
        playlist.getSongList().add(m);
    }

    /**
     * Removes a specified song from a specified playlist.
     *
     * @param playlist A playlist to remove the song from.
     * @param m
     */
    public void removeSong(Playlist playlist, Music m)
    {
        // TODO: Add exception handling.
        playlist.getSongList().remove(m);
    }
}
