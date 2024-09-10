/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package johanp.Domain.Services.UserF;

import java.rmi.Remote;
import java.rmi.RemoteException;
import johanp.Domain.Models.User;

/**
 *
 * @author johan
 */
public interface IUserService extends Remote{
    User login(String userName, String pass) throws RemoteException;
}
