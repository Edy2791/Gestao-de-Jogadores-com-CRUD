package controllerr;

import controllerr.conexao;
import model.Modalidade;
import model.Posicao;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class PosicaoController {

    public static void adicionarPosicao(int codigo, String nomePosicao, int codigoModalidade) throws SQLException {
        try (Connection con = conexao.conectar();
             PreparedStatement stmt = con.prepareStatement("INSERT INTO posicao (codigo, nome_posicao, id_modalidade) VALUES (?, ?, ?)")) { 
            stmt.setInt(1, codigo);
            stmt.setString(2, nomePosicao);
            stmt.setInt(3, codigoModalidade); 
            stmt.executeUpdate();
    }
        }
    
       
    public static ArrayList<Posicao> buscarPosicoesPorModalidade(int codigoModalidade) throws SQLException {
        ArrayList<Posicao> lista = new ArrayList<>();

        try (Connection con = conexao.conectar();
             PreparedStatement ps = con.prepareStatement("SELECT codigo, nome_posicao FROM posicao WHERE id_modalidade = ?")) {

            ps.setInt(1, codigoModalidade);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Posicao p = new Posicao(
                        rs.getInt("codigo"),
                        rs.getString("nome_posicao"),
                        codigoModalidade           
                    );
                    lista.add(p);
                }
            }
        }
        return lista;
    }

 
    public static ArrayList<Posicao> listarTodasPosicoes() throws SQLException {
        ArrayList<Posicao> posicoes = new ArrayList<>();
      
        try (Connection con = conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(
                 "SELECT codigo, nome_posicao, id_modalidade FROM posicao ORDER BY codigo ASC");
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                int codigo = rs.getInt("codigo");
                String nome = rs.getString("nome_posicao");
                int idModalidade = rs.getInt("id_modalidade");
                
                posicoes.add(new Posicao(codigo, nome, idModalidade));
            }
        }

        return posicoes;
    }



    public static void atualizarPosicao(int codigo, String nomePosicao, int codigoModalidade) {
        try (Connection con = conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(
                 "UPDATE posicao SET nome_posicao = ?, id_modalidade = ? WHERE codigo = ?")) {

            stmt.setString(1, nomePosicao);
            stmt.setInt(2, codigoModalidade);
            stmt.setInt(3, codigo);

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar posição: " + e.getMessage());
        }
    }

    public static void removerPosicao(int codigoPosicao) throws Exception {
        Connection con = null;
        try {
            con = conexao.conectar();
            PreparedStatement ps1 = con.prepareStatement(
                "SELECT id_modalidade FROM posicao WHERE codigo = ?"
            );
            ps1.setInt(1, codigoPosicao);
            ResultSet rs = ps1.executeQuery();

            int codigoModalidade;
            if (rs.next()) {
                codigoModalidade = rs.getInt("id_modalidade");
            } else {
                throw new Exception("Posição não encontrada!");
            }
            
            PreparedStatement ps2 = con.prepareStatement(
                "DELETE FROM posicao WHERE codigo = ?"
            );
            ps2.setInt(1, codigoPosicao);
            ps2.executeUpdate();

            PreparedStatement ps3 = con.prepareStatement(
                "SELECT COUNT(*) FROM posicao WHERE id_modalidade = ?"
            );
            ps3.setInt(1, codigoModalidade);
            ResultSet rs2 = ps3.executeQuery();

            if (rs2.next() && rs2.getInt(1) == 0) {
                PreparedStatement ps4 = con.prepareStatement(
                    "DELETE FROM modalidade WHERE codigo = ?"
                );
                ps4.setInt(1, codigoModalidade);
                ps4.executeUpdate();
            }

        } catch (Exception ex) {
            throw new Exception("Erro ao remover posição: " + ex.getMessage());
        } finally {
            if (con != null) {
                try { con.close(); } catch (Exception e) {}
            }
        }
    }

}
