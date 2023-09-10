package midik.frontend;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import midik.Singletons.Biblioteca;
import midik.Singletons.CentroCanales;
import midik.Singletons.CentroCanalesThread;
import midik.Singletons.Consola;
import midik.Singletons.Errores;
import midik.Singletons.Error;
import midik.arbol.NodoAST;
import midik.cup.parser;
import midik.ejecucion.Ejecucion;
import midik.jflex.AnalizadorLexico;
import midik.musica.ListaReproduccion;
import midik.musica.Pista;
import midik.musica.Reproductor;

public class VentanaPrincipal extends javax.swing.JFrame {
    
    private final NumeroLinea numeroLineaPista;
    private String nombrePistaActual = "";
    
    private int indicePistaEditando = -1;
    private int indicePistaSeleccionada = 0;
    private int indiceListaSeleccionada = 0;
    private int indicePistaListaSelecionada = 0;
    
    private StyledDocument styledDoc;
    private Timer timer;
    
    public VentanaPrincipal() {
        Biblioteca.getInstance(); //Recupera las pistas y listas de los archivos binarios.
        initComponents();
        this.numeroLineaPista = new NumeroLinea(editorTa);
        this.editoSp.setRowHeaderView(this.numeroLineaPista);
        
        this.mostrarPistas(listIzq);
        this.mostrarListas(listCentralUp);
        
        this.agregarListenerJListIzq();
        this.agregarCambiarPestanaBotonModificar();
        this.agregarListenerlistCentralUp();
        this.agregarListenerlistCentralDown();
        
        this.cambiarTab();
        this.pintarEditor();
        this.erroresTa.setEditable(false);
        this.consolaTa.setEditable(false);
        
    }
    
    private void pintarEditor() {
        this.styledDoc = editorTa.getStyledDocument();
        
        styledDoc = editorTa.getStyledDocument();

        // Crear un Timer para aplicar estilos de forma periódica
        timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resaltarPalabras();
            }
        });
        timer.setRepeats(true);
        timer.start();
        
    }
    
    private void resaltarPalabras() {
        String texto = editorTa.getText();
        SimpleAttributeSet redAttr = new SimpleAttributeSet();
        SimpleAttributeSet greenAttr = new SimpleAttributeSet();

        // Reservadas
        StyleConstants.setForeground(redAttr, Color.BLUE);

        // Numeros
        StyleConstants.setForeground(greenAttr, Color.decode("#800080"));

        // Borrar estilos anteriores
        styledDoc.setCharacterAttributes(0, texto.length(), new SimpleAttributeSet(), true);

        // Resaltar palabras
        String[] palabras = texto.split("\\s+");

        // Resaltar palabras
        for (String palabra : palabras) {
            if (Pattern.compile("(?i)\\b(Pista|Extiende|Entero|Doble|Boolean|Caracter|Cadena|Keep|Var|Arreglo|Si|Sino|Switch|Caso|Salir|Default|Para|Mientras|Hacer|Continuar|Retorna|Reproducir|Esperar|Ordenar|Sumarizar|Longitud|Mensaje|Principal|Ascendente|Descendente|Pares|Impares|Primos|pista|extiende|entero|doble|boolean|caracter|cadena|keep|var|arreglo|si|sino|switch|caso|salir|default|para|mientras|hacer|continuar|retorna|reproducir|esperar|ordenar|sumarizar|longitud|mensaje|principal|ascendente|descendente|pares|impares|prim)\\b").matcher(palabra).find()) {
                resaltarPalabra(palabra, redAttr);
            } else if (Pattern.compile("(?i)\\b(1|2|3|4|5|6|7|8|9|0|.)\\b").matcher(palabra).find()) {
                resaltarPalabra(palabra, greenAttr);
            }
        }
    }
    
    private void resaltarPalabra(String palabra, AttributeSet attr) {
        String texto = editorTa.getText();
        int inicio = -1;
        
        while ((inicio = texto.toLowerCase().indexOf(palabra.toLowerCase(), inicio + 1)) >= 0) {
            styledDoc.setCharacterAttributes(inicio, palabra.length(), attr, false);
        }
    }
    
    private void cambiarTab() {
        editorTa.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_TAB) {
                    // Inserta un espacio en blanco en lugar de una tabulación completa
                    editorTa.replaceSelection("    "); // 4 espacios
                    e.consume(); // Evita que se procese la tecla TAB de forma predeterminada
                }
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }
    
    private void agregarCambiarPestanaBotonModificar() {
        modificarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                principalJTP.setSelectedIndex(1);
            }
        });
        
        crearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                principalJTP.setSelectedIndex(1);
            }
        });
    }
    
    private void agregarListenerJListIzq() {
        listIzq.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) { // Detectar un clic
                    // Obtener el índice del elemento seleccionado
                    indicePistaSeleccionada = listIzq.locationToIndex(e.getPoint());
                }
            }
        });
        
        listIzq.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Detectar dos clic
                    try {
                        // Obtener el índice del elemento seleccionado
                        indicePistaSeleccionada = listIzq.locationToIndex(e.getPoint());
                        ArrayList<Pista> pistas = Biblioteca.getInstance().getPistas();
                        Reproductor rep = new Reproductor(pistas.get(indicePistaSeleccionada).getCanales());
                        rep.reproducir();
                    } catch (Exception d) {
                        //No hacer nada y esperar que seleccione una pista
                    }
                }
            }
        });
    }
    
    private void agregarListenerlistCentralUp() {
        listCentralUp.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) { // Detectar un clic
                    // Obtener el índice del elemento seleccionado
                    indiceListaSeleccionada = listCentralUp.locationToIndex(e.getPoint());
                    mostrarPistaslista(listCentralDown);
                }
            }
        });
    }
    
    private void agregarListenerlistCentralDown() {
        listCentralDown.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) { // Detectar un clic
                    // Obtener el índice del elemento seleccionado
                    indicePistaListaSelecionada = listCentralDown.locationToIndex(e.getPoint());
                }
            }
        });
        
        listCentralDown.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Detectar dos clic
                    try {
                        // Obtener el índice del elemento seleccionado
                        indicePistaListaSelecionada = listCentralDown.locationToIndex(e.getPoint());
                        Pista pista = Biblioteca.getInstance().getPistaLista(indiceListaSeleccionada, indicePistaListaSelecionada);
                        Reproductor rep = new Reproductor(pista.getCanales());
                        rep.reproducir();
                    } catch (Exception d) {
                        d.printStackTrace();
                        //No hacer nada y esperar que seleccione una pista
                    }
                }
            }
        });
    }
    
    private void mostrarPistaslista(JList<String> jlist) {
        DefaultListModel<String> listaModel = new DefaultListModel<>();
        jlist.setModel(listaModel);
        ArrayList<Pista> pistas = Biblioteca.getInstance().getListaReproduccion(this.indiceListaSeleccionada).getPistas();
        for (Pista p : pistas) {
            listaModel.addElement(p.getNombre() + "      " + p.getDuracion());
        }
    }
    
    private void mostrarListas(JList<String> jlist) {
        DefaultListModel<String> listaModel = new DefaultListModel<>();
        jlist.setModel(listaModel);
        ArrayList<ListaReproduccion> listas = Biblioteca.getInstance().getListasReproduccion();
        for (ListaReproduccion l : listas) {
            listaModel.addElement(l.getNombreLista());
        }
    }
    
    private void mostrarPistas(JList<String> jlist) {
        DefaultListModel<String> listaModel = new DefaultListModel<>();
        jlist.setModel(listaModel);
        ArrayList<Pista> pistas = Biblioteca.getInstance().getPistas();
        for (Pista p : pistas) {
            listaModel.addElement(p.getNombre() + "      " + p.getDuracion());
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        principalJTP = new javax.swing.JTabbedPane();
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
        eliminarBtn = new javax.swing.JButton();
        modificarBtnCentral = new javax.swing.JButton();
        eliminarBtnCentral = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        repetirCb = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        crearListaTf = new javax.swing.JTextField();
        crearListaBtn = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
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
        editorTa = new javax.swing.JTextPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        archivojM = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        ayudajM = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane4.setViewportView(listIzq);

        jScrollPane5.setViewportView(listCentralUp);

        jScrollPane6.setViewportView(listCentralDown);

        playBtn.setText("Play");

        crearBtn.setText("Crear");
        crearBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crearBtnActionPerformed(evt);
            }
        });

        modificarBtn.setText("Modificar");
        modificarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificarBtnActionPerformed(evt);
            }
        });

        eliminarBtn.setText("Eliminar");
        eliminarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarBtnActionPerformed(evt);
            }
        });

        modificarBtnCentral.setText("+");
        modificarBtnCentral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificarBtnCentralActionPerformed(evt);
            }
        });

        eliminarBtnCentral.setText("Eliminar");
        eliminarBtnCentral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarBtnCentralActionPerformed(evt);
            }
        });

        jLabel1.setText("Canciones en la lista");

        jScrollPane7.setViewportView(jEditorPane1);

        repetirCb.setText("Repetir");
        repetirCb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                repetirCbActionPerformed(evt);
            }
        });

        jLabel2.setText("Canciones");

        jLabel3.setText("Listas");

        crearListaBtn.setText("Crear");
        crearListaBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crearListaBtnActionPerformed(evt);
            }
        });

        jButton1.setText("-");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(eliminarBtn)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(crearListaTf, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(crearListaBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                                                .addComponent(modificarBtnCentral, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(eliminarBtnCentral, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(101, 101, 101)
                        .addComponent(playBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(repetirCb)
                        .addGap(7, 7, 7))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(crearListaTf, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(crearListaBtn))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(modificarBtnCentral)
                                    .addComponent(eliminarBtnCentral)
                                    .addComponent(jButton1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(crearBtn)
                        .addComponent(modificarBtn))
                    .addComponent(eliminarBtn))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 56, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        principalJTP.addTab("Biblioteca", jPanel1);

        consolaTa.setColumns(20);
        consolaTa.setRows(5);
        jScrollPane9.setViewportView(consolaTa);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 999, Short.MAX_VALUE)
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
            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 999, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
        );

        reportesJt.addTab("Errores", jPanel4);

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
            .addComponent(editoSp, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
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

        principalJTP.addTab("Editor de codigo", jPanel2);

        archivojM.setText("Archivo");

        jMenuItem1.setText("Ejecutar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        archivojM.add(jMenuItem1);

        jMenuItem2.setText("Guardar");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        archivojM.add(jMenuItem2);

        jMenuBar1.add(archivojM);

        ayudajM.setText("Ayuda");
        jMenuBar1.add(ayudajM);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(principalJTP, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(principalJTP, javax.swing.GroupLayout.Alignment.TRAILING)
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

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        
        if (this.indicePistaEditando != -1) {
            //Remplazar y limpiar el editor de texto
            CentroCanales.getInstance().editarPista(nombrePistaActual, this.editorTa.getText(), this.indicePistaEditando);
            this.indicePistaEditando = -1;
            this.mostrarPistas(listIzq);
            this.editorTa.setText("");
        } else {
            CentroCanales.getInstance().guardarPista(this.nombrePistaActual, this.editorTa.getText());
            this.mostrarPistas(listIzq);
        }
        

    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void eliminarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarBtnActionPerformed
        
        try {
            if (this.indicePistaEditando == -1) {
                Biblioteca.getInstance().eliminarPista(this.indicePistaSeleccionada);
                this.mostrarPistas(listIzq);
            } else {
                JOptionPane.showMessageDialog(this, "Termina de editar");
            }
        } catch (Exception e) {
            //No hacer nada y esperar que seleccione una pista
        }
    }//GEN-LAST:event_eliminarBtnActionPerformed

    private void modificarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificarBtnActionPerformed
        try {
            Pista pista = Biblioteca.getInstance().getPistas().get(this.indicePistaSeleccionada);
            editorTa.setText(pista.getCodigoFuente());
            this.indicePistaEditando = this.indicePistaSeleccionada;
            this.mostrarPistas(listIzq);
        } catch (Exception e) {
            //No hacer nada y esperar a que el usuario seleccion una pista.
        }

    }//GEN-LAST:event_modificarBtnActionPerformed

    private void crearBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crearBtnActionPerformed
        this.indicePistaEditando = -1;
        this.editorTa.setText("");
    }//GEN-LAST:event_crearBtnActionPerformed

    private void crearListaBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crearListaBtnActionPerformed
        
        if (crearListaTf.getText().trim().length() > 0) {
            ListaReproduccion lr = new ListaReproduccion(crearListaTf.getText().trim());
            Biblioteca.getInstance().guardarlistaReproduccion(lr);
            crearListaTf.setText("");
            //Recargar listado pistas
            this.mostrarListas(listCentralUp);
        }
        

    }//GEN-LAST:event_crearListaBtnActionPerformed

    private void eliminarBtnCentralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarBtnCentralActionPerformed
        try {
            Biblioteca.getInstance().eliminarListaReproduccion(this.indiceListaSeleccionada);
            this.mostrarListas(listCentralUp);
        } catch (Exception e) {
            //No hacer nada y esperar que seleccione una pista
        }
    }//GEN-LAST:event_eliminarBtnCentralActionPerformed

    private void modificarBtnCentralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificarBtnCentralActionPerformed
        try {
            Pista pista = Biblioteca.getInstance().getPistas().get(this.indicePistaSeleccionada);
            Biblioteca.getInstance().agregarPistaALista(pista, this.indiceListaSeleccionada);
            this.mostrarPistaslista(listCentralDown);
        } catch (Exception e) {
            //No hacer nada y esperar que seleccione una pista
        }

    }//GEN-LAST:event_modificarBtnCentralActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            Biblioteca.getInstance().eliminarPistaDeLista(this.indiceListaSeleccionada, this.indicePistaListaSelecionada);
            this.mostrarPistaslista(listCentralDown);
        } catch (Exception e) {
            //No hacer nada y esperar que seleccione una pista
        }
    }//GEN-LAST:event_jButton1ActionPerformed
    
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
            this.nombrePistaActual = ejecucion.getNombrePista();
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
    private javax.swing.JButton crearListaBtn;
    private javax.swing.JTextField crearListaTf;
    private javax.swing.JScrollPane editoSp;
    private javax.swing.JTextPane editorTa;
    private javax.swing.JButton eliminarBtn;
    private javax.swing.JButton eliminarBtnCentral;
    private javax.swing.JTextArea erroresTa;
    private javax.swing.JButton jButton1;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
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
    private javax.swing.JList<String> listCentralDown;
    private javax.swing.JList<String> listCentralUp;
    private javax.swing.JList<String> listIzq;
    private javax.swing.JButton modificarBtn;
    private javax.swing.JButton modificarBtnCentral;
    private javax.swing.JButton playBtn;
    private javax.swing.JTabbedPane principalJTP;
    private javax.swing.JCheckBox repetirCb;
    private javax.swing.JTabbedPane reportesJt;
    // End of variables declaration//GEN-END:variables
}
