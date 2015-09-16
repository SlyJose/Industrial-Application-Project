/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectotindustrial;
import java.util.ArrayList;                                                     // Uso de listas dinamicas para el manejo de productos
import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Jose Pablo Urena Gutierrez, Luis Diego Hernandez Herrera
 * 
 * @version 1.0
 */
public class ProyectoTIndustrial {

    /**
     * @param args the command line arguments
     */
    public void limpiaArhivoOrden () {                                          //metodo que comienza limpiando el archivo que carga las ordenes
    
    File file_ = new File("Orden.txt"); 
        String fileName = file_.getAbsolutePath();
        
        try {
            
            
        PrintWriter writer = new PrintWriter(fileName);
        writer.print("");
        writer.close();
        
           }

        catch(IOException e)

        {
            e.printStackTrace();

        }
    }
    
    public static void main(String[] args) { 
        
        ProyectoTIndustrial comienza = new ProyectoTIndustrial();
        
       // comienza.limpiaArhivoOrden();
        
        new VenPrincipal().setVisible(true);    
        
       // Pruebas llena matriz
        
        /**LlenaMatriz llena = new LlenaMatriz();
        llena.llenaMatrizDistancia();
        llena.llenaMatrizTiempos();
        llena.llenaNumEntregas(); **/
        
        
        /**
        for(int q = 1; q < llena.matriz.length;++q){
                    for(int u = 1; u < llena.matriz.length; ++u){
                        System.out.print(llena.matriz[q][u].tiempoM+" ");
                    }
                    System.out.println();
                } **/
        
       //System.out.print(llena.matriz[0][4].tiempoM);
       //System.out.print(llena.matriz[4][0].tiempoM);
       
       
    }
}
