/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import java.util.ArrayList;
import java.util.List;
import BE.Music;

/**
 * Class for filtering in the table of songs.
 *
 * @author Casper & Jens
 */
public class MusicFilter {

    private final List<Music> songs;

    /**
     * Constructor for the search class, which takes in the current list of
     * songs.
     *
     * @param songs
     */
    public MusicFilter(List<Music> songs)
    {
        this.songs = songs;
    }

    /**
     * Search in the list of songs, if the song contains the searchQuery.
     *
     * @param searchQuery
     *
     * @return an ArrayList with the results of songs, which contains the
     * searchQuery.
     */
    public ArrayList<Music> getContains(String searchQuery)
    {
        ArrayList<Music> listResult = new ArrayList<>();

        for (Music m : songs)
        {
            if (m.getTitle().toLowerCase().contains(searchQuery) || m.getArtist().toLowerCase().contains(searchQuery) || m.getGenre().toLowerCase().contains(searchQuery))
            {
                listResult.add(m);
            }
        }
        return listResult;
    }

}
