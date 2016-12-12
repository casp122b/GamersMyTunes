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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Class which reads and writes songs from and to a file.
 *
 * @author Casper & Jens
 */
public class MusicDAO {

    public void writeObjectData(ArrayList<Music> songs, String fileName) throws IOException
    {
        try (FileOutputStream fos = new FileOutputStream(fileName))
        {
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(songs);
        }
    }

    public ArrayList<Music> readObjectData(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException
    {
        ArrayList<Music> songList = new ArrayList<>();

        FileInputStream fis = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(fis);
        try (ObjectInputStream ois = new ObjectInputStream(bis))
        {

            songList = (ArrayList<Music>) ois.readObject();
        }
        
        return songList;
    }
}
