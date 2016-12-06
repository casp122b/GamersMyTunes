/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BE.Music;
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
public class MusicPersistentManager
{

    private static final int ID_SIZE = 4;
    private static final int TITLE_SIZE = 30;
    private static final int ARTIST_SIZE = 30;
    private static final int CATAGORY_SIZE = 30;
    private static final int TIME_SIZE = 30;
    private static final int FILE_SIZE = 120;
    private static final int RECORD_SIZE = ID_SIZE + TITLE_SIZE + ARTIST_SIZE + CATAGORY_SIZE + TIME_SIZE + FILE_SIZE;

    private final String fileName;
    private final String indexFileName;
    private Map<Integer, Long> musicIndex;
    private int nextMusicId;

    /**
     * Puts a string into the variables fileName and indexFileName using the
     * fileName from a MusicManager object It also uses the readMusicAdminData
     * methord
     *
     *
     * @param fileName
     */
    public MusicPersistentManager(String fileName)
    {
        this.fileName = fileName;
        this.indexFileName = fileName.substring(0, fileName.indexOf(".")) + ".idx";
        readMusicAdminData();
    }

    /**
     * Creats an ObjectInputStream object, using BufferedInputStream, which in
     * turn uses FileInputStream, with the .dat file which is then read from
     * Creates an musicIndex HashMap if there isn't already one, puts
     * nextMusicId to 1, if there is not already one
     */
    private void readMusicAdminData()
    {
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(indexFileName))))
        {
            musicIndex = (Map<Integer, Long>) ois.readObject();
            nextMusicId = (Integer) ois.readObject();
        }
        catch (IOException | ClassNotFoundException ex)
        {
            musicIndex = new HashMap<>();
            nextMusicId = 1;
        }
    }

    /**
     * It updates the .dat file, inputting a new Music object into it. This
     * happens by using the Music objects id, and finding it's position in the
     * .dat file, where the method getMusicPos is used, which uses the id to
     * find the number of the class Long, which describes the position of the
     * Music object, it then sets the file pointer at the end of the position,
     * and writes the new Music object from the parameter there.
     *
     * @param m
     * @throws IOException
     */
    public void updateMusic(Music m) throws IOException
    {
        try (RandomAccessFile raf = new RandomAccessFile(fileName, "rw"))
        {
            long pos = getMusicPos(m.getId());
            if (pos == -1)
            {
                throw new IOException("Music not found. Id: " + m.getId());
            }

            raf.seek(pos);
            writeOneMusic(raf, m);
        }
    }

    /**
     * Returns the position of the Music object with the musicId parameter as
     * its id, if there is no Music object with that id it returns -1
     *
     *
     * @param musicId
     * @return position or -1
     * @throws IOException
     */
    private long getMusicPos(int musicId) throws IOException
    {
        if (musicIndex.containsKey(musicId))
        {
            return musicIndex.get(musicId);
        }
        else
        {
            return -1;
        }
    }

    /**
     * First pos is defined, by where the pointer is. Then the diffferent
     * variables from the Music object parameter m is written, and the id is put
     * into the index in pos
     *
     * @param raf
     * @param m
     * @throws IOException
     */
    private void writeOneMusic(RandomAccessFile raf, Music m) throws IOException
    {
        long pos = raf.getFilePointer();
        raf.writeInt(m.getId());

        raf.writeBytes(getFixedSizeString(m.getTitle(), TITLE_SIZE));
        raf.writeBytes(getFixedSizeString(m.getArtist(), ARTIST_SIZE));
        raf.writeBytes(getFixedSizeString(m.getGenre(), CATAGORY_SIZE));
        raf.writeBytes(getFixedSizeString(m.getTime(), TIME_SIZE));
        raf.writeBytes(getFixedSizeString(m.getFile(), FILE_SIZE));

        musicIndex.put(m.getId(), pos);
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
     * @return the formated String
     */
    private String getFixedSizeString(String s, int size)
    {
        String formatString = String.format("%%-%ds", size);
        return String.format(formatString, s).substring(0, size);
    }

    /**
     * Uses the musicIndex to put a Music object into a Music List, using a
     * for-each loop, which states that for each value in musicIndex, it will
     * put the pointer at the end of the .dat file. The method readOneMusic is
     * then used to make a Music object that is then added to the list of Music
     * objects. When there is no more values in musicIndex, it then returns the
     * list
     *
     * @return a list of Music objects
     * @throws IOException
     */
    public List<Music> getAll() throws IOException
    {
        try (RandomAccessFile raf = new RandomAccessFile(fileName, "rw"))
        {
            List<Music> music = new ArrayList<>();
            for (long pos : musicIndex.values())
            {
                raf.seek(pos);
                music.add(readOneMusic(raf));
            }
            return music;
        }
    }

    /**
     * It makes a local viable, buffer, which is the used to read the different
     * parts of the saved Music object in the .dat file, which is then put into
     * different local viables, which then is used to create and return a new
     * Music object
     *
     * @param raf
     * @return Music object
     * @throws IOException
     */
    private Music readOneMusic(RandomAccessFile raf) throws IOException
    {
        byte[] buffer = new byte[FILE_SIZE];

        int id = raf.readInt();

        raf.read(buffer, 0, TITLE_SIZE);
        String title = new String(buffer, 0, TITLE_SIZE).trim();

        raf.read(buffer, 0, ARTIST_SIZE);
        String artist = new String(buffer, 0, ARTIST_SIZE).trim();

        raf.read(buffer, 0, CATAGORY_SIZE);
        String catagory = new String(buffer, 0, CATAGORY_SIZE).trim();

        raf.read(buffer, 0, TIME_SIZE);
        String time = new String(buffer, 0, TIME_SIZE).trim();

        raf.read(buffer, 0, FILE_SIZE);
        String file = new String(buffer, 0, FILE_SIZE).trim();

        return new Music(id, title, artist, catagory, time, file);
    }

    /**
     * Makes a Music object, giving it an id, which is then written at the very
     * back of the .dat file
     *
     * @param aMusic
     * @return Music object
     * @throws IOException
     */
    public Music addMusic(Music aMusic) throws IOException
    {
        Music m = new Music(nextMusicId++, aMusic);
        try (RandomAccessFile raf = new RandomAccessFile(fileName, "rw"))
        {
            long pos = raf.length();
            raf.seek(pos);
            writeOneMusic(raf, m);
        }
        return m;
    }

    /**
     * Used when the program closes, using the saveMusicAdminData to save the
     * musicIndex and the nextMusicId into the .idx file
     *
     * @throws IOException
     */
    public void close() throws IOException
    {

        saveMusicAdminData();
    }

    /**
     * Writes the musicIndex and the nextMusicId into the .idx file
     *
     * @throws IOException
     */
    private void saveMusicAdminData() throws IOException
    {
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(indexFileName))))
        {
            oos.writeObject(musicIndex);
            oos.writeObject(nextMusicId);
        }
    }

    /**
     * Uses the id to find the position of the Music object in the .dat file, it
     * then sets the pointer at the position, it then reads it, using the
     * methord readOneMusic to create a Music object. If, however, it's unable
     * to find the position, it'll return null
     *
     * @param musicId
     * @return Music object or null
     * @throws IOException
     */
    public Music getById(int musicId) throws IOException
    {
        try (RandomAccessFile raf = new RandomAccessFile(fileName, "r"))
        {
            long pos = getMusicPos(musicId);
            if (pos != -1)
            {
                raf.seek(pos);
                return readOneMusic(raf);
            }
            else
            {
                return null;
            }
        }
    }

    /**
     * It finds the position of the Music object which matches the inputted id,
     * if none is found it throws an exception. It the goes to the end of raf,
     * goes back by the length of the written object and copies it, the pointer
     * is then moved to the file to be deleted, the copied Music object then
     * overwrites the deleted Music object, it then deletes the part that was
     * copied from the .dat file so there won't be 2 identical Music object. The
     * deleted object is then deleted from the musicIndex
     *
     * @param musicId
     * @throws IOException
     */
    public void removeMusic(int musicId) throws IOException
    {
        try (RandomAccessFile raf = new RandomAccessFile(fileName, "rw"))
        {
            long pos = getMusicPos(musicId);
            if (pos == -1)
            {
                throw new IOException("No music with id: " + musicId);
            }

            raf.seek(raf.length() - RECORD_SIZE);
            Music m = readOneMusic(raf);

            raf.seek(pos);
            writeOneMusic(raf, m);
            raf.setLength(raf.length() - RECORD_SIZE);

            musicIndex.remove(musicId);
        }
    }

}
