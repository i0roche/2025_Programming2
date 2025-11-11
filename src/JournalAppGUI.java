package JournalApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JournalAppGUI {

    private JournalApp appLogic;

    // GUIの部品
    private JFrame frame;
    private JTextArea displayArea;
    private JTextField inputField;
    private JButton addTextButton;
    private JButton addToDoButton;
    
    private JTextField indexField;
    private JButton completeButton;
    
    private JButton saveButton;

    public JournalAppGUI() {
        appLogic = new JournalApp();

        frame = new JFrame("Journal App (GUI)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());

        displayArea = new JTextArea();
        displayArea.setEditable(false);

        Font MyFont = new Font("SansSerif", Font.PLAIN, 16);
        Font monoFont = new Font("Monospaced", Font.PLAIN, 16);

        displayArea.setFont(monoFont);

        JScrollPane scrollPane = new JScrollPane(displayArea);

        inputField = new JTextField(30);
        inputField.setFont(MyFont);

        addTextButton = new JButton("テキスト追加");
        addToDoButton = new JButton("ToDo追加");
        
        indexField = new JTextField(5); // Index入力欄
        completeButton = new JButton("IndexのToDoを完了");
        
        saveButton = new JButton("保存して終了");

        // 上部: 入力欄と追加ボタンをまとめるパネル
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("入力:"));
        inputPanel.add(inputField);
        inputPanel.add(addTextButton);
        inputPanel.add(addToDoButton);
        
        JPanel actionPanel = new JPanel();
        actionPanel.add(new JLabel("操作するIndex:"));
        actionPanel.add(indexField);
        actionPanel.add(completeButton);
        actionPanel.add(saveButton);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(actionPanel, BorderLayout.SOUTH);

        //ボタンが押されたときの処理
        addTextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = inputField.getText();
                if (!text.isEmpty()) {
                    appLogic.addTextEntry(text);
                    inputField.setText("");
                    updateDisplay();
                }
            }
        });

        // 「ToDo追加」ボタンの処理
        addToDoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String task = inputField.getText();
                if (!task.isEmpty()) {
                    appLogic.addToDoEntry(task);
                    inputField.setText("");
                    updateDisplay();
                }
            }
        });
        
        completeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String indexText = indexField.getText();
                try {
                    int index = Integer.parseInt(indexText);
                    
                    appLogic.markToDoAsCompleted(index); 
                    
                    updateDisplay();
                    indexField.setText("");
                    
                } catch (NumberFormatException ex) {
                    System.err.println("Invalid index format: " + indexText);
                    JOptionPane.showMessageDialog(frame, "Indexには数値を入力してください。", "エラー", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                appLogic.saveEntries();
                System.out.println("保存しました。アプリを終了します。");
                frame.dispose();
                System.exit(0);
            }
        });

        updateDisplay();
        frame.setVisible(true);
    }

    private void updateDisplay() {
        displayArea.setText(""); 
        if (appLogic.getEntries().isEmpty()) {
            displayArea.setText("エントリはありません。");
        } else {
            for (int i = 0; i < appLogic.getEntries().size(); i++) {
                displayArea.append(i + ": " + appLogic.getEntries().get(i).getDisplayString() + "\n");
            }
        }
    }

    public static void main(String[] args) {
        try{
            String laf = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(laf);
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JournalAppGUI();
            }
        });
    }
}