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

import java.io.BufferedWriter;




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

         FileWriter writer = new FileWriter(fileName,true);

         writer.append(numPedido);
         writer.append(",");
         writer.append(nomSocio);
         writer.append(",");
         writer.append(numEntrega);
         writer.append(",");
         writer.append(cantKg);
         writer.append(",");
         writer.append(fechaEntrega);
         writer.append(",");
         writer.append(horaEntrega);
         writer.append(",");
         writer.append(codPedido);
         writer.write(System.lineSeparator());
     
         writer.close(); 
         
        }

        catch(IOException e)

        {
     e.printStackTrace();

        }
       
        
    }
    
  
}
 

         

    


