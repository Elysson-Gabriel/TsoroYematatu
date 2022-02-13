/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.ifceppd.tsoroyematatu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author elysson
 */
public class Jogador extends javax.swing.JFrame {
    
    private ConexaoCliente cliente;
    private int idJogador;
    private int outroJogador;
    private int[] posicoes;
    private int qtdPecas;
    private int qtdPecasAdv;
    private boolean ativaBotoes;
    private boolean empate;
    
    /**
     * Creates new form Jogador
     */
    public Jogador() {
        
        posicoes = new int[7];
        qtdPecas = 0;
        qtdPecasAdv = 0;
                
        this.conectaServidor();
        initComponents();
        this.setTitle("Jogador #" + idJogador + " - Tsoro Yematatu");
        
        if(idJogador == 1){
            mensagem.setText("Jogador #1. Inicie a partida.");
            outroJogador = 2;
            ativaBotoes = true;
        }else{
            mensagem.setText("Jogador #2. Aguarde seu turno.");
            outroJogador = 1;
            ativaBotoes = false;
            
            Thread t = new Thread(new Runnable(){
                public void run(){
                    controlaTurno();;
                }
            });
            t.start();
        }
        
        atualizaTabuleiro();
        
    }
    
    public void atualizaTabuleiro() {
        b1.setEnabled(ativaBotoes);
        b2.setEnabled(ativaBotoes);
        b3.setEnabled(ativaBotoes);
        b4.setEnabled(ativaBotoes);
        b5.setEnabled(ativaBotoes);
        b6.setEnabled(ativaBotoes);
        b7.setEnabled(ativaBotoes);
        
        b1.setText("" + posicoes[0]);
        b2.setText("" + posicoes[1]);
        b3.setText("" + posicoes[2]);
        b4.setText("" + posicoes[3]);
        b5.setText("" + posicoes[4]);
        b6.setText("" + posicoes[5]);
        b7.setText("" + posicoes[6]);
        
        if(posicoes[0] > 0){
            b1.setEnabled(false);
        }
        if(posicoes[1] > 0){
            b2.setEnabled(false);
        }
        if(posicoes[2] > 0){
            b3.setEnabled(false);
        }
        if(posicoes[3] > 0){
            b4.setEnabled(false);
        }
        if(posicoes[4] > 0){
            b5.setEnabled(false);
        }
        if(posicoes[5] > 0){
            b6.setEnabled(false);
        }
        if(posicoes[6] > 0){
            b7.setEnabled(false);
        }
        
        if(qtdPecas == 3 && qtdPecasAdv == 3){
            mudancaBotoes(0, false);
        }
    }
    
    public void mudancaBotoes(int valorComparacao, boolean estado){
        if(posicoes[0] == valorComparacao){
            b1.setEnabled(estado);
        }
        if(posicoes[1] == valorComparacao){
            b2.setEnabled(estado);
        }
        if(posicoes[2] == valorComparacao){
            b3.setEnabled(estado);
        }
        if(posicoes[3] == valorComparacao){
            b4.setEnabled(estado);
        }
        if(posicoes[4] == valorComparacao){
            b5.setEnabled(estado);
        }
        if(posicoes[5] == valorComparacao){
            b6.setEnabled(estado);
        }
        if(posicoes[6] == valorComparacao){
            b7.setEnabled(estado);
        }
    }
    
    public void controlaTurno(){
        int n = cliente.recebeJogada();
        mensagem.setText("Seu adversário clicou o botão #" + n + ". Sua vez");
        
        if(qtdPecasAdv < 3){
            if(idJogador == 1){
                posicoes[n-1] = 2;
            }else{
                posicoes[n-1] = 1;
            }
            qtdPecasAdv++;
        }
        
        if(qtdPecas == 3 && qtdPecasAdv == 3){
            if(idJogador == 1){
                mudancaBotoes(1, true);
                mudancaBotoes(2, false);
            }else{
                mudancaBotoes(2, true);
                mudancaBotoes(1, false);
            }
        }else{
            if(idJogador == 1 && empate == true){
                checkWinner();
            }else{
                ativaBotoes = true;
            }

            atualizaTabuleiro();
        }
        
        
    }
    
    private void checkWinner(){
        ativaBotoes = false;
        /*if(myPoints > enemyPoints){
            message.setText("You WON!\n" + "YOU: " + myPoints + "\n" + "ENEMY: " + enemyPoints);
        } else if(myPoints < enemyPoints){
            message.setText("You LOST!\n" + "YOU: " + myPoints + "\n" + "ENEMY: " + enemyPoints);
        }else{
            message.setText("It's a tie!\n" + "You both got " + myPoints + " points.");
        }*/
        
        cliente.fechaConexao();
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
        
        public void enviaJogada(int n){
            try{
                saida.writeInt(n);
                saida.flush();
            } catch (IOException ex) {
                System.out.println("Erro no enviaJogada() do Cliente");
            }
        }
        
        public int recebeJogada(){
            int n = -1;
            try{
                n = entrada.readInt();
                System.out.println("Jogador #" + outroJogador + " clicou o botão #" + n);
            } catch (IOException ex) {
                System.out.println("Erro no receiveButtonNum() do Cliente");
            }
            
            return n;
        }
        
        public void fechaConexao(){
           try{
                socket.close();
                System.out.println("-----CONEXÃO ENCERRADA-----");
            } catch (IOException ex) {
                System.out.println("Erro no fechaConexao() do Cliente");
            } 
        }
        
    }
    
    public void conectaServidor(){
        cliente = new ConexaoCliente();
    }
    
    public void acaoBotao(int n){
        
        if(qtdPecas < 3){
            if(idJogador == 1){
                posicoes[n-1] = 1;
            }else{
                posicoes[n-1] = 2;
            }
            qtdPecas++;
        }
        
        mensagem.setText("You clicked button #" + n + ". Now wait for player #" + outroJogador);
        
        ativaBotoes = false;
        atualizaTabuleiro();
        
        //myPoints += values[n - 1];
        //System.out.println("My points: " + myPoints);
        cliente.enviaJogada(n);
        
        if(idJogador == 2 && empate == true){
            checkWinner();
        }else{
            Thread t = new Thread(new Runnable(){
                public void run(){
                    controlaTurno();;
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
        mensagem = new javax.swing.JTextArea();
        b1 = new javax.swing.JButton();
        b2 = new javax.swing.JButton();
        b3 = new javax.swing.JButton();
        b4 = new javax.swing.JButton();
        b5 = new javax.swing.JButton();
        b6 = new javax.swing.JButton();
        b7 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tsoro Yematatu");

        mensagem.setEditable(false);
        mensagem.setColumns(20);
        mensagem.setLineWrap(true);
        mensagem.setRows(1);
        mensagem.setText("Tsoro Yematatu");
        mensagem.setWrapStyleWord(true);
        jScrollPane1.setViewportView(mensagem);

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
    private javax.swing.JTextArea mensagem;
    // End of variables declaration//GEN-END:variables
}
