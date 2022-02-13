/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.ifceppd.tsoroyematatu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author elysson
 */
public class Jogador extends javax.swing.JFrame {
    
    private ConexaoCliente cliente;
    private int idJogador;
    private int outroJogador;
    private int[] values;
    private boolean empate;

    private int myPoints;
    private int enemyPoints;
    private boolean buttonsEnabled;
    
    /**
     * Creates new form Player
     */
    public Jogador() {
        
        values = new int[7];
        myPoints = 0;
        enemyPoints = 0;
        
        this.conectaServidor();
        initComponents();
        this.setTitle("Jogador #" + idJogador + " - Tsoro Yematatu");
        
        if(idJogador == 1){
            message.setText("Jogador #1. Inicie a partida.");
            outroJogador = 2;
            buttonsEnabled = true;
        }else{
            message.setText("Jogador #2. Aguarde seu turno.");
            outroJogador = 1;
            buttonsEnabled = false;
            
            Thread t = new Thread(new Runnable(){
                public void run(){
                    updateTurn();;
                }
            });
            t.start();
        }
        
        toggleButtons();
        
    }
    
    public void toggleButtons() {
        b1.setEnabled(buttonsEnabled);
        b2.setEnabled(buttonsEnabled);
        b3.setEnabled(buttonsEnabled);
        b4.setEnabled(buttonsEnabled);
        b5.setEnabled(buttonsEnabled);
        b6.setEnabled(buttonsEnabled);
        b7.setEnabled(buttonsEnabled);
        
        b1.setText("" + values[0]);
        b2.setText("" + values[1]);
        b3.setText("" + values[2]);
        b4.setText("" + values[3]);
        b5.setText("" + values[4]);
        b6.setText("" + values[5]);
        b7.setText("" + values[6]);
    }
    
    public void updateTurn(){
        int n = cliente.receiveButtonNum();
        message.setText("Your enemy clicked button #" + n + ". Your turn");
        
        if(idJogador == 1){
            values[n-1] = 2;
        }else{
            values[n-1] = 1;
        }
        
        //enemyPoints += values[n-1];
        //System.out.println("Your enemy has " + enemyPoints + " points");
        
        if(idJogador == 1 && empate == true){
            checkWinner();
        }else{
            buttonsEnabled = true;
        }
        
        toggleButtons();
    }
    
    private void checkWinner(){
        buttonsEnabled = false;
        if(myPoints > enemyPoints){
            message.setText("You WON!\n" + "YOU: " + myPoints + "\n" + "ENEMY: " + enemyPoints);
        } else if(myPoints < enemyPoints){
            message.setText("You LOST!\n" + "YOU: " + myPoints + "\n" + "ENEMY: " + enemyPoints);
        }else{
            message.setText("It's a tie!\n" + "You both got " + myPoints + " points.");
        }
        
        cliente.closeConnection();
    }
    
    private class ConexaoCliente {
    
        private Socket socket;
        private DataInputStream entrada;
        private DataOutputStream saida;
        
        public ConexaoCliente(){
            System.out.println("----- Cliente -----");

            try{
                socket = new Socket("localhost", 51734);
                entrada = new DataInputStream(socket.getInputStream());
                saida = new DataOutputStream(socket.getOutputStream());
                idJogador = entrada.readInt();
                System.out.println("Conectado ao servidor como Jogador #" + idJogador + ".");
                empate = entrada.readBoolean();
            } catch (IOException ex) {
                System.out.println("Erro no construtor do cliente");
            }
        }
        
        public void sendButtonNum(int n){
            try{
                saida.writeInt(n);
                saida.flush();
            } catch (IOException ex) {
                System.out.println("Erro no sendButtonNum() do Cliente");
            }
        }
        
        public int receiveButtonNum(){
            int n = -1;
            try{
                n = entrada.readInt();
                System.out.println("Player #" + outroJogador + " clicked button #" + n);
            } catch (IOException ex) {
                System.out.println("Erro no receiveButtonNum() do Cliente");
            }
            
            return n;
        }
        
        public void closeConnection(){
           try{
                socket.close();
                System.out.println("-----CONEXÃO ENCERRADA-----");
            } catch (IOException ex) {
                System.out.println("Erro no closeConnection() do Cliente");
            } 
        }
        
    }
    
    public void conectaServidor(){
        cliente = new ConexaoCliente();
    }
    
    public void acaoBotao(int n){
        
        if(idJogador == 1){
            values[n-1] = 1;
        }else{
            values[n-1] = 2;
        }
        
        message.setText("You clicked button #" + n + ". Now wait for player #" + outroJogador);
        
        buttonsEnabled = false;
        toggleButtons();
        
        //myPoints += values[n - 1];
        //System.out.println("My points: " + myPoints);
        cliente.sendButtonNum(n);
        
        if(idJogador == 2 && empate == true){
            checkWinner();
        }else{
            Thread t = new Thread(new Runnable(){
                public void run(){
                    updateTurn();;
                }
            });
            t.start();
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        message = new javax.swing.JTextArea();
        b1 = new javax.swing.JButton();
        b2 = new javax.swing.JButton();
        b3 = new javax.swing.JButton();
        b4 = new javax.swing.JButton();
        b5 = new javax.swing.JButton();
        b6 = new javax.swing.JButton();
        b7 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tsoro Yematatu");

        message.setEditable(false);
        message.setColumns(20);
        message.setLineWrap(true);
        message.setRows(1);
        message.setText("Tsoro Yematatu");
        message.setWrapStyleWord(true);
        jScrollPane1.setViewportView(message);

        b1.setText("1");
        b1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b1ActionPerformed(evt);
            }
        });

        b2.setText("2");
        b2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b2ActionPerformed(evt);
            }
        });

        b3.setText("3");
        b3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b3ActionPerformed(evt);
            }
        });

        b4.setText("4");
        b4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b4ActionPerformed(evt);
            }
        });

        b5.setText("5");
        b5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b5ActionPerformed(evt);
            }
        });

        b6.setText("6");
        b6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b6ActionPerformed(evt);
            }
        });

        b7.setText("7");
        b7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(b2)
                            .addComponent(b5))
                        .addGap(94, 94, 94)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(b3)
                            .addComponent(b6)
                            .addComponent(b1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(b4)
                            .addComponent(b7))
                        .addGap(24, 24, 24)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(b1)
                .addGap(59, 59, 59)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(b2)
                    .addComponent(b3)
                    .addComponent(b4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(b5)
                    .addComponent(b6)
                    .addComponent(b7))
                .addGap(39, 39, 39))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void b1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b1ActionPerformed
        // TODO add your handling code here:
        acaoBotao(1);
    }//GEN-LAST:event_b1ActionPerformed

    private void b2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b2ActionPerformed
        // TODO add your handling code here:
        acaoBotao(2);
    }//GEN-LAST:event_b2ActionPerformed

    private void b3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b3ActionPerformed
        // TODO add your handling code here:
        acaoBotao(3);
    }//GEN-LAST:event_b3ActionPerformed

    private void b4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b4ActionPerformed
        // TODO add your handling code here:
        acaoBotao(4);
    }//GEN-LAST:event_b4ActionPerformed

    private void b5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b5ActionPerformed
        // TODO add your handling code here:
        acaoBotao(5);
    }//GEN-LAST:event_b5ActionPerformed

    private void b6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b6ActionPerformed
        // TODO add your handling code here:
        acaoBotao(6);
    }//GEN-LAST:event_b6ActionPerformed

    private void b7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b7ActionPerformed
        // TODO add your handling code here:
        acaoBotao(7);
    }//GEN-LAST:event_b7ActionPerformed

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
            java.util.logging.Logger.getLogger(Jogador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Jogador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Jogador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Jogador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Jogador().setVisible(true);
            }
        });
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b1;
    private javax.swing.JButton b2;
    private javax.swing.JButton b3;
    private javax.swing.JButton b4;
    private javax.swing.JButton b5;
    private javax.swing.JButton b6;
    private javax.swing.JButton b7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea message;
    // End of variables declaration//GEN-END:variables
}
