/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package correosmedimas;

import Modelo.*;
import Thread.ThreadCRUD;
import javax.swing.JOptionPane;

/**
 *
 * @author elkaw
 */
public class CorreosMedimas 
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        // TODO code application logic here
        if(Reutilizable.ComprobarInternet() == true)
        {
            Reutilizable.ExisteArchivo();
            
            ThreadCRUD hilo_crud = new ThreadCRUD();
            hilo_crud.start();   
        }
        else{
            JOptionPane.showMessageDialog(null, "Intenta: \n      -Comprobar los cables de red, el módem y el router \n      -Volver a conectarte a Wi-Fi" , "Sin internet", JOptionPane.PLAIN_MESSAGE);
        }
    }
    
}
