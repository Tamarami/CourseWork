package com.course;

import java.io.IOException;
import java.util.Scanner;

public class Indexer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите количество потоков необходимых для индексирования: ");
        int threadAmount = scanner.nextInt();
        System.out.println("Запуск индексации с использованием " + threadAmount + " потоков");
        IndexWorker worker = new IndexWorker();
        try {
            worker.construct(threadAmount);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Индексация завершена");


    }
}
