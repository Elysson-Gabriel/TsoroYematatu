/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ifceppd.tsoroyematatu;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author elysson
 */
public interface ServicoServidorItf extends Remote{
    
    public int informaIdJogador() throws RemoteException;
    public void enviaJogada(int n, int idJogador) throws RemoteException;
    public void enviaMensagem(String msg, int idJogador) throws RemoteException;
    public void informaLocalizacao(String localizacao, int idJogador) throws RemoteException;
    
}
