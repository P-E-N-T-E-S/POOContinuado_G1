package br.edu.cesarschool.cc.poo.ac.tela;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tela {

    public void criarTela() {
        JFrame frame = new JFrame("Tela Básica");
        JPanel panel = new JPanel();
        JButton button = new JButton("Clique Aqui");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Você clicou no botão!");
            }
        });

        panel.add(button);
        frame.add(panel);
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}