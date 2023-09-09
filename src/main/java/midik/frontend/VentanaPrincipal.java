package midik.frontend;

import java.io.StringReader;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import midik.Singletons.CentroCanales;
import midik.Singletons.CentroCanalesThread;
import midik.Singletons.Consola;
import midik.Singletons.Errores;
import midik.Singletons.Error;
import midik.arbol.NodoAST;
import midik.cup.parser;
import midik.ejecucion.Ejecucion;
import midik.jflex.AnalizadorLexico;

public class VentanaPrincipal extends javax.swing.JFrame {

    private final NumeroLinea numeroLineaPista;

    public VentanaPrincipal() {
        initComponents();
        this.numeroLineaPista = new NumeroLinea(editorTa);
        this.editoSp.setRowHeaderView(this.numeroLineaPista);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        listIzq = new javax.swing.JList<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        listCentralUp = new javax.swing.JList<>();
        jScrollPane6 = new javax.swing.JScrollPane();
        listCentralDown = new javax.swing.JList<>();
        playBtn = new javax.swing.JButton();
        crearBtn = new javax.swing.JButton();
        modificarBtn = new javax.swing.JButton();
        reproducirBtn = new javax.swing.JButton();
        eliminarBtn = new javax.swing.JButton();
        modificarBtnCentral = new javax.swing.JButton();
        eliminarBtnCentral = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        repetirCb = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        reportesJt = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        consolaTa = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        erroresTa = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        editoSp = new javax.swing.JScrollPane();
        editorTa = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        archivojM = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        ayudajM = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        listIzq.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(listIzq);

        listCentralUp.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane5.setViewportView(listCentralUp);

        listCentralDown.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane6.setViewportView(listCentralDown);

        playBtn.setText("Play");

        crearBtn.setText("Crear");

        modificarBtn.setText("Modificar");

        reproducirBtn.setText("Reproducir");

        eliminarBtn.setText("Eliminar");

        modificarBtnCentral.setText("Modificar");

        eliminarBtnCentral.setText("Eliminar");

        jLabel1.setText("Listas de Reproduccion");

        jScrollPane7.setViewportView(jEditorPane1);

        repetirCb.setText("Repetir");
        repetirCb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                repetirCbActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(crearBtn)
                        .addGap(12, 12, 12)
                        .addComponent(modificarBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(reproducirBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(eliminarBtn)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel6Layout.createSequentialGroup()
                                    .addComponent(modificarBtnCentral, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(eliminarBtnCentral, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane6)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addComponent(playBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(89, 89, 89))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addComponent(repetirCb)
                                .addContainerGap())))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 503, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(modificarBtnCentral)
                                    .addComponent(eliminarBtnCentral))
                                .addGap(12, 12, 12)
                                .addComponent(jLabel1))
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addComponent(playBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(repetirCb)
                                .addGap(1, 1, 1)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(crearBtn)
                    .addComponent(modificarBtn)
                    .addComponent(reproducirBtn)
                    .addComponent(eliminarBtn))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Biblioteca", jPanel1);

        consolaTa.setColumns(20);
        consolaTa.setRows(5);
        jScrollPane9.setViewportView(consolaTa);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 993, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
        );

        reportesJt.addTab("Consola", jPanel3);

        erroresTa.setColumns(20);
        erroresTa.setRows(5);
        jScrollPane10.setViewportView(erroresTa);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 993, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
        );

        reportesJt.addTab("Errores", jPanel4);

        editorTa.setColumns(20);
        editorTa.setRows(5);
        editoSp.setViewportView(editorTa);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(editoSp)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(editoSp, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(reportesJt)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reportesJt, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Editor de codigo", jPanel2);

        archivojM.setText("Archivo");

        jMenuItem1.setText("Ejecutar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        archivojM.add(jMenuItem1);

        jMenuBar1.add(archivojM);

        ayudajM.setText("Ayuda");
        jMenuBar1.add(ayudajM);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void repetirCbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_repetirCbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_repetirCbActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        StringReader sr = new StringReader(editorTa.getText());
        this.ejecutar(sr);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    public void cambiarTextoEntradaTa(String entrada) {
        this.editorTa.setText(entrada);
    }

    private void ejecutar(StringReader entrada) {
        Errores.getInstance().clear();
        CentroCanales.getInstance().clear();
        CentroCanalesThread.getInstance().clear();
        Consola.getInstance().clear();
        erroresTa.setText("");
        consolaTa.setText("");

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
            this.erroresTa.append(e.toString() + "\n");
        }

        //Consola
        Consola mensajes = Consola.getInstance();
        ArrayList<String> listaM = mensajes.getMensajes();
        for (String e : listaM) {
            this.consolaTa.append(e + "\n");
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu archivojM;
    private javax.swing.JMenu ayudajM;
    private javax.swing.JTextArea consolaTa;
    private javax.swing.JButton crearBtn;
    private javax.swing.JScrollPane editoSp;
    private javax.swing.JTextArea editorTa;
    private javax.swing.JButton eliminarBtn;
    private javax.swing.JButton eliminarBtnCentral;
    private javax.swing.JTextArea erroresTa;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JList<String> listCentralDown;
    private javax.swing.JList<String> listCentralUp;
    private javax.swing.JList<String> listIzq;
    private javax.swing.JButton modificarBtn;
    private javax.swing.JButton modificarBtnCentral;
    private javax.swing.JButton playBtn;
    private javax.swing.JCheckBox repetirCb;
    private javax.swing.JTabbedPane reportesJt;
    private javax.swing.JButton reproducirBtn;
    // End of variables declaration//GEN-END:variables
}
