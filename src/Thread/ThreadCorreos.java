/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Thread;

import Modelo.ManejoArchivos;
import Modelo.Reutilizable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author elkaw
 */
public class ThreadCorreos extends Thread
{ 
    
    private ManejoArchivos m_archivos;
    private Reutilizable reu;
    private String[] correo;
    
    public ThreadCorreos(String nombre_hilo) {
        super(nombre_hilo);
    }
    
    public void AsignaDatos(ManejoArchivos m_archivos,Reutilizable reu, String[] correo)
    {
        this.m_archivos = m_archivos;
        this.reu = reu;
        this.correo = correo;
    }
    
    @Override
    public void run()
    {
        Properties propiedades;
        String[] user_password;
        String[] correos_enviar;
        
        try {
            String asunto = correo[0];
            String mensaje = correo[1];
            user_password = m_archivos.ExtraerInformacion().get(0); //0 = correo remitente, 1 = contraseña remitente, 2 = tipo de correo
            propiedades = reu.Propiedades(reu.TipoPropiedades(user_password[2]));
            Session sesion = Session.getDefaultInstance(propiedades);
            
            MimeMessage message = new MimeMessage(sesion);
            message.setFrom(new InternetAddress(user_password[0]));
            
            correos_enviar = m_archivos.ExtraerInformacion().get(1);
            Address[] correos_enviar_final = new Address[correos_enviar.length];
            for(int i = 0; i < correos_enviar.length; i++)
            {
                correos_enviar_final[i] = new InternetAddress(correos_enviar[i]);
            }
            
            message.addRecipients(Message.RecipientType.TO, correos_enviar_final);
            message.setSubject(asunto);
            message.setText(mensaje);
            
            Transport t = sesion.getTransport();
            t.connect(user_password[0], user_password[1]);
            t.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            t.close();
            
            System.out.println("mensaje enviado desde el hilo "+getName());
            
        } catch (AddressException ex) {
            Logger.getLogger(ThreadCorreos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(ThreadCorreos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
