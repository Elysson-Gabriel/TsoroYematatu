/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ifceppd.tsoroyematatu;

import java.net.*;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import javax.swing.JOptionPane;

/**
 *
 * @author elysson
 */
public class Servidor extends UnicastRemoteObject implements ServicoServidorItf{
    
    private int qtdJogadores;
    private ServicoJogadorItf jogador1;
    private ServicoJogadorItf jogador2;
    
    public Servidor() throws RemoteException{
        super();
        System.out.println("----- Servidor -----");
        //Inicializa variáveis usada para comunicação entre os clientes
        qtdJogadores = 0;
        
    }

    @Override
    public void enviaMensagem(String msg, int idJogador) throws RemoteException {
        if(idJogador == 1){
            jogador1.atualizaChat(msg);
        }else{
            jogador2.atualizaChat(msg);
        }
    }

    @Override
    public void enviaJogada(int n, int idJogador) throws RemoteException {
        if(idJogador == 1){
            jogador1.atualizaTurno(n);
        }else{
            jogador2.atualizaTurno(n);
        }
    }

    @Override
    public int informaIdJogador() throws RemoteException {
        
        if(qtdJogadores < 2){
            qtdJogadores++;
            System.out.println("Jogador #" + qtdJogadores + " se conectou");

            if(qtdJogadores == 2){
                //Não aceita mais conexões
                System.out.println("Os 2 jogadores já se conectaram.");
                /* Após os dois jogadores se conectarem, o primeiro jogador a se conectar
                  recebe uma mensagem "/i" que permite o início do jogo*/
                enviaMensagem("/i", 1);
                //qtdJogadores = 0;
            }
            
            return qtdJogadores;
        }else{
            System.out.println("ERROR: Reinicie o servidor");
            return -1;
        }
        
    }

    @Override
    public void informaLocalizacao(String localizacao, int idJogador) throws RemoteException {
        try {
            //Armazena nos atributos locais onde está cada jogador
            if(idJogador == 1){
                jogador1 = (ServicoJogadorItf) Naming.lookup(localizacao);
            }else if(idJogador == 2){
                jogador2 = (ServicoJogadorItf) Naming.lookup(localizacao);
            }
        } catch (MalformedURLException | NotBoundException | RemoteException e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
    }
    
    public static void main(String[] args){
        try {
            Servidor servidor = new Servidor();
            String localizacao = "//localhost/servicoServidor";
            
            //System.out.println("Registrando o objeto no RMIRegistry...");
            LocateRegistry.createRegistry(1099);
            
            Naming.rebind(localizacao, servidor);
            System.out.println("Aguardando Clientes!");
        } catch (RemoteException ex) {
            System.out.println("Erro no servidor:" + ex.getMessage());
        } catch (MalformedURLException ex) {
            System.out.println("Erro de url mal formado:" + ex.getMessage());
        }
    }
    
}
