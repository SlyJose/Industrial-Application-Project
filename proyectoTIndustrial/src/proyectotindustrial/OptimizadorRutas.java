/* 
    Clase encargada de ejecutar el algoritmo de optimizacion de rutas, para 
    encontrar el camino mas optimo. La clase incluye todos los elementos del
    algoritmo sin subdivisiones de clase. 
 */
package proyectotindustrial;

import java.util.ArrayList;                                                     // Uso de listas dinamicas para el manejo de productos
import java.io.*;
import java.util.Random;

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
    
        static class NodoPedido{                                                // Clase que define cada nodo
             
            int numPedido;
            String nomSocio;
            int numEntrega;
            String producto;
            double cantKg;
            String fechaEntrega;
            String horaEntrega;
            String codPedido;       
            boolean procesado = false;
    }
        
        static class NodoRuta{                                                  // Nodo que contiene los datos de una ruta nueva
            
            String mes;
            int dia;
            int numRuta;
            String socio;
            String producto;
            double kgAentregar;
            String horaSalida;
            int zona;
            int numEntrega;
            double precioFlete;
            double montoFlete;
            int placaCamion;
            String proveedor;            
        }
    
    public void cargarBaseDatos(){                                              // Se encarga de que las instancias insasociado, insproducto, inscamion, carguen de los archivos todos los datos al sistema
        insAsociado.cargarArchivo();
        insCamion.cargarArchivo();
        insProducto.cargarArchivo();                
    }
    
    public ArrayList<NodoPedido> listaPedidos = new ArrayList<NodoPedido>();    // Todos los pedidos ingresados por el usuario
    public ArrayList<NodoRuta> listaRutas = new ArrayList<NodoRuta>();          // Rutas actuales generadas por el optimizador
    public ArrayList<NodoPedido> subListaPedidos = new ArrayList<NodoPedido>(); // Maneja los pedidos que se van atrasando en la zona
    
    public void optimizador(){                                                  // Metodo encargado de obtener la ruta de acuerdo a los pedidos ingresados
        
        int tamSubListaTemp = 0;                                                // El tamanio de la sublista de pedidos varia, se usa para el random              
        Random rand = new Random();
        int pedidoAescoger = 0;
        String preferenciaActual = "";
        boolean continuaRandom = true;
        
        for(int i = 1; i < 9; ++i){                                             // Itera sobre las zonas para obtener los pedidos en cada una                  
    
            for(int j = 0; j < listaPedidos.size(); ++j){   
                
                if( insAsociado.getZona(listaPedidos.get(j).numEntrega) == i ){ // Obtiene todos los pedidos de la zona actual y los inserta en la subLista de pedidos                    
                    subListaPedidos.add(listaPedidos.get(j));                    
                }
            }
            tamSubListaTemp = subListaPedidos.size();
            pedidoAescoger = rand.nextInt(tamSubListaTemp - 1) + 0;
            
            
            while(continuaRandom){                                              // Se trabajan los pedidos por zona sabiendo si fueron o no procesados, de forma aleatoria
                if(subListaPedidos.get(pedidoAescoger).procesado == false){                
                    
                    preferenciaActual = insAsociado.getPreferencia(subListaPedidos.get(pedidoAescoger).numEntrega);                    
                    
                    subListaPedidos.get(pedidoAescoger).procesado = true;
                    continuaRandom = false;
                }else{
                    pedidoAescoger = rand.nextInt(tamSubListaTemp - 1) + 0;
                }
            }
            continuaRandom = true;
            
            for(int k = 0; k < insCamion.listaCamiones.size(); ++k){                // Se encarga de escoger un camion que cumpla los requisitos de dicho pedido
                
                if( insCamion.listaCamiones.get(k).proveedor == preferenciaActual ){
                    
                    if(insCamion.listaCamiones.get(k).){
                    }
                                    
                }
                
                
            }
            
        }
        

        
    }
    
    public void evaluarCostos(){                                                // Evalua el costo de las rutas actuales obtenidas con respecto a otras obtenidas anteriormente
    }
    
    public void limpiarOrdenes(){                                               // Se limpian todas las listas para una nueva ejecucion de algoritmo optimizador
    }  
    
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
            
            int numPedido = Integer.parseInt(palabrasSeparadas[0]);             // Variables temporales para casting 
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
