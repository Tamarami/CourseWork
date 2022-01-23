package com.course;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IndexWorker extends Remote {

    void construct(int threadNumber) throws IOException;

    String search(String desiredWord) throws RemoteException;

}
