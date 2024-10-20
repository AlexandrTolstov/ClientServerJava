package client;

import server.ServerWindow;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientGUI extends JFrame {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private final JTextArea log = new JTextArea();
    String logText = "";

    private final JPanel panelTop = new JPanel(new GridLayout(2, 3));
    private final JTextField tfIPAddress = new JTextField("127.0.0.1");
    private final JTextField tfPort = new JTextField("8189");
    private final JTextField tfLogin = new JTextField("");
    private final JPasswordField tfPassword = new JPasswordField("123456");
    private final JButton btnLogin = new JButton("Login");

    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JPanel pannelCenter = new JPanel(new GridLayout(1, 2));

    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Send");

    //Массив имен пользователей
    public String[] usersNames = new String[]{"Павел Кирилов", "Мария Шарапова", "Алия Султанова"};
    JList<String> listOfUsersNames = new JList<String>(usersNames);

    public ClientGUI(ServerWindow serverWindow){
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);




        setLocationRelativeTo(null);
        setSize(WIDTH, HEIGHT);
        setTitle("Chat Client");
        tfLogin.setText("");

        panelTop.add(tfIPAddress);
        panelTop.add(tfPort);
        panelTop.add(tfLogin);
        panelTop.add(tfPassword);
        panelTop.add(btnLogin);
        add(panelTop, BorderLayout.NORTH);

        panelBottom.add(tfMessage, BorderLayout.CENTER);
        panelBottom.add(btnSend, BorderLayout.EAST);
        add(panelBottom, BorderLayout.SOUTH);
        add(pannelCenter, BorderLayout.CENTER);

        pannelCenter.add(listOfUsersNames);

        log.setEditable(false);
        log.setText(logText);
        JScrollPane scrollLog = new JScrollPane(log);

        pannelCenter.add(scrollLog);
        add(pannelCenter, BorderLayout.CENTER);

        listOfUsersNames.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                tfLogin.setText(listOfUsersNames.getSelectedValue());
            }
        });

        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(serverWindow.isServerWorking){
                    if(tfLogin.getText().equals("")){
                        logText += "Выберите пользователя\n";
                        log.setText(logText);
                    }else if (!tfMessage.getText().equals("")){
                        serverWindow.logText = serverWindow.logText + listOfUsersNames.getSelectedValue() + ": " + tfMessage.getText() + "\n";
                        serverWindow.log.setText(serverWindow.logText);
                        serverWindow.repaint();
                    }else {
                        logText += "Не введено сообщение\n";
                        log.setText(logText);
                    };
                }else {
                    logText += "Сервер не запущен\n";
                    log.setText(logText);
                }
            }
        });

        setVisible(true);
    }

}
