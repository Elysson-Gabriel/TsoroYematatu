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
    private ConexaoChat chat;
    private int idJogador;
    private int outroJogador;
    private int[] posicoes;
    private int qtdPecas;
    private int qtdPecasAdv;
    private boolean ativaBotoes;
    private boolean solicitouEmpate;
    private boolean fimJogo;
    
    /**
     * Creates new form Jogador
     */
    public Jogador() {
        
        posicoes = new int[7];
        qtdPecas = 0;
        qtdPecasAdv = 0;
        solicitouEmpate = false;
        fimJogo = false;
                
        this.conectaServidor();
        
        initComponents();
        
        Thread t2 = new Thread(new Runnable(){
            public void run(){
                atualizaChat();;
            }
        });
        t2.start();
        
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
        
        atualizaPecas();
        
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
    
    public void atualizaPecas(){
        
        switch (posicoes[0]) {
            case 1:
                b1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaVermelha.png")));
                b1.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaVermelhaBloqueada.png")));
                break;
            case 2:
                b1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaAzul.png")));
                b1.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaAzulBloqueada.png")));
                break;
            default:
                b1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                b1.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (posicoes[1]) {
            case 1:
                b2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaVermelha.png")));
                b2.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaVermelhaBloqueada.png")));
                break;
            case 2:
                b2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaAzul.png")));
                b2.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaAzulBloqueada.png")));
                break;
            default:
                b2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                b2.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (posicoes[2]) {
            case 1:
                b3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaVermelha.png")));
                b3.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaVermelhaBloqueada.png")));
                break;
            case 2:
                b3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaAzul.png")));
                b3.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaAzulBloqueada.png")));
                break;
            default:
                b3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                b3.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (posicoes[3]) {
            case 1:
                b4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaVermelha.png")));
                b4.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaVermelhaBloqueada.png")));
                break;
            case 2:
                b4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaAzul.png")));
                b4.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaAzulBloqueada.png")));
                break;
            default:
                b4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                b4.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (posicoes[4]) {
            case 1:
                b5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaVermelha.png")));
                b5.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaVermelhaBloqueada.png")));
                break;
            case 2:
                b5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaAzul.png")));
                b5.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaAzulBloqueada.png")));
                break;
            default:
                b5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                b5.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (posicoes[5]) {
            case 1:
                b6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaVermelha.png")));
                b6.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaVermelhaBloqueada.png")));
                break;
            case 2:
                b6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaAzul.png")));
                b6.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaAzulBloqueada.png")));
                break;
            default:
                b6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                b6.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (posicoes[6]) {
            case 1:
                b7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaVermelha.png")));
                b7.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaVermelhaBloqueada.png")));
                break;
            case 2:
                b7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaAzul.png")));
                b7.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaAzulBloqueada.png")));
                break;
            default:
                b7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                b7.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        /*
        b1.setText("" + posicoes[0]);
        b2.setText("" + posicoes[1]);
        b3.setText("" + posicoes[2]);
        b4.setText("" + posicoes[3]);
        b5.setText("" + posicoes[4]);
        b6.setText("" + posicoes[5]);
        b7.setText("" + posicoes[6]);*/
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
    
    int casaTroca;
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
        }else{
            for (int i = 0; i < posicoes.length; i++) {
                if(posicoes[i] == 0){
                    casaTroca = i;
                    break;
                }
            }
            if(idJogador == 1){
                posicoes[casaTroca] = 2;
                posicoes[n-1] = 0;
            }else{
                posicoes[casaTroca] = 1;
                posicoes[n-1] = 0;
            }
        }
        
        if(qtdPecas == 3 && qtdPecasAdv == 3){
            if(idJogador == 1){
                mudancaBotoes(1, true);
                mudancaBotoes(2, false);
            }else{
                mudancaBotoes(2, true);
                mudancaBotoes(1, false);
            }
            
            atualizaPecas();
        }else{
            ativaBotoes = true;
            atualizaTabuleiro();
        }
        
    }
    
    public void atualizaChat(){
        
        try {
            String msgin = "";
            while(!msgin.equals("exit")){
                msgin = chat.din.readUTF();
                //System.out.println("Server: " + msgin);
                if(msgin.equals("/d") && !fimJogo){
                    msg_area.setText(msg_area.getText() + "\n Você venceu! Seu adversario desistiu.");
                    mensagem.setText("Você venceu! Seu adversario desistiu.");
                    mudancaBotoes(0, false);
                    mudancaBotoes(1, false);
                    mudancaBotoes(2, false);
                }else if(msgin.equals("/e") && !fimJogo){
                    if(solicitouEmpate){
                        msg_area.setText(msg_area.getText() + "\n Jogadores concordaram com empate.");
                        mudancaBotoes(0, false);
                        mudancaBotoes(1, false);
                        mudancaBotoes(2, false);
                        chat.dout.writeUTF("/e");
                        solicitouEmpate = false;
                        fimJogo = true;
                    }else if(!fimJogo){
                        msg_area.setText(msg_area.getText() + "\n Seu adversário solicitou empate\n Envie /e para aceitar.");
                    }
                }else if(msgin.equals("/f")){
                    msg_area.setText(msg_area.getText() + "\n Fim de jogo. Você perdeu!");
                    mudancaBotoes(0, false);
                    mudancaBotoes(1, false);
                    mudancaBotoes(2, false);
                    fimJogo = true;
                }
                else{
                    msg_area.setText(msg_area.getText() + "\n Adversario: " + msgin);
                }
                
            }
        } catch (IOException ex) {
            Logger.getLogger(Jogador.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }
    
    private int[] vitoria1 = {0, 1, 4};
    private int[] vitoria2 = {0, 2, 5};
    private int[] vitoria3 = {0, 3, 6};
    private int[] vitoria4 = {1, 2, 3};
    private int[] vitoria5 = {4, 5, 6};
    private int[][] vitorias = {vitoria1, vitoria2, vitoria3, vitoria4, vitoria5};
    
    private void verificaVencedor(){
        ativaBotoes = false;
        
        for (int[] vitoria : vitorias) {
            if(posicoes[vitoria[0]] != 0 && posicoes[vitoria[0]] == posicoes[vitoria[1]] &&
                    posicoes[vitoria[1]] == posicoes[vitoria[2]]){
                
                try {
                    mensagem.setText("Parabéns você venceu!");
                    mudancaBotoes(0, false);
                    mudancaBotoes(1, false);
                    mudancaBotoes(2, false);
                    fimJogo = true;
                    chat.dout.writeUTF("/f");
                    //cliente.fechaConexao();
                } catch (IOException ex) {
                    Logger.getLogger(Jogador.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            }
        }

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
                //empate = entrada.readBoolean();
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
    
    private class ConexaoChat {
    
        private Socket s;
        private DataInputStream din;
        private DataOutputStream dout;
        
        public ConexaoChat(){
            try{
                s = new Socket("localhost", 51738);
                din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());

            } catch (IOException ex) {
                System.out.println("Erro no construtor do chat");
            }
        }
        
        public void fechaConexao(){
           try{
                s.close();
                System.out.println("-----CONEXÃO ENCERRADA-----");
            } catch (IOException ex) {
                System.out.println("Erro no fechaConexao() do ConexaoChat");
            } 
        }
        
    }
    
    public void conectaServidor(){
        cliente = new ConexaoCliente();
        chat = new ConexaoChat();
    }
    
    public void acaoBotao(int n){
        int casaTroca = 0;
        boolean jogadaValida;
        if(qtdPecas < 3){
            jogadaValida = true;
            if(idJogador == 1){
                posicoes[n-1] = 1;
            }else{
                posicoes[n-1] = 2;
            }
            qtdPecas++;
        }else{
            jogadaValida = validaJogada(n);
            
            for (int i = 0; i < posicoes.length; i++) {
                if(posicoes[i] == 0){
                    casaTroca = i;
                }
            }
            
            if(jogadaValida){
                if(idJogador == 1){
                    posicoes[casaTroca] = 1;
                    posicoes[n-1] = 0;
                }else{
                    posicoes[casaTroca] = 2;
                    posicoes[n-1] = 0;
                }
            }
        }
        
        if(jogadaValida){
            mensagem.setText("Você clicou o botão #" + n + ". Agora a vez do jogador #" + outroJogador);

            ativaBotoes = false;
            atualizaTabuleiro();

            //myPoints += values[n - 1];
            //System.out.println("My points: " + myPoints);
            cliente.enviaJogada(n);

            Thread t = new Thread(new Runnable(){
                public void run(){
                    controlaTurno();;
                }
            });
            t.start();
            
            verificaVencedor();
        }
        
    }
    
    private int[] jogadas0 = {1, 2, 3, 4, 5, 6};
    private int[] jogadas1 = {0, 2, 3, 4};
    private int[] jogadas2 = {0, 1, 3, 5};
    private int[] jogadas3 = {0, 1, 2, 6};
    private int[] jogadas4 = {0, 1, 5, 6};
    private int[] jogadas5 = {0, 2, 4, 6};
    private int[] jogadas6 = {0, 3, 4, 5};
    
    private int[][] jogadasPossiveis = {jogadas0, jogadas1, jogadas2, jogadas3, 
        jogadas4, jogadas5, jogadas6};
    
    public boolean validaJogada(int n){
        boolean ehValido = false;
        int[] jogada = jogadasPossiveis[n-1];
        
        for (int elemento : jogada) {
            if(posicoes[elemento] == 0){
                ehValido = true;
                break;
            }
        }
        
        return ehValido;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        b1 = new javax.swing.JButton();
        b2 = new javax.swing.JButton();
        b3 = new javax.swing.JButton();
        b4 = new javax.swing.JButton();
        b5 = new javax.swing.JButton();
        b6 = new javax.swing.JButton();
        b7 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        msg_area = new javax.swing.JTextArea();
        txtMsg = new javax.swing.JTextField();
        btnSend = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        mensagem = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tsoro Yematatu");
        setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().setLayout(null);

        b1.setBackground(new java.awt.Color(204, 204, 204));
        b1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        b1.setBorderPainted(false);
        b1.setContentAreaFilled(false);
        b1.setFocusPainted(false);
        b1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b1ActionPerformed(evt);
            }
        });
        getContentPane().add(b1);
        b1.setBounds(150, 45, 50, 50);

        b2.setBackground(new java.awt.Color(204, 204, 204));
        b2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        b2.setBorderPainted(false);
        b2.setContentAreaFilled(false);
        b2.setFocusPainted(false);
        b2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b2ActionPerformed(evt);
            }
        });
        getContentPane().add(b2);
        b2.setBounds(80, 190, 50, 50);

        b3.setBackground(new java.awt.Color(204, 204, 204));
        b3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        b3.setBorderPainted(false);
        b3.setContentAreaFilled(false);
        b3.setFocusPainted(false);
        b3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b3ActionPerformed(evt);
            }
        });
        getContentPane().add(b3);
        b3.setBounds(150, 190, 50, 50);

        b4.setBackground(new java.awt.Color(204, 204, 204));
        b4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        b4.setBorderPainted(false);
        b4.setContentAreaFilled(false);
        b4.setFocusPainted(false);
        b4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b4ActionPerformed(evt);
            }
        });
        getContentPane().add(b4);
        b4.setBounds(230, 190, 50, 50);

        b5.setBackground(new java.awt.Color(204, 204, 204));
        b5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        b5.setBorderPainted(false);
        b5.setContentAreaFilled(false);
        b5.setFocusPainted(false);
        b5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b5ActionPerformed(evt);
            }
        });
        getContentPane().add(b5);
        b5.setBounds(30, 298, 50, 50);

        b6.setBackground(new java.awt.Color(204, 204, 204));
        b6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        b6.setBorderPainted(false);
        b6.setContentAreaFilled(false);
        b6.setFocusPainted(false);
        b6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b6ActionPerformed(evt);
            }
        });
        getContentPane().add(b6);
        b6.setBounds(150, 298, 50, 50);

        b7.setBackground(new java.awt.Color(204, 204, 204));
        b7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        b7.setBorderPainted(false);
        b7.setContentAreaFilled(false);
        b7.setFocusPainted(false);
        b7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b7ActionPerformed(evt);
            }
        });
        getContentPane().add(b7);
        b7.setBounds(280, 298, 50, 50);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tabuleiro.png"))); // NOI18N
        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(10, 50, 320, 290);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        msg_area.setColumns(20);
        msg_area.setRows(5);
        jScrollPane2.setViewportView(msg_area);

        btnSend.setText("Enviar");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        jLabel2.setText("Comandos para o chat:");

        jLabel3.setText("/d - Desitir da partida");

        jLabel4.setText("/e - Solicitar ou concordar empate");

        jLabel5.setText("/r - Reinicar a partida");

        mensagem.setEditable(false);
        mensagem.setColumns(20);
        mensagem.setLineWrap(true);
        mensagem.setRows(1);
        mensagem.setText("Tsoro Yematatu");
        mensagem.setWrapStyleWord(true);
        jScrollPane1.setViewportView(mensagem);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(341, 341, 341)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(206, 206, 206)
                                .addComponent(btnSend))
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMsg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSend))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 620, 380);

        setBounds(0, 0, 633, 419);
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

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        // TODO add your handling code here:
        try {
            String msg = "";
            msg = txtMsg.getText();
            msg_area.setText(msg_area.getText() + "\n Eu: " + msg);
            if(msg.equals("/d") && !fimJogo){
                msg_area.setText(msg_area.getText() + "\n Você desistiu! Seu adversario venceu.");
                mensagem.setText("Você desistiu! Seu adversario venceu.");
                mudancaBotoes(0, false);
                mudancaBotoes(1, false);
                mudancaBotoes(2, false);
            }else if(msg.equals("/e") && !fimJogo){
                if(solicitouEmpate){
                    msg_area.setText(msg_area.getText() + "\n Aguarde o adversário concordar com empate.");
                }else if(!fimJogo){
                    solicitouEmpate = true;
                    msg_area.setText(msg_area.getText() + "\n Você solicitou empate ao adversário.");
                }
            }
            chat.dout.writeUTF(msg);
            txtMsg.setText("");
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnSendActionPerformed

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
    private javax.swing.JButton btnSend;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea mensagem;
    private static javax.swing.JTextArea msg_area;
    private javax.swing.JTextField txtMsg;
    // End of variables declaration//GEN-END:variables
}
