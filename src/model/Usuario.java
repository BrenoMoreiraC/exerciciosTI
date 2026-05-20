package model;

public class Usuario {

    private int    codigo;
    private String login;
    private String senha;
    private char   sexo;

    // Construtor vazio
    public Usuario() {}

    // Construtor com todos os campos
    public Usuario(int codigo, String login, String senha, char sexo) {
        this.codigo = codigo;
        this.login  = login;
        this.senha  = senha;
        this.sexo   = sexo;
    }

    // Construtor sem o código (usado no INSERT, pois é gerado pelo banco)
    public Usuario(String login, String senha, char sexo) {
        this.login = login;
        this.senha = senha;
        this.sexo  = sexo;
    }

    // Getters e Setters
    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public char getSexo() { return sexo; }
    public void setSexo(char sexo) { this.sexo = sexo; }

    @Override
    public String toString() {
        return String.format("| %-6d | %-20s | %-20s | %-4s |",
                codigo, login, senha, sexo == 'M' ? "M" : "F");
    }
}
