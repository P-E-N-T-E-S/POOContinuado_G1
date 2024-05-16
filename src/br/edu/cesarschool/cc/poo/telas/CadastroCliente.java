package br.edu.cesarschool.cc.poo.ac.tela;

import br.edu.cesarschool.cc.poo.ac.cliente.Cliente;
import br.edu.cesarschool.cc.poo.ac.cliente.ClienteMediator;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastroCliente extends JFrame {
    private ClienteMediator clienteMediator = ClienteMediator.obterInstancia();
    private JPanel painelPrincipal;
    private JTextField cpfTextField;
    private JTextField saldoTextField;
    private JTextField nomeTextField;
    private JButton incluirButton;
    private JButton alterarButton;
    private JButton excluirButton;
    private JButton buscarButton;

    public CadastroCliente() {
        setTitle("Cadastro de Clientes");
        setSize(900, 500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
        addActionListeners();
    }

    private void initComponents() {
        JPanel contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);

        JPanel formularioPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formularioPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPane.add(formularioPanel, BorderLayout.CENTER);

        JLabel nomeLabel = new JLabel("Nome:");
        JLabel cpfLabel = new JLabel("CPF:");
        JLabel saldoLabel = new JLabel("Saldo:");

        nomeTextField = new JTextField(20);
        cpfTextField = new JTextField(20);
        saldoTextField = new JTextField(20);

        incluirButton = new JButton("Incluir");
        alterarButton = new JButton("Alterar");
        excluirButton = new JButton("Excluir");
        buscarButton = new JButton("Buscar");

        estilizarBotao(incluirButton);
        estilizarBotao(alterarButton);
        estilizarBotao(excluirButton);
        estilizarBotao(buscarButton);

        formularioPanel.add(nomeLabel);
        formularioPanel.add(nomeTextField);
        formularioPanel.add(cpfLabel);
        formularioPanel.add(cpfTextField);
        formularioPanel.add(saldoLabel);
        formularioPanel.add(saldoTextField);

        JPanel botoesPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        botoesPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        botoesPanel.add(incluirButton);
        botoesPanel.add(alterarButton);
        botoesPanel.add(excluirButton);
        botoesPanel.add(buscarButton);

        formularioPanel.add(botoesPanel);

        ImageIcon icon = new ImageIcon("C:/PENTES/POOContinuado_G1/src/br/edu/cesarschool/cc/poo/ac/tela/perfil.png");
        JLabel imagemLabel = new JLabel(icon);
        imagemLabel.setPreferredSize(new Dimension(150, 150));
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
        imagemLabel.setBorder(border);

        JPanel imagemPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        imagemPanel.add(imagemLabel);
        contentPane.add(imagemPanel, BorderLayout.WEST);
    }

    private void estilizarBotao(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void addActionListeners() {
        incluirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processaInclusao();
            }
        });

        alterarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processaAlteracao();
            }
        });

        excluirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processaExclusao();
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processaBusca();
            }
        });
    }

    private void processaInclusao() {
        String cpf = cpfTextField.getText();
        String nome = nomeTextField.getText();
        double saldo = Double.parseDouble(saldoTextField.getText());
        Cliente cliente = new Cliente(cpf, nome, saldo);
        String retorno = clienteMediator.incluir(cliente);
        if (retorno == null) {
            JOptionPane.showMessageDialog(this, "Cliente incluído com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this, retorno);
        }
    }

    private void processaAlteracao() {
        String cpf = cpfTextField.getText();
        String nome = nomeTextField.getText();
        double saldo = Double.parseDouble(saldoTextField.getText());
        Cliente cliente = new Cliente(cpf, nome, saldo);
        String retorno = clienteMediator.alterar(cliente);
        if (retorno == null) {
            JOptionPane.showMessageDialog(this, "Cliente alterado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this, retorno);
        }
    }

    private void processaExclusao() {
        String cpf = cpfTextField.getText();
        Cliente cliente = new Cliente(cpf, "", 0);
        String retorno = clienteMediator.excluir(cliente.getCpf());
        if (retorno == null) {
            JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this, retorno);
        }
    }

    private void processaBusca() {
        String cpf = cpfTextField.getText();
        Cliente cliente = clienteMediator.buscar(cpf);
        if (cliente == null) {
            JOptionPane.showMessageDialog(this, "Cliente não encontrado", "Busca", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Cliente encontrado", "Busca", JOptionPane.INFORMATION_MESSAGE);
            nomeTextField.setText(cliente.getNome());
            saldoTextField.setText(Double.toString(cliente.getSaldoPontos()));
        }
    }
}