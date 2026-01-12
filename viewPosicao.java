package VIEW;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import controllerr.PosicaoController;
import controllerr.ModalidadeController;
import model.Posicao;
import model.Modalidade;

public class viewPosicao extends JFrame implements ActionListener, MouseListener {

    private JTextField txtCodigo;
    private JTextField txtNomePosicao;
    private JComboBox<Modalidade> comboModalidade;
    private JTable table;

    private JButton btnAdicionar, btnListar, btnAtualizar, btnRemover, btnFechar;

    private viewJogador viewPrincipal;

    public viewPosicao(viewJogador viewPrincipal) {
    	getContentPane().setBackground(new Color(255, 255, 255));
        this.viewPrincipal = viewPrincipal;
        initialize();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (viewPrincipal != null) {
                    viewPrincipal.atualizarComboBoxPosicao(null);
                }
            }
        });
    }

    
    private void initialize() {
        setBounds(100, 100, 752, 481);
        getContentPane().setLayout(null);

        // Painel de Dados
        JPanel panelDados = new JPanel();
        panelDados.setBackground(new Color(128, 128, 128));
        panelDados.setLayout(null);
        panelDados.setBounds(20, 10, 320, 150);
        panelDados.setBorder(BorderFactory.createTitledBorder("Dados da Posição"));
        getContentPane().add(panelDados);

        JLabel lblCodigo = new JLabel("CÓDIGO:");
        lblCodigo.setBounds(10, 30, 80, 25);
        panelDados.add(lblCodigo);

        txtCodigo = new JTextField();
        txtCodigo.setBounds(100, 30, 200, 25);
        panelDados.add(txtCodigo);

        JLabel lblNome = new JLabel("NOME:");
        lblNome.setBounds(10, 70, 80, 25);
        panelDados.add(lblNome);

        txtNomePosicao = new JTextField();
        txtNomePosicao.setBounds(100, 70, 200, 25);
        panelDados.add(txtNomePosicao);

        JLabel lblModalidade = new JLabel("MODALIDADE:");
        lblModalidade.setBounds(10, 110, 80, 25);
        panelDados.add(lblModalidade);

        comboModalidade = new JComboBox<>();
        comboModalidade.setForeground(new Color(0, 0, 0));
        comboModalidade.setBackground(new Color(255, 255, 255));
        comboModalidade.setBounds(100, 110, 200, 25);
        panelDados.add(comboModalidade);

        carregarModalidades();

        // Painel de Operações
        JPanel panelOperacoes = new JPanel();
        panelOperacoes.setBackground(new Color(128, 128, 128));
        panelOperacoes.setLayout(null);
        panelOperacoes.setBounds(404, 10, 296, 159);
        panelOperacoes.setBorder(BorderFactory.createTitledBorder("Operações"));
        getContentPane().add(panelOperacoes);

        btnAdicionar = new JButton("ADICIONAR");
        btnAdicionar.setForeground(new Color(0, 0, 0));
        btnAdicionar.setBackground(new Color(255, 255, 255));
        btnAdicionar.setBounds(10, 20, 130, 31);
        btnAdicionar.addActionListener(this);
        panelOperacoes.add(btnAdicionar);

        btnListar = new JButton("LISTAR");
        btnListar.setForeground(new Color(0, 0, 0));
        btnListar.setBackground(new Color(255, 255, 255));
        btnListar.setBounds(156, 20, 130, 31);
        btnListar.addActionListener(this);
        panelOperacoes.add(btnListar);

        btnAtualizar = new JButton("ATUALIZAR");
        btnAtualizar.setForeground(new Color(0, 0, 0));
        btnAtualizar.setBackground(new Color(255, 255, 255));
        btnAtualizar.setBounds(156, 109, 130, 31);
        btnAtualizar.addActionListener(this);
        panelOperacoes.add(btnAtualizar);

        btnRemover = new JButton("REMOVER");
        btnRemover.setForeground(new Color(0, 0, 0));
        btnRemover.setBackground(new Color(255, 255, 255));
        btnRemover.setBounds(10, 109, 130, 31);
        btnRemover.addActionListener(this);
        panelOperacoes.add(btnRemover);

        btnFechar = new JButton("SAIR");
        btnFechar.setForeground(new Color(0, 0, 0));
        btnFechar.setBackground(new Color(255, 255, 255));
        btnFechar.setBounds(84, 67, 130, 31);
        btnFechar.addActionListener(this);
        panelOperacoes.add(btnFechar);

        // Painel de Listagem
        JPanel panelListagem = new JPanel();
        panelListagem.setBackground(new Color(128, 128, 128));
        panelListagem.setLayout(new BorderLayout());
        panelListagem.setBounds(21, 191, 680, 220);
        panelListagem.setBorder(BorderFactory.createTitledBorder("Listagem de Posições"));
        getContentPane().add(panelListagem);

        table = new JTable();
        table.setModel(new DefaultTableModel(
                new Object[][] {},
                new String[] {"Código", "Nome", "Modalidade"}
        ));
        JScrollPane scrollPane = new JScrollPane(table);
        panelListagem.add(scrollPane, BorderLayout.CENTER);

        table.addMouseListener(this);
    }

    private void carregarModalidades() {
        try {
            comboModalidade.removeAllItems();
            ArrayList<Modalidade> lista = ModalidadeController.buscarTodasModalidades();
            for (Modalidade m : lista) {
                comboModalidade.addItem(m);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar modalidades: " + ex.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdicionar) {
            try {
                int codigo = Integer.parseInt(txtCodigo.getText().trim());
                String nome = txtNomePosicao.getText().trim();
                Modalidade mod = (Modalidade) comboModalidade.getSelectedItem();

                if (nome.isEmpty() || mod == null) {
                    JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
                    return;
                }

                PosicaoController.adicionarPosicao(codigo, nome, mod.getCodigo());
                JOptionPane.showMessageDialog(this, "Posição cadastrada com sucesso!");
                limparCampos();
                listarPosicoes();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao adicionar: " + ex.getMessage());
            }

        } else if (e.getSource() == btnListar) {
            listarPosicoes();

        } else if (e.getSource() == btnAtualizar) {
            int linha = table.getSelectedRow();
            if (linha != -1) {
                try {
                    int codigo = Integer.parseInt(table.getValueAt(linha, 0).toString());
                    String nome = txtNomePosicao.getText().trim();
                    Modalidade mod = (Modalidade) comboModalidade.getSelectedItem();
                    if (mod == null) {
                        JOptionPane.showMessageDialog(this, "Selecione uma modalidade.");
                        return;
                    }
                    PosicaoController.atualizarPosicao(codigo, nome, mod.getCodigo());
                    JOptionPane.showMessageDialog(this, "Posição atualizada com sucesso!");
                    limparCampos();
                    listarPosicoes();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao atualizar: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma linha para atualizar.");
            }

        } else if (e.getSource() == btnRemover) {
            int linha = table.getSelectedRow();
            if (linha != -1) {
                try {
                    int codigo = Integer.parseInt(table.getValueAt(linha, 0).toString());
                    PosicaoController.removerPosicao(codigo);  
                    JOptionPane.showMessageDialog(this, "Posição removida com sucesso!");
                    limparCampos();
                    listarPosicoes();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao remover: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma linha para remover.");
            }

        } else if (e.getSource() == btnFechar) {
            dispose();
        }
    }
    private void listarPosicoes() {
        try {
            ArrayList<Posicao> lista = PosicaoController.listarTodasPosicoes();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            for (Posicao p : lista) {
                String nomeModalidade = ModalidadeController.getNomePorCodigo(p.getIdModalidade());
                model.addRow(new Object[]{ p.getCodigo(), p.getNomePosicao(), nomeModalidade });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao listar: " + ex.getMessage());
        }
    }


    private void limparCampos() {
        txtCodigo.setText("");
        txtNomePosicao.setText("");
        comboModalidade.setSelectedIndex(-1);
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
        int linha = table.getSelectedRow();
        if (linha != -1) {
            txtCodigo.setText(table.getValueAt(linha, 0).toString());
            txtNomePosicao.setText(table.getValueAt(linha, 1).toString());
            String nomeMod = table.getValueAt(linha, 2).toString();
            
            // Seleciona a modalidade pelo nome
            for (int i = 0; i < comboModalidade.getItemCount(); i++) {
                if (comboModalidade.getItemAt(i).getnomeModalidade().equals(nomeMod)) {
                    comboModalidade.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
