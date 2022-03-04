/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ifceppd.tsoroyematatu;

import java.net.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author elysson
 */
public class Servidor extends UnicastRemoteObject implements Servico{
    
    private int qtdJogadores;
    private int movimentoJ1;
    private int movimentoJ2;
    private String msgJ1;
    private String msgJ2;
    
    public Servidor() throws RemoteException{
        super();
        System.out.println("----- Servidor -----");
        qtdJogadores = 0;
        movimentoJ1 = -1;
        movimentoJ2 = -1;
        msgJ1 = "";
        msgJ2 = "";
    }

    @Override
    public String recebeMensagem(int outroJogador) throws RemoteException {
        String aux = "";
        if(outroJogador == 1){
            aux = msgJ1;
            msgJ1 = "";
        }else{
            aux = msgJ2;
            msgJ2 = "";
        }
        return aux;
    }

    @Override
    public void enviaMensagem(String msg, int idJogador) throws RemoteException {
        if(idJogador == 1){
            msgJ1 = msg;
        }else{
            msgJ2 = msg;
        }
    }

    @Override
    public int recebeJogada(int outroJogador) throws RemoteException {
        int aux = -1;
        if(outroJogador == 1){
            aux = movimentoJ1;
            movimentoJ1 = -1;
        }else{
            aux = movimentoJ2;
            movimentoJ2 = -1;
        }
        return aux;
    }

    @Override
    public void enviaJogada(int n, int idJogador) throws RemoteException {
        if(idJogador == 1){
            movimentoJ1 = n;
        }else{
            movimentoJ2 = n;
        }
    }

    @Override
    public int recebeIdJogador() throws RemoteException {
        
        if(qtdJogadores < 2){
            qtdJogadores++;
            System.out.println("Jogador #" + qtdJogadores + " se conectou");

            //Armazena nos atributos locais onde está cada conexão
            if(qtdJogadores == 2){
                //Não aceita mais conexões
                System.out.println("Os 2 jogadores já se conectaram.");
                /* Após os dois jogadores se conectarem, o primeiro jogador a se conectar
                  recebe uma mensagem "/i" que permite o início do jogo*/
                enviaMensagem("/i", 2);
            }
            
            return qtdJogadores;
        }else{
            return -1;
        }
        
    }
    
    public static void main(String[] args){
        try {
            Servidor servidor = new Servidor();
            String localizacao = "rmi://localhost/servico";
            
            System.out.println("Registrando o objeto no RMIRegistry...");
            LocateRegistry.createRegistry(1099);
            
            Naming.rebind(localizacao, servidor);
            System.out.println("Aguardando Clientes!");
        } catch (RemoteException ex) {
            System.out.println("Erro:" + ex.getMessage());
        } catch (MalformedURLException ex) {
            System.out.println("Erro de url mal formado:" + ex.getMessage());
        }
    }
}
