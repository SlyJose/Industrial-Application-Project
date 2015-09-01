/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectotindustrial;

/**
 *
 * @author luis diego
 */

import java.io.FileWriter; //add to import list

import java.io.IOException;

import java.io.Writer;



public class CreadorOrden {
  
    public void creaOrden (
    String numPedido, 
    String nomSocio,
    String numEntrega,
    String cantKg,
    String fechaEntrega,
    String horaEntrega,
    String codPedido) 
    
    {

         

        String fileName = "C:\\Users\\luis diego\\Desktop\\proyectoTIndustrial\\proyectoTIndustrial\\Orden.txt"; //location of generated report
         

        try

        {

         FileWriter writer = new FileWriter(fileName);

         writer.append(numPedido);
         writer.append(",");
         
        }

        catch(IOException e)

        {
     e.printStackTrace();

        }
       
        
    }
    
  
}
 

         

    


