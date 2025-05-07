package com.dell.ui;

import javax.swing.*;
import java.awt.*;

public class ChatEntryFrame extends JFrame{
    public ChatEntryFrame() {
        // 建立主視窗
        JFrame frame = new JFrame("區域網路聊天 - 進入介面");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // 主面板設計
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 標題
        JLabel titleLabel = new JLabel("請輸入暱稱", SwingConstants.CENTER);
        titleLabel.setFont(new Font("標楷體", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        // 暱稱輸入框
        JTextField nicknameField = new JTextField(15);
        nicknameField.setFont(new Font("標楷體", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(nicknameField, gbc);

        // 按鈕區域
        JButton enterButton = new JButton("進入");
        JButton cancelButton = new JButton("取消");

        // 按鈕美化
        enterButton.setFont(new Font("標楷體", Font.BOLD, 14));
        enterButton.setBackground(new Color(30, 144, 255)); // 深藍色
        enterButton.setForeground(Color.WHITE);

        cancelButton.setFont(new Font("標楷體", Font.BOLD, 14));
        cancelButton.setBackground(new Color(220, 20, 60)); // 紅色
        cancelButton.setForeground(Color.WHITE);

        // 設定按鈕事件
        enterButton.addActionListener(e -> {
            String nickname = nicknameField.getText().trim();
            if (!nickname.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "歡迎 " + nickname + " 進入聊天系統！", "成功", JOptionPane.INFORMATION_MESSAGE);
                // 關閉登入介面
                frame.dispose();
                // 成功進入聊天室
                new ChatRoomFrame(nickname);
            } else {
                JOptionPane.showMessageDialog(frame, "請輸入暱稱！", "錯誤", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> frame.dispose());

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(enterButton, gbc);

        gbc.gridx = 1;
        panel.add(cancelButton, gbc);

        // 設置面板到視窗
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}