/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import BE.Music;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Casper & Jens
 */
public class MusicFilter
{
    

    /**
     * Will looks at a serach word (query) and filter by that word.
     * If nothing is written in query, it will return all songs in the table.
     * @param query
     * @return filtered list
     */
    public static List<Music> getFilteredList(String query)
    {
        
        List<Music> filteredList = new ArrayList<>();
        List<Music> allSongs = MusicManager.getInstance().getAll();
        if (!query.equals(""))
        {
            for (Music m : allSongs)
            {
                
                String name = m.getTitle().toLowerCase(); 
                String artist = m.getArtist().toLowerCase();
                if (name.contains(query) || artist.contains(query))
                {
                    filteredList.add(m);
                }
            }
            return filteredList;
        }
        else
        {
            return allSongs;
        }
    }

}
