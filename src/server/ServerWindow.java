package server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

public class ServerWindow extends JFrame {
    private static final int POS_X = 500;
    private static final int POS_Y = 550;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private final JButton btnStart = new JButton("Start");
    private final JButton btnStop = new JButton("Stop");
    public JTextArea log = new JTextArea();
    JPanel panTop = new JPanel();
    JPanel panBottom = new JPanel(new GridLayout(1,2));
    public boolean isServerWorking;

    public String logText = "";

    String filePath = "log.txt"; //Файл для записи и считывания логов

    public ServerWindow(){
        isServerWorking = false;

        try(FileReader fileReader = new FileReader(filePath))
        {
            BufferedReader br = new BufferedReader(fileReader);

            String line = "";
            // Считываем данные
            while((line = br.readLine()) != null) {
                logText += line + "\n";
            }
            log.setText(logText);
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }

        Font font = new Font("Verdana", Font.BOLD, 12);

        log.setFont(font);
        log.setForeground(Color.BLUE);

        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isServerWorking){
                    isServerWorking = false;
                    logText += "Server stopped " + isServerWorking + "\n";
                    log.setText(logText);
                }else {
                    logText += "Server is not working\n";
                    log.setText(logText);
                }
            }
        });

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isServerWorking = true;
                logText += "Server started " + isServerWorking + "\n";
                log.setText(logText);
            }
        });

        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        jFrame.setSize(300, 200);
        jFrame.setLocationRelativeTo(null);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                super.windowClosing(e);
                int confirm = JOptionPane.showOptionDialog(jFrame,
                        "Сохранить файл перед закрытием", "Сохранение", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == JOptionPane.YES_OPTION) {
                    //Запись лога в файл
                    try(FileWriter writer = new FileWriter(filePath, false))
                    {
                        // запись всей строки
                        writer.write(logText);
                        writer.flush();
                    }
                    catch(IOException ex){
                        System.out.println(ex.getMessage());
                    }
                }
                System.exit(1);
            }
        });


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat server");
        setAlwaysOnTop(true);

        panBottom.add(btnStart);
        panBottom.add(btnStop);
        //panTop.add(log);

        add(panBottom, BorderLayout.SOUTH);
        add(log);

        setVisible(true);
    }
}
