/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BE.Playlist;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Casper & Jens
 */
public class PlaylistPersistentsManager
{

    private static final int NAME_SIZE = 30;
    private static final int SONGS_SIZE = 4;
    private static final int TIME_SIZE = 30;
    private static final int RECORD_SIZE = NAME_SIZE + SONGS_SIZE + TIME_SIZE;

    private final String fileName;
    private final String indexFileName;
    private Map<Integer, Long> playlistIndex;
    private int nextPlaylistId;

    /**
     * Puts a string into the variables fileName and indexFileName using the
     * fileName from a PlaylisyManager object It then uses the
     * readPlaylistAdminData methord
     *
     *
     * @param fileName
     */
    public PlaylistPersistentsManager(String fileName)
    {
        this.fileName = fileName;
        this.indexFileName = fileName.substring(0, fileName.indexOf(".")) + ".idx";
        readPlaylistAdminData();
    }

    /**
     * Makes a Playlist object, giving it an id, which is then written at the
     * very end of the .dat file
     *
     * @param aPlaylist
     * @return Playlist object
     * @throws IOException
     */
    public Playlist addPlaylist(Playlist aPlaylist) throws IOException
    {
        Playlist p = new Playlist(nextPlaylistId++, aPlaylist);
        try (RandomAccessFile raf = new RandomAccessFile(fileName, "rw"))
        {
            long pos = raf.length();
            raf.seek(pos);
            writeOnePlaylist(raf, p);
        }
        return p;
    }

    /**
     * Creats an ObjectInputStream object, using BufferedInputStream, which in
     * turn uses FileInputStream, with the .dat file which is then read from
     * Creates an playlistIndex HashMap if there isn't already one, puts
     * nextPlaylistId to 1, if there is not already one
     */
    private void readPlaylistAdminData()
    {
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(indexFileName))))
        {
            playlistIndex = (Map<Integer, Long>) ois.readObject();
            nextPlaylistId = (Integer) ois.readObject();
        }
        catch (IOException | ClassNotFoundException ex)
        {
            playlistIndex = new HashMap<>();
            nextPlaylistId = 1;
        }
    }

    /**
     * First pos is defined, by where the pointer is. Then the different
     * variables from the Playlist object parameter p is written, and the id is
     * put into the index in pos
     *
     * @param raf
     * @param p
     * @throws IOException
     */
    private void writeOnePlaylist(RandomAccessFile raf, Playlist p) throws IOException
    {
        long pos = raf.getFilePointer();
        raf.writeInt(p.getId());
        raf.writeBytes(getFixedSizeString(p.getName(), NAME_SIZE));
        raf.writeBytes(getFixedSizeString(p.getTime(), TIME_SIZE));
        playlistIndex.put(p.getId(), pos);
    }

    /**
     * It starts by making formatSting, which formats strings, where it uses the
     * parameter size, so formatString will become something like "%30s", which
     * is then used to retrun the string parameter which can only contain the
     * number of charecters that size allows. This ensures that all strings are
     * the same, so objects inside the .dat file will always be placed the same
     * way
     *
     * @param s
     * @param size
     * @return the formated string
     */
    private String getFixedSizeString(String s, int size)
    {
        String formatString = String.format("%%-%ds", size);
        return String.format(formatString, s).substring(0, size);
    }

    /**
     * It updates the .dat file, inputting a new Playlist object over the old
     * one it. This happens by using the PlayList objects id, and finding it's
     * position in the .dat file, where the method getPlaylistPos is used, which
     * uses the id to find the number of the class Long, which describes the
     * position of the Playlist object, it then sets the file pointer at the end
     * of the position, and writes the new PlayList object from the parameter
     * there.
     *
     * @param p
     * @throws IOException
     */
    public void updatePlaylist(Playlist p) throws IOException
    {
        try (RandomAccessFile raf = new RandomAccessFile(fileName, "rw"))
        {
            long pos = getPlaylistPos(p.getId());
            if (pos == -1)
            {
                throw new IOException("Playlist not found. Id: " + p.getId());
            }

            raf.seek(pos);
            writeOnePlaylist(raf, p);
        }
    }

    /**
     * Returns the position of the Playlist object with the musicId parameter as
     * its id, if there is no PlayList object with that id it returns -1
     *
     * @param playlistId
     * @return position of the Playlist object in the .dat file
     */
    private long getPlaylistPos(int playlistId)
    {
        if (playlistIndex.containsKey(playlistId))
        {
            return playlistIndex.get(playlistId);
        }
        else
        {
            return -1;
        }
    }

    /**
     * Writes the playlistIndex and the nextPlaylistId into the .idx file
     *
     * @throws IOException
     */
    private void savePlaylistAdminData() throws IOException
    {
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(indexFileName))))
        {
            oos.writeObject(playlistIndex);
            oos.writeObject(nextPlaylistId);
        }
    }

    /**
     * Used when the program closes, using the saveMusicAdminData to save the
     * musicIndex and the nextMusicId into the .idx file
     *
     * @throws IOException
     */
    public void close() throws IOException
    {
        savePlaylistAdminData();
    }

    /**
     * Uses the playlistIndex to put a Playlist object into a Playlist List,
     * using a for-each loop, which states that for each value in playlistIndex,
     * it will put the pointer at the end of the .dat file. The method
     * readOnePlaylist is then used to make a Playlist object that is then added
     * to the list of Playlist objects. When there is no more values in
     * playlistIndex, it then returns the list
     *
     * @return list of Playlist
     * @throws IOException
     */
    public List<Playlist> getAll() throws IOException
    {
        try (RandomAccessFile raf = new RandomAccessFile(fileName, "rw"))
        {
            List<Playlist> playlist = new ArrayList<>();
            for (long pos : playlistIndex.values())
            {
                raf.seek(pos);
                playlist.add(readOnePlaylist(raf));
            }
            return playlist;
        }
    }

    /**
     * It makes a local viable, buffer, which is the used to read the different
     * parts of the saved Playlist object in the .dat file, which is then put
     * into different local viables, which then is used to create and return a
     * new Music object
     *
     * @param raf
     * @return Playlist object
     * @throws IOException
     */
    private Playlist readOnePlaylist(RandomAccessFile raf) throws IOException
    {
        byte[] buffer = new byte[NAME_SIZE];

        int id = raf.readInt();

        raf.read(buffer, 0, NAME_SIZE);
        String name = new String(buffer, 0, NAME_SIZE).trim();

        return new Playlist(id, name);
    }

    /**
     * It finds the position of the Playlist object which matches the inputted
     * id, if none is found it throws an exception. It the goes to the end of
     * raf, goes back by the length of the written object and copies it, the
     * pointer is then moved to the file to be deleted, the copied PlayList
     * object then overwrites the deleted Playlist object, it then deletes the
     * part that was copied from the .dat file so there won't be 2 identical
     * Playlist objects. The deleted object is then deleted from the
     * playlistIndex
     *
     * @param playlistId
     * @throws IOException
     */
    public void removePlaylist(int playlistId) throws IOException
    {
        try (RandomAccessFile raf = new RandomAccessFile(fileName, "rw"))
        {
            long pos = getPlaylistPos(playlistId);
            if (pos == -1)
            {
                throw new IOException("No playlist with id: " + playlistId);
            }

            raf.seek(raf.length() - RECORD_SIZE);
            Playlist p = readOnePlaylist(raf);

            raf.seek(pos);
            writeOnePlaylist(raf, p);
            raf.setLength(raf.length() - RECORD_SIZE);

            playlistIndex.remove(playlistId);
        }
    }
}
