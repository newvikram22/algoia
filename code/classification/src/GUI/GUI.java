/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GUI.java
 *
 * Created on 20 mai 2010, 10:31:34
 */

package GUI;

import algorithms.AbstractClassifier;
import algorithms.C45;
import algorithms.NaiveBayesV2;
import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author remi
 */
public class GUI extends javax.swing.JFrame {

    final JFileChooser fc;

    /**
     * POUR LE CLASSIFIER
     */
    private File inputFile;

    private AbstractClassifier classifier;

    private double percentage;

    private int classIndex;
    private double pourcentage;


    /** Creates new form GUI */
    public GUI() {
        initComponents();
        fc = new JFileChooser();

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelSource = new javax.swing.JLabel();
        jTextFieldSource = new javax.swing.JTextField();
        jButtonParcourir = new javax.swing.JButton();
        jLabelAlgorithme = new javax.swing.JLabel();
        jComboBoxAlgorihtme = new javax.swing.JComboBox();
        jLabelPourcentage = new javax.swing.JLabel();
        jLabelClassIndex = new javax.swing.JLabel();
        jTextFieldClassIndex = new javax.swing.JTextField();
        jButtonReadData = new javax.swing.JButton();
        jButtonClassify = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaLog = new javax.swing.JTextArea();
        jTextFieldPourcentage = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelSource.setText("source :");

        jTextFieldSource.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldSourceActionPerformed(evt);
            }
        });

        jButtonParcourir.setText("parcourir...");
        jButtonParcourir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonParcourirActionPerformed(evt);
            }
        });

        jLabelAlgorithme.setText("Algorithme : ");

        jComboBoxAlgorihtme.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "C4.5", "Naive Bayes" }));
        jComboBoxAlgorihtme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxAlgorihtmeActionPerformed(evt);
            }
        });

        jLabelPourcentage.setText("Pourcentage : ");

        jLabelClassIndex.setText("Class index : ");

        jTextFieldClassIndex.setText("0");
        jTextFieldClassIndex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldClassIndexActionPerformed(evt);
            }
        });

        jButtonReadData.setText("Read data");
        jButtonReadData.setEnabled(false);
        jButtonReadData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReadDataActionPerformed(evt);
            }
        });

        jButtonClassify.setText("Classify");
        jButtonClassify.setEnabled(false);
        jButtonClassify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClassifyActionPerformed(evt);
            }
        });

        jTextAreaLog.setColumns(20);
        jTextAreaLog.setRows(5);
        jScrollPane1.setViewportView(jTextAreaLog);

        jTextFieldPourcentage.setText("66");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelSource)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldSource, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonParcourir)
                        .addGap(12, 12, 12))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabelAlgorithme)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBoxAlgorihtme, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabelPourcentage)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextFieldPourcentage))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabelClassIndex)
                                    .addGap(18, 18, 18)
                                    .addComponent(jTextFieldClassIndex, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButtonReadData, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                            .addComponent(jButtonClassify, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldSource, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonParcourir))
                    .addComponent(jLabelSource))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelAlgorithme)
                    .addComponent(jComboBoxAlgorihtme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPourcentage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPourcentage, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonReadData))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelClassIndex)
                            .addComponent(jTextFieldClassIndex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jButtonClassify)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldSourceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSourceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldSourceActionPerformed

    private void jButtonParcourirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonParcourirActionPerformed
        int returnVal = fc.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            if (file != null) {
                if (file.isFile() && file.getName().contains(".data")) {
                    jTextFieldSource.setText(file.getPath());
                    this.inputFile = file;
                    jButtonReadData.setEnabled(true);
                }
            }
            //This is where a real application would open the file.
        } 


    }//GEN-LAST:event_jButtonParcourirActionPerformed

    private void jComboBoxAlgorihtmeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxAlgorihtmeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxAlgorihtmeActionPerformed

    private void jTextFieldClassIndexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldClassIndexActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldClassIndexActionPerformed

    private void jButtonClassifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClassifyActionPerformed

        classifier.classify();
      
        classifier.computeResults();

        jTextAreaLog.setText(classifier.getStrResults());

        jButtonClassify.setEnabled(false);

    }//GEN-LAST:event_jButtonClassifyActionPerformed

    private void jButtonReadDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReadDataActionPerformed

        this.classIndex = Integer.parseInt(jTextFieldClassIndex.getText());

        if (jComboBoxAlgorihtme.getSelectedItem().toString().equals("C4.5")) {
            classifier = new C45();
        } else if (jComboBoxAlgorihtme.getSelectedItem().toString().equals("Naive Bayes")) {
            classifier = new NaiveBayesV2();
        }

        classifier.setClassIndex(classIndex);
        classifier.setIntervalNumber(-1);

        this.pourcentage = Double.parseDouble(jTextFieldPourcentage.getText());

        classifier.readData(this.inputFile.getPath(), pourcentage);

        //jButtonReadData.setEnabled(false);
        jButtonClassify.setEnabled(true);

    }//GEN-LAST:event_jButtonReadDataActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClassify;
    private javax.swing.JButton jButtonParcourir;
    private javax.swing.JButton jButtonReadData;
    private javax.swing.JComboBox jComboBoxAlgorihtme;
    private javax.swing.JLabel jLabelAlgorithme;
    private javax.swing.JLabel jLabelClassIndex;
    private javax.swing.JLabel jLabelPourcentage;
    private javax.swing.JLabel jLabelSource;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaLog;
    private javax.swing.JTextField jTextFieldClassIndex;
    private javax.swing.JTextField jTextFieldPourcentage;
    private javax.swing.JTextField jTextFieldSource;
    // End of variables declaration//GEN-END:variables

    //Create a file chooser


}
