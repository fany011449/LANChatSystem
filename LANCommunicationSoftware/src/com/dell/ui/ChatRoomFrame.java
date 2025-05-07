package com.dell.ui;

import javax.swing.*;
import java.awt.*;

public class ChatRoomFrame extends JFrame {
    public ChatRoomFrame(String nickname) {
        // 設定視窗
        JFrame frame = new JFrame("局域網聊天室" + nickname);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);
        frame.setLayout(new BorderLayout());

        // **左邊區域（聊天室 + 輸入框）**
        JPanel leftPanel = new JPanel(new BorderLayout());

        // 左上（聊天內容）
        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setBorder(BorderFactory.createTitledBorder("聊天室"));
        leftPanel.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        // 左下（輸入框）
        JTextArea inputField = new JTextArea(4, 20); // 3 行高
        inputField.setBorder(BorderFactory.createTitledBorder("輸入訊息"));
        leftPanel.add(inputField, BorderLayout.SOUTH);

        // **右邊區域（使用者名單 + 送出按鈕）**
        JPanel rightPanel = new JPanel(new BorderLayout());

        // 右上（在線使用者名單）
        DefaultListModel<String> userListModel = new DefaultListModel<>();
        userListModel.addElement("Alice");
        userListModel.addElement("Bob");
        userListModel.addElement("Charlie");
        JList<String> userList = new JList<>(userListModel);
        userList.setBorder(BorderFactory.createTitledBorder("正在線上用戶"));
        userList.setFixedCellWidth(100); // 擴展寬度
        rightPanel.add(new JScrollPane(userList), BorderLayout.CENTER);

        // 右下（送出按鈕）
        JButton sendButton = new JButton("送出");
        sendButton.setFont(new Font("標楷體", Font.BOLD, 14));
        sendButton.setBackground(new Color(30, 144, 255)); // 深藍色
        sendButton.setForeground(Color.WHITE);

        sendButton.addActionListener(e -> {
            String message = inputField.getText().trim();
            if (!message.isEmpty()) {
                chatArea.append(nickname + "：" + message + "\n");
                inputField.setText("");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(sendButton);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        // **添加區塊到視窗**
        frame.add(leftPanel, BorderLayout.CENTER);
        frame.add(rightPanel, BorderLayout.EAST);

        frame.setVisible(true);
    }
}