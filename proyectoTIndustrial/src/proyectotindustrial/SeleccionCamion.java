/*
 *  Clase encargada de llevar un listado de los camiones junto con cada uno
    de sus atributos, esto a partir de un archivo de texto con todos los datos.

    El estandar de separacion para los valores en el archivo es la coma ",", de
   esta forma la clase separa cada valor del archivo adecuadamente.
 */
package proyectotindustrial;

import java.util.ArrayList;                                                     // Uso de listas dinamicas para el manejo de productos
import java.io.*;                                                               // Bibliotecas necesarias para lectura de archivos

/**
 *
 * @author Jose Pablo Urena Gutierrez, Luis Diego Hernandez Herrera
 * 
 * @version 1.0
 * 
 */
public class SeleccionCamion {
    
    static class NodoLista{                                                     // Clase que define cada nodo
        int numeroCamion;
        int placa;
        int campos;                                                             // Cantidad de contenedores internos del camion
        double capacidadQ;                                                      // Capacidades por campo
        double capacidadKg;
        double capacidad;
        String proveedor;
        //int entregaNodisponible;      
        String carretaProductos[];                                              // Vector que indica el producto que contiene cada compartimento
        double carretaEspacioLibre[];                                           // Vector que indica la cantidad de espacio libre que queda en el compartimento
        double diponibilidadTiempo;
        double numEntregaPedidoAnterior;                                           // Variable que indica el ultimo pedido insertado en los compartimentos
    }
    
    public ArrayList<NodoLista> listaCamiones = new ArrayList<NodoLista>();     // Lista de objetos camiones
    
    public boolean existeCamion(int placa){                                     // Metodo encargado de verificar si se encuentra en la lista el camion
        boolean existe = false;
        for(int iterador = 0; iterador < listaCamiones.size(); ++iterador){
            if(listaCamiones.get(iterador).placa == placa){
                existe = true;
                iterador = listaCamiones.size();
            }
        }
        return existe;    
    }
    
    public void agregarCamion(int numeroCamion, int placa, int campos, double capacidadQ, double capacidadKg, double capacidad, String proveedor){  // Metodo encargado de agregar un camion a la lista de camiones
        NodoLista nuevoNodo = new NodoLista();
        nuevoNodo.numeroCamion = numeroCamion;
        nuevoNodo.placa = placa;
        nuevoNodo.campos = campos;
        nuevoNodo.capacidadQ = capacidadQ;
        nuevoNodo.capacidadKg = capacidadKg;
        nuevoNodo.capacidad = capacidad;
        nuevoNodo.proveedor = proveedor;
        //nuevoNodo.entregaNodisponible = entregaNoDisponible;    
        nuevoNodo.carretaProductos = new String[campos];
        nuevoNodo.carretaEspacioLibre = new double[campos];
        nuevoNodo.diponibilidadTiempo = 660.0;                                     // Todos los camiones tienen una disponibilidad de 11 horas
        nuevoNodo.numEntregaPedidoAnterior = 0.0;
        
        for(int i = 0; i < campos; ++i){                                        // Se asigna la capacidad del compartimento a cada uno de ellos                                    
            nuevoNodo.carretaProductos[i] = "vacio";                            // Todos los compartimentos inician vacios por defecto
            nuevoNodo.carretaEspacioLibre[i] = capacidadKg;
        }                
        listaCamiones.add(nuevoNodo);    
    }
    
    public void agregarProducto(String productoAcargar, double cantidadProducto, int placa){                     // Metodo encargado de reducir la cantidad de espacio libre en un compartimento, e indicar el producto que almacena
        double temporal = 0.0;
        double temporalCant = 0.0;
        for(int i = 0; i < listaCamiones.size(); ++i){                          // Se busca el camion al cual agregar el producto
            if(listaCamiones.get(i).placa == placa){                            // Se asegura que el pedido cabe en el camion antes de depositarlo
                
                for(int k = 0; k < listaCamiones.get(i).carretaProductos.length && cantidadProducto != 0.0; ++k){   // Itera sobre los compartimentos del camion, buscando vacios hasta que deposite todo el producto 
                    
                    if(listaCamiones.get(i).carretaProductos[k].equals("vacio")){                   // Encuentra un compartimento vacio
                        listaCamiones.get(i).carretaProductos[k] = productoAcargar;
                        
                        temporal = listaCamiones.get(i).carretaEspacioLibre[k];                    
                    
                        if(listaCamiones.get(i).carretaEspacioLibre[k] >= cantidadProducto){        // La cantidad de producto cabe perfectamente en el compartimento
                            listaCamiones.get(i).carretaEspacioLibre[k] -= cantidadProducto;
                            cantidadProducto = 0.0;
                        }else{
                            listaCamiones.get(i).carretaEspacioLibre[k] = 0.0;                      // El compartimento no soporta todo el producto, se llena y se reduce la cantidad de producto a depositar
                            cantidadProducto -= temporal;
                        }
                    }
                }
            }
        }
    }
    
    public void cargarArchivo(){                                               // Metodo encargado de cargar el txt de camiones al programa
        File archivo = null;
        FileReader lector = null;
        BufferedReader lectorLinea = null;
        
        File file_ = new File("Camiones.txt"); 
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
            
            int tNumeroCamion = Integer.parseInt(palabrasSeparadas[0]);         // Variables temporales para casting 
            int tPlaca = Integer.parseInt(palabrasSeparadas[1]);
            int tCampos = Integer.parseInt(palabrasSeparadas[2]);
            double tCapacidadQ = Double.parseDouble(palabrasSeparadas[3]);
            double tCapacidadKg = Double.parseDouble(palabrasSeparadas[4]);
            double tCapacidad = Double.parseDouble(palabrasSeparadas[5]);
            //int tEntregaNoDisponible = Integer.parseInt(palabrasSeparadas[7]);                      
            agregarCamion(tNumeroCamion, tPlaca, tCampos, tCapacidadQ, tCapacidadKg, tCapacidad, palabrasSeparadas[6]);                     // Se crea el camion en la lista de camiones
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
    
    public int getNumeroCamion(int placa){                                      // Métodos get para cada uno de los atributos del Camion
        int numeroCamion = 0;
        if(existeCamion(placa)){
            for(int iterador = 0; iterador < listaCamiones.size(); ++iterador){
                if(listaCamiones.get(iterador).placa == placa){
                    numeroCamion = listaCamiones.get(iterador).numeroCamion;
                    iterador = listaCamiones.size();
                }
            }
        }         
        return numeroCamion;    
    }
    
    public int getCampos(int placa){                                            
        int campos = 0;
        if(existeCamion(placa)){
            for(int iterador = 0; iterador < listaCamiones.size(); ++iterador){
                if(listaCamiones.get(iterador).placa == placa){
                    campos = listaCamiones.get(iterador).campos;
                    iterador = listaCamiones.size();
                }
            }
        }         
        return campos;    
    }
    
    public double getCapacidadQ(int placa){                                            
        double capacidad = 0;
        if(existeCamion(placa)){
            for(int iterador = 0; iterador < listaCamiones.size(); ++iterador){
                if(listaCamiones.get(iterador).placa == placa){
                    capacidad = listaCamiones.get(iterador).capacidadQ;
                    iterador = listaCamiones.size();
                }
            }
        }         
        return capacidad;    
    }

    public double getCapacidadKg(int placa){                                            
        double capacidad = 0;
        if(existeCamion(placa)){
            for(int iterador = 0; iterador < listaCamiones.size(); ++iterador){
                if(listaCamiones.get(iterador).placa == placa){
                    capacidad = listaCamiones.get(iterador).capacidadKg;
                    iterador = listaCamiones.size();
                }
            }
        }         
        return capacidad;    
    }
    
    public double getCapacidad(int placa){                                            
        double capacidad = 0;
        if(existeCamion(placa)){
            for(int iterador = 0; iterador < listaCamiones.size(); ++iterador){
                if(listaCamiones.get(iterador).placa == placa){
                    capacidad = listaCamiones.get(iterador).capacidad;
                    iterador = listaCamiones.size();
                }
            }
        }         
        return capacidad;    
    }
    
    public String getProveedor(int placa){                                            
        String proveedor = "";
        if(existeCamion(placa)){
            for(int iterador = 0; iterador < listaCamiones.size(); ++iterador){
                if(listaCamiones.get(iterador).placa == placa){
                    proveedor = listaCamiones.get(iterador).proveedor;
                    iterador = listaCamiones.size();
                }
            }
        }         
        return proveedor;    
    }
    
    /**public int getEntregaNoDisponible(int placa){                                            
        int entregaNoDisponible = 0;
        if(existeCamion(placa)){
            for(int iterador = 0; iterador < listaCamiones.size(); ++iterador){
                if(listaCamiones.get(iterador).placa == placa){
                    entregaNoDisponible = listaCamiones.get(iterador).entregaNodisponible;
                    iterador = listaCamiones.size();
                }
            }
        }         
        return entregaNoDisponible;
    }        **/    

    
    public double getCantidadEspacioLibre(int placa){
        double espacioLibre = 0.0;
        if(existeCamion(placa)){
            for(int iterador = 0; iterador < listaCamiones.size(); ++iterador){
                if(listaCamiones.get(iterador).placa == placa){
                    
                    for(int i = 0; i < listaCamiones.get(iterador).campos; ++i){
                        if(listaCamiones.get(iterador).carretaProductos[i].equals("vacio")){
                            espacioLibre += listaCamiones.get(iterador).capacidadKg;                            
                        }
                    }
                    iterador = listaCamiones.size();                
                }                    
            }
        }
        return espacioLibre;
    }
}
