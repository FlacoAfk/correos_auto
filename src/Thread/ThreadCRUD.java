/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Thread;

import Controlador.CCorreos;
import Modelo.MCorreos;
import Modelo.ManejoArchivos;
import Vista.VCorreos;

/**
 *
 * @author elkaw
 */
public class ThreadCRUD extends Thread
{
    
    @Override
    public void run()
    {
        MCorreos mc = new MCorreos();
        ManejoArchivos mma = new ManejoArchivos();
        VCorreos vc = new VCorreos();
        
        CCorreos cc = new CCorreos(mc, vc, mma);
        cc.Iniciar();
        vc.setVisible(true);
    }
    
}