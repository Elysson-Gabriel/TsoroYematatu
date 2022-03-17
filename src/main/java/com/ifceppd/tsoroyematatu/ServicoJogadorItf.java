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
public interface ServicoJogadorItf extends Remote{
    
    public void atualizaChat(String msg) throws RemoteException;
    public void atualizaTurno(int n) throws RemoteException;
    
}
