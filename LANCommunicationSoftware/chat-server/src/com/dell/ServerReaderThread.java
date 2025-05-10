package com.dell;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;


public class ServerReaderThread extends Thread {
private Socket socket;
    public ServerReaderThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // 接收的訊息會很多類型： 登入消息、群聊訊息
            // 先從Socket中接收Client發過來的消息類型編號
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            while (true) {
                int type = dataInputStream.readInt();
                // 客戶端發1，代表是登入消息
                // 客戶端發2，代表是群聊消息
                switch (type) {
                    case 1:
                        // 登入消息，需要接收暱稱，在更新所有在線人數列表
                        String nickname = dataInputStream.readUTF();
                        // 把當前的Socket存進"註冊表onLineSockets"中
                        Server.onLineSockets.put(socket, nickname);
                        updateClientOnLineUserList(); // 更新在線人數列表
                        break;
                    case 2:
                        // 群聊消息，需要接收群聊消息內容，在把群聊消息發給所有在線人數
                        String msg = dataInputStream.readUTF();
                        sendMessageToAll(msg);
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(socket.getInetAddress().getHostAddress() +"用戶下線了");
            Server.onLineSockets.remove(socket); // 將下線的Client socket從在線人數列表中
            updateClientOnLineUserList(); // 更新在線人數列表
        }
    }

    // 給全部在線的Sockets發送當前客戶發過來的消息
    private void sendMessageToAll(String msg) {
        // 拼裝後，再推播給全部在線的Sockets
        StringBuilder stringBuilder = new StringBuilder();
        // 根據Key(socket)找到對應的value(nickname)
        String name = Server.onLineSockets.get(socket);
        LocalDateTime now = LocalDateTime.now();
        // 格式化
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss EEE a");
        String current = dateTimeFormatter.format(now);

        // 拼裝好展示在聊天室內的訊息
        String msgResult = stringBuilder.append(name).append(" ").append(current).append("：\r\n").append(" ").append(msg).append("\r\n").toString();

        // 推播給全部客戶端的Sockets
        for (Socket socket : Server.onLineSockets.keySet()) {
            try {
                // 3. 透過字節輸出流，傳回給Client
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                // 4. 在傳回更新之前，需要給Client一個信標，表示傳回的類型
                // 傳2:群聊訊息
                dataOutputStream.writeInt(2);
                dataOutputStream.writeUTF(msgResult);

                // 刷新Socket管道
                dataOutputStream.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 更新全部Client的在線人數列表
    private void updateClientOnLineUserList() {
        // 1. 拿到當前在線所有用戶的暱稱
        Collection<String> onLineUsers = Server.onLineSockets.values();

        // 2. 把這些暱稱，透過Socket傳回給Client
        for (Socket socket : Server.onLineSockets.keySet()) {
            try {
                // 3. 透過字節輸出流，傳回給Client
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                // 4. 在傳回更新之前，需要給Client一個信標，表示傳回的類型
                // 傳1：在線人數列表信息
                dataOutputStream.writeInt(1);
                dataOutputStream.writeInt(onLineUsers.size());
                for (String onLineUser : onLineUsers) {
                    dataOutputStream.writeUTF(onLineUser);
                }

                // 刷新Socket管道
                dataOutputStream.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
