/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ifceppd.tsoroyematatu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

/**
 *
 * @author elysson
 */
public class Servidor {
    
    private ServerSocket serverSocket;
    private ServerSocket chat;
    private int qtdJogadores;
    private ConexaoServidor jogador1;
    private ConexaoServidor jogador2;
    private ConexaoChat chat1;
    private ConexaoChat chat2;
    private int movimentoJ1;
    private int movimentoJ2;
    private boolean fimJogo;
    
    public Servidor(){
        System.out.println("----- Servidor -----");
        qtdJogadores = 0;
        //empate = false;
        
        try {
            serverSocket = new ServerSocket(51734);
            chat = new ServerSocket(51738);
        } catch (IOException ex) {
            System.out.println("Erro no construtor do servidor");
        }
    }
    
    public void permiteConexoes(){
        try{
            System.out.println("Aguardando conexões...");
            while(qtdJogadores < 2){
                Socket socket = serverSocket.accept();
                qtdJogadores++;
                System.out.println("Jogador #" + qtdJogadores + " se conectou");
                ConexaoServidor jogador = new ConexaoServidor(socket, qtdJogadores);
                
                Thread t = new Thread(jogador);
                t.start();
                
                Socket s = chat.accept();
                ConexaoChat chat = new ConexaoChat(s, qtdJogadores);
                
                if(qtdJogadores == 1){
                    jogador1 = jogador;
                    chat1 = chat;
                }else{
                    jogador2 = jogador;
                    chat2 = chat;
                }
                
                Thread t2 = new Thread(chat);
                t2.start();
            }
            
            System.out.println("Os 2 jogadores já se conectaram.");
            
            chat1.enviaMsgAdversario("/i");
        } catch (IOException ex) {
            System.out.println("Erro em permiteConexoes()");
        }
    }
    
    private class ConexaoServidor implements Runnable {
    
        private Socket socket;
        private DataInputStream entrada;
        private DataOutputStream saida;
        private int idJogador;

        public ConexaoServidor(Socket socket, int idJogador){
            this.socket = socket;
            this.idJogador = idJogador;

            try {
                entrada = new DataInputStream(socket.getInputStream());
                saida = new DataOutputStream(socket.getOutputStream());
            } catch (IOException ex) {
                System.out.println("Erro no run do jogador");
            }
        }

        public void run(){
            try{
                saida.writeInt(idJogador);
                //saida.writeBoolean(fimJogo);
                saida.flush();

                while(true){
                    if(idJogador == 1){
                        movimentoJ1 = entrada.readInt();
                        System.out.println("Jogador 1 clicou o botão #" + movimentoJ1);
                        jogador2.enviaJogadaAdversario(movimentoJ1);
                    }else{
                        movimentoJ2 = entrada.readInt();
                        System.out.println("Jogador 2 clicou o botão #" + movimentoJ2);
                        jogador1.enviaJogadaAdversario(movimentoJ2);
                    }
 
                    if(fimJogo == true){
                        System.out.println("Fim de jogo.");
                        break;
                    }
                }
                jogador1.fechaConexao();
                jogador2.fechaConexao();
            } catch (IOException ex) {
                System.out.println("Erro no run do jogador");
                System.exit(0);
            }
        }
        
        public void enviaJogadaAdversario(int n){
            try{
                saida.writeInt(n);
                saida.flush();
            } catch (IOException ex) {
                System.out.println("Erro no enviaJogadaAdversario() do Servidor");
            }
        }
        
        public void fechaConexao(){
           try{
                socket.close();
                System.out.println("-----CONEXÃO ENCERRADA-----");
            } catch (IOException ex) {
                System.out.println("Erro no fechaConexao() do Servidor");
            } 
        }
    
    }
    
    private class ConexaoChat implements Runnable {
    
        private ServerSocket ss;
        private Socket s;
        private DataInputStream din;
        private DataOutputStream dout;
        private int idJogador;

        public ConexaoChat(Socket socket, int idJogador){
            this.s = socket;
            this.idJogador = idJogador;

            try {
                din = new DataInputStream(socket.getInputStream());
                dout = new DataOutputStream(socket.getOutputStream());
            } catch (IOException ex) {
                System.out.println("Erro no run do jogador");
            }
       
        }

        public void run(){
            try{
                //dout.writeInt(idJogador);
                //dout.flush();
                
                String msgin = "";
                
                while(!msgin.equals("exit")){
                    msgin = din.readUTF();
                    //System.out.println("Client: " + msgin);
                    if(jogador2 != null){
                        if(idJogador == 1){
                            chat2.enviaMsgAdversario(msgin);
                        }else{
                            chat1.enviaMsgAdversario(msgin);
                        }
                    }
                }
                
                chat1.fechaConexao();
                chat2.fechaConexao();
            } catch (IOException ex) {
                System.out.println("Erro no run do ConexaoChat");
            }
        }
        
        public void enviaMsgAdversario(String msg){
            try{
                dout.writeUTF(msg);
                dout.flush();
            } catch (IOException ex) {
                System.out.println("Erro no enviaMsgAdversario() do Servidor");
            }
        }
        
        public void fechaConexao(){
           try{
                s.close();
                System.out.println("-----CONEXÃO ENCERRADA-----");
            } catch (IOException ex) {
                System.out.println("Erro no fechaConexao() do Servidor");
            } 
        }
    
    }
    
    public static void main(String[] args){
        Servidor servidor = new Servidor();
        servidor.permiteConexoes();
    }
}
