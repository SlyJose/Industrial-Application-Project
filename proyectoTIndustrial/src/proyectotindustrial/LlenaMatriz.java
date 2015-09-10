/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectotindustrial;

import java.io.FileWriter; //add to import list

import java.io.IOException;

import java.io.Writer;

import java.io.BufferedWriter;

import java.io.*;

/**
 *
 * @author Jose Sly
 */
public class LlenaMatriz {
   
   int height, width;
   int subHeight, subWidth; 
   
   matrizObjetos matriz[][] = new matrizObjetos[width][height];
   matrizObjetos subMatriz[][] = new matrizObjetos[subWidth][subHeight];
   
   matrizObjetos obj [] = new matrizObjetos[1];
//   matrizObjetos obj1 [] = new matrizObjetos[1];
   
   
   static class matrizObjetos{
       
       double tiempoM;
       double distanciaM;
       double numEntrega;
   
   }
   
   
       public int retornaIndices(double numEntrega){                                                //retorna los indices de un numero de entrega
    
        int indice;
        
        for (int i =0; i < matriz.length; i++){
            
            if (matriz[0][i].distanciaM  == numEntrega){
            
                return indice = i;
            }
        }
        return 0;
    }
   
   public void llenaMatrizTiempos(){                                               // Metodo encargado de cargar el txt de productos al programa
        File archivo = null;
        FileReader lector = null;
        BufferedReader lectorLinea = null;
        
        File file_ = new File("Prueba4x4.txt"); 
        String fileName = file_.getAbsolutePath();
        
        try {                                                                   // Se abre el archivo de asociados
         
         int contador = 0;
         archivo = new File (fileName);
         lector = new FileReader(archivo);
         lectorLinea = new BufferedReader(lector);
          
         String linea;
         String delimitadoresPalabras = ",";                                    // Variable utilizada para separar los valores en el archivo por cada linea
         String[] palabrasSeparadas;
         
         
         while( ( linea = lectorLinea.readLine() ) != null){                    // Se lee cada linea del archivo
            palabrasSeparadas = linea.split(delimitadoresPalabras);  
            
   
                   for (int i = 0; i < 251; i++){
                   
                       matriz [contador][i] = new matrizObjetos();
                       double campo = (Double.parseDouble(palabrasSeparadas[i]));
                       matriz [contador][i].tiempoM = campo;
                   }
                   
                   contador++;
                     
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
   
   public void llenaMatrizDistancia(){                                               // Metodo encargado de cargar el txt de productos al programa
        File archivo = null;
        FileReader lector = null;
        BufferedReader lectorLinea = null;
        
        File file_ = new File("Prueba4x4.txt"); 
        String fileName = file_.getAbsolutePath();
        
        try {                                                                   // Se abre el archivo de asociados
         
         int contador = 0;
         archivo = new File (fileName);
         lector = new FileReader(archivo);
         lectorLinea = new BufferedReader(lector);
          
         String linea;
         String delimitadoresPalabras = ",";                                    // Variable utilizada para separar los valores en el archivo por cada linea
         String[] palabrasSeparadas;
         
         while( ( linea = lectorLinea.readLine() ) != null){                    // Se lee cada linea del archivo
            palabrasSeparadas = linea.split(delimitadoresPalabras);  
            
   
                   for (int i = 0; i < 251; i++){
                   
                       matriz [contador][i] = new matrizObjetos();
                       double campo = (Double.parseDouble(palabrasSeparadas[i]));
                       matriz [contador][i].distanciaM = campo;
                   }
                   
                   contador++;
                     
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
