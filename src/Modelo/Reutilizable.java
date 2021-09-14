/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.*;
import java.net.Socket;
import java.text.*;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import javax.swing.JOptionPane;

/**
 *
 * @author elkaw
 */
public class Reutilizable 
{
    
    public static File fichero_bd = new File("bd.txt");
    public static File fichero_config = new File("config.txt");
    
    public static void ExisteArchivo()//metodo para hacer la base de datos
    {
        try
        {
            if (!fichero_bd.exists())//saber si existe el archivo de texto
            {
                fichero_bd.createNewFile();
                
            }
            if(!fichero_config.exists())
            {
                fichero_config.createNewFile();
            }
        }
        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    
    public static String[][] SacarInformacion(int opcion)
    {   
        FileReader fr = null;
        BufferedReader br = null;
        int lineas = 0;
        int cols = 0;
        File fichero = new File("");
        String separador = "";

        try 
        {
            switch(opcion)
            {
                case 1:
                    fichero = fichero_bd;
                    separador = "-";
                    break;
                case 2:
                    fichero = fichero_config;
                    separador = ",";
                    break;
            }
            if(fichero.exists())
            {
                fr = new FileReader(fichero);
                br = new BufferedReader(fr);
                ArrayList<String[]> AUX = new ArrayList<>();

                String cadena;
                while((cadena=br.readLine())!=null)
                {
                    AUX.add(cadena.split(separador));
                    lineas++;
                }
                
                String[][] infor; 
                
                if(AUX.size()>0) 
                {
                    cols = AUX.get(0).length;
                    infor = new String[lineas][cols];
                    for(int i=0; i<lineas; i++) {
                        infor = AUX.toArray(infor);
                    }
                }
                else 
                {
                    JOptionPane.showMessageDialog(null, "No hay datos");
                    infor = null;
                }
                return infor;
            }
            else
            {
                JOptionPane.showMessageDialog(null, "No existe el archivo BD");
                return null;
            }
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
        finally
        {
            try
            {                    
                if( null != fr )
                {   
                   fr.close();     
                }                  
            }
            catch (IOException e2)
            { 
                System.out.println(e2.getMessage());
            }
        }
    }
    
    public static int[] String_ArrayInt(String fecha)
    {
        String[] fecha_texto;
        try
        {
            fecha_texto = fecha.split("/");
            int tamaño = fecha_texto.length;
            int[] fecha_final = new int[tamaño];
            for(int i = 0; i < tamaño; i++)
            {
                fecha_final[i] = Integer.parseInt(fecha_texto[i]);
            }
            return fecha_final;
        }
        catch(NumberFormatException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
       
    }
    
    public static List<String> String_ListString(String dias)
    {
        try
        {
            List<String> dias_final = new ArrayList<>(Arrays.asList(dias.split(",")));
            /*int tamaño = (dias_final.size() - 1);
            dias_final.remove(tamaño);*/
            return dias_final;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public static StringBuffer ListString_StringBuffer(List<String> dias)
    {
        try
        {
            List<String> array_dias = dias;
            StringBuffer dias_final = new StringBuffer();

            for (String array_dia : array_dias) 
            {
                dias_final = dias_final.append(array_dia).append(",");
            }
            return dias_final;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public static int String_Int(String var_string)
    {
        int var_int;
        try
        {
            var_int = Integer.parseInt(var_string);
            
            return var_int;
        }
        catch(NumberFormatException e)
        {
            System.out.println(e.getMessage());
            return 0;
        }
    }
    
    public static String AsuntoMensaje_Separar(String[] array_mensaje, int opcion)
    {
        String retorno = "";
        try
        {
            switch(opcion)
            {
                case 1:
                    retorno = array_mensaje[0];//asunto
                    break;
                case 2:
                    retorno = array_mensaje[1];//mensaje
                    break;
            }
            return retorno;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public static String[] RemplazarEspacios(String[] correo)
    {
        
        String mensaje_sin_espacios = correo[1].replace("%%", "\n");
        correo[1] = mensaje_sin_espacios;
        
        return correo;
        
    }
    
    public static String[] AsuntoMensaje_Unir(String asunto, String mensaje)
    {
        try
        {
            String[] mensaje_completo = {asunto, mensaje};
            return mensaje_completo;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public static String FechaActual()
    {
        String fecha_actual = new SimpleDateFormat("EEEE").format(new Date());
        return fecha_actual;
    }
    
    public static LocalTime HoraActual()
    {
        LocalTime hora_actual = LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
        return hora_actual;
    }
    
    public static LocalTime String_LocalTime(String tiempo)
    {
        LocalTime tiempo_sin_formato = LocalTime.parse(tiempo);
        LocalTime tiempo_final = tiempo_sin_formato.truncatedTo(ChronoUnit.SECONDS);
        return tiempo_final;
    }
    
    public static boolean ComprobarInternet()
    {
        String dirWeb = "www.google.com";
        int puerto= 80;
        try
        {
            Socket s = new Socket(dirWeb, puerto);
            if(s.isConnected()){
                System.out.println("Conexión establecida con la dirección: " + dirWeb + " a través del pueto: " + puerto);
            }
        }
        catch (IOException e)
        {
            System.err.println("No se pudo establecer conexión con: " + dirWeb + " a través del puerto: " + puerto);
            return false;
        }
        return true;
    }
    
    public int TipoPropiedades(String tipo_correo)
    {
        int server = 0;
        
        switch(tipo_correo.toUpperCase())
        {
            case "GMAIL":
                server = 0;
                break;
            case "HOTMAIL":
                server = 1;
                break;
            case "OUTLOOK":
                server = 1;
                break;
            case "LIVE":
                server = 1;
                break;
            case "YAHOO":
                server = 2;
                break;
        }
        return server;
    }
    
    public Properties Propiedades(int server)
    {
        Properties props = new Properties();
        
        
        if(server==0)
        {
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "587");
            props.put("mail.smtp.socketFactory.fallback", "false");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "587");
        }
        if(server==1)
        {
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.host", "smtp.live.com");
            props.put("mail.smtp.socketFactory.port", "587");
            props.put("mail.smtp.socketFactory.fallback", "false");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "587");
        }
        if(server==2)
        {
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.host", "smtp.mail.yahoo.com");
            props.put("mail.smtp.socketFactory.port", "587");
            props.put("mail.smtp.socketFactory.fallback", "false");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "587");
        }
        
        return props;
    }
}
