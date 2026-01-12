package VIEW;

import controllerr.JogadorController;
import controllerr.ModalidadeController;
import controllerr.PosicaoController;
import controllerr.conexao;
import model.Jogador;
import model.Modalidade;
import model.Posicao;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class viewJogador implements ActionListener, MouseListener {

	 JFrame frame;
	    private JTable tabelaJogadores;
	    private JTextField txtCodigo, txtNome, txtContacto, txtSalarioBase, txtNumeroVitorias, txtSalarioPorVitoria;

	    private JButton btnAdicionar, btnListar, btnEditar, btnRemover, btnLimpar, btnNovo;
	    private final ButtonGroup buttonGroup = new ButtonGroup();
	    private JRadioButton rdbtnNewRadioButton_2;
	    private JRadioButton rdbtnNewRadioButton_3;
	    private JComboBox<String> comboEstadoCivil;
	    private JComboBox comboBoxModalidade;
	    private JComboBox comboBoxPosicao;
	    private ArrayList<Modalidade> listaModalidades;
	    private ArrayList<Posicao> listaPosicoes;
	    public static viewJogador jogadorInstance;
	    private JButton btnFiltro;
	    
	    

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

    public viewJogador() {
        jogadorInstance = this;
        initialize();
        atualizarComboBoxModalidade();
        atualizarEstadoCivil("Masculino");
        atualizarEstadoCivil("Feminino");

    }

    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setBackground(new Color(255, 255, 255));
        frame.setTitle("GESTÃO DE JOGADORES");
        frame.setBounds(100, 100, 1243, 786);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JPanel panelDados = new JPanel();
        panelDados.setBackground(new Color(128, 128, 128));
        panelDados.setBorder(new TitledBorder(null, "DADOS DO JOGADOR", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelDados.setBounds(27, 10, 700, 451);
        frame.getContentPane().add(panelDados);
        panelDados.setLayout(null);

        
        JLabel lblCodigo = new JLabel("CÓDIGO");
        lblCodigo.setFont(new Font("Verdana", Font.PLAIN, 12));
        lblCodigo.setBounds(20, 30, 100, 25);
        panelDados.add(lblCodigo);

        txtCodigo = new JTextField();
        txtCodigo.setFont(new Font("Verdana", Font.PLAIN, 12));
        txtCodigo.setBounds(130, 30, 200, 30);
        panelDados.add(txtCodigo);

        JLabel lblNome = new JLabel("NOME");
        lblNome.setFont(new Font("Verdana", Font.PLAIN, 12));	
        lblNome.setBounds(20, 70, 100, 25);
        panelDados.add(lblNome);

        txtNome = new JTextField();
        txtNome.setFont(new Font("Verdana", Font.PLAIN, 12));
        txtNome.setBounds(130, 70, 363, 30);
        panelDados.add(txtNome);

        JLabel lblContacto = new JLabel("CONTACTO");
        lblContacto.setFont(new Font("Verdana", Font.PLAIN, 12));
        lblContacto.setBounds(20, 110, 100, 25);
        panelDados.add(lblContacto);

        txtContacto = new JTextField();
        txtContacto.setFont(new Font("Verdana", Font.PLAIN, 12));
        txtContacto.setBounds(130, 110, 200, 30);
        panelDados.add(txtContacto);

        JLabel lblGenero = new JLabel("GÊNERO");
        lblGenero.setFont(new Font("Verdana", Font.PLAIN, 12));
        lblGenero.setBounds(20, 150, 100, 25);
        panelDados.add(lblGenero);

        
        JLabel lblEstadoCivil = new JLabel("ESTADO CIVIL");
        lblEstadoCivil.setFont(new Font("Verdana", Font.PLAIN, 12));
        lblEstadoCivil.setBounds(20, 190, 100, 25);
        panelDados.add(lblEstadoCivil);

        JLabel lblModalidade = new JLabel("MODALIDADE");
        lblModalidade.setFont(new Font("Verdana", Font.PLAIN, 12));
        lblModalidade.setBounds(20, 230, 100, 25);
        panelDados.add(lblModalidade);

        JLabel lblPosicao = new JLabel("POSIÇÃO");
        lblPosicao.setFont(new Font("Verdana", Font.PLAIN, 12));
        lblPosicao.setBounds(20, 270, 100, 25);
        panelDados.add(lblPosicao);

        JLabel lblSalarioBase = new JLabel("SALÁRIO BASE");
        lblSalarioBase.setFont(new Font("Verdana", Font.PLAIN, 12));
        lblSalarioBase.setBounds(20, 311, 120, 25);
        panelDados.add(lblSalarioBase);

        txtSalarioBase = new JTextField();
        txtSalarioBase.setFont(new Font("Verdana", Font.PLAIN, 12));
        txtSalarioBase.setBounds(130, 311, 200, 30);
        panelDados.add(txtSalarioBase);

        JLabel lblNumVitorias = new JLabel("Nº VITÓRIAS");
        lblNumVitorias.setFont(new Font("Verdana", Font.PLAIN, 12));
        lblNumVitorias.setBounds(20, 359, 90, 25);
        panelDados.add(lblNumVitorias);

        txtNumeroVitorias = new JTextField();
        txtNumeroVitorias.setFont(new Font("Verdana", Font.PLAIN, 12));
        txtNumeroVitorias.setBounds(130, 356, 200, 30);
        panelDados.add(txtNumeroVitorias);

        JLabel lblSalarioPorVitoria = new JLabel("SALÁRIO POR VITÓRIA");
        lblSalarioPorVitoria.setFont(new Font("Verdana", Font.PLAIN, 12));
        lblSalarioPorVitoria.setBounds(10, 400, 113, 25);
        panelDados.add(lblSalarioPorVitoria);

        txtSalarioPorVitoria = new JTextField();
        txtSalarioPorVitoria.setFont(new Font("Verdana", Font.PLAIN, 12));
        txtSalarioPorVitoria.setBounds(130, 397, 200, 30);
        panelDados.add(txtSalarioPorVitoria);
        
        rdbtnNewRadioButton_2 = new JRadioButton("MASCULINO");
        buttonGroup.add(rdbtnNewRadioButton_2);
        rdbtnNewRadioButton_2.setBackground(UIManager.getColor("Button.highlight"));
        rdbtnNewRadioButton_2.setFont(new Font("Verdana", Font.PLAIN, 12));
        rdbtnNewRadioButton_2.setForeground(new Color(0, 0, 0));
        rdbtnNewRadioButton_2.setSelected(true);
        rdbtnNewRadioButton_2.setBounds(130, 152, 109, 23);
        panelDados.add(rdbtnNewRadioButton_2);
        rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (rdbtnNewRadioButton_2.isSelected()) {
                    atualizarEstadoCivil("MASCULINO");
                }
            }
        });

        
        rdbtnNewRadioButton_3 = new JRadioButton("FEMININO");
        buttonGroup.add(rdbtnNewRadioButton_3);
        rdbtnNewRadioButton_3.setBackground(UIManager.getColor("Button.highlight"));
        rdbtnNewRadioButton_3.setFont(new Font("Verdana", Font.PLAIN, 12));
        rdbtnNewRadioButton_3.setForeground(new Color(0, 0, 0));
        rdbtnNewRadioButton_3.setBounds(269, 152, 109, 23);
        panelDados.add(rdbtnNewRadioButton_3);
        rdbtnNewRadioButton_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (rdbtnNewRadioButton_3.isSelected()) {
                    atualizarEstadoCivil("FEMININO");
                }
            }
        });
        

        comboEstadoCivil = new JComboBox<>();
        comboEstadoCivil.setFont(new Font("Verdana", Font.PLAIN, 12));
        comboEstadoCivil.setForeground(new Color(0, 0, 0));
        comboEstadoCivil.setEditable(true);
        comboEstadoCivil.setBounds(130, 187, 200, 30);
        panelDados.add(comboEstadoCivil);
        
        comboBoxModalidade = new JComboBox();
        comboBoxModalidade.setFont(new Font("Verdana", Font.PLAIN, 12));
        comboBoxModalidade.setEditable(true);
        comboBoxModalidade.setModel(new DefaultComboBoxModel<>());
        comboBoxModalidade.setForeground(new Color(0, 0, 0));
        comboBoxModalidade.setBounds(130, 230, 200, 26);
        panelDados.add(comboBoxModalidade);
        
        comboBoxModalidade.addActionListener(e -> {
            String modalidadeSelecionada = (String) comboBoxModalidade.getSelectedItem();
            if (modalidadeSelecionada != null && !modalidadeSelecionada.isEmpty()) {
                atualizarComboBoxPosicao(modalidadeSelecionada);
            }
        });


        comboBoxPosicao = new JComboBox();
        comboBoxPosicao.setFont(new Font("Verdana", Font.PLAIN, 12));
        comboBoxPosicao.setEditable(true); 
        comboBoxPosicao.setForeground(new Color(0, 0, 0));
        comboBoxPosicao.setBounds(130, 271, 200, 26);
        panelDados.add(comboBoxPosicao);

        JPanel panelOperacoes = new JPanel();
        panelOperacoes.setForeground(new Color(255, 102, 102));
        panelOperacoes.setBackground(new Color(128, 128, 128));
        panelOperacoes.setBorder(new TitledBorder(null, "OPERAÇÕES", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelOperacoes.setBounds(737, 10, 480, 451);
        frame.getContentPane().add(panelOperacoes);
        panelOperacoes.setLayout(null);

        btnAdicionar = new JButton("ADICIONAR");
        btnAdicionar.setFont(new Font("Verdana", Font.PLAIN, 12));
        btnAdicionar.setForeground(new Color(0, 0, 0));
        btnAdicionar.setBackground(new Color(255, 255, 255));
        btnAdicionar.setBounds(21, 24, 200, 40);
        btnAdicionar.addActionListener(this);
        panelOperacoes.add(btnAdicionar);

        btnListar = new JButton("LISTAR");
        btnListar.setFont(new Font("Verdana", Font.PLAIN, 12));
        btnListar.setForeground(new Color(0, 0, 0));
        btnListar.setBackground(new Color(255, 255, 255));
        btnListar.setBounds(270, 74, 200, 40);
        btnListar.addActionListener(this);
        panelOperacoes.add(btnListar);

        btnEditar = new JButton("ACTUALIZAR");
        btnEditar.setFont(new Font("Verdana", Font.PLAIN, 12));
        btnEditar.setForeground(new Color(0, 0, 0));
        btnEditar.setBackground(new Color(255, 255, 255));
        btnEditar.setBounds(21, 124, 200, 40);
        btnEditar.addActionListener(this);
        panelOperacoes.add(btnEditar);

        btnRemover = new JButton("REMOVER");
        btnRemover.setFont(new Font("Verdana", Font.PLAIN, 12));
        btnRemover.setForeground(new Color(0, 0, 0));
        btnRemover.setBackground(new Color(255, 255, 255));
        btnRemover.setBounds(270, 174, 200, 40);
        btnRemover.addActionListener(this);
        panelOperacoes.add(btnRemover);

        btnLimpar = new JButton("LIMPAR");
        btnLimpar.setFont(new Font("Verdana", Font.PLAIN, 12));
        btnLimpar.setForeground(new Color(0, 0, 0));
        btnLimpar.setBackground(new Color(255, 255, 255));
        btnLimpar.setBounds(21, 223, 200, 40);
        btnLimpar.addActionListener(this);
        panelOperacoes.add(btnLimpar);

        btnNovo = new JButton("NOVO");
        btnNovo.setFont(new Font("Verdana", Font.PLAIN, 12));
        btnNovo.setForeground(new Color(0, 0, 0));
        btnNovo.setBackground(new Color(255, 255, 255));
        btnNovo.setBounds(270, 274, 200, 40);
        btnNovo.addActionListener(this);
        panelOperacoes.add(btnNovo);
        
        JButton btnCadastrarPosicao = new JButton("POSIÇÃO");
        btnCadastrarPosicao.setFont(new Font("Verdana", Font.PLAIN, 12));
        btnCadastrarPosicao.setForeground(new Color(0, 0, 0));
        btnCadastrarPosicao.setBackground(new Color(255, 255, 255));
        btnCadastrarPosicao.setBounds(21, 325, 200, 40);
        btnCadastrarPosicao.addActionListener(e -> {
            new viewPosicao(this).setVisible(true);
        });
        panelOperacoes.add(btnCadastrarPosicao);
        
        
        JButton btnModalidade = new JButton("MODALIDADE");
        btnModalidade.setForeground(new Color(0, 0, 0));
        btnModalidade.setBackground(new Color(255, 255, 255));
        btnModalidade.setFont(new Font("Verdana", Font.PLAIN, 12));
        btnModalidade.setBounds(270, 375, 200, 41);
        btnModalidade.addActionListener(e -> {
            new viewModalidade(this).setVisible(true);
        });
        panelOperacoes.add(btnModalidade);
        
        btnFiltro = new JButton("FILTRO");
        btnFiltro.setForeground(Color.BLACK);
        btnFiltro.setFont(new Font("Verdana", Font.PLAIN, 12));
        btnFiltro.setBackground(Color.WHITE);
        btnFiltro.setBounds(21, 390, 200, 40);
        btnFiltro.addActionListener(this);
        panelOperacoes.add(btnFiltro);
       
        JPanel panelListagem = new JPanel();
        panelListagem.setBackground(new Color(128, 128, 128));
        panelListagem.setBorder(new TitledBorder(null, "LISTAGEM DE JOGADORES", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelListagem.setBounds(27, 482, 1190, 256);
        frame.getContentPane().add(panelListagem);
        panelListagem.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane = new JScrollPane();
        panelListagem.add(scrollPane, BorderLayout.NORTH);

        tabelaJogadores = new JTable();
        tabelaJogadores.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                        "Código", "Nome", "Contacto", "Gênero", "Estado Civil", "Modalidade",
                        "Posição", "Salário Base", "Nº Vitórias", "Salário p/ Vitória", "Salário Total"
                }
        ));
        tabelaJogadores.addMouseListener(this);
        scrollPane.setViewportView(tabelaJogadores);
    }

    
    
    private void atualizarEstadoCivil(String genero) {
        comboEstadoCivil.removeAllItems(); 
        comboEstadoCivil.addItem(" "); 

        if (genero.equals("MASCULINO")) {
            comboEstadoCivil.addItem("Solteiro");
            comboEstadoCivil.addItem("Casado");
            comboEstadoCivil.addItem("Viúvo");
            comboEstadoCivil.addItem("Divorciado");
        } else if (genero.equals("FEMININO")) {
            comboEstadoCivil.addItem("Solteira");
            comboEstadoCivil.addItem("Casada");
            comboEstadoCivil.addItem("Viúva");
            comboEstadoCivil.addItem("Divorciada");
        }
    }
    
    public void atualizarComboBoxModalidade() {
        try {
            comboBoxModalidade.removeAllItems();
            listaModalidades = ModalidadeController.buscarTodasModalidades();

            for (Modalidade m : listaModalidades) {
                comboBoxModalidade.addItem(m.getnomeModalidade()); // só nome
            }

            comboBoxModalidade.setSelectedIndex(-1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar modalidades: " + e.getMessage());
        }
    }
    
    private Jogador lerCamposJogador() throws Exception {
        int codigo = Integer.parseInt(txtCodigo.getText().trim());
        String nome = txtNome.getText().trim();
        String contacto = txtContacto.getText().trim();
        String genero = "";

        if (rdbtnNewRadioButton_2.isSelected()) {
            genero = "Masculino";
        } else if (rdbtnNewRadioButton_3.isSelected()) {
            genero = "Feminino";
        }

        String estadoCivil = (String) comboEstadoCivil.getSelectedItem();

        String modalidadeNome = (String) comboBoxModalidade.getSelectedItem();
        if (modalidadeNome == null) throw new Exception("Modalidade não selecionada");

        int idModalidade = listaModalidades.stream()
                .filter(m -> m.getnomeModalidade().equals(modalidadeNome))
                .map(Modalidade::getCodigo)
                .findFirst()
                .orElseThrow(() -> new Exception("Modalidade inválida"));

        String posicaoNome = (String) comboBoxPosicao.getSelectedItem();
        if (posicaoNome == null) throw new Exception("Posição não selecionada");

        int idPosicao = listaPosicoes.stream()
                .filter(p -> p.getNomePosicao().equals(posicaoNome))
                .map(Posicao::getCodigo)
                .findFirst()
                .orElseThrow(() -> new Exception("Posição inválida"));

        if (nome.isEmpty()) throw new Exception("Nome não pode estar vazio");

        double salarioBase = Double.parseDouble(txtSalarioBase.getText().trim());
        int numeroVitorias = Integer.parseInt(txtNumeroVitorias.getText().trim());
        double salarioPorVitoria = Double.parseDouble(txtSalarioPorVitoria.getText().trim());

        return new Jogador(
            codigo,
            nome,
            contacto,
            genero,
            estadoCivil,
            idModalidade,
            idPosicao,
            salarioBase,
            numeroVitorias,
            salarioPorVitoria
        );
    }


    private void limparTabela() {
        DefaultTableModel model = (DefaultTableModel) tabelaJogadores.getModel();
        model.setRowCount(0);
    }
    
    private void limparCampos() {
        txtCodigo.setText("");
        txtNome.setText("");
        txtContacto.setText("");
        buttonGroup.clearSelection();
        comboEstadoCivil.setSelectedIndex(0);
        comboBoxModalidade.setSelectedIndex(-1);
        comboBoxPosicao.removeAllItems(); 
        txtSalarioBase.setText("");
        txtNumeroVitorias.setText("");
        txtSalarioPorVitoria.setText("");
        txtCodigo.setEnabled(true);
    }
    
    public int getCodigoModalidadeSelecionada() throws Exception {
        Modalidade modalidadeSelecionada = (Modalidade) comboBoxModalidade.getSelectedItem();
        if (modalidadeSelecionada == null) {
            throw new Exception("Modalidade não selecionada");
        }
        return modalidadeSelecionada.getCodigo();
    }

    public int getCodigoPosicaoSelecionada() throws Exception {
        Posicao posicaoSelecionada = (Posicao) comboBoxPosicao.getSelectedItem();
        if (posicaoSelecionada == null) {
            throw new Exception("Posição não selecionada");
        }
        return posicaoSelecionada.getCodigo();
    }

    public void atualizarComboBoxPosicao(String nomeModalidade) {
        try {
            comboBoxPosicao.removeAllItems();

            // procurar o ID da modalidade pelo nome
            int idModalidade = listaModalidades.stream()
                .filter(m -> m.getnomeModalidade().equals(nomeModalidade))
                .map(Modalidade::getCodigo)
                .findFirst()
                .orElse(0);

            // buscar todas as posições dessa modalidade
            listaPosicoes = PosicaoController.buscarPosicoesPorModalidade(idModalidade);

            // preencher o combo só com nomes
            for (Posicao p : listaPosicoes) {
                comboBoxPosicao.addItem(p.getNomePosicao());
            }

            comboBoxPosicao.setSelectedIndex(-1);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Erro ao carregar posições: " + e.getMessage());
        }
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == btnAdicionar) {
            try {
                int codigo = Integer.parseInt(txtCodigo.getText().trim());
                String nome = txtNome.getText().trim();
                String contacto = txtContacto.getText().trim();
                String genero = rdbtnNewRadioButton_2.isSelected() ? "Masculino" : "Feminino";
                String estadoCivil = (String) comboEstadoCivil.getSelectedItem();
                String modalidade = (String) comboBoxModalidade.getSelectedItem();
                String posicao = (String) comboBoxPosicao.getSelectedItem();
                double salarioBase = Double.parseDouble(txtSalarioBase.getText().trim());
                int numeroVitorias = Integer.parseInt(txtNumeroVitorias.getText().trim());
                double salarioPorVitoria = Double.parseDouble(txtSalarioPorVitoria.getText().trim());

                JogadorController.adicionarJogador(
                	    codigo,
                	    nome,
                	    contacto,
                	    genero,
                	    estadoCivil,
                	    modalidade,
                	    posicao,    
                	    salarioBase,
                	    numeroVitorias,
                	    salarioPorVitoria
                	);

                JOptionPane.showMessageDialog(frame, "Jogador adicionado com sucesso!");
                limparCampos();
                limparTabela();
                listarJogadores();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Erro ao adicionar jogador: " + ex.getMessage());
            }
        }

        else if (src == btnListar) {
            limparTabela();
            listarJogadores();
        }

        else if (src == btnEditar) {
            try {
                Jogador j = lerCamposJogador();
                JogadorController.atualizarJogador(j);
                JOptionPane.showMessageDialog(frame, "Jogador atualizado com sucesso!");
                limparCampos();
                limparTabela();
                listarJogadores();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Erro ao atualizar jogador: " + ex.getMessage());
            }
        }

        else if (src == btnRemover) {
            try {
                int codigo = Integer.parseInt(txtCodigo.getText().trim());
                JogadorController.removerJogador(codigo);
                JOptionPane.showMessageDialog(frame, "Jogador removido com sucesso!");
                limparCampos();
                limparTabela();
                listarJogadores();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Erro ao remover jogador: " + ex.getMessage());
            }
        }  else if (src == btnFiltro) {
            Filtro();
            return;
        }
        else if (src == btnNovo) {
            try {
                int maxCodigo = JogadorController.listarJogadores()
                        .stream()
                        .mapToInt(Jogador::getCodigo)
                        .max()
                        .orElse(0);
                txtCodigo.setText(String.valueOf(maxCodigo + 1));
                txtCodigo.setEditable(false);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Erro ao gerar novo código: " + ex.getMessage());
            }
        }
        else if (src == btnLimpar) {
            limparCampos();
        }
    }
    
    private void listarJogadores() {
        DefaultTableModel modelo = (DefaultTableModel) tabelaJogadores.getModel();
        modelo.setRowCount(0); 

        try {
            ArrayList<Jogador> listaDeJogadores = JogadorController.listarJogadores();
            listaModalidades = ModalidadeController.buscarTodasModalidades();
            listaPosicoes = PosicaoController.listarTodasPosicoes();

            for (Jogador j : listaDeJogadores) {
                String nomeModalidade = listaModalidades.stream()
                        .filter(m -> m.getCodigo() == j.getIdModalidade())
                        .map(Modalidade::getnomeModalidade)
                        .findFirst()
                        .orElse("Desconhecida");

                String nomePosicao = listaPosicoes.stream()
                        .filter(p -> p.getCodigo() == j.getIdPosicao())
                        .map(Posicao::getNomePosicao)
                        .findFirst()
                        .orElse("Desconhecida");

                modelo.addRow(new Object[]{
                        j.getCodigo(),
                        j.getNome(),
                        j.getContacto(),
                        j.getGenero(),
                        j.getEstadoCivil(),
                        nomeModalidade,
                        nomePosicao,
                        String.format("%.2f", j.getSalarioBase()),
                        j.getNumeroVitorias(),
                        String.format("%.2f", j.getSalarioPorVitoria()),
                        String.format("%.2f", j.getSalarioTotal())
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Erro ao listar jogadores: " + e.getMessage());
        }
    }

    private void Filtro() { 
    	 DefaultTableModel modelo = (DefaultTableModel) tabelaJogadores.getModel();
         modelo.setRowCount(0);  

    try {
        ArrayList<Jogador> listaDeJogadores = JogadorController.listarJogadores();
        listaModalidades = ModalidadeController.buscarTodasModalidades();
        listaPosicoes = PosicaoController.listarTodasPosicoes();

        for (Jogador j : listaDeJogadores) {
            String nomeModalidade = listaModalidades.stream()
                    .filter(m -> m.getCodigo() == j.getIdModalidade())
                    .map(Modalidade::getnomeModalidade)
                    .findFirst()
                    .orElse("Desconhecida");

            String nomePosicao = listaPosicoes.stream()
                    .filter(p -> p.getCodigo() == j.getIdPosicao())
                    .map(Posicao::getNomePosicao)
                    .findFirst()
                    .orElse("Desconhecida");
            
            boolean ehMasculino = j.getGenero() != null && j.getGenero().trim().equalsIgnoreCase("Masculino");
            boolean ehFutebol   = nomeModalidade.trim().equalsIgnoreCase("Futebol");
            boolean ehAvancado = nomePosicao.trim().equalsIgnoreCase("Avançado")
                    || nomePosicao.trim().equalsIgnoreCase("Avancado");
            
            if (!(ehMasculino && ehFutebol && ehAvancado)) {
                continue;
            }
            
            modelo.addRow(new Object[]{
                    j.getCodigo(),
                    j.getNome(),
                    j.getContacto(),
                    j.getGenero(),
                    j.getEstadoCivil(),
                    nomeModalidade,
                    nomePosicao,
                    String.format("%.2f", j.getSalarioBase()),
                    j.getNumeroVitorias(),
                    String.format("%.2f", j.getSalarioPorVitoria()),
                    String.format("%.2f", j.getSalarioTotal())
            });
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(frame, "Erro ao listar jogadores filtrados: " + e.getMessage());
    }
}
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
        int selectedRow = tabelaJogadores.getSelectedRow();
        if (selectedRow != -1) {
            try {
             
                txtCodigo.setText(tabelaJogadores.getValueAt(selectedRow, 0).toString());
                txtNome.setText(tabelaJogadores.getValueAt(selectedRow, 1).toString());
                txtContacto.setText(tabelaJogadores.getValueAt(selectedRow, 2).toString());

                
                String genero = tabelaJogadores.getValueAt(selectedRow, 3).toString();
                if (genero.equalsIgnoreCase("Masculino")) {
                	rdbtnNewRadioButton_2.setSelected(true);
                } else if (genero.equalsIgnoreCase("Feminino")) {
                	 rdbtnNewRadioButton_3.setSelected(true);
                }

             
                comboEstadoCivil.setSelectedItem(tabelaJogadores.getValueAt(selectedRow, 4).toString());

                String nomeModalidade = tabelaJogadores.getValueAt(selectedRow, 5).toString();
                for (int i = 0; i < comboBoxModalidade.getItemCount(); i++) {
                    String nome = (String) comboBoxModalidade.getItemAt(i);
                    if (nome.equals(nomeModalidade)) {
                        comboBoxModalidade.setSelectedIndex(i);
                        atualizarComboBoxPosicao(nomeModalidade);
                        break;
                    }
                }


                String nomePosicao = tabelaJogadores.getValueAt(selectedRow, 6).toString();
                for (int i = 0; i < comboBoxPosicao.getItemCount(); i++) {
                    String nome = (String) comboBoxPosicao.getItemAt(i);
                    if (nome.equals(nomePosicao)) {
                        comboBoxPosicao.setSelectedIndex(i);
                        break;
                    }
                }

             
                txtSalarioBase.setText(tabelaJogadores.getValueAt(selectedRow, 7).toString());
                txtNumeroVitorias.setText(tabelaJogadores.getValueAt(selectedRow, 8).toString());
                txtSalarioPorVitoria.setText(tabelaJogadores.getValueAt(selectedRow, 9).toString());

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Erro ao carregar dados do jogador: " + ex.getMessage());
            }
        }
    } 
        

    
    @Override
    public void mousePressed(MouseEvent e) {    }
    @Override
    public void mouseReleased(MouseEvent e) {    }
    @Override
    public void mouseEntered(MouseEvent e) {    }
    @Override
    public void mouseExited(MouseEvent e) {    }

	
}
