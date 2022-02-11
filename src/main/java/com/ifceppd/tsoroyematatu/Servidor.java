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
    private Cliente jogador1;
    private Cliente jogador2;
    private int turnsMade;
    private int maxTurns;
    private int[] values;
    private int player1ButtonNum;
    private int player2ButtonNum;
    
    public Servidor(){
        System.out.println("----- Servidor -----");
        qtdJogadores = 0;
        turnsMade = 0;
        maxTurns = 4;
        values = new int[4];
        
        for (int i = 0; i < values.length; i++){
            values[i] = (int) Math.ceil(Math.random() * 100);
            System.out.println("Value #" + (i+1) + " is " + values[i]);
        }
        
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
                Cliente jogador = new Cliente(s, qtdJogadores);
                
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
    
    private class Cliente implements Runnable {
    
        private Socket socket;
        private DataInputStream entrada;
        private DataOutputStream saida;
        private int idJogador;

        public Cliente(Socket socket, int idJogador){
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
                saida.writeInt(maxTurns);
                saida.writeInt(values[0]);
                saida.writeInt(values[1]);
                saida.writeInt(values[2]);
                saida.writeInt(values[3]);
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
                    turnsMade++;
                    if(turnsMade == maxTurns){
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
