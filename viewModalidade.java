package VIEW;

import controllerr.ModalidadeController;
import model.Modalidade;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class viewModalidade extends JFrame implements ActionListener, MouseListener {

    private JTextField txtCodigo, txtNome;
    private JTable table;
    private DefaultTableModel model;

    private JButton btnCadastrar, btnListar, btnAtualizar, btnRemover, btnFechar;
    private int modalidadeSelecionada = -1;

    public viewModalidade(viewJogador viewJogador) {
        setTitle("MODALIDADE");
        setBounds(100, 100, 750, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().setBackground(new Color(245, 245, 245));

        JPanel panelCampos = new JPanel();
        panelCampos.setBackground(new Color(128, 128, 128));
        panelCampos.setLayout(null);
        panelCampos.setBounds(20, 10, 300, 149);
        panelCampos.setBorder(BorderFactory.createTitledBorder("Campos"));
        getContentPane().add(panelCampos);

        JLabel lblCodigo = new JLabel("CÓDIGO:");
        lblCodigo.setBounds(10, 40, 70, 25);
        panelCampos.add(lblCodigo);

        txtCodigo = new JTextField();
        txtCodigo.setBounds(90, 40, 180, 25);
        panelCampos.add(txtCodigo);

        JLabel lblNome = new JLabel("NOME:");
        lblNome.setBounds(10, 80, 70, 25);
        panelCampos.add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(90, 80, 180, 25);
        panelCampos.add(txtNome);

        JPanel panelBotoes = new JPanel();
        panelBotoes.setBackground(new Color(128, 128, 128));
        panelBotoes.setLayout(null);
        panelBotoes.setBounds(340, 10, 380, 158);
        panelBotoes.setBorder(BorderFactory.createTitledBorder("Ações"));
        getContentPane().add(panelBotoes);

        btnCadastrar = new JButton("ADICIONAR");
        btnCadastrar.setBackground(new Color(255, 255, 255));
        btnCadastrar.setForeground(new Color(0, 0, 0));
        btnCadastrar.setBounds(20, 20, 150, 30);
        panelBotoes.add(btnCadastrar);

        btnAtualizar = new JButton("ATUALIZAR");
        btnAtualizar.setBackground(new Color(255, 255, 255));
        btnAtualizar.setForeground(new Color(0, 0, 0));
        btnAtualizar.setBounds(200, 20, 150, 30);
        panelBotoes.add(btnAtualizar);

        btnListar = new JButton("LISTAR");
        btnListar.setBackground(new Color(255, 255, 255));
        btnListar.setForeground(new Color(0, 0, 0));
        btnListar.setBounds(20, 117, 150, 30);
        panelBotoes.add(btnListar);

        btnRemover = new JButton("REMOVER");
        btnRemover.setBackground(new Color(255, 255, 255));
        btnRemover.setForeground(new Color(0, 0, 0));
        btnRemover.setBounds(200, 117, 150, 30);
        panelBotoes.add(btnRemover);

        btnFechar = new JButton("SAIR");
        btnFechar.setBackground(new Color(255, 255, 255));
        btnFechar.setForeground(new Color(0, 0, 0));
        btnFechar.setBounds(113, 65, 150, 30);
        panelBotoes.add(btnFechar);

        JPanel panelTabela = new JPanel(new BorderLayout());
        panelTabela.setBackground(new Color(128, 128, 128));
        panelTabela.setBounds(20, 179, 700, 271);
        panelTabela.setBorder(BorderFactory.createTitledBorder("Lista de Modalidades"));
        getContentPane().add(panelTabela);

        model = new DefaultTableModel(new Object[]{"CÓDIGO", "NOME"}, 0);
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        panelTabela.add(scroll, BorderLayout.CENTER);

        btnCadastrar.addActionListener(this);
        btnAtualizar.addActionListener(this);
        btnListar.addActionListener(this);
        btnRemover.addActionListener(this);
        btnFechar.addActionListener(this);
        table.addMouseListener(this);

        setVisible(true);
    }

    private void limparCampos() {
        txtCodigo.setText("");
        txtNome.setText("");
        modalidadeSelecionada = -1;
    }

    private void carregarTabela() {
        try {
            ArrayList<Modalidade> lista = ModalidadeController.buscarTodasModalidades();
            model.setRowCount(0);
            for (Modalidade m : lista) {
                model.addRow(new Object[]{m.getCodigo(), m.getnomeModalidade()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao listar modalidades: " + ex.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == btnCadastrar) {
                int codigo = Integer.parseInt(txtCodigo.getText().trim());
                String nome = txtNome.getText().trim();
                if (nome.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "O nome não pode estar vazio.");
                    return;
                }
                ModalidadeController.adicionarModalidade(codigo, nome);
                JOptionPane.showMessageDialog(this, "Modalidade cadastrada!");
                limparCampos();
                carregarTabela();
            } else if (e.getSource() == btnListar) {
                carregarTabela();
            } else if (e.getSource() == btnAtualizar) {
                if (modalidadeSelecionada == -1) {
                    JOptionPane.showMessageDialog(this, "Selecione uma modalidade na tabela.");
                    return;
                }
                int codigo = Integer.parseInt(txtCodigo.getText().trim());
                String nome = txtNome.getText().trim();
                if (nome.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "O nome não pode estar vazio.");
                    return;
                }
                ModalidadeController.atualizarModalidade(codigo, nome);
                JOptionPane.showMessageDialog(this, "Modalidade atualizada!");
                limparCampos();
                carregarTabela();
            } else if (e.getSource() == btnRemover) {
                if (modalidadeSelecionada == -1) {
                    JOptionPane.showMessageDialog(this, "Selecione uma modalidade na tabela.");
                    return;
                }
                int codigo = Integer.parseInt(txtCodigo.getText().trim());
                int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente remover?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirm != JOptionPane.YES_OPTION) return;
                ModalidadeController.removerModalidade(codigo);
                JOptionPane.showMessageDialog(this, "Modalidade removida!");
                limparCampos();
                carregarTabela();
            } else if (e.getSource() == btnFechar) {
                dispose();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }
    
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                viewJogador window = new viewJogador();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        JTable table = (JTable) e.getSource();
        int linha = table.getSelectedRow();

        if (linha != -1) {
            try {
                // Converte o índice da linha da visualização para o modelo
                int modelLinha = table.convertRowIndexToModel(linha);

                // Obtém os valores das colunas 0 e 1
                Object codigoValue = table.getValueAt(modelLinha, 0);
                Object nomeValue = table.getValueAt(modelLinha, 1);

                // Verifica se os valores não são nulos antes de usar
                if (codigoValue != null && nomeValue != null) {
                    modalidadeSelecionada = (int) codigoValue; // Supondo que a coluna 0 contém um inteiro
                    txtCodigo.setText(codigoValue.toString());
                    txtNome.setText(nomeValue.toString());
                } else {
                    // Tratar caso de célula vazia
                    System.out.println("Célula vazia na linha " + linha + ": Código ou Nome é nulo.");
                    txtCodigo.setText("");
                    txtNome.setText("");
                }
            } catch (ClassCastException ex) {
                System.err.println("Erro ao converter valor para inteiro: " + ex.getMessage());
                txtCodigo.setText("");
                txtNome.setText("");
            } catch (Exception ex) {
                System.err.println("Erro ao processar clique na tabela: " + ex.getMessage());
            }
        } else {
            System.out.println("Nenhuma linha selecionada.");
        }
    }
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
    
