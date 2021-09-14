/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.*;
import Vista.VCorreos;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 *
 * @author elkaw
 */
public class CCorreos implements ActionListener
{
    private MCorreos m_correos;
    private VCorreos v_correos;
    private ManejoArchivos manejo_archivos;
    
    public CCorreos (MCorreos m_correos, VCorreos v_correos, ManejoArchivos manejo_archivos)
    {
        this.m_correos = m_correos;
        this.manejo_archivos = manejo_archivos;
        this.v_correos = v_correos;
        this.v_correos.BtnIniciar.addActionListener(this);
        this.v_correos.BtnGuardar.addActionListener(this);
        this.v_correos.BtnEliminar.addActionListener(this);
        this.v_correos.BtnConsultar.addActionListener(this);
    }
    
    public void Iniciar()
    {
        v_correos.setTitle("Envio de Correos");
        v_correos.setLocationRelativeTo(null);
    }
    
    public void LLenarTabla(JTable tablaD)
    {
        if(manejo_archivos.MostrarInformacion() != null && manejo_archivos.MostrarInformacion().size() > 0)
        {
            DefaultTableModel modeloT = new DefaultTableModel();
            tablaD.setModel(modeloT);

            modeloT.addColumn("#");
            modeloT.addColumn("Asunto");
            modeloT.addColumn("Mensaje");
            modeloT.addColumn("Dias a enviar");
            modeloT.addColumn("Hora inicio");
            modeloT.addColumn("Hora final");
            modeloT.addColumn("Cada cuanto(minutos)");
            modeloT.addColumn("Eliminar");

            Object[] columna = new Object[7];

            int numero_registros = manejo_archivos.MostrarInformacion().size();

            for(int i=0;i<numero_registros;i++)
            {
                columna[0] = (i+1);
                columna[1] = Reutilizable.AsuntoMensaje_Separar(manejo_archivos.MostrarInformacion().get(i).getMensaje(), 1);
                columna[2] = Reutilizable.AsuntoMensaje_Separar(manejo_archivos.MostrarInformacion().get(i).getMensaje(), 2);
                columna[3] = Reutilizable.ListString_StringBuffer(manejo_archivos.MostrarInformacion().get(i).getDias());
                columna[4] = manejo_archivos.MostrarInformacion().get(i).getHora_inicio();
                columna[5] = manejo_archivos.MostrarInformacion().get(i).getHora_final();
                columna[6] = String.valueOf(manejo_archivos.MostrarInformacion().get(i).getCada_cuanto())+" min";
                modeloT.addRow(columna);
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        List<String> dias = new ArrayList<>();
        try
        {
            String hora_inicio = v_correos.TimeHoraInicio.getFormatedTime();
            String hora_final = v_correos.TimeHoraFinal.getFormatedTime();
                if(v_correos.CheckLunes.isSelected()) { dias.add(v_correos.CheckLunes.getText());}
                if(v_correos.CheckMartes.isSelected()) { dias.add(v_correos.CheckMartes.getText());}
                if(v_correos.CheckMiercoles.isSelected()) { dias.add(v_correos.CheckMiercoles.getText());}
                if(v_correos.CheckJueves.isSelected()) { dias.add(v_correos.CheckJueves.getText());}
                if(v_correos.CheckViernes.isSelected()) { dias.add(v_correos.CheckViernes.getText());}
                if(v_correos.CheckSabado.isSelected()) { dias.add(v_correos.CheckSabado.getText());}
                if(v_correos.CheckDomingo.isSelected()) { dias.add(v_correos.CheckDomingo.getText());}
                String[] mensaje = {v_correos.TxtAsunto.getText(), v_correos.TxtMensaje.getText().replaceAll("(\n|\r)","%")};
                int cada_cuanto = (int)v_correos.NumberCadaCuanto.getValue();
                if(e.getSource() == v_correos.BtnGuardar)
                {
                    m_correos.setDias(dias);
                    m_correos.setMensaje(mensaje);
                    m_correos.setHora_inicio(hora_inicio);
                    m_correos.setHora_final(hora_final);
                    m_correos.setCada_cuanto(cada_cuanto);
                    if(Reutilizable.String_LocalTime(hora_inicio).isBefore(Reutilizable.String_LocalTime(hora_final)))
                    {
                        if(manejo_archivos.AsignarDatos(m_correos))
                        {
                            JOptionPane.showMessageDialog(null, "Registro guardado");
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Registro no guardado");
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "La hora de inicio no debe ser mayor a la hora final");
                    }
                }
                if(e.getSource() == v_correos.BtnConsultar)
                {
                    LLenarTabla(v_correos.TablaConsulta);
                }
                if(e.getSource() == v_correos.BtnEliminar)
                {
                    int numero_filas = v_correos.TablaConsulta.getRowCount();
                    if(numero_filas > 0)
                    {
                        int filas = v_correos.TablaConsulta.getSelectedRowCount();
                        
                        if(filas == 1)
                        {
                            
                            int fila_seleccionada = v_correos.TablaConsulta.getSelectedRow();
                        
                            if(manejo_archivos.EliminarDato(fila_seleccionada))
                            {
                                JOptionPane.showMessageDialog(null, "Registro eliminado");
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "Registro no eliminado");
                            }
                            
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Ninguna fila seleccionada");
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "No hay datos que eliminar");
                    }
                }
                if(e.getSource() == v_correos.BtnIniciar)
                {
                    ComprobacionRegistros comprobar = new ComprobacionRegistros();
                    comprobar.RegistrosMensajes();
                }
        }
        catch(NumberFormatException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}
