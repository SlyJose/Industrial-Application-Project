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
    
    int numFincas = 253 ;
    
    matrizObjetos matriz[][];                                                   // Se declara la matriz de objetos
   

   static class matrizObjetos{
       
       double tiempoM;
       double distanciaM;
       double numEntrega;
   
   }
   
     public void seteaMatriz(){
         
         matriz = new matrizObjetos [numFincas][numFincas];                     // Una vez obtenida la cantidad de fincas, se inicializa la matriz de objetos
   
       for(int i=0; i < matriz.length; i++)
           
           for (int j=0; j < matriz.length; j++)
               
               matriz [i][j] = new matrizObjetos();      
       
       llenaMatrizTiempos();
       llenaMatrizDistancia();
       llenaNumEntregas();
   }
   
   
   public double retornaTiempo(double fincaA, double fincaB){
       
       int indiceA = 0;
       int indiceB = 0;
       
       if(fincaA == 0.0){
           fincaA = 1.0;
       }
       
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
        
        File file_ = new File("Tiempos.txt"); 
        String fileName = file_.getAbsolutePath();
                                                                                // Se evita leer la primera linea del archivo de texto
        
        try {                                                                   // Se abre el archivo de asociados
         
         int contador = 0;                                                      // Arranca de la fila 1, la fila 0 contiene numeros de entrega
         archivo = new File (fileName);
         lector = new FileReader(archivo);
         lectorLinea = new BufferedReader(lector);
          
         String linea;
         String delimitadoresPalabras = ",";                                    // Variable utilizada para separar los valores en el archivo por cada linea
         String[] palabrasSeparadas;
         
         
         while( ( linea = lectorLinea.readLine() ) != null){                    // Se lee cada linea del archivo
                palabrasSeparadas = linea.split(delimitadoresPalabras);  
            
              
                    for (int i = 0; i < numFincas; i++){                        
                        //try{
                            
                            //matriz [contador][i] = new matrizObjetos();
                          
                            double campo = (Double.parseDouble(palabrasSeparadas[i]));
                            matriz [contador][i].tiempoM = campo;
                          //  }  
                       
                       //catch(NumberFormatException ex){ // handle your exception  
                        //   System.out.println(" Not a number ");

                        //}
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
   
   public void llenaMatrizDistancia(){                                               // Metodo encargado de cargar el txt de distancias al programa
        File archivo = null;
        FileReader lector = null;
        BufferedReader lectorLinea = null;                                         // Se evita leer la primera linea del archivo de texto
        
        File file_ = new File("Distancias.txt"); 
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
            
                    for (int i = 0; i < numFincas; i++){
                   
                       //matriz [contador][i] = new matrizObjetos();
                       
                       //try{
                          double campo = (Double.parseDouble(palabrasSeparadas[i]));
                            matriz [contador][i].distanciaM = campo;
                            //System.out.print(campo +  "  ");
                            //}  
                       
                       //catch(NumberFormatException ex){ // handle your exception
                            
                          // System.out.println(" Not a number ");

                        //}
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
   
   public void llenaNumEntregas(){                                              //metodo que llena el campo numEntrega en la fila 0 y columna 0
   
       int semilla = 0;
      
       for (int i =0; i < matriz.length; i++){           
           matriz[semilla][i].numEntrega = matriz[semilla][i].distanciaM;
           matriz[i][semilla].numEntrega = matriz[i][semilla].distanciaM;           
       }
   }
}
