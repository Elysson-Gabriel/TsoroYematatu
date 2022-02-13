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
    private int qtdJogadores;
    private ConexaoServidor jogador1;
    private ConexaoServidor jogador2;
    private int movimentoJ1;
    private int movimentoJ2;
    private boolean empate;
    
    public Servidor(){
        System.out.println("----- Servidor -----");
        qtdJogadores = 0;
        empate = false;
        
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
                Socket socket = serverSocket.accept();
                qtdJogadores++;
                System.out.println("Jogador #" + qtdJogadores + " se conectou");
                ConexaoServidor jogador = new ConexaoServidor(socket, qtdJogadores);
                
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
                        movimentoJ1 = entrada.readInt();
                        System.out.println("Jogador 1 clicou o botão #" + movimentoJ1);
                        jogador2.enviaJogadaAdversario(movimentoJ1);
                    }else{
                        movimentoJ2 = entrada.readInt();
                        System.out.println("Jogador 2 clicou o botão #" + movimentoJ2);
                        jogador1.enviaJogadaAdversario(movimentoJ2);
                    }
 
                    if(empate == true){
                        System.out.println("Fim de jogo.");
                        break;
                    }
                }
                jogador1.fechaConexao();
                jogador2.fechaConexao();
            } catch (IOException ex) {
                System.out.println("Erro no run do jogador");
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
    
    public static void main(String[] args){
        Servidor servidor = new Servidor();
        servidor.permiteConexoes();
    }
}
