package com.dell.ui;

import javax.swing.*;
import java.awt.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatRoomFrame extends JFrame {
    private Socket socket;
    private JList<String> userList = new JList<>();
    private JTextArea chatArea = new JTextArea();
    private JTextArea inputField = new JTextArea(4, 20); // 3 行高
    public ChatRoomFrame(){
        InitView();
        this.setVisible(true);
    }


    public ChatRoomFrame(String nickname, Socket socket) {
        this(); // 先調用上面的無參構造器，初始化界面信息
        this.setTitle("局域網聊天室" + nickname);
        this.socket = socket;

        // 子線程1：負責讀取Server發來的在線人數列表/群發消息
        new ClientReaderThread(socket, this).start();
    }

    private void InitView() {
        // 設定視窗
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 600);
        this.setLayout(new BorderLayout());

        // **左邊區域（聊天室 + 輸入框）**
        JPanel leftPanel = new JPanel(new BorderLayout());

        // 左上（聊天內容）
        chatArea.setEditable(false);
        chatArea.setBorder(BorderFactory.createTitledBorder("聊天室"));
        leftPanel.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        // 左下（輸入框）
        inputField.setBorder(BorderFactory.createTitledBorder("輸入訊息"));
        leftPanel.add(inputField, BorderLayout.SOUTH);

        // **右邊區域（使用者名單 + 送出按鈕）**
        JPanel rightPanel = new JPanel(new BorderLayout());

        // 右上（在線使用者名單）
        userList.setBorder(BorderFactory.createTitledBorder("正在線上用戶"));
        userList.setFixedCellWidth(100); // 擴展寬度
        rightPanel.add(new JScrollPane(userList), BorderLayout.CENTER);

        // 右下（送出按鈕）
        JButton sendButton = new JButton("送出");
        sendButton.setFont(new Font("標楷體", Font.BOLD, 14));
        sendButton.setBackground(new Color(30, 144, 255)); // 深藍色
        sendButton.setForeground(Color.WHITE);

        // 將送出按鈕綁定事件監聽器
        sendButton.addActionListener(e -> {
            String message = inputField.getText().trim();
            if (!message.isEmpty()) {
                // 清空訊息輸入欄
                inputField.setText("");
                // 發送消息
                sendMsgToServer(message);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(sendButton);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        // **添加區塊到視窗**
        this.add(leftPanel, BorderLayout.CENTER);
        this.add(rightPanel, BorderLayout.EAST);
    }

    private void sendMsgToServer(String message) {
        // 1. 從Socket中得到一個特殊數據輸出流
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeInt(2);
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush(); // 刷新數據，以免內存還有殘渣
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 更新右側窗口的在線人數列表
    public void updateOnLineUsers(String[] onLineNicknameList) {
        userList.setListData(onLineNicknameList);
    }

    // 將群聊消息顯示在聊天室中
    public void setMsgToWin(String msg) {
        chatArea.append(msg);
    }
}