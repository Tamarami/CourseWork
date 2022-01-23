package com.course;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class IndexerClient {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        //Постучимся по порту
        Registry registry = LocateRegistry.getRegistry(12345);
        //Найдем экземпляр индексатора по его имени
        IndexWorker worker = (IndexWorker) registry.lookup("Index worker");

        while (true) {
            Scanner textScanner = new Scanner(System.in);
            System.out.println("Введите слово для поиска. Для выхода введите exit: ");
            String word = textScanner.nextLine();
            if(word.equals("exit")){
                break;
            }
            System.out.println("Искомое слово содержится в файлах: ");
            System.out.println(worker.search(word));
        }
    }
}
