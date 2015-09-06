/* 
    Clase encargada de ejecutar el algoritmo de optimizacion de rutas, para 
    encontrar el camino mas optimo. La clase incluye todos los elementos del
    algoritmo sin subdivisiones de clase. 
 */
package proyectotindustrial;

import java.util.ArrayList;                                                     // Uso de listas dinamicas para el manejo de productos
import java.io.*;

/**
 *
 * @author Jose P. * @author Jose Pablo Urena Gutierrez, Luis Diego Hernandez Herrera
 * 
 * @version 1.0
 */

public class OptimizadorRutas {
    
    SeleccionAsociado insAsociado = new SeleccionAsociado();                    // Instancias de las clases que manejan los archivos de datos
    SeleccionCamion insCamion = new SeleccionCamion();
    SeleccionProducto insProducto = new SeleccionProducto();
    
        static class NodoPedido{                                                     // Clase que define cada nodo
             
            int numPedido;
            String nomSocio;
            int numEntrega;
            String producto;
            double cantKg;
            String fechaEntrega;
            String horaEntrega;
            String codPedido;       
    }
    
    public void cargarBaseDatos(){                                             // Se encarga de que las instancias insasociado, insproducto, inscamion, carguen de los archivos todos los datos al sistema
        insAsociado.cargarArchivo();
        insCamion.cargarArchivo();
        insProducto.cargarArchivo();                
    }
    
    public ArrayList<NodoPedido> listaPedidos = new ArrayList<NodoPedido>(); 
    
    public void cargarPedidos(){
        
        File archivo = null;
        FileReader lector = null;
        BufferedReader lectorLinea = null;
    
       File file_ = new File("Orden.txt"); 
       String fileName = file_.getAbsolutePath();
       
       try {                                                                   // Se abre el archivo de camiones
         archivo = new File (fileName);
         lector = new FileReader(archivo);
         lectorLinea = new BufferedReader(lector);
          
         String linea;
         String delimitadoresPalabras = ",";                                    // Variable utilizada para separar los valores en el archivo por cada linea
         String[] palabrasSeparadas;
         while( ( linea = lectorLinea.readLine() ) != null){                    // Se lee cada linea del archivo
            palabrasSeparadas = linea.split(delimitadoresPalabras);  
            
            int numPedido = Integer.parseInt(palabrasSeparadas[0]);         // Variables temporales para casting 
            String nomSocio = palabrasSeparadas[1];
            int numEntrega = Integer.parseInt(palabrasSeparadas[2]);
            String producto = palabrasSeparadas[3];
            double cantKg = Double.parseDouble(palabrasSeparadas[4]);
            String fechaEntrega = palabrasSeparadas[5];
            String horaEntrega = palabrasSeparadas[6];
            String codPedido = palabrasSeparadas[7];  
            
            NodoPedido nuevoPedido = new NodoPedido();
            nuevoPedido.numPedido = numPedido;
            nuevoPedido.nomSocio = nomSocio;
            nuevoPedido.numEntrega = numEntrega;
            nuevoPedido.producto = producto;
            nuevoPedido.cantKg = cantKg;
            nuevoPedido.fechaEntrega = fechaEntrega;
            nuevoPedido.horaEntrega = horaEntrega;
            nuevoPedido.codPedido = codPedido;
            
            listaPedidos.add(nuevoPedido);
           
        }
      }
      catch(Exception e){
         e.printStackTrace();
      }finally{        
         try{                    
            if( null != lector ){   
               lector.close();     
            }                  
         }catch (Exception e2){ 
            e2.printStackTrace();
         }
      } 
    
    }
    
        
    
    
}
