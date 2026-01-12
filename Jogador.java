package model;

public class Jogador {
    private int codigo;           
    private String nome;
    private String contacto;
    private String genero;
    private String estadoCivil;
    private int idModalidade;     
    private int idPosicao;        
    private double salarioBase;
    private int numeroVitorias;
    private double salarioPorVitoria;

    public Jogador(int codigo, String nome, String contacto, String genero, String estadoCivil,
                   int idModalidade, int idPosicao,
                   double salarioBase, int numeroVitorias, double salarioPorVitoria) {
        this.codigo = codigo;
        this.nome = nome;
        this.contacto = contacto;
        this.genero = genero;
        this.estadoCivil = estadoCivil;
        this.idModalidade = idModalidade;
        this.idPosicao = idPosicao;
        this.salarioBase = salarioBase;
        this.numeroVitorias = numeroVitorias;
        this.salarioPorVitoria = salarioPorVitoria;
    }

    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public String getEstadoCivil() { return estadoCivil; }
    public void setEstadoCivil(String estadoCivil) { this.estadoCivil = estadoCivil; }

    public int getIdModalidade() { return idModalidade; }
    public void setIdModalidade(int idModalidade) { this.idModalidade = idModalidade; }

    public int getIdPosicao() { return idPosicao; }
    public void setIdPosicao(int idPosicao) { this.idPosicao = idPosicao; }

    public double getSalarioBase() { return salarioBase; }
    public void setSalarioBase(double salarioBase) { this.salarioBase = salarioBase; }

    public int getNumeroVitorias() { return numeroVitorias; }
    public void setNumeroVitorias(int numeroVitorias) { this.numeroVitorias = numeroVitorias; }

    public double getSalarioPorVitoria() { return salarioPorVitoria; }
    public void setSalarioPorVitoria(double salarioPorVitoria) { this.salarioPorVitoria = salarioPorVitoria; }

    public double getSalarioTotal() {
        return salarioBase + (numeroVitorias * salarioPorVitoria);
    }

  
}
