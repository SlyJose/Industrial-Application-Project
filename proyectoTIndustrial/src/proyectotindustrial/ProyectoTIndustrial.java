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
        
       LlenaMatriz llena = new LlenaMatriz();
       llena.seteaMatriz();
        
        for(int i = 0; i < llena.matriz.length; i++){
                
            for(int j= 0; j < llena.matriz.length; j++){
        
//                System.out.print(llena.matriz[i][j].distanciaM + "\t");
//                System.out.print(llena.matriz[i][j].tiempoM + "\n");
//                System.out.print(llena.matriz[j][i].distanciaM + "\t");
//                System.out.print(llena.matriz[j][i].tiempoM + "\n");
                
                if (llena.matriz[i][j].distanciaM != llena.matriz[j][i].distanciaM ){
                System.out.println("BRINCA LA EMPANADA");
                }
                
                if (llena.matriz[i][j].tiempoM != llena.matriz[j][i].tiempoM ){
                System.out.println("BRINCA LA EMPANADA");
                }
                
        }
            
            
         
    }
}
}
