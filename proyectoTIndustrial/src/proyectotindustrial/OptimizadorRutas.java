/* 
    Clase encargada de ejecutar el algoritmo de optimizacion de rutas, para 
    encontrar el camino mas optimo. La clase incluye todos los elementos del
    algoritmo sin subdivisiones de clase. 
 */
package proyectotindustrial;

import java.util.ArrayList;                                                     // Uso de listas dinamicas para el manejo de productos
import java.io.*;
import java.util.Random;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
    }
        
        static class NodoRuta{                                                  // Nodo que contiene los datos de una ruta nueva
            
            int mes;
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
    public ArrayList<NodoPedido> auxSubListaPedidos = new ArrayList<NodoPedido>();
    
    public void optimizador(){                                                  // Metodo encargado de obtener la ruta de acuerdo a los pedidos ingresados
        
        int tamSubListaTemp = 0;                                                // El tamanio de la sublista de pedidos varia, se usa para el random              
        Random rand = new Random();
        int pedidoAescoger = 0;
        String preferenciaActual = "";
        
        for(int i = 1; i < 9; ++i){                                             // Itera sobre las zonas para obtener los pedidos en cada una                  
    
            for(int j = 0; j < listaPedidos.size(); ++j){   
                
                if( insAsociado.getZona(listaPedidos.get(j).numEntrega) == i ){ // Obtiene todos los pedidos de la zona actual y los inserta en la subLista de pedidos                    
                    subListaPedidos.add(listaPedidos.get(j));                    
                }
            }
            
            
            for(int m = 0; subListaPedidos.size() != 0; ++m){
                
                tamSubListaTemp = subListaPedidos.size();
                pedidoAescoger = rand.nextInt(tamSubListaTemp - 1) + 0;

                preferenciaActual = insAsociado.getPreferencia(subListaPedidos.get(pedidoAescoger).numEntrega);                    

                for(int k = 0; k < insCamion.listaCamiones.size(); ++k){                // Se encarga de escoger un camion que cumpla los requisitos de dicho pedido

                    if( insCamion.listaCamiones.get(k).proveedor == preferenciaActual ){    // Se verifica que el camion sea de la preferencia requerida por el pedido

                        if(insCamion.getCantidadEspacioLibre(insCamion.listaCamiones.get(k).placa) >= subListaPedidos.get(pedidoAescoger).cantKg){      // Se verifica si la cantidad de producto cabe en el camion escogido

                            insCamion.agregarProducto(subListaPedidos.get(pedidoAescoger).producto, subListaPedidos.get(pedidoAescoger).cantKg, insCamion.listaCamiones.get(k).placa);

                            // se mete este agregado a la lista de rutas
                            
                            
                            int day, month, year;
                            int second, minute, hour;
                            
                            GregorianCalendar date = new GregorianCalendar();
 
                            day = date.get(Calendar.DAY_OF_MONTH);
                            month = date.get(Calendar.MONTH) + 1;
                            year = date.get(Calendar.YEAR);

                            second = date.get(Calendar.SECOND);
                            minute = date.get(Calendar.MINUTE);
      
                         
                            NodoRuta nuevaRuta = new NodoRuta();
                            
                            
                            nuevaRuta.mes = month;
                            nuevaRuta.dia = day;
                            nuevaRuta.numRuta = insCamion.listaCamiones.get(k).placa; // declarado por placa como identificador de pedido
                            nuevaRuta.socio = subListaPedidos.get(pedidoAescoger).nomSocio;
                            nuevaRuta.producto = subListaPedidos.get(pedidoAescoger).producto;
                            nuevaRuta.kgAentregar = subListaPedidos.get(pedidoAescoger).cantKg;
                            nuevaRuta.horaSalida = "";
                            nuevaRuta.zona = insAsociado.getZona(subListaPedidos.get(pedidoAescoger).numEntrega);
                            nuevaRuta.numEntrega = subListaPedidos.get(pedidoAescoger).numEntrega;
                            nuevaRuta.placaCamion = insCamion.listaCamiones.get(k).placa;
                            nuevaRuta.proveedor = insCamion.listaCamiones.get(k).proveedor;
                            nuevaRuta.precioFlete = 0.0;
                            nuevaRuta.montoFlete = 0.0;
                            
                            
                            listaRutas.add(nuevaRuta);

                            subListaPedidos.remove(pedidoAescoger);                 // El pedido se elimina de la sublista, ya fue procesado                        
                            k = insCamion.listaCamiones.size();
                        }else{
                            auxSubListaPedidos.add(subListaPedidos.get(pedidoAescoger));        // Agrega el pedido que no cabe en el camion actual a una lista auxiliar, para que pase a la siguiente zona
                            subListaPedidos.remove(pedidoAescoger);
                            k = insCamion.listaCamiones.size();
                        }                                    
                    }
                }            
            }
            
            for(int l = 0; l < auxSubListaPedidos.size(); l++){      
                subListaPedidos.add(auxSubListaPedidos.get(l));
            }
            
            auxSubListaPedidos.clear();
            
            //verificar si esta empanada sirve subListaPedidos = auxSubListaPedidos;
            if (i == 9 && subListaPedidos.size() != 0) {
            
                cargarAfuerza();
            }
            
        }
        
        // Evalua ruta
        
    }
    
public void cargarAfuerza(){
    
    for (int i = 0; i < subListaPedidos.size(); i++){
        
       String preferenciaActual = insAsociado.getPreferencia(subListaPedidos.get(i).numEntrega);                    
            
        for (int j= 0; j < insCamion.listaCamiones.size(); ++j ) {
            
            if( insCamion.listaCamiones.get(j).proveedor == preferenciaActual ){    // Se verifica que el camion sea de la preferencia requerida por el pedido

                if(insCamion.getCantidadEspacioLibre(insCamion.listaCamiones.get(j).placa) >= subListaPedidos.get(i).cantKg){      // Se verifica si la cantidad de producto cabe en el camion escogido

                    insCamion.agregarProducto(subListaPedidos.get(i).producto, subListaPedidos.get(i).cantKg, insCamion.listaCamiones.get(j).placa);

                    // se mete este agregado a la lista de rutas
                            
                            
                    int day, month, year;
                    int second, minute, hour;
                            
                    GregorianCalendar date = new GregorianCalendar();
 
                    day = date.get(Calendar.DAY_OF_MONTH);
                    month = date.get(Calendar.MONTH) + 1;
                    year = date.get(Calendar.YEAR);

                    second = date.get(Calendar.SECOND);
                    minute = date.get(Calendar.MINUTE);
      
                         
                    NodoRuta nuevaRuta = new NodoRuta();
                            
                            
                    nuevaRuta.mes = month;
                    nuevaRuta.dia = day;
                    nuevaRuta.numRuta = insCamion.listaCamiones.get(j).placa; // declarado por placa como identificador de pedido
                    nuevaRuta.socio = subListaPedidos.get(i).nomSocio;
                    nuevaRuta.producto = subListaPedidos.get(i).producto;
                    nuevaRuta.kgAentregar = subListaPedidos.get(i).cantKg;
                    nuevaRuta.horaSalida = "";
                    nuevaRuta.zona = insAsociado.getZona(subListaPedidos.get(i).numEntrega);
                    nuevaRuta.numEntrega = subListaPedidos.get(i).numEntrega;
                    nuevaRuta.placaCamion = insCamion.listaCamiones.get(j).placa;
                    nuevaRuta.proveedor = insCamion.listaCamiones.get(j).proveedor;
                    nuevaRuta.precioFlete = 0.0;
                    nuevaRuta.montoFlete = 0.0;
                     
                            
                   listaRutas.add(nuevaRuta);

                   subListaPedidos.remove(i);                 // El pedido se elimina de la sublista, ya fue procesado                        
                   j = insCamion.listaCamiones.size();
                
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
