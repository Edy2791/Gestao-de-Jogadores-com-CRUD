package controllerr;

import controllerr.conexao;
import model.Modalidade;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ModalidadeController {

    public static void adicionarModalidade(int codigo, String nome) throws SQLException {
        try (Connection con = conexao.conectar();
             PreparedStatement ps = con.prepareStatement("INSERT INTO modalidade (codigo, nome_modalidade) VALUES (?, ?)")) {
            ps.setInt(1, codigo);
            ps.setString(2, nome);
            ps.executeUpdate();
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar modalidade: " + e.getMessage());
        }
    }

    public static ArrayList<Modalidade> buscarTodasModalidades() throws SQLException { 
        ArrayList<Modalidade> modalidades = new ArrayList<>();
           Connection con = conexao.conectar();
             PreparedStatement ps = con.prepareStatement("SELECT codigo, nome_modalidade FROM modalidade ORDER BY codigo ASC");
             ResultSet rs = ps.executeQuery(); 
            while (rs.next()) {
                modalidades.add(new Modalidade(rs.getInt("codigo"), rs.getString("nome_modalidade")));
            }
            
            return modalidades;
        }
        
    

    public static String getNomePorCodigo(int codigo) throws SQLException {
        String nome = null;
        try (Connection con = conexao.conectar();
             PreparedStatement ps = con.prepareStatement("SELECT nome_modalidade FROM modalidade WHERE codigo = ?")) {
            ps.setInt(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    nome = rs.getString("nome_modalidade");
                }
            }
        }
        return nome;
    }

    public static void atualizarModalidade(int codigo, String nome_modalidade) throws SQLException {
        String sql = "UPDATE modalidade SET nome_modalidade = ? WHERE codigo = ?";
        try (Connection con = conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, nome_modalidade);
            stmt.setInt(2, codigo);
            stmt.executeUpdate();
        } 
    }
    
    public static void removerModalidade(int codigo) throws SQLException {
        try (Connection con = conexao.conectar();
             PreparedStatement stmt = con.prepareStatement("DELETE FROM modalidade WHERE codigo = ?")) {

            stmt.setInt(1, codigo);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new SQLException("Nenhuma modalidade encontrada com este código.");
            }
        }
    }

}
