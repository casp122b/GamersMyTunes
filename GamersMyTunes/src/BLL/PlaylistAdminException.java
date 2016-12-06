/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

/**
 * @author Casper & Jens
 */
public class PlaylistAdminException extends RuntimeException
{
    /**
     * Sends an error message, only to be used in relation to PlayList objects
     * @param message
     */
    public PlaylistAdminException(String message)
    {
        super(message);
    }  
}
