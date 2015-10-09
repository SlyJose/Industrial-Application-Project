package proyectotindustrial;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;                                                               // Bibliotecas necesarias para lectura de archivos
import java.util.ArrayList;                                                     // Uso de listas dinamicas para el manejo de productos

/**
 *
 * @author luis diego
 */
public class VenModificarProd extends javax.swing.JFrame {

    /**
     * Creates new form VenModificarProd
     */
    public VenModificarProd() {
        initComponents();
        textoDensidad.setText("------------------------------------------------------------------------------");
    }
    
    static class NodoLista{                                                     // Clase que define cada nodo
         String producto;         
         double unidadDensidad;        
         double factorRelacion;                                                 // Relacion directa con el Vap Feed Granel
    }
    
    String productoAbuscar;                                                     // Variable global utilizada para que ambos botones "buscar" y "aceptar" reconozcan cual producto modificar

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textoDensidad = new javax.swing.JTextField();
        textoProducto = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Aceptar = new javax.swing.JToggleButton();
        Buscar = new javax.swing.JToggleButton();
        jLabel4 = new javax.swing.JLabel();
        mensaje = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(530, 280));
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 2, 48)); // NOI18N
        jLabel1.setText("Modificar Producto");

        jLabel2.setText("Producto");

        jLabel3.setText("Factor de Relación");

        Aceptar.setText("Aceptar");
        Aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarActionPerformed(evt);
            }
        });

        Buscar.setText("Buscar");
        Buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarActionPerformed(evt);
            }
        });

        mensaje.setText(".");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(textoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Buscar)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Aceptar)
                            .addComponent(textoDensidad, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(mensaje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(83, 83, 83))))
            .addGroup(layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 32, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(textoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Buscar))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(textoDensidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(mensaje))
                .addGap(18, 18, 18)
                .addComponent(Aceptar)
                .addContainerGap(58, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarActionPerformed
        // TODO add your handling code here:
        
        String producto = textoProducto.getText();
        productoAbuscar = producto;
        textoDensidad.setVisible(true);
        
        ArrayList<NodoLista> listaProductos = new ArrayList<NodoLista>();    // Lista de objetos producto
        
        // Busca en la lista de productos el producto seleccionado
        
        File archivo = null;                                                    // Se carga el archivo de productos
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
            double factorRelacion = Double.parseDouble(palabrasSeparadas[2]);
        
            NodoLista nuevoNodo = new NodoLista();
            nuevoNodo.factorRelacion = factorRelacion;
            nuevoNodo.unidadDensidad = densidad;
            nuevoNodo.producto = palabrasSeparadas[0];
            
            listaProductos.add(nuevoNodo);
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
        
        // Una vez cargado en la lista de productos todos los productos, se busca el que el usuario desea modificar.
        
        for(int i = 0; i < listaProductos.size(); ++i){
            if(listaProductos.get(i).producto.equals(producto)){                
                mensaje.setText("Producto encontrado");
                
                textoProducto.setText(listaProductos.get(i).producto);
                textoDensidad.setText(""+listaProductos.get(i).factorRelacion);
                i = listaProductos.size();
                
            }else{
                if(i == listaProductos.size() - 1){
                    mensaje.setText("Producto no encontrado");
                    textoDensidad.setText("");
                }
            }                
        }
    }//GEN-LAST:event_BuscarActionPerformed

    private void AceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarActionPerformed

        String producto = textoProducto.getText();
        String nuevaLinea = producto + ",0.0," + textoDensidad.getText();
        
        try {
            File inFile = new File("Productos.txt");

            if (!inFile.isFile()) {
                System.out.println("Parameter is not an existing file");
                return;
            }

        //Construct the new file that will later be renamed to the original filename. 
        File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

        BufferedReader br = new BufferedReader(new FileReader("Productos.txt"));
        PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

         String line = null;
         String delimitadoresPalabras = ",";                                    // Variable utilizada para separar los valores en el archivo por cada linea
         String[] lineas;

        //Read from the original file and write to the new 
        //unless content matches data to be removed.
        while ((line = br.readLine()) != null) {

            
            if( line.trim().split(delimitadoresPalabras)[0].equals(productoAbuscar) ){
                pw.println(nuevaLinea);
                pw.flush();
            }else{
                if (!(line.trim().split(delimitadoresPalabras)[0].equals(productoAbuscar))) {
                    pw.println(line);
                    pw.flush();
                }                
            }
        }
        pw.close();
        br.close();

        //Delete the original file
        if (!inFile.delete()) {
        System.out.println("Could not delete file");
        return;
        } 

        //Rename the new file to the filename the original file had.
        if (!tempFile.renameTo(inFile))
            System.out.println("Could not rename file");
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        
        mensaje.setText("Producto modificado");
        
    }//GEN-LAST:event_AceptarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VenModificarProd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VenModificarProd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VenModificarProd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VenModificarProd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VenModificarProd().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton Aceptar;
    private javax.swing.JToggleButton Buscar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel mensaje;
    private javax.swing.JTextField textoDensidad;
    private javax.swing.JTextField textoProducto;
    // End of variables declaration//GEN-END:variables
}
