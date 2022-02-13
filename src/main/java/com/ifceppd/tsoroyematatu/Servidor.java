/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ifceppd.tsoroyematatu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author elysson
 */
public class Servidor {
    
    private ServerSocket serverSocket;
    private int qtdJogadores;
    private ConexaoServidor jogador1;
    private ConexaoServidor jogador2;
    private boolean empate;
    private int[] values;
    private int player1ButtonNum;
    private int player2ButtonNum;
    
    public Servidor(){
        System.out.println("----- Servidor -----");
        qtdJogadores = 0;
        empate = false;
        values = new int[7];
        
        try {
            serverSocket = new ServerSocket(51734);
        } catch (IOException ex) {
            System.out.println("Erro no construtor do servidor");
        }
    }
    
    public void permiteConexoes(){
        try{
            System.out.println("Aguardando conexões...");
            while(qtdJogadores < 2){
                Socket s = serverSocket.accept();
                qtdJogadores++;
                System.out.println("Jogador #" + qtdJogadores + " se conectou");
                ConexaoServidor jogador = new ConexaoServidor(s, qtdJogadores);
                
                if(qtdJogadores == 1){
                    jogador1 = jogador;
                }else{
                    jogador2 = jogador;
                }
                
                Thread t = new Thread(jogador);
                t.start();
            }
            System.out.println("Os 2 jogadores já se conectaram.");
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
                saida.writeBoolean(empate);
                saida.flush();

                while(true){
                    if(idJogador == 1){
                        player1ButtonNum = entrada.readInt();
                        System.out.println("Player 1 clicked button #" + player1ButtonNum);
                        jogador2.sendButtonNum(player1ButtonNum);
                    }else{
                        player2ButtonNum = entrada.readInt();
                        System.out.println("Player 2 clicked button #" + player2ButtonNum);
                        jogador1.sendButtonNum(player2ButtonNum);
                    }
 
                    if(empate == true){
                        System.out.println("Max turns has been reached.");
                        break;
                    }
                }
                jogador1.closeConnection();
                jogador2.closeConnection();
            } catch (IOException ex) {
                System.out.println("Erro no run do jogador");
            }
        }
        
        public void sendButtonNum(int n){
            try{
                saida.writeInt(n);
                saida.flush();
            } catch (IOException ex) {
                System.out.println("Erro no sendButtonNum() do Servidor");
            }
        }
        
        public void closeConnection(){
           try{
                socket.close();
                System.out.println("-----CONEXÃO ENCERRADA-----");
            } catch (IOException ex) {
                System.out.println("Erro no closeConnection() do Servidor");
            } 
        }
    
    }
    
    public static void main(String[] args){
        Servidor servidor = new Servidor();
        servidor.permiteConexoes();
    }
}
