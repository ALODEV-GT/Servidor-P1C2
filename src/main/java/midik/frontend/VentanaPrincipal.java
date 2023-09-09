package midik.frontend;

import java.io.StringReader;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import midik.Singletons.CentroCanales;
import midik.Singletons.CentroCanalesThread;
import midik.Singletons.Errores;
import midik.Singletons.Error;
import midik.arbol.NodoAST;
import midik.cup.parser;
import midik.ejecucion.Ejecucion;
import midik.jflex.AnalizadorLexico;

public class VentanaPrincipal extends javax.swing.JFrame {

    public VentanaPrincipal() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        entradaTa = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        ejecutarBtn = new javax.swing.JButton();
        limpiarBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        entradaTa.setColumns(20);
        entradaTa.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        entradaTa.setRows(5);
        jScrollPane1.setViewportView(entradaTa);

        jLabel1.setFont(new java.awt.Font("URW Bookman", 1, 48)); // NOI18N
        jLabel1.setText("EDITOR");

        ejecutarBtn.setText("Ejecutar");
        ejecutarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ejecutarBtnActionPerformed(evt);
            }
        });

        limpiarBtn.setText("Limpiar");
        limpiarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limpiarBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(76, 76, 76)
                .addComponent(ejecutarBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(limpiarBtn)
                .addGap(37, 37, 37))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 692, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ejecutarBtn)
                    .addComponent(limpiarBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void limpiarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limpiarBtnActionPerformed
        this.entradaTa.setText("");
    }//GEN-LAST:event_limpiarBtnActionPerformed

    private void ejecutarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ejecutarBtnActionPerformed
        this.ejecutar(new StringReader(this.entradaTa.getText()));
    }//GEN-LAST:event_ejecutarBtnActionPerformed

    public void cambiarTextoEntradaTa(String entrada) {
        this.entradaTa.setText(entrada);
    }

    private void ejecutar(StringReader entrada) {
        Errores.getInstance().clear();
        CentroCanales.getInstance().clear();
        CentroCanalesThread.getInstance().clear();
                

        AnalizadorLexico lexer = new AnalizadorLexico(entrada, "");
        parser par = new parser(lexer, "");

        try {
            par.parse();
            NodoAST raiz = par.getRaiz();
            Ejecucion ejecucion = new Ejecucion(raiz);
            ejecucion.ejecutar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Hay errores", "", JOptionPane.ERROR_MESSAGE);
        }

        Errores errs = Errores.getInstance();
        ArrayList<Error> lista = errs.getErrors();
        for (Error e : lista) {
            System.out.println(e);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ejecutarBtn;
    private javax.swing.JTextArea entradaTa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton limpiarBtn;
    // End of variables declaration//GEN-END:variables
}
