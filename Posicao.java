package model;


public class Posicao {
    private int codigo;
    private String nomePosicao;
    private int idModalidade;

    public Posicao(int codigo, String nomePosicao, int idModalidade) {
        if (nomePosicao == null || nomePosicao.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da posição não pode ser nulo ou vazio.");
        }
        if (codigo < 0 || idModalidade < 0) {
            throw new IllegalArgumentException("Código e ID da modalidade não podem ser negativos.");
        }
        this.codigo = codigo;
        this.nomePosicao = nomePosicao;
        this.idModalidade = idModalidade;
    }

   
    public int getCodigo() {
        return codigo;
    }

  
    public String getNomePosicao() {
        return nomePosicao;
    }

  
    public int getIdModalidade() {
        return idModalidade;
    }

    
    public void setCodigo(int codigo) {
        if (codigo < 0) {
            throw new IllegalArgumentException("Código não pode ser negativo.");
        }
        this.codigo = codigo;
    }

   
    public void setNomePosicao(String nomePosicao) {
        if (nomePosicao == null || nomePosicao.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da posição não pode ser nulo ou vazio.");
        }
        this.nomePosicao = nomePosicao;
    }

    public void setIdModalidade(int idModalidade) {
        if (idModalidade < 0) {
            throw new IllegalArgumentException("ID da modalidade não pode ser negativo.");
        }
        this.idModalidade = idModalidade;
    }

   
    @Override
    public String toString() {
        return "Posicao{codigo=" + codigo + ", nomePosicao='" + nomePosicao + "', idModalidade=" + idModalidade + "}";
    }
}