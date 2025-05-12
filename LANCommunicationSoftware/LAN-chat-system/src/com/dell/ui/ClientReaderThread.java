package com.dell.ui;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class ClientReaderThread extends Thread {
private Socket socket;
private ChatRoomFrame chatRoomFrame;
private DataInputStream dataInputStream;
    public ClientReaderThread(Socket socket, ChatRoomFrame chatRoomFrame) {
        this.chatRoomFrame = chatRoomFrame;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // 接收的訊息會很多類型： 1.登入消息、 2.群聊訊息
            // 先從Socket中接收Client發過來的消息類型編號
            dataInputStream = new DataInputStream(socket.getInputStream());
            while (true) {
                // Server發1，代表是登入消息
                // Server發2，代表是群聊消息
                int type = dataInputStream.readInt(); //消息類型種類
                switch (type) {
                    case 1:
                        // Server發來的在線人數更新消息
                        updateClientOnLineUsersListFromServer();
                        break;
                    case 2:
                        // Server發來的群聊消息
                        getMsgToWin();
                        break;
                    case 3:
                        // Server發來的群聊消息
                        showOffLineUserMessage();
                        break;
                }
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    private void showOffLineUserMessage() throws Exception {
        String offLineMsg = dataInputStream.readUTF();
        chatRoomFrame.setMsgToWin(offLineMsg);
    }

    private void getMsgToWin() throws Exception {
        String msg = dataInputStream.readUTF();
        chatRoomFrame.setMsgToWin(msg);
    }

    // 更新在線用戶列表
    private void updateClientOnLineUsersListFromServer() throws Exception {
        // 從Server會傳過來
        // 1. 消息類型種類
        // 2. 多少個在線人數
        // 3. 每個人的暱稱
        int count = dataInputStream.readInt();

        // 需要有個集合來裝這些暱稱
        String[] onLineNicknameList = new String[count];
        for (int i = 0; i < count; i++) {
            onLineNicknameList[i] = dataInputStream.readUTF();
        }

        // 更新窗口介面右側
        chatRoomFrame.updateOnLineUsers(onLineNicknameList);
    }
}
