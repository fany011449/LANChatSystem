package com.dell;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    // 定義一個集合容器來存所有登入進來的客戶端Socket，以便將來群發消息給用戶。
    public static final Map<Socket, String> onLineSockets = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("啟動服務端系統 . . . .");

        try {
            // 1. 註冊端口
            ServerSocket serverSocket = new ServerSocket(Constant.SERVER_PORT);
            // 2. 主線程負責接受客戶端的連接請求
            while (true) {
                // 3. 調用accept方法，獲取到客戶端的Socket對象
                System.out.println("等待客戶端的連接 . . . . .");
                Socket socket = serverSocket.accept();
                new ServerReaderThread(socket).start();
                System.out.println("一個客戶端的連接 . . . . .");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
