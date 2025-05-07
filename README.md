# 區域網路的通訊軟體 LAN Communication Software

## 需求

展示一個用戶的登入介面，這個介面只要求用戶輸入自己的暱稱就行。

登入後，展開一個群體聊天的窗口，這窗口會展示：在線人數、 展示消息展示框、發送輸入框、發送按鈕。實現即時通訊。

## 技術選型 Technology Selection

- 圖形介面程式設計：Swing

- 網路程式設計 Network Programming

- 物件導向設計 OOD

## 思路分析

### 1. 創建一個Module, 代表項目：LANCommunicationSoftware

### 2. 用戶登入介面：Swing

- 登入介面：這個介面只要求用戶輸入自己的暱稱就行
  
  ```java
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
  }(panel, BorderLayout.CENTER);
          frame.setVisible(true);
      }
  ```

- 獲取聊天室的UI介面
  
  ```java
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
          JTextArea inputField = new JTextArea(4, 20); // 4 行高
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
  }el, BorderLayout.EAST);
  
          frame.setVisible(true);
      }
  }
  ```

### 3. 定義一個APP啟動類：創建進入介面並展示。














