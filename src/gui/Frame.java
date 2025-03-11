package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.*;
import businesslogic.*;
public class Frame extends JFrame{
    public JPanel threads;
    private JButton generateSimulationButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField8;

    public Frame() {
        generateSimulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int nrClients, nrQueues, minArrival, maxArrival, minService, maxService, tMax;
                nrClients = Integer.parseInt(textField1.getText());
                nrQueues = Integer.parseInt(textField4.getText());
                minArrival = Integer.parseInt(textField5.getText());
                maxArrival = Integer.parseInt(textField8.getText());
                minService = Integer.parseInt(textField2.getText());
                maxService = Integer.parseInt(textField3.getText());
                tMax = Integer.parseInt(textField6.getText());

                SimulationManager gen = new SimulationManager(nrClients, nrQueues,minService, maxService,minArrival,maxArrival,tMax);

                Thread t = new Thread(gen);
                t.start();



            }
        });
    }

    public static void main(String[] args){
        Frame f = new Frame();

        f.setContentPane(f.threads);
        f.setVisible(true);
        f.setTitle("Queue Management");
        f.setSize(700,700);
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
