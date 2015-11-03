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
        llena.numFincas = insAsociado.listaAsociados.size() + 2;                    // Se obtiene la cantidad de fincas en el sistema
        
        System.out.println("Numero de fincas: "+llena.numFincas);
        
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
        
        //System.out.println("Cantidad de pedidos: "+listaPedidos.size());
        
        for(int i = 1; i < 9; ++i){                                             // Itera sobre las zonas para obtener los pedidos en cada una                  
            
            for(int j = 0; j < listaPedidos.size(); ++j){   
                
                if( insAsociado.getZona(listaPedidos.get(j).numEntrega) == i ){ // Obtiene todos los pedidos de la zona actual y los inserta en la subLista de pedidos                    
                    subListaPedidos.add(listaPedidos.get(j));                    
                }
            }
            
            //System.out.println("tamano de subpedidos: "+subListaPedidos.size());
            
            cantPedidos = subListaPedidos.size();
            
            for(int m = 0; cantPedidos > 0; ++m){
                
                tamSubListaTemp = subListaPedidos.size();
                
                if(tamSubListaTemp == 1){
                    pedidoAescoger = 0;
                }else{
                    pedidoAescoger = rand.nextInt( (tamSubListaTemp - 1) - 0 + 1) + 0;                    
                }
                
                preferenciaActual = insAsociado.getPreferencia(subListaPedidos.get(pedidoAescoger).numEntrega);                    

                for(int k = 0; k < insCamion.listaCamiones.size(); ++k){                // Se encarga de escoger un camion que cumpla los requisitos de dicho pedido

                    if(insCamion.camionLleno(insCamion.listaCamiones.get(k).placa) == false){            // Si el camion esta lleno, evita utilizarlo
                        
                            //System.out.println("Numero de pedido: "+subListaPedidos.get(pedidoAescoger).numEntrega);
                        if( insCamion.listaCamiones.get(k).proveedor.equals(preferenciaActual)  || preferenciaActual.equals("no hay preferencia")){    // Se verifica que el camion sea de la preferencia requerida por el pedido

                            //System.out.println("Soy finca "+subListaPedidos.get(k).numEntrega+" y pase preferencia");

                            if(insCamion.getCantidadEspacioLibre(insCamion.listaCamiones.get(k).placa) >= subListaPedidos.get(pedidoAescoger).cantKg){      // Se verifica si la cantidad de producto cabe en el camion escogido

                                //System.out.println("Soy finca "+subListaPedidos.get(k).numEntrega+" y pase cantidad");

                                numEntregaAnterior = insCamion.listaCamiones.get(k).numEntregaPedidoAnterior;
                                numEntregaPedido = subListaPedidos.get(pedidoAescoger).numEntrega;
                                tiempoEntreFincas = llena.retornaTiempo(numEntregaAnterior, numEntregaPedido);//obtenerTiempoEntreFincas(numEntregaAnterior, numEntregaPedido);

                                /**System.out.println("Soy finca "+subListaPedidos.get(pedidoAescoger).numEntrega);
                                System.out.println("Camion "+insCamion.listaCamiones.get(k).placa);
                                System.out.println("Le queda de tiempo: "+insCamion.listaCamiones.get(k).diponibilidadTiempo);
                                System.out.println("Ultimo pedido metido "+insCamion.listaCamiones.get(k).numEntregaPedidoAnterior);
                                System.out.println("Pedido a meter "+subListaPedidos.get(pedidoAescoger).numEntrega);
                                System.out.println("Tiempo a tardar: "+tiempoEntreFincas);**/
                                

                                
                                if(insCamion.listaCamiones.get(k).diponibilidadTiempo >= tiempoEntreFincas){                    // Se verifica si el camion tiene disponibilidad de tiempo

                                     //System.out.println("Soy finca "+subListaPedidos.get(k).numEntrega+" y pase tiempo");

                                    //System.out.println("Se metio al camion: "+insCamion.listaCamiones.get(k).placa);

                                    insCamion.listaCamiones.get(k).diponibilidadTiempo -= tiempoEntreFincas;                    // Se reduce la cantidad de tiempo disponible en el camion
                                    insCamion.listaCamiones.get(k).numEntregaPedidoAnterior = numEntregaPedido;                 // El ultimo pedido agregado al camion se modifica


                                    //System.out.println("Entro pedido normal a listaRutas");

                                    //System.out.println("Camion: "+insCamion.listaCamiones.get(k).placa+", Capacidad: "+insCamion.listaCamiones.get(k).capacidad);
                                    
                                    insCamion.agregarProducto(subListaPedidos.get(pedidoAescoger).producto, subListaPedidos.get(pedidoAescoger).cantKg, insCamion.listaCamiones.get(k).placa);

                                    /* El siguiente segmento se encarga de colocar la ruta recien creada, dentro de la lista
                                        de rutas, incluyendo diferentes aspectos requeridos, faltando los elementos:                                    
                                            - hora de entrega
                                    */

                                    /**System.out.println("Camion: "+insCamion.listaCamiones.get(k).placa);
                                    for(int g=0;g<insCamion.listaCamiones.get(k).campos; ++g){
                                        System.out.println("Campo "+g+" "+insCamion.listaCamiones.get(k).carretaProductos[g]);                                        
                                    }**/


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
                                    nuevaRuta.horaSalida = "Coyol";
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

                                    //System.out.println("Se agrega el pedido a la lista");

                                }else{
                                    //System.out.println("No entre por tiempo, soy pedido "+subListaPedidos.get(pedidoAescoger).numEntrega);
                                    /**auxSubListaPedidos.add(subListaPedidos.get(pedidoAescoger));        // Agrega el pedido que no cabe en el camion actual a una lista auxiliar, para que pase a la siguiente zona
                                    
                                    subListaPedidos.remove(pedidoAescoger);
                                    k = insCamion.listaCamiones.size();
                                    --cantPedidos; **/
                                    //System.out.println("No entre por tiempo al camion "+insCamion.listaCamiones.get(k).placa+", soy finca "+subListaPedidos.get(pedidoAescoger).numEntrega);



                                    //System.out.println("Se agrega el pedido a una sublista");
                                    //System.out.println("Tamaño lista auxiliar: "+auxSubListaPedidos.size());
                                }           
                        }else{
                                /**System.out.println("No entre por cantidad al camion "+insCamion.listaCamiones.get(k).placa+", soy finca "+subListaPedidos.get(pedidoAescoger).numEntrega);
                                auxSubListaPedidos.add(subListaPedidos.get(pedidoAescoger));        // Agrega el pedido que no cabe en el camion actual a una lista auxiliar, para que pase a la siguiente zona
                                subListaPedidos.remove(pedidoAescoger);
                                k = insCamion.listaCamiones.size();
                                --cantPedidos;**/
                                //System.out.println("No entre por cantidad al camion "+insCamion.listaCamiones.get(k).placa+", soy finca "+subListaPedidos.get(pedidoAescoger).numEntrega);
                            }
                    }else{
                            /**System.out.println("No entre por preferencia al camion "+insCamion.listaCamiones.get(k).placa+", soy finca "+subListaPedidos.get(pedidoAescoger).numEntrega);
                            auxSubListaPedidos.add(subListaPedidos.get(pedidoAescoger));        // Agrega el pedido que no cabe en el camion actual a una lista auxiliar, para que pase a la siguiente zona
                            subListaPedidos.remove(pedidoAescoger);
                            k = insCamion.listaCamiones.size();
                            --cantPedidos;**/
                            //System.out.println("No entre por preferencia al camion "+insCamion.listaCamiones.get(k).placa+", soy finca "+subListaPedidos.get(pedidoAescoger).numEntrega);
                        }                    
                }else{
                                //System.out.println("Camion: "+insCamion.listaCamiones.get(k).placa+"esta lleno, pedido actual: "+subListaPedidos.get(pedidoAescoger).numEntrega);
                    }       
            } // Fin de la iteracion por los camiones           
        }
            
            /**if (i == 8 && auxSubListaPedidos.size() != 0) {                        // Si quedaron pedidos sin tramitar, se incluyen en camiones libre a fuerza por medio del metodo cargarAfuerza()                
                
                for(int l = 0; l < auxSubListaPedidos.size(); l++){      
                    subListaPedidos.add(auxSubListaPedidos.get(l));
                    //System.out.println("Numero del pedido en aux: "+auxSubListaPedidos.get(l).numEntrega);
                }
                
                //System.out.println("Tamaño lista auxiliar antes: "+auxSubListaPedidos.size());
                
                auxSubListaPedidos.clear();
                
                //System.out.println("Entre a cargar a fuerza, soy pedido "+);
                //System.out.println("Tamaño lista auxiliar: "+auxSubListaPedidos.size());
                
                cargarAfuerza();
            } 
            
            subListaPedidos.clear();**/
    }

       /** Una vez obtenidas todas las rutas de todas las zonas, se busca
        *  order estas y analizar si el conjunto de rutas tiene un buen costo
        */     

    evaluarCostos();                                                         // Al finalizar todas las rutas, se evalua si el nuevo conjunto de rutas obtenido es mas eficiente que el anterior obtenido
    limpiarOrdenes();                                                        // Se limpian las listas para una nueva busqueda de rutas     
        
}
    
public void cargarAfuerza(){                                                    // Método encargado de revisar la sublista de pedidos y procesar los restantes que no hayan sido procesados
    
    double numEntregaAnterior;
    double numEntregaPedido;
    double tiempoEntreFincas;
    
    //System.out.println("Tamaño inicial de la sublista de pedidos: "+subListaPedidos.size());
    
    int cantPedidos = subListaPedidos.size();
    
    for (int i = 0; i < cantPedidos; i++){                           // Se itera sobre la sublista de pedidos
        
       String preferenciaActual = insAsociado.getPreferencia(subListaPedidos.get(i).numEntrega);                    
            
        for (int j= 0; j < insCamion.listaCamiones.size(); ++j ) {              // Se escoge un camion que cumpla la preferencia y el espacio libre disponible
            
            if( insCamion.listaCamiones.get(j).proveedor.equals(preferenciaActual)  || preferenciaActual.equals("no hay preferencia") ){    
                
                if(insCamion.getCantidadEspacioLibre(insCamion.listaCamiones.get(j).placa) >= subListaPedidos.get(i).cantKg){      // Se verifica si la cantidad de producto cabe en el camion escogido

                            numEntregaAnterior = insCamion.listaCamiones.get(j).numEntregaPedidoAnterior;
                            numEntregaPedido = subListaPedidos.get(i).numEntrega;
                            tiempoEntreFincas = llena.retornaTiempo(numEntregaAnterior, numEntregaPedido);
                            
                            if(insCamion.listaCamiones.get(j).diponibilidadTiempo >= tiempoEntreFincas){                    // Se verifica si el camion tiene disponibilidad de tiempo
                                
                                insCamion.listaCamiones.get(j).diponibilidadTiempo -= tiempoEntreFincas;                    // Se reduce la cantidad de tiempo disponible en el camion
                                insCamion.listaCamiones.get(j).numEntregaPedidoAnterior = numEntregaPedido;                 // El ultimo pedido agregado al camion se modifica
                                
                                //System.out.println("Entro pedido por cargar a fuerza a listaRutas");
                                
                                insCamion.agregarProducto(subListaPedidos.get(i).producto, subListaPedidos.get(i).cantKg, insCamion.listaCamiones.get(j).placa);

                                 //System.out.println("Entro pedido por cargar a fuerza a listaRutas");
                                
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
                                nuevaRuta.horaSalida = "Coyol";
                                nuevaRuta.zona = insAsociado.getZona(subListaPedidos.get(i).numEntrega);
                                nuevaRuta.numEntrega = subListaPedidos.get(i).numEntrega;
                                nuevaRuta.placaCamion = insCamion.listaCamiones.get(j).placa;
                                nuevaRuta.proveedor = insCamion.listaCamiones.get(j).proveedor;
                                nuevaRuta.precioFlete = insAsociado.getCostoD(subListaPedidos.get(i).numEntrega);
                                nuevaRuta.montoFlete = nuevaRuta.precioFlete * nuevaRuta.kgAentregar;


                                listaRutas.add(nuevaRuta);

                                //System.out.println("Se agrega el pedido a la lista por aux, pedido: "+subListaPedidos.get(i).numEntrega);
                                
                                //subListaPedidos.remove(i);                 // El pedido se elimina de la sublista, ya fue procesado                                                           // El pedido se elimina de la sublista, ya fue procesado                                        
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
        
        //System.out.println("cantidad de rutas: "+listaRutas.size());
        
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
            
            
            
            //System.out.println("Tamaño de sublista: "+subListaRutas.size());
            
            if(tienePedido){                                                    // Ya que el camion si tiene pedidos, se ordenan sus rutas
                
                
                // Logica de sublistas 
                
                double numEntregaActual = 1.0;

                distMinima = 49999999999999999.0;

                double numEntregaSig = 0.0;
                
                for(int p = 0; p < subListaRutas.size(); ++p){							// Primero busco la mas cerca al coyol
		
                    if(llena.retornaDistancia(numEntregaActual, subListaRutas.get(p).numEntrega ) < distMinima){
			numEntregaSig = subListaRutas.get(p).numEntrega;
			distMinima = llena.retornaDistancia(numEntregaActual, subListaRutas.get(p).numEntrega );
                    }
                }
                
                int flag = 0;                                                   // Indica si es o no la primera corrida de busqueda del Coyol a su siguiente finca
                
                int tamano = subListaRutas.size();
                
                
                while(tamano > 1){															// Hago esto hasta limpiar la lista de todo el camion

                        numEntregaActual = numEntregaSig; 
                        
                        if(flag != 0 /*|| subListaRutas.size() != 1*/){
                            //distMinima = 49999999999999.0;
                            for(int a = 0; a < subListaRutas.size(); ++a){	
                                    if(llena.retornaDistancia(numEntregaActual, subListaRutas.get(a).numEntrega) <   distMinima && numEntregaActual != subListaRutas.get(a).numEntrega){
                                            
                                                numEntregaSig = subListaRutas.get(a).numEntrega;
                                                distMinima = llena.retornaDistancia(numEntregaActual, subListaRutas.get(a).numEntrega);

                                                System.out.println("Entra aqui, fincas: "+numEntregaActual +" "+ subListaRutas.get(a).numEntrega);
                                                System.out.println("Distancia: "+distMinima);                                                
                                            
                                    }/*else{
                                            System.out.println("No entró aqui, fincas: "+numEntregaActual +" "+ subListaRutas.get(a).numEntrega);
                                            System.out.println("Distancia: "+distMinima);
                                    }*/
                            }                            
                        }

                        System.out.println("Tamano de listaFinalPedidos: "+listaFinalPedidos.length);
                        
                        for(int b = 0; b < subListaRutas.size(); ++b){												// borro la finca actual en la que me encuentro
                                if(subListaRutas.get(b).numEntrega == numEntregaActual){
                                    
                                    System.out.println("Valor de indice: "+indiceVectorFinal);
                                    
                                        listaFinalPedidos[indiceVectorFinal] = subListaRutas.get(b);
                                        System.out.println("Estoy metiendo de distancia: "+distMinima);
                                        listaFinalPedidos[indiceVectorFinal].costoDist = distMinima;                // Variables utilizadas para obtener el costo de la ruta
                                        listaFinalPedidos[indiceVectorFinal].costoTiemp = llena.retornaTiempo(numEntregaActual, numEntregaSig);
                                        
                                        if(flag != 0 && subListaRutas.size() > 1 ){                 // El camion tiene mas de un pedido, no debe borrar en la primera iteracion
                                            subListaRutas.remove(b);
                                            --tamano;
                                        }else{
                                            if(flag == 0 && subListaRutas.size() == 1){             // El camion tiene solo un pedido
                                                subListaRutas.remove(b);
                                                --tamano;
                                            }
                                        }                                        
                                        
                                        b = subListaRutas.size();                                        
                                        ++indiceVectorFinal;
                                }                                
                        }    
                        //if(flag == 0){
                            distMinima = 49999999999999.0;
                        //}
                        ++flag;
                        
                        /*if(subListaRutas.size() == 1){
                            tamano = 0;
                        }else{
                            tamano = subListaRutas.size();
                            
                        }*/
                }                
            } // fin de if tienePedido
                        
            tienePedido = false;                                                // Ya que se procede a ordenar las rutas de otro camion, se reinicia la variable
            
        }  // fin de for iteraCamiones
        
               // System.out.println("cant de pedidos: "+listaFinalPedidos.length);
        
        /** Una vez ordenadas todas las rutas por camion, se verifica si este conjunto de 
         
            rutas tiene un mejor costo que rutas almacenadas anteriormente **/
        
        double costoTotalRutas = 0.0;                                                // Indicador del costo total de todas las rutas
       
        for(int i = 0; i < listaFinalPedidos.length; ++i){                      // Itera por el set de rutas completo obteniendo su costo
            
            costoTotalRutas += (listaFinalPedidos[i].costoDist * 0.70) + (listaFinalPedidos[i].costoTiemp * 0.30);          // Se promedia el costo de la ruta
            
        }
        
        //System.out.println("Costo de las rutas: "+costoTotalRutas);
        
        
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

                     writer.append(""+costoTotalRutas+"|"); // Lo primero escrito es el costo de dichas rutas
                     writer.write(System.lineSeparator());
                     writer.write(System.lineSeparator());
                     writer.append("Mes,Dia,#de Ruta,Socio,Producto,KGS A Entregar,Salida del Coyol,Lugar,# Entrega,Precio Flete,Monto Flete,Placa del Camion,Proveedor,Kilómetros");
                     writer.write(System.lineSeparator()); 
                     
                     for(int i = 0; i < listaFinalPedidos.length; ++i){ // Se escriben todas las rutas en orden
                         writer.append(""+listaFinalPedidos[i].mes+","+listaFinalPedidos[i].dia+","+listaFinalPedidos[i].numRuta+","+listaFinalPedidos[i].socio+
                                        ","+listaFinalPedidos[i].producto+","+listaFinalPedidos[i].kgAentregar+","+
                                        listaFinalPedidos[i].horaSalida+","+listaFinalPedidos[i].zona+","+listaFinalPedidos[i].numEntrega+
                                        ","+listaFinalPedidos[i].precioFlete+","+listaFinalPedidos[i].montoFlete+","+listaFinalPedidos[i].placaCamion
                                        +","+listaFinalPedidos[i].proveedor+","+listaFinalPedidos[i].costoDist);
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
            
            //int numPedido = Integer.parseInt(palabrasSeparadas[0]);             // Variables temporales para casting 
            String nomSocio = palabrasSeparadas[0];
            double numEntrega = Integer.parseInt(palabrasSeparadas[1]);
            String producto = palabrasSeparadas[2];
            double cantKg = Double.parseDouble(palabrasSeparadas[3]);
            //String fechaEntrega = palabrasSeparadas[5];
            //String horaEntrega = palabrasSeparadas[6];
            //String codPedido = palabrasSeparadas[7];  
            
            NodoPedido nuevoPedido = new NodoPedido();
            //nuevoPedido.numPedido = numPedido;
            nuevoPedido.nomSocio = nomSocio;
            nuevoPedido.numEntrega = numEntrega;
            nuevoPedido.producto = producto;            
            //nuevoPedido.fechaEntrega = fechaEntrega;
            //nuevoPedido.horaEntrega = horaEntrega;
            //nuevoPedido.codPedido = codPedido;
            
            double temp = insProducto.getFactorRelacion(nuevoPedido.producto);  // Factor de relacion con respecto al Vap Feed Granel de los productos
            
            nuevoPedido.cantKg = cantKg * temp;
            
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
       
       /**ArrayList<NodoPedido> tempLista = new ArrayList<NodoPedido>();             // Lista temporal de pedidos utilizada para ordenar por tamano de pedido
       
       boolean continuar = true;
       double cantidad = 0; 
       int indice = 0;**/
       /**while(continuar){
           for(int j = 0; j < listaPedidos.size(); ++j){
               if(listaPedidos.get(j).cantKg > cantidad){
                   indice = j;
                   cantidad = listaPedidos.get(j).cantKg;
                   //System.out.println("entré");
               }                              
           }
           
           //System.out.println("indice: "+indice);
           //System.out.println("tamano: "+listaPedidos.size());
           
           tempLista.add(listaPedidos.get(indice));
           listaPedidos.remove(indice);
           //System.out.println("saque");
           cantidad = 0;
           indice = 0;
           
           if(listaPedidos.size() == 0){
               continuar = false;
           }
       }**/
       
       /**for(int i = 0; i < tempLista.size(); ++i){                               // Se retornan los valores a la lista de forma ordenada
           listaPedidos.add(tempLista.get(i));
           //System.out.println(""+listaPedidos.get(i).cantKg);
       }**/
    }
}
