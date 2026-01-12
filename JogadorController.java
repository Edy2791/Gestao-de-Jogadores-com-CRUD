package controllerr;

import controllerr.conexao;
import model.Jogador;
import model.Modalidade;
import model.Posicao;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import controllerr.ModalidadeController;
import controllerr.PosicaoController;

public class JogadorController {

	public static void adicionarJogador(int codigo, String nome, String contacto, String genero, String estadoCivil,
			String nomeModalidade, String nomePosicao, double salarioBase, int numeroVitorias,
			double salarioPorVitoria) {
		try {

			ArrayList<Modalidade> modalidades = ModalidadeController.buscarTodasModalidades();
			ArrayList<Posicao> posicoes = PosicaoController.listarTodasPosicoes();

			int idModalidade = 0;
			for (Modalidade m : modalidades) {
				if (m.getnomeModalidade().equalsIgnoreCase(nomeModalidade)) {
					idModalidade = m.getCodigo();
					break;
				}
			}

			int idPosicao = 0;
			for (Posicao p : posicoes) {
				if (p.getNomePosicao().equalsIgnoreCase(nomePosicao) && p.getIdModalidade() == idModalidade) {
					idPosicao = p.getCodigo();
					break;
				}
			}

			if (idModalidade == 0 || idPosicao == 0) {
				throw new SQLException("Modalidade ou Posição não encontradas");
			}

			String sql = "INSERT INTO jogador (codigo, nome, contacto, genero, estado_civil, id_modalidade, id_posicao, salario_base, numero_vitorias, salario_por_vitoria) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			try (Connection con = conexao.conectar(); PreparedStatement stmt = con.prepareStatement(sql)) {

				stmt.setInt(1, codigo);
				stmt.setString(2, nome);
				stmt.setString(3, contacto);
				stmt.setString(4, genero);
				stmt.setString(5, estadoCivil);
				stmt.setInt(6, idModalidade);
				stmt.setInt(7, idPosicao);
				stmt.setDouble(8, salarioBase);
				stmt.setInt(9, numeroVitorias);
				stmt.setDouble(10, salarioPorVitoria);

				stmt.executeUpdate();
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao adicionar Jogador verifique e tente novamente: " + e.getMessage());
		}
	}
    
	public static ArrayList<Jogador> filtrarJogadores() throws SQLException {
	    ArrayList<Jogador> lista = new ArrayList<>();

	    String sql = """
	        SELECT j.codigo, j.nome, j.contacto, j.genero, j.estado_civil,
	               j.id_modalidade, j.id_posicao,
	               j.salario_base, j.numero_vitorias, j.salario_por_vitoria
	        FROM jogador j
	        JOIN modalidade m ON j.id_modalidade = m.codigo
	        JOIN posicao p ON j.id_posicao = p.codigo
	        WHERE j.genero = ?
	          AND j.salario_base > ?
	          AND m.nome_modalidade = ?
	          AND (p.nomePosicao = ? OR p.nomePosicao = ?)
	    """;

	    try (Connection con = conexao.conectar();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, "Masculino");   
	        ps.setDouble(2, 3000);         
	        ps.setString(3, "Futebol");     
	        ps.setString(4, "Atacante");    
	        ps.setString(5, "Defesa");     

	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                Jogador j = new Jogador(
	                    rs.getInt("codigo"),
	                    rs.getString("nome"),
	                    rs.getString("contacto"),
	                    rs.getString("genero"),
	                    rs.getString("estado_civil"),
	                    rs.getInt("id_modalidade"),
	                    rs.getInt("id_posicao"),
	                    rs.getDouble("salario_base"),
	                    rs.getInt("numero_vitorias"),
	                    rs.getDouble("salario_por_vitoria")
	                );
	                lista.add(j);
	            }
	        }
	    }
	    return lista;
	}
	
	public static ArrayList<Jogador> listarJogadores() throws SQLException {
		ArrayList<Jogador> lista = new ArrayList<>();

		String sql = "SELECT codigo, nome, contacto, genero, estado_civil, "
				+ "id_modalidade, id_posicao, salario_base, numero_vitorias, salario_por_vitoria "
				+ "FROM jogador ORDER BY codigo";

		try (Connection con = conexao.conectar();
				PreparedStatement stmt = con.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				int codigo = rs.getInt("codigo");
				String nome = rs.getString("nome");
				String contacto = rs.getString("contacto");
				String genero = rs.getString("genero");
				String estadoCivil = rs.getString("estado_civil");
				int idModalidade = rs.getInt("id_modalidade");
				int idPosicao = rs.getInt("id_posicao");
				double salarioBase = rs.getDouble("salario_base");
				int numeroVitorias = rs.getInt("numero_vitorias");
				double salarioPorVitoria = rs.getDouble("salario_por_vitoria");

				Jogador j = new Jogador(codigo, nome, contacto, genero, estadoCivil, idModalidade, idPosicao,
						salarioBase, numeroVitorias, salarioPorVitoria);

				lista.add(j);
			}
		}

		return lista;
	}

	public static void atualizarJogador(int codigo, String nome, String contacto, String genero, String estadoCivil,
			int idModalidade, int idPosicao, double salarioBase, int numeroVitorias, double salarioPorVitoria)
			throws SQLException {

		String sql = "UPDATE jogador SET nome=?, contacto=?, genero=?, estado_civil=?, "
				+ "id_modalidade=?, id_posicao=?, salario_base=?, numero_vitorias=?, salario_por_vitoria=? "
				+ "WHERE codigo=?";

		try (Connection con = conexao.conectar(); PreparedStatement stmt = con.prepareStatement(sql)) {

			stmt.setString(1, nome);
			stmt.setString(2, contacto);
			stmt.setString(3, genero);
			stmt.setString(4, estadoCivil);
			stmt.setInt(5, idModalidade);
			stmt.setInt(6, idPosicao);
			stmt.setDouble(7, salarioBase);
			stmt.setInt(8, numeroVitorias);
			stmt.setDouble(9, salarioPorVitoria);
			stmt.setInt(10, codigo);

			stmt.executeUpdate();
		}
	}

	public static void atualizarJogador(Jogador j) throws SQLException {
		ArrayList<Modalidade> listaModalidades = ModalidadeController.buscarTodasModalidades();
		ArrayList<Posicao> listaPosicoes = PosicaoController.listarTodasPosicoes();

		int idModalidade = 0;
		for (Modalidade m : listaModalidades) {
			if (m.getCodigo() == j.getIdModalidade()) {
				idModalidade = m.getCodigo();
				break;
			}
		}

		int idPosicao = 0;
		for (Posicao p : listaPosicoes) {
			if (p.getCodigo() == j.getIdPosicao() && p.getIdModalidade() == idModalidade) {
				idPosicao = p.getCodigo();
				break;
			}
		}

		if (idModalidade == 0 || idPosicao == 0) {
			throw new SQLException("Modalidade ou Posição não encontrados");
		}

		String sql = "UPDATE jogador SET nome=?, contacto=?, genero=?, estado_civil=?, "
				+ "id_modalidade=?, id_posicao=?, salario_base=?, numero_vitorias=?, salario_por_vitoria=? "
				+ "WHERE codigo=?";

		try (Connection con = conexao.conectar(); PreparedStatement stmt = con.prepareStatement(sql)) {

			stmt.setString(1, j.getNome());
			stmt.setString(2, j.getContacto());
			stmt.setString(3, j.getGenero());
			stmt.setString(4, j.getEstadoCivil());
			stmt.setInt(5, idModalidade);
			stmt.setInt(6, idPosicao);
			stmt.setDouble(7, j.getSalarioBase());
			stmt.setInt(8, j.getNumeroVitorias());
			stmt.setDouble(9, j.getSalarioPorVitoria());
			stmt.setInt(10, j.getCodigo());

			stmt.executeUpdate();
		}
	}

	public static void removerJogador(int codigo) throws SQLException {
		String sql = "DELETE FROM jogador WHERE codigo=?";

		try (Connection con = conexao.conectar(); PreparedStatement stmt = con.prepareStatement(sql)) {

			stmt.setInt(1, codigo);
			stmt.executeUpdate();
		}
	}

}
