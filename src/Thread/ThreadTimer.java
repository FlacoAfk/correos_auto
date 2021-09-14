/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Thread;

import Modelo.ManejoArchivos;
import Modelo.Reutilizable;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author elkaw
 */
public class ThreadTimer extends Thread
{
    int cada_cuanto;
    LocalTime hora_inicio;
    LocalTime hora_final;
    String[] mensaje;
    boolean respuesta = true;
    
    public ThreadTimer(String nombre_hilo) {
        super(nombre_hilo);
    }
    
    public void AsignarDatos(int cada_cuanto, String hora_inicio, String hora_final, String[] mensaje)
    {
        this.cada_cuanto = cada_cuanto*60000;
        this.hora_inicio = Reutilizable.String_LocalTime(hora_inicio);
        this.hora_final = Reutilizable.String_LocalTime(hora_final);
        this.mensaje = Reutilizable.RemplazarEspacios(mensaje);
    }
    
    @Override
    public void run()
    {
        while(respuesta==true)
        {
            if(Reutilizable.HoraActual().isAfter(hora_inicio) && Reutilizable.HoraActual().isBefore(hora_final))
            {
                Timer timer = new Timer();
                TimerTask tarea = new TimerTask() 
                {
                    @Override
                    public void run() 
                    {
                        if(Reutilizable.HoraActual().isAfter(hora_final))
                        {
                            this.cancel();
                        }
                        else
                        {
                            ManejoArchivos m_archivos = new ManejoArchivos();
                            Reutilizable reu = new Reutilizable();
                            ThreadCorreos envio_correos = new ThreadCorreos(getName()+"_hijo");
                            envio_correos.AsignaDatos(m_archivos,reu,mensaje);
                            envio_correos.start();
                        }
                    }
                };
                timer.schedule(tarea, 0, cada_cuanto);
                respuesta=false;
            }
            else if(Reutilizable.HoraActual().isBefore(hora_inicio) || Reutilizable.HoraActual().equals(hora_inicio))
            {
                respuesta=true;
                
                try 
                {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) 
                {
                    System.out.println(ex.getMessage());
                }
                
            }
            else
            {
                respuesta=false;
            }
        }
    }
}
