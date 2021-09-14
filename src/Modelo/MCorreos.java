/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.List;

/**
 *
 * @author elkaw
 */
public class MCorreos 
{
    private int cada_cuanto;
    private String hora_inicio;
    private String hora_final;
    private List<String> dias;
    private String mensaje[];
    
    public MCorreos()
    {
        this.cada_cuanto=0;
        this.hora_inicio="";
        this.hora_final="";
        this.dias=null;
        this.mensaje=null;
    }
    
    public int getCada_cuanto() 
    {
        return cada_cuanto;
    }

    public void setCada_cuanto(int cada_cuanto) 
    {
        this.cada_cuanto = cada_cuanto;
    }


    public String getHora_inicio() 
    {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) 
    {
        this.hora_inicio = hora_inicio;
    }

    public String getHora_final() 
    {
        return hora_final;
    }

    public void setHora_final(String hora_final) 
    {
        this.hora_final = hora_final;
    }

    public List<String> getDias() 
    {
        return dias;
    }

    public void setDias(List<String> dias) 
    {
        this.dias = dias;
    }
    
    public String[] getMensaje() 
    {
        return mensaje;
    }

    public void setMensaje(String[] mensaje) 
    {
        this.mensaje = mensaje;
    }
}
