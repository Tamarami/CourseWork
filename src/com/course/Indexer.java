package com.course;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Indexer {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        //Инициализируем экземпляр обработчика
        IndexWorkerImpl worker = new IndexWorkerImpl();
        //Транслируем обработчик
        IndexWorker workerCast = (IndexWorker) UnicastRemoteObject.exportObject(worker, 0);
        //Начнем слушать порт на предмет удаленного вызова методов окружения воркера
        LocateRegistry.createRegistry(12345);
        LocateRegistry.getRegistry(12345).bind("Index worker", workerCast);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите количество потоков необходимых для индексирования: ");
        int threadAmount = scanner.nextInt();
        System.out.println("Запуск индексации с использованием " + threadAmount + " потоков");

        try {
            worker.construct(threadAmount);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Индексация завершена");
    }
}
