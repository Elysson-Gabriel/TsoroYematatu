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

/**
 *
 * @author elysson
 */
public class Player extends javax.swing.JFrame {
    
    private ConexaoJogo clienteJogo;
    private ConexaoChat clienteChat;
    private int idJogador;
    private int outroJogador;
    private int[] posicoes;
    private int[][] tabuleiro;
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
    public Player() {
        
        posicoes = new int[7];
        tabuleiro = new int[8][8];
        qtdPecas = 0;
        qtdPecasAdv = 0;
        solicitouEmpate = false;
        fimJogo = false;
        
        //Conexão para o jogo e para o chat
        this.conectaServidor();
        
        initComponents();
        
        tabuleiro[3][3] = 2;
        tabuleiro[3][4] = 1;
        tabuleiro[4][3] = 1;
        tabuleiro[4][4] = 2;
        
        Thread threadChat = new Thread(new Runnable(){
            public void run(){
                executaChat();;
            }
        });
        threadChat.start();
        
        this.setTitle("Jogador #" + idJogador + " - Otello Reversi");
        
        if(idJogador == 1){
            titulo.setText("Jogador #1. Aguarde o Jogador #2 se conectar.");
            Color preto = new Color(0x000000);
            titulo.setBackground(preto);
            outroJogador = 2;
            ativaBotoes = false;
        }else{
            titulo.setText("Jogador #2. Aguarde seu turno.");
            Color branco = new Color(0xffffff);
            titulo.setBackground(branco);
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
        
        
        
        bd4.setEnabled(true);
        be5.setEnabled(true);
        be4.setEnabled(true);
        bd5.setEnabled(true);
        
    }
    
    public void conectaServidor(){
        clienteJogo = new ConexaoJogo();
        clienteChat = new ConexaoChat();
    }
    
    public void atualizaEstadoBotoes() {//Habilita os botões de acordo com seu turno
        ba1.setEnabled(ativaBotoes);
        ba2.setEnabled(ativaBotoes);
        ba3.setEnabled(ativaBotoes);
        ba4.setEnabled(ativaBotoes);
        ba5.setEnabled(ativaBotoes);
        ba6.setEnabled(ativaBotoes);
        ba7.setEnabled(ativaBotoes);
        ba8.setEnabled(ativaBotoes);
        bb1.setEnabled(ativaBotoes);
        bb2.setEnabled(ativaBotoes);
        bb3.setEnabled(ativaBotoes);
        bb4.setEnabled(ativaBotoes);
        bb5.setEnabled(ativaBotoes);
        bb6.setEnabled(ativaBotoes);
        bb7.setEnabled(ativaBotoes);
        bb8.setEnabled(ativaBotoes);
        bc1.setEnabled(ativaBotoes);
        bc2.setEnabled(ativaBotoes);
        bc3.setEnabled(ativaBotoes);
        bc4.setEnabled(ativaBotoes);
        bc5.setEnabled(ativaBotoes);
        bc6.setEnabled(ativaBotoes);
        bc7.setEnabled(ativaBotoes);
        bc8.setEnabled(ativaBotoes);
        bd1.setEnabled(ativaBotoes);
        bd2.setEnabled(ativaBotoes);
        bd3.setEnabled(ativaBotoes);
        bd4.setEnabled(ativaBotoes);
        bd5.setEnabled(ativaBotoes);
        bd6.setEnabled(ativaBotoes);
        bd7.setEnabled(ativaBotoes);
        bd8.setEnabled(ativaBotoes);
        be1.setEnabled(ativaBotoes);
        be2.setEnabled(ativaBotoes);
        be3.setEnabled(ativaBotoes);
        be4.setEnabled(ativaBotoes);
        be5.setEnabled(ativaBotoes);
        be6.setEnabled(ativaBotoes);
        be7.setEnabled(ativaBotoes);
        be8.setEnabled(ativaBotoes);
        bf1.setEnabled(ativaBotoes);
        bf2.setEnabled(ativaBotoes);
        bf3.setEnabled(ativaBotoes);
        bf4.setEnabled(ativaBotoes);
        bf5.setEnabled(ativaBotoes);
        bf6.setEnabled(ativaBotoes);
        bf7.setEnabled(ativaBotoes);
        bf8.setEnabled(ativaBotoes);
        bg1.setEnabled(ativaBotoes);
        bg2.setEnabled(ativaBotoes);
        bg3.setEnabled(ativaBotoes);
        bg4.setEnabled(ativaBotoes);
        bg5.setEnabled(ativaBotoes);
        bg6.setEnabled(ativaBotoes);
        bg7.setEnabled(ativaBotoes);
        bg8.setEnabled(ativaBotoes);
        bh1.setEnabled(ativaBotoes);
        bh2.setEnabled(ativaBotoes);
        bh3.setEnabled(ativaBotoes);
        bh4.setEnabled(ativaBotoes);
        bh5.setEnabled(ativaBotoes);
        bh6.setEnabled(ativaBotoes);
        bh7.setEnabled(ativaBotoes);
        bh8.setEnabled(ativaBotoes);
        
        atualizaImagensBotoes();
        
        //Desabilita todas os botões que não são vazios
        /*if(posicoes[0] > 0){
            ba2.setEnabled(false);
        }
        if(posicoes[1] > 0){
            bb2.setEnabled(false);
        }
        if(posicoes[2] > 0){
            bc2.setEnabled(false);
        }
        if(posicoes[3] > 0){
            bd2.setEnabled(false);
        }
        if(posicoes[4] > 0){
            be2.setEnabled(false);
        }
        if(posicoes[5] > 0){
            bf2.setEnabled(false);
        }
        if(posicoes[6] > 0){
            bg2.setEnabled(false);
        }*/
        
    }
    
    public void jogadaInicial() {//Habilita o primeiro movimento
        bd3.setEnabled(ativaBotoes);
        bc4.setEnabled(ativaBotoes);
        bf5.setEnabled(ativaBotoes);
        be6.setEnabled(ativaBotoes);
        bd3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
        bc4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
        bf5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
        be6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
        
        tabuleiro[2][3] = 9;
        tabuleiro[3][2] = 9;
        tabuleiro[4][5] = 9;
        tabuleiro[5][4] = 9;

        
    }
    
    public void atualizaImagensBotoes(){
        
        switch (tabuleiro[0][0]) {
            case 1:
                ba1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                ba1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				ba1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                ba1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[0][1]) {
            case 1:
                bb1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bb1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bb1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bb1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[0][2]) {
            case 1:
                bc1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bc1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bc1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bc1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[0][3]) {
            case 1:
                bd1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bd1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bd1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bd1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[0][4]) {
            case 1:
                be1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                be1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				be1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                be1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[0][5]) {
            case 1:
                bf1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bf1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bf1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bf1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[0][6]) {
            case 1:
                bg1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bg1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bg1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bg1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[0][7]) {
            case 1:
                bh1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bh1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bh1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bh1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[1][0]) {
            case 1:
                ba2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                ba2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				ba2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                ba2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[1][1]) {
            case 1:
                bb2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bb2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bb2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bb2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[1][2]) {
            case 1:
                bc2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bc2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bc2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bc2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[1][3]) {
            case 1:
                bd2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bd2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bd2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bd2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[1][4]) {
            case 1:
                be2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                be2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				be2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                be2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[1][5]) {
            case 1:
                bf2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bf2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bf2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bf2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[1][6]) {
            case 1:
                bg2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bg2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bg2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bg2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[1][7]) {
            case 1:
                bh2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bh2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bh2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bh2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[2][0]) {
            case 1:
                ba3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                ba3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				ba3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                ba3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[2][1]) {
            case 1:
                bb3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bb3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bb3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bb3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[2][2]) {
            case 1:
                bc3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bc3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bc3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bc3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[2][3]) {
            case 1:
                bd3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bd3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bd3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bd3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[2][4]) {
            case 1:
                be3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                be3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				be3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                be3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[2][5]) {
            case 1:
                bf3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bf3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bf3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bf3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[2][6]) {
            case 1:
                bg3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bg3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bg3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bg3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[2][7]) {
            case 1:
                bh3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bh3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bh3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bh3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[3][0]) {
            case 1:
                ba4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                ba4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				ba4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                ba4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[3][1]) {
            case 1:
                bb4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bb4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bb4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bb4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[3][2]) {
            case 1:
                bc4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bc4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bc4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bc4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[3][3]) {
            case 1:
                bd4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bd4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bd4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bd4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[3][4]) {
            case 1:
                be4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                be4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				be4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                be4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[3][5]) {
            case 1:
                bf4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bf4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bf4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bf4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[3][6]) {
            case 1:
                bg4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bg4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bg4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bg4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[3][7]) {
            case 1:
                bh4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bh4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bh4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bh4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[4][0]) {
            case 1:
                ba5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                ba5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				ba5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                ba5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[4][1]) {
            case 1:
                bb5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bb5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bb5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bb5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[4][2]) {
            case 1:
                bc5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bc5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bc5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bc5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[4][3]) {
            case 1:
                bd5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bd5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bd5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bd5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[4][4]) {
            case 1:
                be5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                be5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				be5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                be5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[4][5]) {
            case 1:
                bf5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bf5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bf5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bf5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[4][6]) {
            case 1:
                bg5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bg5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bg5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bg5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[4][7]) {
            case 1:
                bh5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bh5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bh5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bh5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[5][0]) {
            case 1:
                ba6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                ba6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				ba6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                ba6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[5][1]) {
            case 1:
                bb6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bb6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bb6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bb6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[5][2]) {
            case 1:
                bc6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bc6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bc6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bc6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[5][3]) {
            case 1:
                bd6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bd6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bd6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bd6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[5][4]) {
            case 1:
                be6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                be6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				be6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                be6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[5][5]) {
            case 1:
                bf6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bf6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bf6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bf6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[5][6]) {
            case 1:
                bg6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bg6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bg6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bg6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[5][7]) {
            case 1:
                bh6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bh6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bh6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bh6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[6][0]) {
            case 1:
                ba7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                ba7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				ba7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                ba7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[6][1]) {
            case 1:
                bb7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bb7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bb7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bb7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[6][2]) {
            case 1:
                bc7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bc7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bc7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bc7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[6][3]) {
            case 1:
                bd7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bd7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bd7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bd7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[6][4]) {
            case 1:
                be7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                be7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				be7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                be7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[6][5]) {
            case 1:
                bf7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bf7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bf7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bf7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[6][6]) {
            case 1:
                bg7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bg7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bg7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bg7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[6][7]) {
            case 1:
                bh7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bh7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bh7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bh7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[7][0]) {
            case 1:
                ba8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                ba8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				ba8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                ba8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[7][1]) {
            case 1:
                bb8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bb8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bb8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bb8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[7][2]) {
            case 1:
                bc8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bc8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bc8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bc8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[7][3]) {
            case 1:
                bd8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bd8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bd8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bd8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[7][4]) {
            case 1:
                be8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                be8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				be8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                be8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[7][5]) {
            case 1:
                bf8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bf8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bf8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bf8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[7][6]) {
            case 1:
                bg8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bg8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bg8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bg8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
        
        switch (tabuleiro[7][7]) {
            case 1:
                bh8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
                break;
            case 2:
                bh8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
                break;
            case 9:
				bh8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png")));
				break;
			default:
                bh8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
                break;
        }
    }
    
    public void controlaTurno(){        
        //Aguarda seu turno enquanto recebe uma jogada válida do adversário
        int jogada = clienteJogo.recebeJogada();
        String nm = jogada + "";
        int n = 0;
        int m = 0;
        
        if(nm.length()>1){
            n = Integer.parseInt(nm.substring(0,1));
            m = Integer.parseInt(nm.substring(1,2));
        }else{
            m = Integer.parseInt(nm.substring(0,0));
        }
        
        viraPecas(outroJogador, idJogador, n, m);

        titulo.setText("Seu adversário clicou o botão #[" + n + "][" + m + "]. Sua vez");
        
        //Desenvolvimento do jogo após peças dispostas
        //Procura qual posicao não possui nenhuma peça
        //posicaoVazia = posicaoVaziaTabuleiro();
        //Atualiza o vetor trocando a jogada do advserário com a posição vazia
        if(idJogador == 1){//n-1 é a posição do botão na interface
            tabuleiro[n][m] = 2;
        }else{
            tabuleiro[n][m] = 1;
        }
        
        mostraPosicoesValidas(idJogador);
        
        //No início do jogo apenas atualiza o tabuleiro de acordo com as peças colocadas
        ativaBotoes = true;
        atualizaEstadoBotoes();
        
    }
    
    public void mostraPosicoesValidas(int idJogador){
        for(int i = 0; i< 8; i++){
            for(int j =0; j< 8; j++){
                if(tabuleiro[i][j] == idJogador){
                    procuraHorizontal(i, j);
                    procuraVertical(i, j);
                    procuraDiagonal1(i, j);
                    procuraDiagonal2(i, j);
                }
            }
        }
    }
    
    public void inverterHorizontal(int novo, int adversario, int lin, int col){
        int limite = -1;
        
        for(int i = col+1; i < 8; i++){
            if(tabuleiro[lin][i] == novo){
                limite = i;
                break;
            }
        }
        for(int k = col+1; k < limite; k++){
            
            if(tabuleiro[lin][k] == adversario){
                tabuleiro[lin][k] = novo;
                continue;
            }
            
            if(tabuleiro[lin][k] == novo){
                break;
            }
        }
        
        limite = 99;
        
        for(int i = col-1; i > 0; i--){
            if(tabuleiro[lin][i] == novo){
                limite = i;
                break;
            }
        }
        for(int l = col-1; l >= limite; l--){
            if(tabuleiro[lin][l] == adversario){
                tabuleiro[lin][l] = novo;
                continue;
            }
            
            if(tabuleiro[lin][l] == novo){
                break;
            }
        }
    }
    
    public void inverterVertical(int novo, int adversario, int lin, int col){
        int limite = -1;
        
        for(int i = lin+1; i < 8; i++){
            if(tabuleiro[i][col] == novo){
                limite = i;
                break;
            }
        }
        for(int k = lin+1; k < limite; k++){
            if(tabuleiro[k][col] == adversario){
                tabuleiro[k][col] = novo;
                continue;
            }
            
            if(tabuleiro[k][col] == novo){
                break;
            }
        }
        
        limite = 99;
        for(int i = lin-1; i > 0; i--){
            if(tabuleiro[i][col] == novo){
                limite = i;
                break;
            }
        }
        for(int l = lin-1; l >= limite; l--){
            if(tabuleiro[l][col] == adversario){
                tabuleiro[l][col] = novo;
                continue;
            }
            
            if(tabuleiro[l][col] == novo){
                break;
            }
        }
    }
    
    public void inverterDiagonal1(int novo, int adversario, int lin, int col){
        int i = lin + 1;
        int j = col + 1;
        
        int limiteX = -1;
        int limiteY = -1;
        
        while(i < 7 && j < 7){
            if(tabuleiro[i][j] == novo){
                limiteX = i;
                limiteY = j;
                break;
            }
            i++;
            j++;
        }
        
        i = lin + 1;
        j = col + 1;
        
        while(i < limiteX && j < limiteY){
            if(tabuleiro[i][j] == adversario){
                tabuleiro[i][j] = novo;
                i++;
                j++;
                continue;
            }
            
            if(tabuleiro[i][j] == novo){
                break;
            }
            
            i++;
            j++;
        }
        
        i = lin - 1;
        j = col - 1;
        
        limiteX = 99;
        limiteY = 99;
        
        while(i > 0 && j > 0){
            if(tabuleiro[i][j] == novo){
                limiteX = i;
                limiteY = j;
                break;
            }
            i--;
            j--;
        }
        
        i = lin - 1;
        j = col - 1;
        
        while(i >= limiteX && j >= limiteY){
            
            if(tabuleiro[i][j] == adversario){
                tabuleiro[i][j] = novo;
                i--;
                j--;
                continue;
            }
            
            if(tabuleiro[i][j] == novo){
                break;
            }
            
            i--;
            j--;
        }
        
    }
    
    public void inverterDiagonal2(int novo, int adversario, int lin, int col){
        int i = lin - 1;
        int j = col + 1;
        
        int limiteX = 99;
        int limiteY = -1;
        
        while(i > 0 && j < 7){
            if(tabuleiro[i][j] == novo){
                limiteX = i;
                limiteY = j;
                break;
            }
            i--;
            j++;
        }
        
        i = lin - 1;
        j = col + 1;
        
        while(i >= limiteX && j < limiteY){
            if(tabuleiro[i][j] == adversario){
                tabuleiro[i][j] = novo;
                i--;
                j++;
                continue;
            }
            
            if(tabuleiro[i][j] == novo){
                break;
            }
            
            i--;
            j++;
        }
        
        i = lin + 1;
        j = col - 1;
        
        limiteX = 0;
        limiteY = 99;
        
        while(i < 7 && j > 0){
            if(tabuleiro[i][j] == novo){
                limiteX = i;
                limiteY = j;
                break;
            }
            i++;
            j--;
        }
        
        i = lin + 1;
        j = col - 1;
        
        while(i < limiteX && j >= limiteY){
            
            if(tabuleiro[i][j] == adversario){
                tabuleiro[i][j] = novo;
                i++;
                j--;
                continue;
            }
            
            if(tabuleiro[i][j] == novo){
                break;
            }
            
            i++;
            j--;
        }
        
    }
    
    public void procuraHorizontal(int lin, int col){
        boolean marca = false;
        for(int k = col+1; k < 8; k++){
            if(tabuleiro[lin][k] == idJogador || tabuleiro[lin][k] == 9){
                break;
            }
            if(tabuleiro[lin][k] == outroJogador){
                marca = true;
                continue;
            }
            if(marca && tabuleiro[lin][k] != 9){
                tabuleiro[lin][k] = 9;
                break;
            }
        }
        
        marca = false;
        for(int l = col-1; l > 0; l--){
            if(tabuleiro[lin][l] == idJogador || tabuleiro[lin][l] == 9){
                break;
            }
            if(tabuleiro[lin][l] == outroJogador){
                marca = true;
                continue;
            }
            if(marca && tabuleiro[lin][l] != 9){
                tabuleiro[lin][l] = 9;
                break;
            }
        }
    }
    
    public void procuraVertical(int lin, int col){
        boolean marca = false;
        for(int k = lin+1; k < 8; k++){
            if(tabuleiro[k][col] == idJogador || tabuleiro[k][col] == 9){
                break;
            }
            if(tabuleiro[k][col] == outroJogador){
                marca = true;
                continue;
            }
            if(marca && tabuleiro[k][col] != 9){
                tabuleiro[k][col] = 9;
                break;
            }
        }
        
        marca = false;
        for(int l = lin-1; l > 0; l--){
            if(tabuleiro[l][col] == idJogador || tabuleiro[l][col] == 9){
                break;
            }
            if(tabuleiro[l][col] == outroJogador){
                marca = true;
                continue;
            }
            if(marca && tabuleiro[l][col] != 9){
                tabuleiro[l][col] = 9;
                break;
            }
        }
    }
    
    public void procuraDiagonal1(int lin, int col){
        boolean marca = false;
        int i = lin + 1;
        int j = col + 1;
        while(i < 7 && j < 7){
            if(tabuleiro[i][j] == idJogador || tabuleiro[i][j] == 9){
                break;
            }
            if(tabuleiro[i][j] == outroJogador){
                marca = true;
                i++;
                j++;
                continue;
            }
            if(marca && tabuleiro[i][j] != 9){
                tabuleiro[i][j] = 9;
                break;
            }
            
            i++;
            j++;
        }
        
        marca = false;
        i = lin - 1;
        j = col - 1;
        while(i > 0 && j > 0){
            if(tabuleiro[i][j] == idJogador || tabuleiro[i][j] == 9){
                break;
            }
            if(tabuleiro[i][j] == outroJogador){
                marca = true;
                i--;
                j--;
                continue;
            }
            if(marca && tabuleiro[i][j] != 9){
                tabuleiro[i][j] = 9;
                break;
            }
            
            i--;
            j--;
        }
    }
    
    public void procuraDiagonal2(int lin, int col){
        boolean marca = false;
        int i = lin - 1;
        int j = col + 1;
        while(i > 0 && j < 7){
            if(tabuleiro[i][j] == idJogador || tabuleiro[i][j] == 9){
                break;
            }
            if(tabuleiro[i][j] == outroJogador){
                marca = true;
                i--;
                j++;
                continue;
            }
            if(marca && tabuleiro[i][j] != 9){
                tabuleiro[i][j] = 9;
                break;
            }
            
            i--;
            j++;
        }
        
        marca = false;
        i = lin + 1;
        j = col - 1;
        while(i < 7 && j > 0){
            if(tabuleiro[i][j] == idJogador || tabuleiro[i][j] == 9){
                break;
            }
            if(tabuleiro[i][j] == outroJogador){
                marca = true;
                i++;
                j--;
                continue;
            }
            if(marca && tabuleiro[i][j] != 9){
                tabuleiro[i][j] = 9;
                break;
            }
            
            i++;
            j--;
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
            msgRecebida = clienteChat.recebeMensagem();
            
            if(msgRecebida.equals("/d") && !fimJogo){
                chatArea.setText(chatArea.getText() + "\n Você venceu! Seu adversario desistiu.");
                titulo.setText("Você venceu! Seu adversario desistiu.");
                fimJogo = true;
            }else if(msgRecebida.equals("/e") && !fimJogo){
                if(solicitouEmpate){
                    chatArea.setText(chatArea.getText() + "\n Jogadores concordaram com empate.\n Fim de jogo.");
                    titulo.setText("Fim de jogo! Jogadores concordaram com empate. ");
                    clienteChat.enviaMensagem("/e");
                    solicitouEmpate = false;
                    fimJogo = true;
                }else {
                    chatArea.setText(chatArea.getText() + "\n Seu adversário solicitou empate\n Envie /e para aceitar.");
                }
            }else if(msgRecebida.equals("/f")){
                chatArea.setText(chatArea.getText() + "\n Fim de jogo. Você perdeu!");
                titulo.setText("Fim de jogo! Você perdeu.");
                fimJogo = true;
            }else if(msgRecebida.equals("/i")){//Quando os dois se conectam o jogador 1 pode jogar
                if(idJogador == 1){
                    ativaBotoes = true;
                    //atualizaEstadoBotoes();
                    jogadaInicial();
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
                fimJogo = true;
                //Comunica o outro jogador que ele foi derrotado
                clienteChat.enviaMensagem("/f");
            }
        }

    }
 
    public void cliqueBotao(int n, int m){
        //int casaTroca = 0;
        //boolean jogadaValida;
        
        //Movimentação de peças
        //jogadaValida = validaJogada(n);

        //casaTroca = posicaoVaziaTabuleiro();
        
        if(tabuleiro[n][m] != 9){
            return;
        }
        
        if(idJogador == 1){
            //posicoes[casaTroca] = 1;
            tabuleiro[n][m] = 1;
        }else{
            //posicoes[casaTroca] = 2;
            tabuleiro[n][m] = 2;
        }
        viraPecas(idJogador, outroJogador, n, m);
            
        titulo.setText("Você clicou o botão #" + n + ". Agora a vez do jogador #" + outroJogador);
            
        //ativaBotoes = false;
        zeraJogadasPossiveis();
        atualizaImagensBotoes();//atualizaEstadoBotoes();
        clienteJogo.enviaJogada(n * 10 + m);

        Thread t = new Thread(new Runnable(){
            public void run(){
                controlaTurno();;
            }
        });
        t.start();
        //Toda jogada verifica se alguem venceu
        verificaVencedor();
        
    }
    
    public void viraPecas(int novo, int adversario, int n, int m){
        inverterHorizontal(novo, adversario, n, m);
        inverterVertical(novo, adversario, n, m);
        inverterDiagonal1(novo, adversario, n, m);
        inverterDiagonal2(novo, adversario, n, m);
    }
    
    public void zeraJogadasPossiveis(){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(tabuleiro[i][j] == 9){
                    tabuleiro[i][j] = -1;
                }
            }
        }
    }
    
    public void mudaBotaoParaVazio(javax.swing.JButton botao){
        if(qtdPecas < 3){
            botao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png")));
        }
    }
    
    public void mudaCorBotao(javax.swing.JButton botao){
        if(idJogador == 1 && botao.isEnabled()){
            botao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png")));
        }else if(botao.isEnabled()){
            botao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png")));
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
        clienteChat.enviaMensagem(msg);
        mensagem.setText("");
    }
    
    private class ConexaoJogo {
    
        private Socket socket;
        private DataInputStream entrada;
        private DataOutputStream saida;
        
        public ConexaoJogo(){
            System.out.println("----- Cliente -----");

            try{
                //Se conecta via socket ao jogo do servidor
                socket = new Socket("localhost", 51734);
                entrada = new DataInputStream(socket.getInputStream());
                saida = new DataOutputStream(socket.getOutputStream());
                //Recebe seu id de conexão
                idJogador = entrada.readInt();
                System.out.println("Conectado ao servidor como Jogador #" + idJogador + ".");
            } catch (IOException ex) {
                System.out.println("Erro no construtor do ConexaoJogo");
            }
        }
        
        public void enviaJogada(int n){
            try{
                saida.writeInt(n);
                saida.flush();
            } catch (IOException ex) {
                System.out.println("Erro no enviaJogada() do Jogador");
            }
        }
        
        public int recebeJogada(){
            int jogada = -1;
            try{
                jogada = entrada.readInt();
                System.out.println("Jogador #" + outroJogador + " clicou o botão #" + jogada);
            } catch (IOException ex) {
                System.out.println("Erro no recebeJogada() do Jogador");
            }
            
            return jogada;
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
    
        private Socket socket;
        private DataInputStream entrada;
        private DataOutputStream saida;
        
        public ConexaoChat(){
            try{
                //Se conecta via socket ao chat do servidor
                socket = new Socket("localhost", 51738);
                entrada = new DataInputStream(socket.getInputStream());
                saida = new DataOutputStream(socket.getOutputStream());
            } catch (IOException ex) {
                System.out.println("Erro no construtor do chat");
            }
        }
        
        public void enviaMensagem(String msg){
            try{
                saida.writeUTF(msg);
                saida.flush();
            } catch (IOException ex) {
                System.out.println("Erro no enviaMensagem() do Cliente");
            }
        }
        
        public String recebeMensagem(){
            String msg = "";
            try{
                msg = entrada.readUTF();
            } catch (IOException ex) {
                System.out.println("Erro no recebeMensagem() do Cliente");
                System.exit(0);
            }
            
            return msg;
        }
        
        public void fechaConexao(){
           try{
                socket.close();
                System.out.println("-----CONEXÃO ENCERRADA-----");
            } catch (IOException ex) {
                System.out.println("Erro no fechaConexao() do ConexaoChat");
            } 
        }
        
    }//A partir dessa linha são códigos gerados pela IDE Netbeans, utlizada para criação do projeto.

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ba2 = new javax.swing.JButton();
        bb2 = new javax.swing.JButton();
        bc2 = new javax.swing.JButton();
        bd2 = new javax.swing.JButton();
        be2 = new javax.swing.JButton();
        bf2 = new javax.swing.JButton();
        bg2 = new javax.swing.JButton();
        bh2 = new javax.swing.JButton();
        ba1 = new javax.swing.JButton();
        bb1 = new javax.swing.JButton();
        bc1 = new javax.swing.JButton();
        bd1 = new javax.swing.JButton();
        be1 = new javax.swing.JButton();
        bf1 = new javax.swing.JButton();
        bg1 = new javax.swing.JButton();
        bh1 = new javax.swing.JButton();
        bb4 = new javax.swing.JButton();
        bc4 = new javax.swing.JButton();
        bd4 = new javax.swing.JButton();
        be4 = new javax.swing.JButton();
        bf4 = new javax.swing.JButton();
        bg4 = new javax.swing.JButton();
        bh4 = new javax.swing.JButton();
        ba3 = new javax.swing.JButton();
        bb3 = new javax.swing.JButton();
        bc3 = new javax.swing.JButton();
        bd3 = new javax.swing.JButton();
        be3 = new javax.swing.JButton();
        bf3 = new javax.swing.JButton();
        bg3 = new javax.swing.JButton();
        bh3 = new javax.swing.JButton();
        ba4 = new javax.swing.JButton();
        ba6 = new javax.swing.JButton();
        bb6 = new javax.swing.JButton();
        bc6 = new javax.swing.JButton();
        bd6 = new javax.swing.JButton();
        be6 = new javax.swing.JButton();
        bf6 = new javax.swing.JButton();
        bg6 = new javax.swing.JButton();
        bh6 = new javax.swing.JButton();
        ba5 = new javax.swing.JButton();
        bb5 = new javax.swing.JButton();
        bc5 = new javax.swing.JButton();
        bd5 = new javax.swing.JButton();
        be5 = new javax.swing.JButton();
        bf5 = new javax.swing.JButton();
        bg5 = new javax.swing.JButton();
        bh5 = new javax.swing.JButton();
        bb8 = new javax.swing.JButton();
        bc8 = new javax.swing.JButton();
        bd8 = new javax.swing.JButton();
        be8 = new javax.swing.JButton();
        bf8 = new javax.swing.JButton();
        bg8 = new javax.swing.JButton();
        bh8 = new javax.swing.JButton();
        ba7 = new javax.swing.JButton();
        bb7 = new javax.swing.JButton();
        bc7 = new javax.swing.JButton();
        bd7 = new javax.swing.JButton();
        be7 = new javax.swing.JButton();
        bf7 = new javax.swing.JButton();
        bg7 = new javax.swing.JButton();
        bh7 = new javax.swing.JButton();
        ba8 = new javax.swing.JButton();
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

        ba2.setBackground(new java.awt.Color(204, 204, 204));
        ba2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        ba2.setBorderPainted(false);
        ba2.setContentAreaFilled(false);
        ba2.setFocusPainted(false);
        ba2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ba2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ba2MouseExited(evt);
            }
        });
        ba2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ba2ActionPerformed(evt);
            }
        });
        getContentPane().add(ba2);
        ba2.setBounds(30, 120, 50, 50);

        bb2.setBackground(new java.awt.Color(204, 204, 204));
        bb2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bb2.setBorderPainted(false);
        bb2.setContentAreaFilled(false);
        bb2.setFocusPainted(false);
        bb2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bb2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bb2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bb2MouseExited(evt);
            }
        });
        bb2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bb2ActionPerformed(evt);
            }
        });
        getContentPane().add(bb2);
        bb2.setBounds(80, 120, 50, 50);

        bc2.setBackground(new java.awt.Color(204, 204, 204));
        bc2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bc2.setBorderPainted(false);
        bc2.setContentAreaFilled(false);
        bc2.setFocusPainted(false);
        bc2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bc2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bc2MouseExited(evt);
            }
        });
        bc2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bc2ActionPerformed(evt);
            }
        });
        getContentPane().add(bc2);
        bc2.setBounds(120, 120, 50, 50);

        bd2.setBackground(new java.awt.Color(204, 204, 204));
        bd2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bd2.setBorderPainted(false);
        bd2.setContentAreaFilled(false);
        bd2.setFocusPainted(false);
        bd2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bd2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bd2MouseExited(evt);
            }
        });
        bd2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bd2ActionPerformed(evt);
            }
        });
        getContentPane().add(bd2);
        bd2.setBounds(170, 120, 50, 50);

        be2.setBackground(new java.awt.Color(204, 204, 204));
        be2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        be2.setBorderPainted(false);
        be2.setContentAreaFilled(false);
        be2.setFocusPainted(false);
        be2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                be2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                be2MouseExited(evt);
            }
        });
        be2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                be2ActionPerformed(evt);
            }
        });
        getContentPane().add(be2);
        be2.setBounds(210, 120, 50, 50);

        bf2.setBackground(new java.awt.Color(204, 204, 204));
        bf2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bf2.setBorderPainted(false);
        bf2.setContentAreaFilled(false);
        bf2.setFocusPainted(false);
        bf2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bf2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bf2MouseExited(evt);
            }
        });
        bf2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bf2ActionPerformed(evt);
            }
        });
        getContentPane().add(bf2);
        bf2.setBounds(260, 120, 50, 50);

        bg2.setBackground(new java.awt.Color(204, 204, 204));
        bg2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bg2.setBorderPainted(false);
        bg2.setContentAreaFilled(false);
        bg2.setFocusPainted(false);
        bg2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bg2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bg2MouseExited(evt);
            }
        });
        bg2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bg2ActionPerformed(evt);
            }
        });
        getContentPane().add(bg2);
        bg2.setBounds(310, 120, 50, 50);

        bh2.setBackground(new java.awt.Color(204, 204, 204));
        bh2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bh2.setBorderPainted(false);
        bh2.setContentAreaFilled(false);
        bh2.setFocusPainted(false);
        bh2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bh2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bh2MouseExited(evt);
            }
        });
        bh2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bh2ActionPerformed(evt);
            }
        });
        getContentPane().add(bh2);
        bh2.setBounds(350, 120, 50, 50);

        ba1.setBackground(new java.awt.Color(204, 204, 204));
        ba1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        ba1.setBorderPainted(false);
        ba1.setContentAreaFilled(false);
        ba1.setFocusPainted(false);
        ba1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ba1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ba1MouseExited(evt);
            }
        });
        ba1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ba1ActionPerformed(evt);
            }
        });
        getContentPane().add(ba1);
        ba1.setBounds(30, 70, 50, 50);

        bb1.setBackground(new java.awt.Color(204, 204, 204));
        bb1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bb1.setBorderPainted(false);
        bb1.setContentAreaFilled(false);
        bb1.setFocusPainted(false);
        bb1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bb1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bb1MouseExited(evt);
            }
        });
        bb1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bb1ActionPerformed(evt);
            }
        });
        getContentPane().add(bb1);
        bb1.setBounds(80, 70, 50, 50);

        bc1.setBackground(new java.awt.Color(204, 204, 204));
        bc1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bc1.setBorderPainted(false);
        bc1.setContentAreaFilled(false);
        bc1.setFocusPainted(false);
        bc1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bc1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bc1MouseExited(evt);
            }
        });
        bc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bc1ActionPerformed(evt);
            }
        });
        getContentPane().add(bc1);
        bc1.setBounds(120, 70, 50, 50);

        bd1.setBackground(new java.awt.Color(204, 204, 204));
        bd1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bd1.setBorderPainted(false);
        bd1.setContentAreaFilled(false);
        bd1.setFocusPainted(false);
        bd1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bd1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bd1MouseExited(evt);
            }
        });
        bd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bd1ActionPerformed(evt);
            }
        });
        getContentPane().add(bd1);
        bd1.setBounds(170, 70, 50, 50);

        be1.setBackground(new java.awt.Color(204, 204, 204));
        be1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        be1.setBorderPainted(false);
        be1.setContentAreaFilled(false);
        be1.setFocusPainted(false);
        be1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                be1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                be1MouseExited(evt);
            }
        });
        be1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                be1ActionPerformed(evt);
            }
        });
        getContentPane().add(be1);
        be1.setBounds(210, 70, 50, 50);

        bf1.setBackground(new java.awt.Color(204, 204, 204));
        bf1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bf1.setBorderPainted(false);
        bf1.setContentAreaFilled(false);
        bf1.setFocusPainted(false);
        bf1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bf1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bf1MouseExited(evt);
            }
        });
        bf1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bf1ActionPerformed(evt);
            }
        });
        getContentPane().add(bf1);
        bf1.setBounds(260, 70, 50, 50);

        bg1.setBackground(new java.awt.Color(204, 204, 204));
        bg1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bg1.setBorderPainted(false);
        bg1.setContentAreaFilled(false);
        bg1.setFocusPainted(false);
        bg1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bg1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bg1MouseExited(evt);
            }
        });
        bg1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bg1ActionPerformed(evt);
            }
        });
        getContentPane().add(bg1);
        bg1.setBounds(310, 70, 50, 50);

        bh1.setBackground(new java.awt.Color(204, 204, 204));
        bh1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bh1.setBorderPainted(false);
        bh1.setContentAreaFilled(false);
        bh1.setFocusPainted(false);
        bh1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bh1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bh1MouseExited(evt);
            }
        });
        bh1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bh1ActionPerformed(evt);
            }
        });
        getContentPane().add(bh1);
        bh1.setBounds(350, 70, 50, 50);

        bb4.setBackground(new java.awt.Color(204, 204, 204));
        bb4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bb4.setBorderPainted(false);
        bb4.setContentAreaFilled(false);
        bb4.setFocusPainted(false);
        bb4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bb4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bb4MouseExited(evt);
            }
        });
        bb4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bb4ActionPerformed(evt);
            }
        });
        getContentPane().add(bb4);
        bb4.setBounds(80, 210, 50, 50);

        bc4.setBackground(new java.awt.Color(204, 204, 204));
        bc4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png"))); // NOI18N
        bc4.setBorderPainted(false);
        bc4.setContentAreaFilled(false);
        bc4.setFocusPainted(false);
        bc4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bc4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bc4MouseExited(evt);
            }
        });
        bc4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bc4ActionPerformed(evt);
            }
        });
        getContentPane().add(bc4);
        bc4.setBounds(120, 210, 50, 50);

        bd4.setBackground(new java.awt.Color(204, 204, 204));
        bd4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png"))); // NOI18N
        bd4.setBorderPainted(false);
        bd4.setContentAreaFilled(false);
        bd4.setFocusPainted(false);
        bd4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bd4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bd4MouseExited(evt);
            }
        });
        bd4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bd4ActionPerformed(evt);
            }
        });
        getContentPane().add(bd4);
        bd4.setBounds(170, 210, 50, 50);

        be4.setBackground(new java.awt.Color(204, 204, 204));
        be4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png"))); // NOI18N
        be4.setBorderPainted(false);
        be4.setContentAreaFilled(false);
        be4.setFocusPainted(false);
        be4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                be4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                be4MouseExited(evt);
            }
        });
        be4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                be4ActionPerformed(evt);
            }
        });
        getContentPane().add(be4);
        be4.setBounds(210, 210, 50, 50);

        bf4.setBackground(new java.awt.Color(204, 204, 204));
        bf4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bf4.setBorderPainted(false);
        bf4.setContentAreaFilled(false);
        bf4.setFocusPainted(false);
        bf4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bf4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bf4MouseExited(evt);
            }
        });
        bf4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bf4ActionPerformed(evt);
            }
        });
        getContentPane().add(bf4);
        bf4.setBounds(260, 210, 50, 50);

        bg4.setBackground(new java.awt.Color(204, 204, 204));
        bg4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bg4.setBorderPainted(false);
        bg4.setContentAreaFilled(false);
        bg4.setFocusPainted(false);
        bg4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bg4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bg4MouseExited(evt);
            }
        });
        bg4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bg4ActionPerformed(evt);
            }
        });
        getContentPane().add(bg4);
        bg4.setBounds(310, 210, 50, 50);

        bh4.setBackground(new java.awt.Color(204, 204, 204));
        bh4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bh4.setBorderPainted(false);
        bh4.setContentAreaFilled(false);
        bh4.setFocusPainted(false);
        bh4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bh4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bh4MouseExited(evt);
            }
        });
        bh4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bh4ActionPerformed(evt);
            }
        });
        getContentPane().add(bh4);
        bh4.setBounds(350, 210, 50, 50);

        ba3.setBackground(new java.awt.Color(204, 204, 204));
        ba3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        ba3.setBorderPainted(false);
        ba3.setContentAreaFilled(false);
        ba3.setFocusPainted(false);
        ba3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ba3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ba3MouseExited(evt);
            }
        });
        ba3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ba3ActionPerformed(evt);
            }
        });
        getContentPane().add(ba3);
        ba3.setBounds(30, 160, 50, 50);

        bb3.setBackground(new java.awt.Color(204, 204, 204));
        bb3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bb3.setBorderPainted(false);
        bb3.setContentAreaFilled(false);
        bb3.setFocusPainted(false);
        bb3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bb3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bb3MouseExited(evt);
            }
        });
        bb3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bb3ActionPerformed(evt);
            }
        });
        getContentPane().add(bb3);
        bb3.setBounds(80, 160, 50, 50);

        bc3.setBackground(new java.awt.Color(204, 204, 204));
        bc3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bc3.setBorderPainted(false);
        bc3.setContentAreaFilled(false);
        bc3.setFocusPainted(false);
        bc3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bc3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bc3MouseExited(evt);
            }
        });
        bc3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bc3ActionPerformed(evt);
            }
        });
        getContentPane().add(bc3);
        bc3.setBounds(120, 160, 50, 50);

        bd3.setBackground(new java.awt.Color(204, 204, 204));
        bd3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png"))); // NOI18N
        bd3.setBorderPainted(false);
        bd3.setContentAreaFilled(false);
        bd3.setFocusPainted(false);
        bd3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bd3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bd3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bd3MouseExited(evt);
            }
        });
        bd3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bd3ActionPerformed(evt);
            }
        });
        getContentPane().add(bd3);
        bd3.setBounds(170, 160, 50, 50);

        be3.setBackground(new java.awt.Color(204, 204, 204));
        be3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        be3.setBorderPainted(false);
        be3.setContentAreaFilled(false);
        be3.setFocusPainted(false);
        be3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                be3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                be3MouseExited(evt);
            }
        });
        be3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                be3ActionPerformed(evt);
            }
        });
        getContentPane().add(be3);
        be3.setBounds(210, 160, 50, 50);

        bf3.setBackground(new java.awt.Color(204, 204, 204));
        bf3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bf3.setBorderPainted(false);
        bf3.setContentAreaFilled(false);
        bf3.setFocusPainted(false);
        bf3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bf3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bf3MouseExited(evt);
            }
        });
        bf3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bf3ActionPerformed(evt);
            }
        });
        getContentPane().add(bf3);
        bf3.setBounds(260, 160, 50, 50);

        bg3.setBackground(new java.awt.Color(204, 204, 204));
        bg3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bg3.setBorderPainted(false);
        bg3.setContentAreaFilled(false);
        bg3.setFocusPainted(false);
        bg3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bg3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bg3MouseExited(evt);
            }
        });
        bg3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bg3ActionPerformed(evt);
            }
        });
        getContentPane().add(bg3);
        bg3.setBounds(310, 160, 50, 50);

        bh3.setBackground(new java.awt.Color(204, 204, 204));
        bh3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bh3.setBorderPainted(false);
        bh3.setContentAreaFilled(false);
        bh3.setFocusPainted(false);
        bh3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bh3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bh3MouseExited(evt);
            }
        });
        bh3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bh3ActionPerformed(evt);
            }
        });
        getContentPane().add(bh3);
        bh3.setBounds(350, 160, 50, 50);

        ba4.setBackground(new java.awt.Color(204, 204, 204));
        ba4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        ba4.setBorderPainted(false);
        ba4.setContentAreaFilled(false);
        ba4.setFocusPainted(false);
        ba4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ba4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ba4MouseExited(evt);
            }
        });
        ba4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ba4ActionPerformed(evt);
            }
        });
        getContentPane().add(ba4);
        ba4.setBounds(30, 210, 50, 50);

        ba6.setBackground(new java.awt.Color(204, 204, 204));
        ba6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        ba6.setBorderPainted(false);
        ba6.setContentAreaFilled(false);
        ba6.setFocusPainted(false);
        ba6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ba6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ba6MouseExited(evt);
            }
        });
        ba6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ba6ActionPerformed(evt);
            }
        });
        getContentPane().add(ba6);
        ba6.setBounds(30, 300, 50, 50);

        bb6.setBackground(new java.awt.Color(204, 204, 204));
        bb6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bb6.setBorderPainted(false);
        bb6.setContentAreaFilled(false);
        bb6.setFocusPainted(false);
        bb6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bb6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bb6MouseExited(evt);
            }
        });
        bb6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bb6ActionPerformed(evt);
            }
        });
        getContentPane().add(bb6);
        bb6.setBounds(80, 300, 50, 50);

        bc6.setBackground(new java.awt.Color(204, 204, 204));
        bc6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bc6.setBorderPainted(false);
        bc6.setContentAreaFilled(false);
        bc6.setFocusPainted(false);
        bc6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bc6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bc6MouseExited(evt);
            }
        });
        bc6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bc6ActionPerformed(evt);
            }
        });
        getContentPane().add(bc6);
        bc6.setBounds(120, 300, 50, 50);

        bd6.setBackground(new java.awt.Color(204, 204, 204));
        bd6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bd6.setBorderPainted(false);
        bd6.setContentAreaFilled(false);
        bd6.setFocusPainted(false);
        bd6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bd6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bd6MouseExited(evt);
            }
        });
        bd6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bd6ActionPerformed(evt);
            }
        });
        getContentPane().add(bd6);
        bd6.setBounds(170, 300, 50, 50);

        be6.setBackground(new java.awt.Color(204, 204, 204));
        be6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png"))); // NOI18N
        be6.setBorderPainted(false);
        be6.setContentAreaFilled(false);
        be6.setFocusPainted(false);
        be6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                be6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                be6MouseExited(evt);
            }
        });
        be6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                be6ActionPerformed(evt);
            }
        });
        getContentPane().add(be6);
        be6.setBounds(210, 300, 50, 50);

        bf6.setBackground(new java.awt.Color(204, 204, 204));
        bf6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bf6.setBorderPainted(false);
        bf6.setContentAreaFilled(false);
        bf6.setFocusPainted(false);
        bf6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bf6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bf6MouseExited(evt);
            }
        });
        bf6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bf6ActionPerformed(evt);
            }
        });
        getContentPane().add(bf6);
        bf6.setBounds(260, 300, 50, 50);

        bg6.setBackground(new java.awt.Color(204, 204, 204));
        bg6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bg6.setBorderPainted(false);
        bg6.setContentAreaFilled(false);
        bg6.setFocusPainted(false);
        bg6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bg6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bg6MouseExited(evt);
            }
        });
        bg6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bg6ActionPerformed(evt);
            }
        });
        getContentPane().add(bg6);
        bg6.setBounds(310, 300, 50, 50);

        bh6.setBackground(new java.awt.Color(204, 204, 204));
        bh6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bh6.setBorderPainted(false);
        bh6.setContentAreaFilled(false);
        bh6.setFocusPainted(false);
        bh6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bh6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bh6MouseExited(evt);
            }
        });
        bh6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bh6ActionPerformed(evt);
            }
        });
        getContentPane().add(bh6);
        bh6.setBounds(350, 300, 50, 50);

        ba5.setBackground(new java.awt.Color(204, 204, 204));
        ba5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        ba5.setBorderPainted(false);
        ba5.setContentAreaFilled(false);
        ba5.setFocusPainted(false);
        ba5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ba5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ba5MouseExited(evt);
            }
        });
        ba5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ba5ActionPerformed(evt);
            }
        });
        getContentPane().add(ba5);
        ba5.setBounds(30, 250, 50, 50);

        bb5.setBackground(new java.awt.Color(204, 204, 204));
        bb5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bb5.setBorderPainted(false);
        bb5.setContentAreaFilled(false);
        bb5.setFocusPainted(false);
        bb5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bb5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bb5MouseExited(evt);
            }
        });
        bb5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bb5ActionPerformed(evt);
            }
        });
        getContentPane().add(bb5);
        bb5.setBounds(80, 250, 50, 50);

        bc5.setBackground(new java.awt.Color(204, 204, 204));
        bc5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bc5.setBorderPainted(false);
        bc5.setContentAreaFilled(false);
        bc5.setFocusPainted(false);
        bc5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bc5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bc5MouseExited(evt);
            }
        });
        bc5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bc5ActionPerformed(evt);
            }
        });
        getContentPane().add(bc5);
        bc5.setBounds(120, 250, 50, 50);

        bd5.setBackground(new java.awt.Color(204, 204, 204));
        bd5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaPreta.png"))); // NOI18N
        bd5.setBorderPainted(false);
        bd5.setContentAreaFilled(false);
        bd5.setFocusPainted(false);
        bd5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bd5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bd5MouseExited(evt);
            }
        });
        bd5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bd5ActionPerformed(evt);
            }
        });
        getContentPane().add(bd5);
        bd5.setBounds(170, 250, 50, 50);

        be5.setBackground(new java.awt.Color(204, 204, 204));
        be5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pecaBranca.png"))); // NOI18N
        be5.setBorderPainted(false);
        be5.setContentAreaFilled(false);
        be5.setFocusPainted(false);
        be5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                be5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                be5MouseExited(evt);
            }
        });
        be5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                be5ActionPerformed(evt);
            }
        });
        getContentPane().add(be5);
        be5.setBounds(210, 250, 50, 50);

        bf5.setBackground(new java.awt.Color(204, 204, 204));
        bf5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoValido.png"))); // NOI18N
        bf5.setBorderPainted(false);
        bf5.setContentAreaFilled(false);
        bf5.setFocusPainted(false);
        bf5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bf5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bf5MouseExited(evt);
            }
        });
        bf5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bf5ActionPerformed(evt);
            }
        });
        getContentPane().add(bf5);
        bf5.setBounds(260, 250, 50, 50);

        bg5.setBackground(new java.awt.Color(204, 204, 204));
        bg5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bg5.setBorderPainted(false);
        bg5.setContentAreaFilled(false);
        bg5.setFocusPainted(false);
        bg5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bg5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bg5MouseExited(evt);
            }
        });
        bg5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bg5ActionPerformed(evt);
            }
        });
        getContentPane().add(bg5);
        bg5.setBounds(310, 250, 50, 50);

        bh5.setBackground(new java.awt.Color(204, 204, 204));
        bh5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bh5.setBorderPainted(false);
        bh5.setContentAreaFilled(false);
        bh5.setFocusPainted(false);
        bh5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bh5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bh5MouseExited(evt);
            }
        });
        bh5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bh5ActionPerformed(evt);
            }
        });
        getContentPane().add(bh5);
        bh5.setBounds(350, 250, 50, 50);

        bb8.setBackground(new java.awt.Color(204, 204, 204));
        bb8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bb8.setBorderPainted(false);
        bb8.setContentAreaFilled(false);
        bb8.setFocusPainted(false);
        bb8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bb8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bb8MouseExited(evt);
            }
        });
        bb8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bb8ActionPerformed(evt);
            }
        });
        getContentPane().add(bb8);
        bb8.setBounds(80, 390, 50, 50);

        bc8.setBackground(new java.awt.Color(204, 204, 204));
        bc8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bc8.setBorderPainted(false);
        bc8.setContentAreaFilled(false);
        bc8.setFocusPainted(false);
        bc8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bc8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bc8MouseExited(evt);
            }
        });
        bc8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bc8ActionPerformed(evt);
            }
        });
        getContentPane().add(bc8);
        bc8.setBounds(120, 390, 50, 50);

        bd8.setBackground(new java.awt.Color(204, 204, 204));
        bd8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bd8.setBorderPainted(false);
        bd8.setContentAreaFilled(false);
        bd8.setFocusPainted(false);
        bd8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bd8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bd8MouseExited(evt);
            }
        });
        bd8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bd8ActionPerformed(evt);
            }
        });
        getContentPane().add(bd8);
        bd8.setBounds(170, 390, 50, 50);

        be8.setBackground(new java.awt.Color(204, 204, 204));
        be8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        be8.setBorderPainted(false);
        be8.setContentAreaFilled(false);
        be8.setFocusPainted(false);
        be8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                be8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                be8MouseExited(evt);
            }
        });
        be8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                be8ActionPerformed(evt);
            }
        });
        getContentPane().add(be8);
        be8.setBounds(210, 390, 50, 50);

        bf8.setBackground(new java.awt.Color(204, 204, 204));
        bf8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bf8.setBorderPainted(false);
        bf8.setContentAreaFilled(false);
        bf8.setFocusPainted(false);
        bf8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bf8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bf8MouseExited(evt);
            }
        });
        bf8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bf8ActionPerformed(evt);
            }
        });
        getContentPane().add(bf8);
        bf8.setBounds(260, 390, 50, 50);

        bg8.setBackground(new java.awt.Color(204, 204, 204));
        bg8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bg8.setBorderPainted(false);
        bg8.setContentAreaFilled(false);
        bg8.setFocusPainted(false);
        bg8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bg8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bg8MouseExited(evt);
            }
        });
        bg8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bg8ActionPerformed(evt);
            }
        });
        getContentPane().add(bg8);
        bg8.setBounds(310, 390, 50, 50);

        bh8.setBackground(new java.awt.Color(204, 204, 204));
        bh8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bh8.setBorderPainted(false);
        bh8.setContentAreaFilled(false);
        bh8.setFocusPainted(false);
        bh8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bh8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bh8MouseExited(evt);
            }
        });
        bh8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bh8ActionPerformed(evt);
            }
        });
        getContentPane().add(bh8);
        bh8.setBounds(350, 390, 50, 50);

        ba7.setBackground(new java.awt.Color(204, 204, 204));
        ba7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        ba7.setBorderPainted(false);
        ba7.setContentAreaFilled(false);
        ba7.setFocusPainted(false);
        ba7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ba7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ba7MouseExited(evt);
            }
        });
        ba7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ba7ActionPerformed(evt);
            }
        });
        getContentPane().add(ba7);
        ba7.setBounds(30, 340, 50, 50);

        bb7.setBackground(new java.awt.Color(204, 204, 204));
        bb7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bb7.setBorderPainted(false);
        bb7.setContentAreaFilled(false);
        bb7.setFocusPainted(false);
        bb7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bb7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bb7MouseExited(evt);
            }
        });
        bb7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bb7ActionPerformed(evt);
            }
        });
        getContentPane().add(bb7);
        bb7.setBounds(80, 340, 50, 50);

        bc7.setBackground(new java.awt.Color(204, 204, 204));
        bc7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bc7.setBorderPainted(false);
        bc7.setContentAreaFilled(false);
        bc7.setFocusPainted(false);
        bc7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bc7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bc7MouseExited(evt);
            }
        });
        bc7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bc7ActionPerformed(evt);
            }
        });
        getContentPane().add(bc7);
        bc7.setBounds(120, 340, 50, 50);

        bd7.setBackground(new java.awt.Color(204, 204, 204));
        bd7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bd7.setBorderPainted(false);
        bd7.setContentAreaFilled(false);
        bd7.setFocusPainted(false);
        bd7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bd7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bd7MouseExited(evt);
            }
        });
        bd7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bd7ActionPerformed(evt);
            }
        });
        getContentPane().add(bd7);
        bd7.setBounds(170, 340, 50, 50);

        be7.setBackground(new java.awt.Color(204, 204, 204));
        be7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        be7.setBorderPainted(false);
        be7.setContentAreaFilled(false);
        be7.setFocusPainted(false);
        be7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                be7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                be7MouseExited(evt);
            }
        });
        be7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                be7ActionPerformed(evt);
            }
        });
        getContentPane().add(be7);
        be7.setBounds(210, 340, 50, 50);

        bf7.setBackground(new java.awt.Color(204, 204, 204));
        bf7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bf7.setBorderPainted(false);
        bf7.setContentAreaFilled(false);
        bf7.setFocusPainted(false);
        bf7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bf7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bf7MouseExited(evt);
            }
        });
        bf7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bf7ActionPerformed(evt);
            }
        });
        getContentPane().add(bf7);
        bf7.setBounds(260, 340, 50, 50);

        bg7.setBackground(new java.awt.Color(204, 204, 204));
        bg7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bg7.setBorderPainted(false);
        bg7.setContentAreaFilled(false);
        bg7.setFocusPainted(false);
        bg7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bg7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bg7MouseExited(evt);
            }
        });
        bg7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bg7ActionPerformed(evt);
            }
        });
        getContentPane().add(bg7);
        bg7.setBounds(310, 340, 50, 50);

        bh7.setBackground(new java.awt.Color(204, 204, 204));
        bh7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        bh7.setBorderPainted(false);
        bh7.setContentAreaFilled(false);
        bh7.setFocusPainted(false);
        bh7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bh7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bh7MouseExited(evt);
            }
        });
        bh7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bh7ActionPerformed(evt);
            }
        });
        getContentPane().add(bh7);
        bh7.setBounds(350, 340, 50, 50);

        ba8.setBackground(new java.awt.Color(204, 204, 204));
        ba8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/espacoVazio.png"))); // NOI18N
        ba8.setBorderPainted(false);
        ba8.setContentAreaFilled(false);
        ba8.setFocusPainted(false);
        ba8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ba8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ba8MouseExited(evt);
            }
        });
        ba8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ba8ActionPerformed(evt);
            }
        });
        getContentPane().add(ba8);
        ba8.setBounds(30, 390, 50, 50);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tabuleiro.jpg"))); // NOI18N
        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(10, 50, 410, 410);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

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
        titulo.setForeground(new java.awt.Color(60, 128, 71));
        titulo.setLineWrap(true);
        titulo.setRows(1);
        titulo.setText("Othello");
        titulo.setWrapStyleWord(true);
        jScrollPane1.setViewportView(titulo);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(mensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(enviar))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addContainerGap(126, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 730, 460);

        setBounds(0, 0, 744, 496);
    }// </editor-fold>//GEN-END:initComponents

    private void ba2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ba2ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(1, 0);
    }//GEN-LAST:event_ba2ActionPerformed

    private void bb2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bb2ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(1, 1);
    }//GEN-LAST:event_bb2ActionPerformed

    private void bc2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bc2ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(1, 2);
    }//GEN-LAST:event_bc2ActionPerformed

    private void bd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bd2ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(1, 3);
    }//GEN-LAST:event_bd2ActionPerformed

    private void be2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_be2ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(1, 4);
    }//GEN-LAST:event_be2ActionPerformed

    private void bf2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bf2ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(1, 5);
    }//GEN-LAST:event_bf2ActionPerformed

    private void bg2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bg2ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(1, 6);
    }//GEN-LAST:event_bg2ActionPerformed

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

    private void ba2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ba2MouseEntered
        // TODO add your handling code here:
        
    }//GEN-LAST:event_ba2MouseEntered

    private void ba2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ba2MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_ba2MouseExited

    private void bb2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bb2MouseEntered
        // TODO add your handling code here:
        
    }//GEN-LAST:event_bb2MouseEntered

    private void bb2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bb2MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bb2MouseExited

    private void bc2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bc2MouseEntered
        // TODO add your handling code here:

    }//GEN-LAST:event_bc2MouseEntered

    private void bc2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bc2MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bc2MouseExited

    private void bd2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bd2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bd2MouseEntered

    private void bd2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bd2MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bd2MouseExited

    private void be2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_be2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_be2MouseEntered

    private void be2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_be2MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_be2MouseExited

    private void bf2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bf2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bf2MouseEntered

    private void bf2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bf2MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bf2MouseExited

    private void bg2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bg2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bg2MouseEntered

    private void bg2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bg2MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bg2MouseExited

    private void bh2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bh2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bh2MouseEntered

    private void bh2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bh2MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bh2MouseExited

    private void bh2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bh2ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(1, 7);
    }//GEN-LAST:event_bh2ActionPerformed

    private void ba1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ba1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_ba1MouseEntered

    private void ba1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ba1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_ba1MouseExited

    private void ba1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ba1ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(0, 0);
    }//GEN-LAST:event_ba1ActionPerformed

    private void bb1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bb1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bb1MouseEntered

    private void bb1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bb1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bb1MouseExited

    private void bb1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bb1ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(0, 1);
    }//GEN-LAST:event_bb1ActionPerformed

    private void bc1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bc1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bc1MouseEntered

    private void bc1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bc1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bc1MouseExited

    private void bc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bc1ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(0, 2);
    }//GEN-LAST:event_bc1ActionPerformed

    private void bd1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bd1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bd1MouseEntered

    private void bd1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bd1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bd1MouseExited

    private void bd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bd1ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(0, 3);
    }//GEN-LAST:event_bd1ActionPerformed

    private void be1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_be1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_be1MouseEntered

    private void be1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_be1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_be1MouseExited

    private void be1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_be1ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(0, 4);
    }//GEN-LAST:event_be1ActionPerformed

    private void bf1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bf1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bf1MouseEntered

    private void bf1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bf1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bf1MouseExited

    private void bf1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bf1ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(0, 5);
    }//GEN-LAST:event_bf1ActionPerformed

    private void bg1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bg1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bg1MouseEntered

    private void bg1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bg1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bg1MouseExited

    private void bg1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bg1ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(0, 6);
    }//GEN-LAST:event_bg1ActionPerformed

    private void bh1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bh1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bh1MouseEntered

    private void bh1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bh1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bh1MouseExited

    private void bh1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bh1ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(0, 7);
    }//GEN-LAST:event_bh1ActionPerformed

    private void bb4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bb4MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bb4MouseEntered

    private void bb4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bb4MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bb4MouseExited

    private void bb4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bb4ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(3, 1);
    }//GEN-LAST:event_bb4ActionPerformed

    private void bc4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bc4MouseEntered
        
    }//GEN-LAST:event_bc4MouseEntered

    private void bc4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bc4MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bc4MouseExited

    private void bc4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bc4ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(3, 2);
    }//GEN-LAST:event_bc4ActionPerformed

    private void bd4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bd4MouseEntered
        
    }//GEN-LAST:event_bd4MouseEntered

    private void bd4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bd4MouseExited
        
    }//GEN-LAST:event_bd4MouseExited

    private void bd4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bd4ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(3, 3);
    }//GEN-LAST:event_bd4ActionPerformed

    private void be4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_be4MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_be4MouseEntered

    private void be4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_be4MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_be4MouseExited

    private void be4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_be4ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(3, 4);
    }//GEN-LAST:event_be4ActionPerformed

    private void bf4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bf4MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bf4MouseEntered

    private void bf4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bf4MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bf4MouseExited

    private void bf4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bf4ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(3, 5);
    }//GEN-LAST:event_bf4ActionPerformed

    private void bg4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bg4MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bg4MouseEntered

    private void bg4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bg4MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bg4MouseExited

    private void bg4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bg4ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(3, 6);
    }//GEN-LAST:event_bg4ActionPerformed

    private void bh4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bh4MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bh4MouseEntered

    private void bh4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bh4MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bh4MouseExited

    private void bh4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bh4ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(3, 7);
    }//GEN-LAST:event_bh4ActionPerformed

    private void ba3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ba3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_ba3MouseEntered

    private void ba3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ba3MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_ba3MouseExited

    private void ba3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ba3ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(2, 0);
    }//GEN-LAST:event_ba3ActionPerformed

    private void bb3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bb3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bb3MouseEntered

    private void bb3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bb3MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bb3MouseExited

    private void bb3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bb3ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(2, 1);
    }//GEN-LAST:event_bb3ActionPerformed

    private void bc3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bc3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bc3MouseEntered

    private void bc3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bc3MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bc3MouseExited

    private void bc3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bc3ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(2, 2);
    }//GEN-LAST:event_bc3ActionPerformed

    private void bd3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bd3MouseEntered
    }//GEN-LAST:event_bd3MouseEntered

    private void bd3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bd3MouseExited
    }//GEN-LAST:event_bd3MouseExited

    private void bd3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bd3ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(2, 3);
    }//GEN-LAST:event_bd3ActionPerformed

    private void be3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_be3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_be3MouseEntered

    private void be3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_be3MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_be3MouseExited

    private void be3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_be3ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(2, 4);
    }//GEN-LAST:event_be3ActionPerformed

    private void bf3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bf3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bf3MouseEntered

    private void bf3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bf3MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bf3MouseExited

    private void bf3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bf3ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(2, 5);
    }//GEN-LAST:event_bf3ActionPerformed

    private void bg3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bg3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bg3MouseEntered

    private void bg3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bg3MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bg3MouseExited

    private void bg3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bg3ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(2, 6);
    }//GEN-LAST:event_bg3ActionPerformed

    private void bh3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bh3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bh3MouseEntered

    private void bh3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bh3MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bh3MouseExited

    private void bh3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bh3ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(2, 7);
    }//GEN-LAST:event_bh3ActionPerformed

    private void ba4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ba4MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_ba4MouseEntered

    private void ba4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ba4MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_ba4MouseExited

    private void ba4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ba4ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(3, 0);
    }//GEN-LAST:event_ba4ActionPerformed

    private void ba6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ba6MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_ba6MouseEntered

    private void ba6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ba6MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_ba6MouseExited

    private void ba6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ba6ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(5, 0);
    }//GEN-LAST:event_ba6ActionPerformed

    private void bb6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bb6MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bb6MouseEntered

    private void bb6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bb6MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bb6MouseExited

    private void bb6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bb6ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(5, 1);
    }//GEN-LAST:event_bb6ActionPerformed

    private void bc6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bc6MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bc6MouseEntered

    private void bc6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bc6MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bc6MouseExited

    private void bc6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bc6ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(5, 2);
    }//GEN-LAST:event_bc6ActionPerformed

    private void bd6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bd6MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bd6MouseEntered

    private void bd6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bd6MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bd6MouseExited

    private void bd6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bd6ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(5, 3);
    }//GEN-LAST:event_bd6ActionPerformed

    private void be6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_be6MouseEntered
    }//GEN-LAST:event_be6MouseEntered

    private void be6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_be6MouseExited
    }//GEN-LAST:event_be6MouseExited

    private void be6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_be6ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(5, 4);
    }//GEN-LAST:event_be6ActionPerformed

    private void bf6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bf6MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bf6MouseEntered

    private void bf6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bf6MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bf6MouseExited

    private void bf6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bf6ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(5, 5);
    }//GEN-LAST:event_bf6ActionPerformed

    private void bg6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bg6MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bg6MouseEntered

    private void bg6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bg6MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bg6MouseExited

    private void bg6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bg6ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(5, 6);
    }//GEN-LAST:event_bg6ActionPerformed

    private void bh6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bh6MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bh6MouseEntered

    private void bh6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bh6MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bh6MouseExited

    private void bh6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bh6ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(5, 7);
    }//GEN-LAST:event_bh6ActionPerformed

    private void ba5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ba5MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_ba5MouseEntered

    private void ba5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ba5MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_ba5MouseExited

    private void ba5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ba5ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(4, 0);
    }//GEN-LAST:event_ba5ActionPerformed

    private void bb5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bb5MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bb5MouseEntered

    private void bb5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bb5MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bb5MouseExited

    private void bb5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bb5ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(4, 1);
    }//GEN-LAST:event_bb5ActionPerformed

    private void bc5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bc5MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bc5MouseEntered

    private void bc5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bc5MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bc5MouseExited

    private void bc5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bc5ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(4, 2);
    }//GEN-LAST:event_bc5ActionPerformed

    private void bd5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bd5MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bd5MouseEntered

    private void bd5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bd5MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bd5MouseExited

    private void bd5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bd5ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(4, 3);
    }//GEN-LAST:event_bd5ActionPerformed

    private void be5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_be5MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_be5MouseEntered

    private void be5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_be5MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_be5MouseExited

    private void be5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_be5ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(4, 4);
    }//GEN-LAST:event_be5ActionPerformed

    private void bf5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bf5MouseEntered
    }//GEN-LAST:event_bf5MouseEntered

    private void bf5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bf5MouseExited
    }//GEN-LAST:event_bf5MouseExited

    private void bf5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bf5ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(4, 5);
    }//GEN-LAST:event_bf5ActionPerformed

    private void bg5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bg5MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bg5MouseEntered

    private void bg5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bg5MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bg5MouseExited

    private void bg5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bg5ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(4, 6);
    }//GEN-LAST:event_bg5ActionPerformed

    private void bh5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bh5MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bh5MouseEntered

    private void bh5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bh5MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bh5MouseExited

    private void bh5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bh5ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(4, 7);
    }//GEN-LAST:event_bh5ActionPerformed

    private void bb8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bb8MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bb8MouseEntered

    private void bb8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bb8MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bb8MouseExited

    private void bb8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bb8ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(7, 1);
    }//GEN-LAST:event_bb8ActionPerformed

    private void bc8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bc8MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bc8MouseEntered

    private void bc8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bc8MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bc8MouseExited

    private void bc8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bc8ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(7, 2);
    }//GEN-LAST:event_bc8ActionPerformed

    private void bd8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bd8MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bd8MouseEntered

    private void bd8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bd8MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bd8MouseExited

    private void bd8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bd8ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(7, 3);
    }//GEN-LAST:event_bd8ActionPerformed

    private void be8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_be8MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_be8MouseEntered

    private void be8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_be8MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_be8MouseExited

    private void be8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_be8ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(7, 4);
    }//GEN-LAST:event_be8ActionPerformed

    private void bf8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bf8MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bf8MouseEntered

    private void bf8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bf8MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bf8MouseExited

    private void bf8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bf8ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(7, 5);
    }//GEN-LAST:event_bf8ActionPerformed

    private void bg8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bg8MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bg8MouseEntered

    private void bg8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bg8MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bg8MouseExited

    private void bg8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bg8ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(7, 6);
    }//GEN-LAST:event_bg8ActionPerformed

    private void bh8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bh8MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bh8MouseEntered

    private void bh8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bh8MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bh8MouseExited

    private void bh8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bh8ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(7, 7);
    }//GEN-LAST:event_bh8ActionPerformed

    private void ba7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ba7MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_ba7MouseEntered

    private void ba7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ba7MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_ba7MouseExited

    private void ba7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ba7ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(6, 0);
    }//GEN-LAST:event_ba7ActionPerformed

    private void bb7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bb7MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bb7MouseEntered

    private void bb7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bb7MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bb7MouseExited

    private void bb7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bb7ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(6, 1);
    }//GEN-LAST:event_bb7ActionPerformed

    private void bc7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bc7MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bc7MouseEntered

    private void bc7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bc7MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bc7MouseExited

    private void bc7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bc7ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(6, 2);
    }//GEN-LAST:event_bc7ActionPerformed

    private void bd7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bd7MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bd7MouseEntered

    private void bd7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bd7MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bd7MouseExited

    private void bd7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bd7ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(6, 3);
    }//GEN-LAST:event_bd7ActionPerformed

    private void be7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_be7MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_be7MouseEntered

    private void be7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_be7MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_be7MouseExited

    private void be7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_be7ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(6, 4);
    }//GEN-LAST:event_be7ActionPerformed

    private void bf7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bf7MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bf7MouseEntered

    private void bf7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bf7MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bf7MouseExited

    private void bf7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bf7ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(6, 5);
    }//GEN-LAST:event_bf7ActionPerformed

    private void bg7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bg7MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bg7MouseEntered

    private void bg7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bg7MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bg7MouseExited

    private void bg7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bg7ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(6, 6);
    }//GEN-LAST:event_bg7ActionPerformed

    private void bh7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bh7MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bh7MouseEntered

    private void bh7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bh7MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bh7MouseExited

    private void bh7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bh7ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(6, 7);
    }//GEN-LAST:event_bh7ActionPerformed

    private void ba8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ba8MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_ba8MouseEntered

    private void ba8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ba8MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_ba8MouseExited

    private void ba8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ba8ActionPerformed
        // TODO add your handling code here:
        cliqueBotao(7, 0);
    }//GEN-LAST:event_ba8ActionPerformed

    private void bd3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bd3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_bd3MouseClicked

    private void bb2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bb2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_bb2MouseClicked

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
            java.util.logging.Logger.getLogger(Player.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Player.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Player.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Player.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Player().setVisible(true);
            }
        });
        
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ba1;
    private javax.swing.JButton ba2;
    private javax.swing.JButton ba3;
    private javax.swing.JButton ba4;
    private javax.swing.JButton ba5;
    private javax.swing.JButton ba6;
    private javax.swing.JButton ba7;
    private javax.swing.JButton ba8;
    private javax.swing.JButton bb1;
    private javax.swing.JButton bb2;
    private javax.swing.JButton bb3;
    private javax.swing.JButton bb4;
    private javax.swing.JButton bb5;
    private javax.swing.JButton bb6;
    private javax.swing.JButton bb7;
    private javax.swing.JButton bb8;
    private javax.swing.JButton bc1;
    private javax.swing.JButton bc2;
    private javax.swing.JButton bc3;
    private javax.swing.JButton bc4;
    private javax.swing.JButton bc5;
    private javax.swing.JButton bc6;
    private javax.swing.JButton bc7;
    private javax.swing.JButton bc8;
    private javax.swing.JButton bd1;
    private javax.swing.JButton bd2;
    private javax.swing.JButton bd3;
    private javax.swing.JButton bd4;
    private javax.swing.JButton bd5;
    private javax.swing.JButton bd6;
    private javax.swing.JButton bd7;
    private javax.swing.JButton bd8;
    private javax.swing.JButton be1;
    private javax.swing.JButton be2;
    private javax.swing.JButton be3;
    private javax.swing.JButton be4;
    private javax.swing.JButton be5;
    private javax.swing.JButton be6;
    private javax.swing.JButton be7;
    private javax.swing.JButton be8;
    private javax.swing.JButton bf1;
    private javax.swing.JButton bf2;
    private javax.swing.JButton bf3;
    private javax.swing.JButton bf4;
    private javax.swing.JButton bf5;
    private javax.swing.JButton bf6;
    private javax.swing.JButton bf7;
    private javax.swing.JButton bf8;
    private javax.swing.JButton bg1;
    private javax.swing.JButton bg2;
    private javax.swing.JButton bg3;
    private javax.swing.JButton bg4;
    private javax.swing.JButton bg5;
    private javax.swing.JButton bg6;
    private javax.swing.JButton bg7;
    private javax.swing.JButton bg8;
    private javax.swing.JButton bh1;
    private javax.swing.JButton bh2;
    private javax.swing.JButton bh3;
    private javax.swing.JButton bh4;
    private javax.swing.JButton bh5;
    private javax.swing.JButton bh6;
    private javax.swing.JButton bh7;
    private javax.swing.JButton bh8;
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
