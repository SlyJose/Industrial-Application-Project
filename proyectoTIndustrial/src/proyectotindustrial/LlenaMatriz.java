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
   
   
    int numFincas = 251;
    
    matrizObjetos matriz[][] = new matrizObjetos [numFincas][numFincas];
   

   static class matrizObjetos{
       
       double tiempoM;
       double distanciaM;
       double numEntrega;
   
   }
   
//      public void seteaSub(){
//   
//       for(int i=0; i < subWidth; i++)
//           
//           for (int j=0; j < subHeight; j++)
//               
//               subMatriz [i][j] = new matrizObjetos();
//   
//   }
   
   
   public double retornaTiempo(double fincaA, double fincaB){
       
       int indiceA = 0;
       int indiceB = 0;
       
       for(int i=0; i < matriz.length ; ++i){
           if(matriz[0][i].numEntrega == fincaA ){
               indiceA = i;
           }
       }
       for(int i=0; i < matriz.length ; ++i){
           if(matriz[i][0].numEntrega == fincaB ){
               indiceB = i;
           }
       }
       return matriz[indiceA][indiceB].tiempoM;
   }
   
   public double retornaDistancia(double fincaA, double fincaB){
       
       int indiceA = 0;
       int indiceB = 0;
       
       for(int i=0; i < matriz.length ; ++i){
           if(matriz[0][i].numEntrega == fincaA ){
               indiceA = i;
           }
       }
       for(int i=0; i < matriz.length ; ++i){
           if(matriz[i][0].numEntrega == fincaB ){
               indiceB = i;
           }
       }
       return matriz[indiceA][indiceB].distanciaM;
   }
   
   public void llenaMatrizTiempos(){                                               // Metodo encargado de cargar el txt de tiempos al programa
        File archivo = null;
        FileReader lector = null;
        BufferedReader lectorLinea = null;
        
        File file_ = new File("Prueba4x4.txt"); 
        String fileName = file_.getAbsolutePath();
        
        boolean leoPrimeraLinea = true;                                         // Se evita leer la primera linea del archivo de texto
        
        try {                                                                   // Se abre el archivo de asociados
         
         int contador = 1;                                                      // Arranca de la fila 1, la fila 0 contiene numeros de entrega
         archivo = new File (fileName);
         lector = new FileReader(archivo);
         lectorLinea = new BufferedReader(lector);
          
         String linea;
         String delimitadoresPalabras = ",";                                    // Variable utilizada para separar los valores en el archivo por cada linea
         String[] palabrasSeparadas;
         
         
         while( ( linea = lectorLinea.readLine() ) != null){                    // Se lee cada linea del archivo
                palabrasSeparadas = linea.split(delimitadoresPalabras);  
            
                if(!leoPrimeraLinea){
                    for (int i = 1; i < numFincas; i++){
                   
                       matriz [contador][i] = new matrizObjetos();
                       double campo = (Double.parseDouble(palabrasSeparadas[i]));
                       matriz [contador][i].tiempoM = campo;
                   }
                   contador++;
                }
                leoPrimeraLinea = true;
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
   
   public void llenaMatrizDistancia(){                                               // Metodo encargado de cargar el txt de distancias al programa
        File archivo = null;
        FileReader lector = null;
        BufferedReader lectorLinea = null;
        boolean leoPrimeraLinea = true;                                         // Se evita leer la primera linea del archivo de texto
        
        File file_ = new File("Prueba4x4.txt"); 
        String fileName = file_.getAbsolutePath();
        
        try {                                                                   // Se abre el archivo de asociados
         
         int contador = 1;
         archivo = new File (fileName);
         lector = new FileReader(archivo);
         lectorLinea = new BufferedReader(lector);
          
         String linea;
         String delimitadoresPalabras = ",";                                    // Variable utilizada para separar los valores en el archivo por cada linea
         String[] palabrasSeparadas;
         
         while( ( linea = lectorLinea.readLine() ) != null){                    // Se lee cada linea del archivo
            palabrasSeparadas = linea.split(delimitadoresPalabras);  
            
                if(!leoPrimeraLinea){
                    for (int i = 1; i < numFincas; i++){
                   
                       matriz [contador][i] = new matrizObjetos();
                       double campo = (Double.parseDouble(palabrasSeparadas[i]));
                       matriz [contador][i].distanciaM = campo;
                   }
                   
                   contador++;
                }
                
                leoPrimeraLinea = false;
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
