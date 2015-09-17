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
import java.io.Writer;                                                          // Biblioteca para la escritura de archivos
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


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
    LlenaMatriz llena = new LlenaMatriz();                                                    
        
        static class matrizObjetos{
       
            double tiempoM;
            double distanciaM;
            double numEntrega;
            boolean visitado = false;
        }
    
        static class NodoPedido{                                                // Clase que define cada nodo
             
            int numPedido;
            String nomSocio;
            double numEntrega;
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
            double numEntrega;
            double precioFlete;
            double montoFlete;
            int placaCamion;
            String proveedor;     
            
            double costoDist;                                                   // Estas dos variables son utilizadas en la lista final para indicar el costo de una ruta en tiempo y distancia
            double costoTiemp;
            
        }
    
    public void cargarBaseDatos(){                                              // Se encarga de que las instancias insasociado, insproducto, inscamion, carguen de los archivos todos los datos al sistema
        insAsociado.cargarArchivo();        
        insCamion.cargarArchivo();
        insProducto.cargarArchivo(); 
        llena.seteaMatriz();
        //llena.llenaMatrizDistancia(); 
        //llena.llenaMatrizTiempos();
        //llena.llenaNumEntregas();
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
        double numEntregaAnterior;
        double numEntregaPedido;
        double tiempoEntreFincas;
        int cantPedidos = 0;
        
        for(int i = 1; i < 9; ++i){                                             // Itera sobre las zonas para obtener los pedidos en cada una                  
            
            for(int j = 0; j < listaPedidos.size(); ++j){   
                
                if( insAsociado.getZona(listaPedidos.get(j).numEntrega) == i ){ // Obtiene todos los pedidos de la zona actual y los inserta en la subLista de pedidos                    
                    subListaPedidos.add(listaPedidos.get(j));                    
                }
            }
            
            cantPedidos = subListaPedidos.size();
            
            for(int m = 0; cantPedidos > 0; ++m){
                
                System.out.println("Doy vueltas");
                
                tamSubListaTemp = subListaPedidos.size();
                
                if(tamSubListaTemp == 1){
                    pedidoAescoger = 0;
                }else{
                    pedidoAescoger = rand.nextInt( (tamSubListaTemp - 1) - 0 + 1) + 0;                    
                }
                
                preferenciaActual = insAsociado.getPreferencia(subListaPedidos.get(pedidoAescoger).numEntrega);                    

                for(int k = 0; k < insCamion.listaCamiones.size(); ++k){                // Se encarga de escoger un camion que cumpla los requisitos de dicho pedido

                    //System.out.println("Numero de pedido: "+subListaPedidos.get(pedidoAescoger).numEntrega);
                    if( insCamion.listaCamiones.get(k).proveedor.equals(preferenciaActual)  || preferenciaActual.equals("no hay preferencia")){    // Se verifica que el camion sea de la preferencia requerida por el pedido
                        
                        //System.out.println("Soy finca "+subListaPedidos.get(k).numEntrega+" y pase preferencia");
                        
                        if(insCamion.getCantidadEspacioLibre(insCamion.listaCamiones.get(k).placa) >= subListaPedidos.get(pedidoAescoger).cantKg){      // Se verifica si la cantidad de producto cabe en el camion escogido
                            
                            //System.out.println("Soy finca "+subListaPedidos.get(k).numEntrega+" y pase cantidad");
                            
                            numEntregaAnterior = insCamion.listaCamiones.get(k).numEntregaPedidoAnterior;
                            numEntregaPedido = subListaPedidos.get(pedidoAescoger).numEntrega;
                            tiempoEntreFincas = obtenerTiempoEntreFincas(numEntregaAnterior, numEntregaPedido);
                            
                            
                            if(insCamion.listaCamiones.get(k).diponibilidadTiempo >= tiempoEntreFincas){                    // Se verifica si el camion tiene disponibilidad de tiempo
                                
                                 //System.out.println("Soy finca "+subListaPedidos.get(k).numEntrega+" y pase tiempo");
                                
                                //System.out.println("Se metio al camion: "+insCamion.listaCamiones.get(k).placa);
                                
                                insCamion.listaCamiones.get(k).diponibilidadTiempo -= tiempoEntreFincas;                    // Se reduce la cantidad de tiempo disponible en el camion
                                insCamion.listaCamiones.get(k).numEntregaPedidoAnterior = numEntregaPedido;                 // El ultimo pedido agregado al camion se modifica
                                
                                insCamion.agregarProducto(subListaPedidos.get(pedidoAescoger).producto, subListaPedidos.get(pedidoAescoger).cantKg, insCamion.listaCamiones.get(k).placa);
                                
                                /* El siguiente segmento se encarga de colocar la ruta recien creada, dentro de la lista
                                    de rutas, incluyendo diferentes aspectos requeridos, faltando los elementos:                                    
                                        - hora de entrega
                                */


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
                                nuevaRuta.numRuta = insCamion.listaCamiones.get(k).placa;                   // Declarado por placa como identificador de ruta
                                nuevaRuta.socio = subListaPedidos.get(pedidoAescoger).nomSocio;
                                nuevaRuta.producto = subListaPedidos.get(pedidoAescoger).producto;
                                nuevaRuta.kgAentregar = subListaPedidos.get(pedidoAescoger).cantKg;
                                nuevaRuta.horaSalida = "";
                                nuevaRuta.zona = insAsociado.getZona(subListaPedidos.get(pedidoAescoger).numEntrega);
                                nuevaRuta.numEntrega = subListaPedidos.get(pedidoAescoger).numEntrega;
                                nuevaRuta.placaCamion = insCamion.listaCamiones.get(k).placa;
                                nuevaRuta.proveedor = insCamion.listaCamiones.get(k).proveedor;
                                nuevaRuta.precioFlete = insAsociado.getCostoD(subListaPedidos.get(pedidoAescoger).numEntrega);
                                nuevaRuta.montoFlete = nuevaRuta.precioFlete * nuevaRuta.kgAentregar;

                                listaRutas.add(nuevaRuta);
                                
                                subListaPedidos.remove(pedidoAescoger);                 // El pedido se elimina de la sublista, ya fue procesado                        
                                k = insCamion.listaCamiones.size();
                                --cantPedidos;
                                
                                //System.out.println("Se agrega el pedido a un camion");
                                
                            }else{
                                auxSubListaPedidos.add(subListaPedidos.get(pedidoAescoger));        // Agrega el pedido que no cabe en el camion actual a una lista auxiliar, para que pase a la siguiente zona
                                subListaPedidos.remove(pedidoAescoger);
                                k = insCamion.listaCamiones.size();
                                --cantPedidos;
                                //System.out.println("Se agrega el pedido a una sublista");
                                //System.out.println("Tamaño lista auxiliar: "+auxSubListaPedidos.size());
                            }           
                    }
                }            
            }           
        }
            
            if (i == 8 && auxSubListaPedidos.size() != 0) {                        // Si quedaron pedidos sin tramitar, se incluyen en camiones libre a fuerza por medio del metodo cargarAfuerza()                
                
                for(int l = 0; l < auxSubListaPedidos.size(); l++){      
                    subListaPedidos.add(auxSubListaPedidos.get(l));
                    //System.out.println("Numero del pedido en aux: "+auxSubListaPedidos.get(l).numEntrega);
                }
                
                //System.out.println("Tamaño lista auxiliar antes: "+auxSubListaPedidos.size());
                
                auxSubListaPedidos.clear();
                
                //System.out.println("Entre a cargar a fuerza");
                //System.out.println("Tamaño lista auxiliar: "+auxSubListaPedidos.size());
                
                cargarAfuerza();
            } 
            
            subListaPedidos.clear();
    }

       /** Una vez obtenidas todas las rutas de todas las zonas, se busca
        *  order estas y analizar si el conjunto de rutas tiene un buen costo
        */     

    evaluarCostos();                                                         // Al finalizar todas las rutas, se evalua si el nuevo conjunto de rutas obtenido es mas eficiente que el anterior obtenido
    limpiarOrdenes();                                                        // Se limpian las listas para una nueva busqueda de rutas     
        
}
    
public double obtenerTiempoEntreFincas(double numEntCamion, double numEntPedido){     // Metodo encargado de recibir dos fincas de asociados y obtener el tiempo entre ellas
    
    double tiempo = 0.0;
    int indice1 = 0;
    int indice2 = 0;
    if(numEntCamion == 0.0){                                                      // No se ha insertado ninguna orden en el camion
        
        for(int i = 0; i < 253; ++i){
            if(llena.matriz[0][i].tiempoM == numEntPedido){                     // Busca la distancia de la finca del pedido al Coyol
                tiempo = llena.matriz[252][i].tiempoM;
                i = 253;
            }
        }
        
    }else{
        
        for(int i = 0; i < 253; i++){
            if(llena.matriz[0][i].tiempoM == numEntCamion){
                indice1 = i;
                i = 253;
            }
        }
        for(int i = 0; i < 253; ++i){
            if(llena.matriz[i][0].tiempoM == numEntPedido){
                indice1 = i;
                i = 253;
            }
        }
        tiempo = llena.matriz[indice1][indice2].tiempoM;        
    }
    return tiempo;
}    
    
public void cargarAfuerza(){                                                    // Método encargado de revisar la sublista de pedidos y procesar los restantes que no hayan sido procesados
    
    double numEntregaAnterior;
    double numEntregaPedido;
    double tiempoEntreFincas;
    
    //System.out.println("Tamaño inicial de la sublista de pedidos: "+subListaPedidos.size());
    
    for (int i = 0; i < subListaPedidos.size(); i++){                           // Se itera sobre la sublista de pedidos
        
        
        
       String preferenciaActual = insAsociado.getPreferencia(subListaPedidos.get(i).numEntrega);                    
            
        for (int j= 0; j < insCamion.listaCamiones.size(); ++j ) {              // Se escoge un camion que cumpla la preferencia y el espacio libre disponible
            
            if( insCamion.listaCamiones.get(j).proveedor == preferenciaActual || preferenciaActual.equals("no hay preferencia") ){    
                
                if(insCamion.getCantidadEspacioLibre(insCamion.listaCamiones.get(j).placa) >= subListaPedidos.get(i).cantKg){      // Se verifica si la cantidad de producto cabe en el camion escogido

                            numEntregaAnterior = insCamion.listaCamiones.get(j).numEntregaPedidoAnterior;
                            numEntregaPedido = subListaPedidos.get(i).numEntrega;
                            tiempoEntreFincas = obtenerTiempoEntreFincas(numEntregaAnterior, numEntregaPedido);
                            
                            if(insCamion.listaCamiones.get(j).diponibilidadTiempo >= tiempoEntreFincas){                    // Se verifica si el camion tiene disponibilidad de tiempo
                                
                                insCamion.listaCamiones.get(j).diponibilidadTiempo -= tiempoEntreFincas;                    // Se reduce la cantidad de tiempo disponible en el camion
                                insCamion.listaCamiones.get(j).numEntregaPedidoAnterior = numEntregaPedido;                 // El ultimo pedido agregado al camion se modifica
                                
                                insCamion.agregarProducto(subListaPedidos.get(i).producto, subListaPedidos.get(i).cantKg, insCamion.listaCamiones.get(j).placa);

                                //System.out.println("El aux se metio en el camion: "+insCamion.listaCamiones.get(j).placa);
                                
                                /* El siguiente segmento se encarga de colocar la ruta recien creada, dentro de la lista
                                    de rutas, incluyendo diferentes aspectos requeridos, faltando los elementos:                                    
                                        - hora de entrega
                                */


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
                                nuevaRuta.numRuta = insCamion.listaCamiones.get(j).placa;                   // Declarado por placa como identificador de ruta
                                nuevaRuta.socio = subListaPedidos.get(i).nomSocio;
                                nuevaRuta.producto = subListaPedidos.get(i).producto;
                                nuevaRuta.kgAentregar = subListaPedidos.get(i).cantKg;
                                nuevaRuta.horaSalida = "";
                                nuevaRuta.zona = insAsociado.getZona(subListaPedidos.get(i).numEntrega);
                                nuevaRuta.numEntrega = subListaPedidos.get(i).numEntrega;
                                nuevaRuta.placaCamion = insCamion.listaCamiones.get(j).placa;
                                nuevaRuta.proveedor = insCamion.listaCamiones.get(j).proveedor;
                                nuevaRuta.precioFlete = insAsociado.getCostoD(subListaPedidos.get(i).numEntrega);
                                nuevaRuta.montoFlete = nuevaRuta.precioFlete * nuevaRuta.kgAentregar;


                                listaRutas.add(nuevaRuta);

                                subListaPedidos.remove(i);                 // El pedido se elimina de la sublista, ya fue procesado                                                           // El pedido se elimina de la sublista, ya fue procesado                                        
                                j = insCamion.listaCamiones.size();                          // Se detiene la busqueda de camiones para este pedido             
                            }                    
                }
            }
        }
    }
    
    //System.out.println("Tamaño final de la sublista de pedidos: "+subListaPedidos.size());
    
}

    
    public void evaluarCostos(){                                                // Evalua el costo de las rutas actuales obtenidas con respecto a otras obtenidas anteriormente
        
        ArrayList<NodoRuta> subListaRutas = new ArrayList<NodoRuta>();          //crear lista de subrutas para identificarlas con el mismo numero de placa
        //ArrayList<NodoRuta> listaFinalPedidos = new ArrayList<NodoRuta>();    
        NodoRuta [] listaFinalPedidos = new NodoRuta[listaRutas.size()];        // Contiene el conjunto de rutas finales que todos los camiones deben tomar, en orden
        
        double distMinima = 999999999999.0;                                     // Finca con la menor distancia a la finca anterior
        double tiempMinimo = 0.0;
        int ultimoIndice = 0;                                                   // Numero de finca en el que el camion se encuentra en el momento
        int espejoIndice = 0;                                                   // Indice paralelo el actual indica la siguiente finca a la que se debe ir
        boolean tienePedido = false;                                            // Variable bandera que identifica si tiene o no pedidos el camion
        int indiceVectorFinal = 0;                                              // Indice estatico que corre sobre la listaFinalPedidos
        
        for(int l = 0; l < listaFinalPedidos.length; ++l){
                listaFinalPedidos[l] = new NodoRuta();
        }
        
        for (int i = 0; i < insCamion.listaCamiones.size(); i++){               // Itera por la lista de camiones ordenando las rutas del camion
         
            subListaRutas.clear();
            
            for (int iterador = 0; iterador < listaRutas.size(); iterador++) {  // Buscamos en la lista de rutas, todas las pertenecientes a un camion especifico
                
                if (insCamion.listaCamiones.get(i).placa == listaRutas.get(iterador).placaCamion){
                
                    subListaRutas.add(listaRutas.get(iterador));                // Contiene las rutas de un solo camion
                    tienePedido = true;                
                }
            }
            
            for(int o = 0; o  < subListaRutas.size(); ++o){
                    System.out.println("En subListaRutas: "+subListaRutas.get(o).numEntrega);
            }
            
            //System.out.println("Tamaño de sublista: "+subListaRutas.size());
            
            if(tienePedido){                                                    // Ya que el camion si tiene pedidos, se ordenan sus rutas
                
                matrizObjetos [][]subMatriz  = new matrizObjetos [subListaRutas.size()+ 2][subListaRutas.size()+ 2];            // Matriz que almacena los pedidos del camion, mas el cero mas el Coyol
                
                double temp2;
                
            for (int j = 1; j < subListaRutas.size() + 1; j++){                 // Llena la matriz con sus primeras filas y columnas de numeros de entrega
                    
                    subMatriz[0][j] = new matrizObjetos();    
                    subMatriz[j][0] = new matrizObjetos();
                    subMatriz[0][j].numEntrega = subListaRutas.get(j - 1).numEntrega;
                    subMatriz[j][0].numEntrega = subListaRutas.get(j - 1).numEntrega;                    
                    subMatriz[0][j].visitado = false;
                    subMatriz[j][0].visitado = false;
            }
            
             
            
            //subListaRutas.size() + 1
            
            
            subMatriz[0][subMatriz.length-1] = new matrizObjetos();
            
            subMatriz[subMatriz.length-1][0] = new matrizObjetos();
            
            subMatriz[0][subMatriz.length-1].numEntrega = llena.matriz[0][llena.numFincas - 1].numEntrega;        // Se coloca el numero de entrega del Coyol en la ultima casilla de la primera fila y columna
            
            subMatriz[subMatriz.length-1][0].numEntrega = llena.matriz[llena.numFincas - 1][0].numEntrega;
            
            for(int h = 1; h < subMatriz.length; ++h){                          // Evita que se vuelva a considerar la ruta al Coyol, en la ultima fila de la matriz 
                
                subMatriz[subListaRutas.size()+ 1][h] = new matrizObjetos();
                
                subMatriz[subListaRutas.size()+ 1][h].visitado = true;
                
            }
            
                for (int k=1; k < subMatriz.length; ++k){                       // Se llena el resto de casillas de la submatriz, con las distancias y los tiempos provenientes de la matriz grande Llena 
                    for (int l = 1; l < subMatriz.length; ++l ) {
                            
                            subMatriz[k][l] = new matrizObjetos(); 
                            subMatriz[k][l].distanciaM = llena.retornaDistancia(subMatriz[0][l].numEntrega, subMatriz[k][0].numEntrega);                            
                            subMatriz[k][l].tiempoM = llena.retornaTiempo(subMatriz[0][l].numEntrega, subMatriz[k][0].numEntrega);
                            
                            /**
                            System.out.println("Fincas: "+subMatriz[0][l].numEntrega+", "+subMatriz[k][0].numEntrega);
                            System.out.println("dist retornado: "+llena.retornaDistancia(subMatriz[0][l].numEntrega, subMatriz[k][0].numEntrega));
                            System.out.println("tiempo retornado: "+llena.retornaTiempo(subMatriz[0][l].numEntrega, subMatriz[k][0].numEntrega)); **/
                            
                    }
                } 
                
                subMatriz[0][0]= new matrizObjetos();
                
                
                for(int q = 0; q < subMatriz.length;++q){
                    for(int u = 0; u < subMatriz.length; ++u){
                        System.out.print(subMatriz[q][u].numEntrega+" ");
                    }
                    System.out.println();
                }
                    System.out.println();
                        System.out.println();
                for(int q = 0; q < subMatriz.length;++q){
                    for(int u = 0; u < subMatriz.length; ++u){
                        System.out.print(subMatriz[q][u].distanciaM+" ");
                    }
                    System.out.println();
                }
                    System.out.println();
                        System.out.println();
                for(int q = 0; q < subMatriz.length;++q){
                    for(int u = 0; u < subMatriz.length; ++u){
                        System.out.print(subMatriz[q][u].tiempoM+" ");
                    }
                    System.out.println();
                }
                System.out.println();
                        System.out.println();
                
                
          
                // A continuacion se realiza el ordenamiento de rutas, basado en la subMatriz
                
                ultimoIndice = subMatriz.length - 1;                            // Arranca de la finca el Coyol por su numero de entrega
                //boolean agarraAlguien = false;
                
                
                for(int m = 0; m < subMatriz.length - 2; ++m){                  // Cantidad de iteraciones totales a realizar para ordenar las rutas ( cantidad de fincas)
                    
                    for(int fil = 0; fil < subMatriz.length; ++fil ){           // Busca la distancia minima en una columna, que es la finca actual en donde se encuentra el camion, asi encuentra la siguiente finca a la cual ir 
                        
                        if(subMatriz[fil][ultimoIndice].distanciaM < distMinima && subMatriz[fil][ultimoIndice].distanciaM != 0.0 && subMatriz[fil][ultimoIndice].visitado == false){
                            
                            distMinima = subMatriz[fil][ultimoIndice].distanciaM;
                            tiempMinimo = subMatriz[fil][ultimoIndice].tiempoM;
                            espejoIndice = fil;                                 // Se almacena el indice de la siguiente finca
                        }
                        subMatriz[fil][ultimoIndice].visitado = true; 
                        subMatriz[ultimoIndice][fil].visitado = true; 
                    }
                    
                    //System.out.println("Indice del espejo: "+espejoIndice);
                    
                    
                    for(int g = 0; g < subListaRutas.size(); ++g){              // Busca la finca siguiente en la subLista de rutas, la agrega a la lista final y la remueve
                        
                        if(subListaRutas.get(g).numEntrega == subMatriz[espejoIndice][0].numEntrega ){
                            
                            listaFinalPedidos[indiceVectorFinal] = subListaRutas.get(g);                // Se agrega la ruta a su respectiva posicion
                            listaFinalPedidos[indiceVectorFinal].costoDist = distMinima;                // Variables utilizadas para obtener el costo de la ruta
                            listaFinalPedidos[indiceVectorFinal].costoTiemp = tiempMinimo;
                            ++indiceVectorFinal;                            
                            subListaRutas.remove(g);
                            g = subListaRutas.size();
                        }
                    }
                    
                    ultimoIndice = espejoIndice;                                // La finca siguiente a visitar, se convierte en la actual
                    
                    distMinima = 999999999999.0;                    
                }
            } // fin de if tienePedido
            
            
            
            tienePedido = false;                                                // Ya que se procede a ordenar las rutas de otro camion, se reinicia la variable
            
        }  // fin de for iteraCamiones
        
                
        
        /** Una vez ordenadas todas las rutas por camion, se verifica si este conjunto de 
         
            rutas tiene un mejor costo que rutas almacenadas anteriormente **/
        
        double costoTotalRutas = 0.0;                                                // Indicador del costo total de todas las rutas
       
        for(int i = 0; i < listaFinalPedidos.length; ++i){                      // Itera por el set de rutas completo obteniendo su costo
            
            costoTotalRutas += (listaFinalPedidos[i].costoDist * 0.70) + (listaFinalPedidos[i].costoTiemp * 0.30);          // Se promedia el costo de la ruta
            
        }
        
        System.out.println("Costo de las rutas: "+costoTotalRutas);
        
        
        int placaAnterior = listaFinalPedidos[0].placaCamion;
        int numRuta = 1;
        
        for(int w = 0; w < listaFinalPedidos.length; ++w){
            if(listaFinalPedidos[w].placaCamion == placaAnterior){
                listaFinalPedidos[w].numRuta = numRuta;                
            }else{
                ++numRuta;
                placaAnterior = listaFinalPedidos[w].placaCamion;
                listaFinalPedidos[w].numRuta = numRuta;                
            }
            
        }
        
        File archivo = null;                                                    // Se procede con la revision del archivo
        FileReader lector = null;
        BufferedReader lectorLinea = null;
        
        File file_ = new File("Rutas Finales.txt"); 
       String fileName = file_.getAbsolutePath();
        
        try {                                                                   // Se abre el archivo de rutas finales
         archivo = new File (fileName);
         lector = new FileReader(archivo);
         lectorLinea = new BufferedReader(lector);
          
         String linea;
         double comparador = 0.0;
         String temp = "";
         
         if( ( linea = lectorLinea.readLine() ) != null ){                      // Se lee solo la primera linea
             
             for(int i = 0; i < linea.length(); ++i){                           // Se obtiene el costo de las rutas ya almacenadas
                 if(linea.charAt(i) == '|'){
                     i = linea.length();
                 }else{
                     temp += linea.charAt(i);
                 }
              }
             
             comparador = Double.parseDouble(temp);
                 
             if(costoTotalRutas < comparador || comparador == 0.0){             // Las rutas actuales tienen un mejor costo, se escriben en el archivo
                     
                     // Borrar el archivo y escribir las nuevas rutas
                 
                 file_.delete();                                                // El archivo de rutas se borra
                 
                 FileWriter fw = null;                                          // Se crea un nuevo archivo para almacenar las nuevas rutas finales
                 try { 
                    fw = new FileWriter(fileName);                              // La extension de la ruta es la misma que tenia el archivo de rutas borrado
            
                 } 
                    catch (IOException ex) { 
                 }
                 
                 File nuevoArchivo = new File("Rutas Finales.txt");             // Se abre el archivo para su corespondiente escritura
                 String filename = nuevoArchivo.getAbsolutePath();
                 
                    try

                    {

                     FileWriter writer = new FileWriter(filename,true);

                     writer.append(""+costoTotalRutas+"|");                         // Lo primero escrito es el costo de dichas rutas
                     writer.write(System.lineSeparator());
                     writer.write(System.lineSeparator());
                     writer.append("Mes    Dia    # de Ruta    Socio            Producto            KGS A Entregar    Hora Salida del Coyol    Lugar    # Entrega    Precio Flete    Monto Flete    Placa del Camion    Proveedor");
                     writer.write(System.lineSeparator());                     
                     
                     for(int i = 0; i < listaFinalPedidos.length; ++i){         // Se escriben todas las rutas en orden
                         writer.append(""+listaFinalPedidos[i].mes+"    "+listaFinalPedidos[i].dia+"    "+listaFinalPedidos[i].numRuta+"    "+listaFinalPedidos[i].socio+
                                        "            "+listaFinalPedidos[i].producto+"            "+listaFinalPedidos[i].kgAentregar+"    "+
                                        listaFinalPedidos[i].horaSalida+"    "+listaFinalPedidos[i].zona+"    "+listaFinalPedidos[i].numEntrega+
                                        "    "+listaFinalPedidos[i].precioFlete+"    "+listaFinalPedidos[i].montoFlete+"    "+listaFinalPedidos[i].placaCamion
                                        +"    "+listaFinalPedidos[i].proveedor);
                         writer.write(System.lineSeparator());
                     }                     
                     writer.close(); 
                    }

                    catch(IOException e)

                    {
                 e.printStackTrace();

                    }
             }             
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
        
        subListaRutas.clear();   // Se libera memoria
 }  
    
    public void limpiarOrdenes(){                                               // Se limpian todas las listas para una nueva ejecucion de algoritmo optimizador
        
        listaPedidos.clear();
        listaRutas.clear();
        subListaPedidos.clear();
        auxSubListaPedidos.clear();
        int campos;
        int placa;
        for(int i = 0; i < insCamion.listaCamiones.size(); ++i){                // Se recorre la lista de camiones para reiniciar los valores de los compartimentos
            placa = insCamion.listaCamiones.get(i).placa;
            campos = insCamion.getCampos(placa);
            for(int j = 0; j < campos; ++j){                                    // Reinicia los valores de los compartimentos en el camion
                insCamion.listaCamiones.get(i).carretaEspacioLibre[j] = insCamion.listaCamiones.get(i).capacidadKg;
                insCamion.listaCamiones.get(i).carretaProductos[j] = "vacio";
                insCamion.listaCamiones.get(i).diponibilidadTiempo = 11.0;
                insCamion.listaCamiones.get(i).numEntregaPedidoAnterior = 0;
            }
        }
    }
    
    public void cargarPedidos(){
        
        File archivo = null;
        FileReader lector = null;
        BufferedReader lectorLinea = null;
    
       File file_ = new File("Orden.txt"); 
       String fileName = file_.getAbsolutePath();
       
       try {                                                                   // 
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
            double numEntrega = Double.parseDouble(palabrasSeparadas[2]);
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
