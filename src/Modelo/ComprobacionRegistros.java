/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Thread.ThreadTimer;
import java.util.List;

/**
 *
 * @author elkaw
 */
public class ComprobacionRegistros {
    private ManejoArchivos manejo_archivos = new ManejoArchivos();
    
    public void RegistrosMensajes()
    {
        String hora_inicio;
        String hora_final;
        List<String> dias;
        int cada_cuanto;
        String[] mensaje;
        int cont = 0;
        
        try
        {
            int numero_registros = manejo_archivos.MostrarInformacion().size();
            
            if(numero_registros > 0)
            {
                for(int i=0;i<numero_registros;i++)
                {
                    cada_cuanto = manejo_archivos.MostrarInformacion().get(i).getCada_cuanto();
                    hora_inicio = manejo_archivos.MostrarInformacion().get(i).getHora_inicio();
                    hora_final = manejo_archivos.MostrarInformacion().get(i).getHora_final();
                    dias = manejo_archivos.MostrarInformacion().get(i).getDias();
                    mensaje = manejo_archivos.MostrarInformacion().get(i).getMensaje();

                    for(int o=0; o < dias.size(); o++)
                    {
                        if(dias.get(o).toLowerCase() == null ? Reutilizable.FechaActual() == null : dias.get(o).toLowerCase().equals(Reutilizable.FechaActual()))
                        {
                            ThreadTimer timer = new ThreadTimer("Hilo"+cont);
                            timer.AsignarDatos(cada_cuanto, hora_inicio, hora_final, mensaje);
                            timer.start();
                            break;
                        }
                    }
                    cont++;
                }
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
