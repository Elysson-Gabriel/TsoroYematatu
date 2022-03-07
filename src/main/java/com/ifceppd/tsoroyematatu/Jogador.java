/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.ifceppd.tsoroyematatu;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import javax.swing.JOptionPane;

/**
 *
 * @author elysson
 */
public class Jogador extends javax.swing.JFrame {
    
    private Servico servico;
    private int idJogador;
    private int outroJogador;
    private int[] posicoes;
    private int qtdPecas;
    private int qtdPecasAdv;
    private boolean ativaBotoes;
    private boolean solicitouEmpate;
    private boolean fimJogo;
    private int posicaoVazia;
    
    private int[] possibilidadesCasa0 = {1, 2, 3, 4, 5, 6};
    private int[] possibilidadesCasa1 = {0, 2, 3, 4};
    private int[] possibilidadesCasa2 = {0, 1, 3, 5};
    private int[] possibilidadesCasa3 = {0, 1, 2, 6};
    private int[] possibilidadesCasa4 = {0, 1, 5, 6};
    private int[] possibilidadesCasa5 = {0, 2, 4, 6};
    private int[] possibilidadesCasa6 = {0, 3, 4, 5};
    
    private int[][] jogadasPossiveis = {possibilidadesCasa0, possibilidadesCasa1, possibilidadesCasa2, 
        possibilidadesCasa3, possibilidadesCasa4, possibilidadesCasa5, possibilidadesCasa6};
    
    private int[] vitoria1 = {0, 1, 4};
    private int[] vitoria2 = {0, 2, 5};
    private int[] vitoria3 = {0, 3, 6};
    private int[] vitoria4 = {1, 2, 3};
    private int[] vitoria5 = {4, 5, 6};
    
    private int[][] vitoriasPossiveis = {vitoria1, vitoria2, vitoria3, vitoria4, vitoria5};
    
    /**
     * Creates new form Jogador
     */
    public Jogador() {
        
        posicoes = new int[7];
        qtdPecas = 0;
        qtdPecasAdv = 0;
        solicitouEmpate = false;
        fimJogo = false;
        
        String localizacao = "rmi://localhost/servico";
        try {
            servico = (Servico) Naming.lookup(localizacao);
            idJogador = servico.recebeIdJogador();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro no construtor do jogador: " + e.getMessage());
        }
        
        //Conexão para o jogo e para o chat
        //this.conectaServidor();
        
        initComponents();
        
        Thread threadChat = new Thread(new Runnable(){
            public void run(){
                executaChat();;
            }
        });
        threadChat.start();
        
        this.setTitle("Jogador #" + idJogador + " - Tsoro Yematatu");
        
        if(idJogador == 1){
            titulo.setText("Jogador #1. Aguarde o Jogador #2 se conectar.");
            Color vermelho = new Color(0xdb3c30);
            titulo.setBackground(vermelho);
            outroJogador = 2;
            ativaBotoes = false;
        }else{
            titulo.setText("Jogador #2. Aguarde seu turno.");
            Color azul = new Color(0x3394e8);
            titulo.setBackground(azul);
            outroJogador = 1;
            ativaBotoes = false;
            
            Thread t = new Thread(new Runnable(){
                public void run(){
                    controlaTurno();;
                }
            });
            t.start();
        }

        atualizaEstadoBotoes();
        
    }
    
    public void atualizaEstadoBotoes() {//Habilita os botões de acordo com seu turno
        b1.setEnabled(ativaBotoes);
        b2.setEnabled(ativaBotoes);
        b3.setEnabled(ativaBotoes);
        b4.setEnabled(ativaBotoes);
        b5.setEnabled(ativaBotoes);
        b6.setEnabled(ativaBotoes);
        b7.setEnabled(ativaBotoes);
        
        atualizaImagensBotoes();
        //Desabilita todas os botões que não são vazios
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
        //Após a disposição de peças, desabilita o espaço vazio para clique
        if(qtdPecas == 3 && qtdPecasAdv == 3){
            alteraEstadoBotoes(0, false);
        }
        
    }
    
    public void atualizaImagensBotoes(){
        
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
    }
    
    public void alteraEstadoBotoes(int valorComparacao, boolean estado){
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
    
    public void desabilitaTodosBotoes(){
        for(int i = 0; i < 3; i++){
            alteraEstadoBotoes(i, false);
        }
    }
    
    public void controlaTurno(){        
        //Aguarda seu turno enquanto recebe uma jogada válida do adversário
        //int n = clienteJogo.recebeJogada();
        int n = -1;
        
        try {
            n = servico.recebeJogada(outroJogador);
        } catch (RemoteException e) {
            JOptionPane.showMessageDialog(null, "Erro no controlaTurno do jogador: " + e.getMessage());
        }
        
        if(n != -1){
            titulo.setText("Seu adversário clicou o botão #" + n + ". Sua vez");
            //No início do jogo apenas dispões as peças no tabuleiro
            if(qtdPecasAdv < 3){
                //Atualiza o vetor com a jogada recebida do adversário
                if(idJogador == 1){
                    posicoes[n-1] = 2;
                }else{
                    posicoes[n-1] = 1;
                }
                qtdPecasAdv++;
            }else{//Desenvolvimento do jogo após peças dispostas
                //Procura qual posicao não possui nenhuma peça
                posicaoVazia = posicaoVaziaTabuleiro();
                //Atualiza o vetor trocando a jogada do advserário com a posição vazia
                if(idJogador == 1){//n-1 é a posição do botão na interface
                    posicoes[posicaoVazia] = 2;
                    posicoes[n-1] = 0;
                }else{
                    posicoes[posicaoVazia] = 1;
                    posicoes[n-1] = 0;
                }
            }
            //Só habilita a movimentação de peças após as 6 estiverem dispostas no tabuleiro
            if(qtdPecas == 3 && qtdPecasAdv == 3){
                //Habilita somente as peças do jogador correspondente ao turno
                if(idJogador == 1){
                    alteraEstadoBotoes(1, true);
                    alteraEstadoBotoes(2, false);
                }else{
                    alteraEstadoBotoes(2, true);
                    alteraEstadoBotoes(1, false);
                }

                atualizaImagensBotoes();
            }else{//No início do jogo apenas atualiza o tabuleiro de acordo com as peças colocadas
                ativaBotoes = true;
                atualizaEstadoBotoes();
            }
        }else{//Executa a Thread novamente enquanto não recebe jogada do adversário
            Thread t = new Thread(new Runnable(){
                public void run(){
                    controlaTurno();;
                }
            });
            t.start();
        }
        
    }
    
    public int posicaoVaziaTabuleiro(){
        int posicao = 0;
        for (int i = 0; i < posicoes.length; i++) {
            if(posicoes[i] == 0){
                posicao = i;
            }
        }
        return posicao;
    }
    
    public void executaChat(){
        String msgRecebida = "";
        
        //Execução da thread chat
        while(!msgRecebida.equals("exit")){
            //msgRecebida = clienteChat.recebeMensagem();
            try {
                msgRecebida = servico.recebeMensagem(outroJogador);
            } catch (RemoteException e) {
                JOptionPane.showMessageDialog(null, "Erro no executaChat do jogador: " + e.getMessage());
            }
            if(msgRecebida.equals("/d") && !fimJogo){
                chatArea.setText(chatArea.getText() + "\n Você venceu! Seu adversario desistiu.");
                titulo.setText("Você venceu! Seu adversario desistiu.");
                desabilitaTodosBotoes();
                fimJogo = true;
            }else if(msgRecebida.equals("/e") && !fimJogo){
                if(solicitouEmpate){
                    chatArea.setText(chatArea.getText() + "\n Jogadores concordaram com empate.\n Fim de jogo.");
                    titulo.setText("Fim de jogo! Jogadores concordaram com empate. ");
                    desabilitaTodosBotoes();
                    //clienteChat.enviaMensagem("/e");
                    try {
                        servico.enviaMensagem("/e", idJogador);
                    } catch (RemoteException e) {
                        JOptionPane.showMessageDialog(null, "Erro no executaChat do jogador: " + e.getMessage());
                    }
                    
                    solicitouEmpate = false;
                    fimJogo = true;
                }else {
                    chatArea.setText(chatArea.getText() + "\n Seu adversário solicitou empate\n Envie /e para aceitar.");
                }
            }else if(msgRecebida.equals("/f")){
                chatArea.setText(chatArea.getText() + "\n Fim de jogo. Você perdeu!");
                titulo.setText("Fim de jogo! Você perdeu.");
                desabilitaTodosBotoes();
                fimJogo = true;
            }else if(msgRecebida.equals("/i")){//Quando os dois se conectam o jogador 1 pode jogar
                if(idJogador == 1){
                    ativaBotoes = true;
                    atualizaEstadoBotoes();
                    titulo.setText("Jogador #1. Inicie a partida!");
                    chatArea.setText("Jogador #2 se conectou.");
                }
            }else if(!msgRecebida.isBlank()){
                chatArea.setText(chatArea.getText() + "\n Adversario: " + msgRecebida);
            }
            chatArea.setCaretPosition(chatArea.getText().length());
        }
        
    }
    
    private void verificaVencedor(){
        ativaBotoes = false;
        
        for (int[] vitoria : vitoriasPossiveis) {
            if(posicoes[vitoria[0]] != 0 && posicoes[vitoria[0]] == posicoes[vitoria[1]] &&
                    posicoes[vitoria[1]] == posicoes[vitoria[2]]){
        //Verifica em cada possibilidade se não existe uma posição vazia e se as três possuem o mesmo valor
                titulo.setText("Parabéns você venceu!");
                desabilitaTodosBotoes();
                fimJogo = true;
                //Comunica o outro jogador que ele foi derrotado
                //clienteChat.enviaMensagem("/f");
                try {
                    servico.enviaMensagem("/f", idJogador);
                } catch (RemoteException e) {
                    JOptionPane.showMessageDialog(null, "Erro no verificaVencedor do jogador: " + e.getMessage());
                }
            }
        }

    }
 
    public void cliqueBotao(int n){
        int casaTroca = 0;
        boolean jogadaValida;
        //No início do jogo, apenas marca a posição com a cor de sua peça
        if(qtdPecas < 3){
            jogadaValida = true;
            if(idJogador == 1){
                posicoes[n-1] = 1;
            }else{
                posicoes[n-1] = 2;
            }
            qtdPecas++;
        }else{//Movimentação de peças
            jogadaValida = validaJogada(n);
            
            if(jogadaValida){
                casaTroca = posicaoVaziaTabuleiro();
                if(idJogador == 1){
                    posicoes[casaTroca] = 1;
                    posicoes[n-1] = 0;
                }else{
                    posicoes[casaTroca] = 2;
                    posicoes[n-1] = 0;
                }
            }
        }
        //Só troca o turno quando a jogada é válida, no início todas serão válidas
        if(jogadaValida){
            titulo.setText("Você clicou o botão #" + n + ". Agora a vez do jogador #" + outroJogador);
            
            ativaBotoes = false;
            atualizaEstadoBotoes();
            //clienteJogo.enviaJogada(n);
            
            try {
                servico.enviaJogada(n, idJogador);
            } catch (RemoteException e) {
                JOptionPane.showMessageDialog(null, "Erro no cliqueBotao do jogador: " + e.getMessage());
            }
            
            Thread t = new Thread(new Runnable(){
                public void run(){
                    controlaTurno();;
                }
            });
            t.start();
            //Toda jogada verifica se alguem venceu
            verificaVencedor();
        }
        
    }
    
    public void mudaBotaoParaVazio(javax.swing.JButton botao){
        if(qtdPecas < 3){
            botao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
        }
    }
    
    public void mudaCorBotao(javax.swing.JButton botao){
        if(qtdPecas < 3){
            if(idJogador == 1){
                botao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaVermelha.png")));
            }else{
                botao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaAzul.png")));
            }
        }
    }
    
    public boolean validaJogada(int n){
        boolean ehValido = false;
        int[] possibilidades = jogadasPossiveis[n-1];
        //Verifica se existe uma casa vazia possível de cada peça para que possa se deslocar
        for (int elemento : possibilidades) {
            if(posicoes[elemento] == 0){
                ehValido = true;
                break;
            }
        }
        return ehValido;
    }
    
    public void enviarMensagemChat(){
        String msg = "";
        msg = mensagem.getText();

        if(msg.equals("/d") && !fimJogo){
            chatArea.setText(chatArea.getText() + "\n Você desistiu! Seu adversario venceu.");
            titulo.setText("Você desistiu! Seu adversario venceu.");
            desabilitaTodosBotoes();
            fimJogo = true;
        }else if(msg.equals("/e") && !fimJogo){
            if(solicitouEmpate){
                chatArea.setText(chatArea.getText() + "\n Aguarde o adversário concordar com empate.");
            }else if(!fimJogo){
                solicitouEmpate = true;
                chatArea.setText(chatArea.getText() + "\n Você solicitou empate ao adversário.");
            }
        }else if(!msg.isBlank()){
            chatArea.setText(chatArea.getText() + "\n Eu: " + msg);
        }
        //clienteChat.enviaMensagem(msg);
        
        try {
            servico.enviaMensagem(msg, idJogador);
        } catch (RemoteException e) {
            JOptionPane.showMessageDialog(null, "Erro no enviaMensagemChat do jogador: " + e.getMessage());
        }
        
        mensagem.setText("");
    }
    
    //A partir dessa linha são códigos gerados pela IDE Netbeans, utlizada para criação do projeto.

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
        chatArea = new javax.swing.JTextArea();
        mensagem = new javax.swing.JTextField();
        enviar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        titulo = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tsoro Yematatu");
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);
        getContentPane().setLayout(null);

        b1.setBackground(new java.awt.Color(204, 204, 204));
        b1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        b1.setBorderPainted(false);
        b1.setContentAreaFilled(false);
        b1.setFocusPainted(false);
        b1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                b1MouseExited(evt);
            }
        });
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
        b2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                b2MouseExited(evt);
            }
        });
        b2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b2ActionPerformed(evt);
            }
        });
        getContentPane().add(b2);
        b2.setBounds(75, 190, 50, 50);

        b3.setBackground(new java.awt.Color(204, 204, 204));
        b3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        b3.setBorderPainted(false);
        b3.setContentAreaFilled(false);
        b3.setFocusPainted(false);
        b3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                b3MouseExited(evt);
            }
        });
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
        b4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                b4MouseExited(evt);
            }
        });
        b4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b4ActionPerformed(evt);
            }
        });
        getContentPane().add(b4);
        b4.setBounds(225, 190, 50, 50);

        b5.setBackground(new java.awt.Color(204, 204, 204));
        b5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        b5.setBorderPainted(false);
        b5.setContentAreaFilled(false);
        b5.setFocusPainted(false);
        b5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                b5MouseExited(evt);
            }
        });
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
        b6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                b6MouseExited(evt);
            }
        });
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
        b7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                b7MouseExited(evt);
            }
        });
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

        chatArea.setEditable(false);
        chatArea.setColumns(20);
        chatArea.setRows(5);
        jScrollPane2.setViewportView(chatArea);

        mensagem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                mensagemKeyPressed(evt);
            }
        });

        enviar.setText("Enviar");
        enviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enviarActionPerformed(evt);
            }
        });

        jLabel2.setText("Comandos para o chat:");

        jLabel3.setText("/d - Desitir da partida");

        jLabel4.setText("/e - Solicitar ou concordar empate");

        titulo.setEditable(false);
        titulo.setBackground(new java.awt.Color(0, 0, 0));
        titulo.setColumns(20);
        titulo.setFont(new java.awt.Font("Arial Black", 1, 11)); // NOI18N
        titulo.setForeground(new java.awt.Color(255, 255, 255));
        titulo.setLineWrap(true);
        titulo.setRows(1);
        titulo.setText("Tsoro Yematatu");
        titulo.setWrapStyleWord(true);
        jScrollPane1.setViewportView(titulo);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(mensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(enviar))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(341, 341, 341)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mensagem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(enviar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addContainerGap(52, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 620, 380);

        setBounds(0, 0, 635, 419);
    }// </editor-fold>//GEN-END:initComponents

    private void b1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b1ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(1);
    }//GEN-LAST:event_b1ActionPerformed

    private void b2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b2ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(2);
    }//GEN-LAST:event_b2ActionPerformed

    private void b3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b3ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(3);
    }//GEN-LAST:event_b3ActionPerformed

    private void b4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b4ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(4);
    }//GEN-LAST:event_b4ActionPerformed

    private void b5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b5ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(5);
    }//GEN-LAST:event_b5ActionPerformed

    private void b6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b6ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(6);
    }//GEN-LAST:event_b6ActionPerformed

    private void b7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b7ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(7);
    }//GEN-LAST:event_b7ActionPerformed

    private void enviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enviarActionPerformed
        // TODO add your handling code here:
        enviarMensagemChat();
    }//GEN-LAST:event_enviarActionPerformed

    private void mensagemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mensagemKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == evt.VK_ENTER){
            enviarMensagemChat();
        }
    }//GEN-LAST:event_mensagemKeyPressed

    private void b1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b1MouseEntered
        // TODO add your handling code here:
        mudaCorBotao(b1);
    }//GEN-LAST:event_b1MouseEntered

    private void b1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b1MouseExited
        // TODO add your handling code here:
        mudaBotaoParaVazio(b1);
    }//GEN-LAST:event_b1MouseExited

    private void b2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b2MouseEntered
        // TODO add your handling code here:
        mudaCorBotao(b2);
    }//GEN-LAST:event_b2MouseEntered

    private void b2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b2MouseExited
        // TODO add your handling code here:
        mudaBotaoParaVazio(b2);
    }//GEN-LAST:event_b2MouseExited

    private void b3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b3MouseEntered
        // TODO add your handling code here:
        mudaCorBotao(b3);
    }//GEN-LAST:event_b3MouseEntered

    private void b3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b3MouseExited
        // TODO add your handling code here:
        mudaBotaoParaVazio(b3);
    }//GEN-LAST:event_b3MouseExited

    private void b4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b4MouseEntered
        // TODO add your handling code here:
        mudaCorBotao(b4);
    }//GEN-LAST:event_b4MouseEntered

    private void b4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b4MouseExited
        // TODO add your handling code here:
        mudaBotaoParaVazio(b4);
    }//GEN-LAST:event_b4MouseExited

    private void b5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b5MouseEntered
        // TODO add your handling code here:
        mudaCorBotao(b5);
    }//GEN-LAST:event_b5MouseEntered

    private void b5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b5MouseExited
        // TODO add your handling code here:
        mudaBotaoParaVazio(b5);
    }//GEN-LAST:event_b5MouseExited

    private void b6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b6MouseEntered
        // TODO add your handling code here:
        mudaCorBotao(b6);
    }//GEN-LAST:event_b6MouseEntered

    private void b6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b6MouseExited
        // TODO add your handling code here:
        mudaBotaoParaVazio(b6);
    }//GEN-LAST:event_b6MouseExited

    private void b7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b7MouseEntered
        // TODO add your handling code here:
        mudaCorBotao(b7);
    }//GEN-LAST:event_b7MouseEntered

    private void b7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b7MouseExited
        // TODO add your handling code here:
        mudaBotaoParaVazio(b7);
    }//GEN-LAST:event_b7MouseExited

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
    private static javax.swing.JTextArea chatArea;
    private javax.swing.JButton enviar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField mensagem;
    private javax.swing.JTextArea titulo;
    // End of variables declaration//GEN-END:variables
}
