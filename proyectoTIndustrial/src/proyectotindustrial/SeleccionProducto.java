/**
 *  Clase encargada de manejar todos los productos, se cargan los productos a
 * partir de un archivo de texto que incluye los atributos para cada valor.
 * 
 * El estandar de separacion para los valores en el archivo es la coma ",", de
 * esta forma la clase separa cada valor del archivo adecuadamente. 
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
public class SeleccionProducto {
    
    static class NodoLista{                                                     // Clase que define cada nodo
         String producto;         
         double unidadDensidad;        
    }
    
    public ArrayList<NodoLista> listaProductos = new ArrayList<NodoLista>();    // Lista de objetos producto
    
    public boolean existeProducto(String producto){                             // Metodo encargado de verificar si se encuentra en la lista el producto
        boolean existe = false;
        for(int iterador = 0; iterador < listaProductos.size(); ++iterador){
            if(listaProductos.get(iterador).producto.equals(producto)){
                existe = true;
                iterador = listaProductos.size();
            }
        }
        return existe;
    }
    
    public void agregaProducto(String producto, double densidad){               // Metodo encargado de agregar un producto a la lista de productos
        NodoLista nuevoNodo = new NodoLista();
        nuevoNodo.producto = producto;
        nuevoNodo.unidadDensidad = densidad;
        listaProductos.add(nuevoNodo);
    }
    
    public void cargarArchivo(){                                               // Metodo encargado de cargar el txt de productos al programa
        File archivo = null;
        FileReader lector = null;
        BufferedReader lectorLinea = null;
        
        File file_ = new File("Productos.txt"); 
       String fileName = file_.getAbsolutePath();
        
        try {                                                                   // Se abre el archivo de productos
         archivo = new File (fileName);
         lector = new FileReader(archivo);
         lectorLinea = new BufferedReader(lector);
          
         String linea;
         String delimitadoresPalabras = ",";                                    // Variable utilizada para separar los valores en el archivo por cada linea
         String[] palabrasSeparadas;
         while( ( linea = lectorLinea.readLine() ) != null){                    // Se lee cada linea del archivo
            palabrasSeparadas = linea.split(delimitadoresPalabras); 
            double densidad = Double.parseDouble(palabrasSeparadas[1]);
            agregaProducto(palabrasSeparadas[0], densidad);                     // Se crea el producto en la lista de productos
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
    
    public double getDensidad(String producto){                                 // Metodo encargado de retornar la densidad de un producto especifico
        double densidad = 0;
        if(existeProducto(producto)){
            for(int iterador = 0; iterador < listaProductos.size(); ++iterador){
                if(listaProductos.get(iterador).producto.equals(producto)){
                    densidad = listaProductos.get(iterador).unidadDensidad;
                    iterador = listaProductos.size();
                }
            }
        }         
        return densidad;
    }
    
    public String getProductos(){                                               // Metodo encargado de retornar todos los productos en forma de string
        String productos = "";
        for(int iterador = 0; iterador < listaProductos.size(); ++iterador){
            productos += listaProductos.get(iterador).producto + " ";                
        }
        return productos;
    }    
}
