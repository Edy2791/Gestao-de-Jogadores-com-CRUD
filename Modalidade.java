package model;

public class Modalidade {
    private int codigo;
    private String nomeModalidade;

    public Modalidade(int codigo, String nomeModalidade) {
        this.codigo = codigo;
        this.nomeModalidade = nomeModalidade;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getnomeModalidade() {
        return nomeModalidade;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setnomeodalidade(String nomeModalidade) {
        this.nomeModalidade = nomeModalidade;
    }
    
     @Override
    public String toString() { return nomeModalidade; }


}


   