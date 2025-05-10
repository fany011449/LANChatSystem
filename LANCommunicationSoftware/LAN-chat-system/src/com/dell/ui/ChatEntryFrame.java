package com.dell.ui;
import javax.swing.*;
import java.awt.*;
import java.io.DataOutputStream;
import java.net.Socket;

// 定義登入視窗類，繼承 JFrame
public class ChatEntryFrame extends JFrame {
    private Socket socket; // 記住當前Client的Socket

    // 構造函數，建立視窗介面
    public ChatEntryFrame() {
        // 建立主視窗，設定標題
        JFrame frame = new JFrame("區域網路聊天 - 進入介面");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 設定關閉視窗時結束應用程式
        frame.setSize(400, 250); // 設定視窗大小（寬 400px，高 250px）
        frame.setLocationRelativeTo(null); // 讓視窗出現在螢幕中央
        frame.setLayout(new BorderLayout()); // 使用 BorderLayout 佈局

        // 建立主面板，使用 GridBagLayout 來靈活排列元件
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // 設定內邊距

        // 設定佈局控制項
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // 設定元件間距
        gbc.fill = GridBagConstraints.HORIZONTAL; // 讓元件填滿可用空間

        // **標題部分**
        JLabel titleLabel = new JLabel("請輸入暱稱", SwingConstants.CENTER); // 建立標題標籤，居中對齊
        titleLabel.setFont(new Font("標楷體", Font.BOLD, 18)); // 設定字體大小與樣式
        gbc.gridx = 0; // 設定 X 座標
        gbc.gridy = 0; // 設定 Y 座標
        gbc.gridwidth = 2; // 讓標題占用 2 格寬度
        panel.add(titleLabel, gbc); // 添加到面板

        // **暱稱輸入框**
        JTextField nicknameField = new JTextField(15); // 建立文字輸入框，最多顯示 15 個字元
        nicknameField.setFont(new Font("標楷體", Font.PLAIN, 14)); // 設定字體大小
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2; // 讓輸入框占用 2 格寬度
        panel.add(nicknameField, gbc); // 添加到面板

        // **按鈕區域**
        JButton enterButton = new JButton("登入"); // 建立「登入」按鈕
        JButton cancelButton = new JButton("取消"); // 建立「取消」按鈕

        // **美化按鈕**
        enterButton.setFont(new Font("標楷體", Font.BOLD, 14)); // 設定字體大小
        enterButton.setBackground(new Color(30, 144, 255)); // 設定背景顏色為深藍色
        enterButton.setForeground(Color.WHITE); // 設定文字顏色為白色

        cancelButton.setFont(new Font("標楷體", Font.BOLD, 14)); // 設定字體大小
        cancelButton.setBackground(new Color(220, 20, 60)); // 設定背景顏色為紅色
        cancelButton.setForeground(Color.WHITE); // 設定文字顏色為白色

        // **按鈕事件 - 登入**
        enterButton.addActionListener(e -> {
            // **獲取使用者輸入的暱稱，並去除頭尾空格**
            String nickname = nicknameField.getText().trim();
            if (!nickname.isEmpty()) { // 確保輸入不為空
                // 顯示歡迎消息
                JOptionPane.showMessageDialog(frame, "歡迎 " + nickname + " 進入聊天系統！", "成功", JOptionPane.INFORMATION_MESSAGE);
                try {
                    login(nickname);
                    // **關閉登入介面**
                    frame.dispose();
                    // **成功進入聊天室，開啟主聊天視窗**
                    new ChatRoomFrame(nickname, socket);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                // 如果暱稱為空，顯示錯誤提示
                JOptionPane.showMessageDialog(frame, "請輸入暱稱！", "錯誤", JOptionPane.ERROR_MESSAGE);
            }
        });

        // **按鈕事件 - 取消**
        cancelButton.addActionListener(e -> frame.dispose()); // 點擊取消則關閉視窗

        // **佈局設定：讓按鈕排在輸入框下方**
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(enterButton, gbc); // 添加「登入」按鈕

        gbc.gridx = 1;
        panel.add(cancelButton, gbc); // 添加「取消」按鈕

        // **將面板添加到視窗**
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true); // 顯示視窗
    }

    // 登入聊天室
    private void login(String nickname) throws Exception {
        // 立即發送登入消息給Server
        // 1. 創建Socket請求與Server的Socket連結
        socket = new Socket(Constant.SERVER_IP, Constant.SERVER_PORT);

        // 2. 傳送消息類型編號、暱稱，給Server的Socket
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeInt(1); // 傳1：暱稱
        dataOutputStream.writeUTF(nickname);
        dataOutputStream.flush(); // 更新
    }
}