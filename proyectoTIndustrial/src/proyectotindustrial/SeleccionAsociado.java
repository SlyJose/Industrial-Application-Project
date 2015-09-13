/*
 * Clase encargada de manejar un listado de todos los asociados y sus principales
    atributos, esto a partir de un archivo de texto con todos los datos. 

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
public class SeleccionAsociado {
    
    static class NodoLista{                                                     // Clase que define cada nodo
        
        int numeroEntrega;                                                      // Numero identificador de la finca
        int zona;                                                               // Numero de la zona 1-8
        int capacidad;                                                          // Capacidad de instalacion de la finca
        //int ventanaH;                                                           // Ventana de tiempo de entrega, horas
        //int ventanaM;                                                           // Ventana de tiempo de entrega, minutos
        double costoDistribucion;                                               // Costo de distribucion por kilogramo
        //double kmBuenos;                                                        // Kilometros buenos al Coyol
        //double kmMalos;                                                         // Kilometros malos al Coyol
        String preferencia;                                                     // Preferencia de transporte, si el asociado no posee preferencia, se inicializa como (No disponible)
    }
    
    public ArrayList<NodoLista> listaAsociados = new ArrayList<NodoLista>();    // Lista de objetos asociados
    
    public boolean existeAsociado(int numeroEntrega){                           // Metodo encargado de verificar si se encuentra en la lista el asociado
        boolean existe = false;
        for(int iterador = 0; iterador < listaAsociados.size(); ++iterador){
            if(listaAsociados.get(iterador).numeroEntrega == numeroEntrega){
                existe = true;
                iterador = listaAsociados.size();
            }
        }
        return existe;    
    }
    
    public void agregarAsociado(int numeroEntrega, int zona, int capacidad, double costoDistribucion, String preferencia){  // Metodo encargado de agregar un asociado a la lista de asociados
        NodoLista nuevoNodo = new NodoLista();
        nuevoNodo.numeroEntrega = numeroEntrega;
        nuevoNodo.zona = zona;
        nuevoNodo.capacidad = capacidad;
        //nuevoNodo.ventanaH = ventanaH;
        //nuevoNodo.ventanaM = ventanaM;
        nuevoNodo.costoDistribucion = costoDistribucion;
        //nuevoNodo.kmBuenos = kmBuenos;
        //nuevoNodo.kmMalos = kmMalos;
        nuevoNodo.preferencia = preferencia;
        listaAsociados.add(nuevoNodo);    
    }
    
    public void cargarArchivo(){                                               // Metodo encargado de cargar el txt de productos al programa
        File archivo = null;
        FileReader lector = null;
        BufferedReader lectorLinea = null;
        
        File file_ = new File("Asociados.txt"); 
       String fileName = file_.getAbsolutePath();
        
        try {                                                                   // Se abre el archivo de asociados
         archivo = new File (fileName);
         lector = new FileReader(archivo);
         lectorLinea = new BufferedReader(lector);
          
         String linea;
         String delimitadoresPalabras = ",";                                    // Variable utilizada para separar los valores en el archivo por cada linea
         String[] palabrasSeparadas;
         while( ( linea = lectorLinea.readLine() ) != null){                    // Se lee cada linea del archivo
            palabrasSeparadas = linea.split(delimitadoresPalabras);  
            
            int tEntrega = Integer.parseInt(palabrasSeparadas[0]);              // Variables temporales para casting 
            int tZona = Integer.parseInt(palabrasSeparadas[1]);
            int tCapacidad = Integer.parseInt(palabrasSeparadas[2]);
            //int tVenH = Integer.parseInt(palabrasSeparadas[3]);
            //int tVenM = Integer.parseInt(palabrasSeparadas[4]);
            double tCosto = Double.parseDouble(palabrasSeparadas[3]);
            //double tKmB = Double.parseDouble(palabrasSeparadas[6]);
            //double tKmM = Double.parseDouble(palabrasSeparadas[7]);              
            agregarAsociado(tEntrega, tZona, tCapacidad, tCosto, palabrasSeparadas[4]);                     // Se crea el producto en la lista de productos            
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
    
    public int getZona(int numEntrega){                                         // Métodos get para cada uno de los atributos del Asociado
        int zona = 0;
        if(existeAsociado(numEntrega)){
            for(int iterador = 0; iterador < listaAsociados.size(); ++iterador){
                if(listaAsociados.get(iterador).numeroEntrega == numEntrega){
                    zona = listaAsociados.get(iterador).zona;
                    iterador = listaAsociados.size();
                }
            }
        }         
        return zona;    
    }
    
    public int getCapacidad(int numEntrega){
        int capacidad = 0;
        if(existeAsociado(numEntrega)){
            for(int iterador = 0; iterador < listaAsociados.size(); ++iterador){
                if(listaAsociados.get(iterador).numeroEntrega == numEntrega){
                    capacidad = listaAsociados.get(iterador).capacidad;
                    iterador = listaAsociados.size();
                }
            }
        }         
        return capacidad;  
    
    }
    
    /**public int getVentanaH(int numEntrega){
        int ventanaH = 0;
        if(existeAsociado(numEntrega)){
            for(int iterador = 0; iterador < listaAsociados.size(); ++iterador){
                if(listaAsociados.get(iterador).numeroEntrega == numEntrega){
                    ventanaH = listaAsociados.get(iterador).ventanaH;
                    iterador = listaAsociados.size();
                }
            }
        }         
        return ventanaH;      
    }
    
    public int getVentanaM(int numEntrega){
        int ventanaM = 0;
        if(existeAsociado(numEntrega)){
            for(int iterador = 0; iterador < listaAsociados.size(); ++iterador){
                if(listaAsociados.get(iterador).numeroEntrega == numEntrega){
                    ventanaM = listaAsociados.get(iterador).ventanaM;
                    iterador = listaAsociados.size();
                }
            }
        }         
        return ventanaM;       
    }**/
    
    public double getCostoD(int numEntrega){
        double costo = 0;
        if(existeAsociado(numEntrega)){
            for(int iterador = 0; iterador < listaAsociados.size(); ++iterador){
                if(listaAsociados.get(iterador).numeroEntrega == numEntrega){
                    costo = listaAsociados.get(iterador).costoDistribucion;
                    iterador = listaAsociados.size();
                }
            }
        }         
        return costo;       
    }
    
    /**public double getKmBuenos(int numEntrega){
        double kilometros = 0;
        if(existeAsociado(numEntrega)){
            for(int iterador = 0; iterador < listaAsociados.size(); ++iterador){
                if(listaAsociados.get(iterador).numeroEntrega == numEntrega){
                    kilometros = listaAsociados.get(iterador).kmBuenos;
                    iterador = listaAsociados.size();
                }
            }
        }         
        return kilometros;    
    }
    
    public double getKmMalos(int numEntrega){
        double kilometros = 0;
        if(existeAsociado(numEntrega)){
            for(int iterador = 0; iterador < listaAsociados.size(); ++iterador){
                if(listaAsociados.get(iterador).numeroEntrega == numEntrega){
                    kilometros = listaAsociados.get(iterador).kmMalos;
                    iterador = listaAsociados.size();
                }
            }
        }         
        return kilometros;    
    }**/
    
    public String getPreferencia(int numEntrega){
        String preferencia = "";
        if(existeAsociado(numEntrega)){
            for(int iterador = 0; iterador < listaAsociados.size(); ++iterador){
                if(listaAsociados.get(iterador).numeroEntrega == numEntrega){
                    preferencia = listaAsociados.get(iterador).preferencia;
                    iterador = listaAsociados.size();
                }
            }
        }         
        return preferencia;    
    }
}
