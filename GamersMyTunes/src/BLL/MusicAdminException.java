/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

/**
 *
 * @author Casper & Jens
 */
public class MusicAdminException extends RuntimeException
{
    /**
     * Sends an error message, only to be used in relation to Music objects
     * @param message 
     */
    public MusicAdminException(String message)
    {
        super(message);
    }
    
}
