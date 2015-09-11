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
    
    public static void main(String[] args) {
    
        new VenPrincipal().setVisible(true);
        SeleccionCamion nuevo = new SeleccionCamion();
        nuevo.agregarCamion(3, 111, 5, 333.3, 22.2, 15000.0, "DP", 23);
        nuevo.agregarProducto("arroz", 2330.0, 111);
        nuevo.agregarProducto("frijoles", 5500.25, 111);
        nuevo.agregarProducto("arroz", 300.0, 111);
        
        
        
        

    }
    
}
