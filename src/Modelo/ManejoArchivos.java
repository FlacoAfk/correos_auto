/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.awt.HeadlessException;
import java.io.*;
import java.util.*;
/**
 *
 * @author elkaw
 */
public class ManejoArchivos 
{
            
    public boolean AsignarDatos(MCorreos mc)
    {
        try
        {
            String[] mensaje = mc.getMensaje();
            int cada_cuanto = mc.getCada_cuanto();
            String hora_inicio = mc.getHora_inicio();
            String hora_final = mc.getHora_final();
            StringBuffer dias = Reutilizable.ListString_StringBuffer(mc.getDias());
            
            try (BufferedWriter Fescribe = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Reutilizable.fichero_bd,true)))) {
                Fescribe.write(mensaje[0]+"-"+mensaje[1]+"-"+dias+"-"+hora_inicio+"-"+hora_final+"-"+cada_cuanto); //guarda en la base de datos los archivos
                Fescribe.write("\n");
            }
            return true;
        }
        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    public boolean EliminarDato(int dato_eliminar)
    {
        String[][] datos;
        String[] datos2;
        ArrayList<String[]> lista = new ArrayList<>();
        MCorreos m_correos;
        datos = Reutilizable.SacarInformacion(1);
        lista.addAll(Arrays.asList(datos));
        boolean respuesta;
        for (int i = 0; i < lista.size(); i++) {
            if(i == dato_eliminar)
            {
                lista.remove(i);
                break;
            }
        }
        if(Reutilizable.fichero_bd.delete())
        {
            Reutilizable.ExisteArchivo();
            for (int i = 0; i < lista.size(); i++) {
                datos2 = lista.get(i);

                m_correos = new MCorreos();
                m_correos.setMensaje(Reutilizable.AsuntoMensaje_Unir(datos2[0],datos2[1]));
                m_correos.setDias(Reutilizable.String_ListString(datos2[2]));
                m_correos.setHora_inicio(datos2[3]);
                m_correos.setHora_final(datos2[4]);
                m_correos.setCada_cuanto(Reutilizable.String_Int(datos2[5]));
                respuesta = AsignarDatos(m_correos);
            }
        
            return true;
        }
        else{
            return false;
        }
    }

    public ArrayList<MCorreos> MostrarInformacion()
    {
        try
        {
            String[][] informacion;
            if(Reutilizable.SacarInformacion(1) != null)
            {
                informacion = Reutilizable.SacarInformacion(1);
                ArrayList MostrarInformacion = new ArrayList();
                MCorreos m_correos;
                
                for (int i=0; informacion.length>i; i++) 
                {   

                    m_correos = new MCorreos();
                    m_correos.setMensaje(Reutilizable.AsuntoMensaje_Unir(informacion[i][0], informacion[i][1]));
                    m_correos.setDias(Reutilizable.String_ListString(informacion[i][2]));
                    m_correos.setHora_inicio(informacion[i][3]);
                    m_correos.setHora_final(informacion[i][4]);
                    m_correos.setCada_cuanto(Reutilizable.String_Int(informacion[i][5]));
                    MostrarInformacion.add(m_correos);
                }
                return MostrarInformacion;
            }
            else
            {
                return null;
            }
        }
        catch(HeadlessException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public ArrayList<String[]> ExtraerInformacion(){
        
        try
        {
            String[][] info_config = Reutilizable.SacarInformacion(2);
            if(info_config != null && info_config.length==2)
            {
                ArrayList config = new ArrayList();

                String[] user_pass;
                String[] mails;
                
                user_pass = Arrays.copyOf(info_config[0], info_config[0].length);
                mails = Arrays.copyOf(info_config[1], info_config[1].length);
                
                config.add(user_pass);
                config.add(mails);
                
                return config;
            }
            else
            {
                return null;
            }
        }
        catch(HeadlessException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }  
}
