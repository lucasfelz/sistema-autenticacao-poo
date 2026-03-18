

abstract class Pessoa {
  private String nome;
  private String CPF;
  private String dataNascimento;
  private String login;
  private String senha;


  // construtor da classe abstrata
  public Pessoa(String nome, String CPF, String dataNascimento, String login, String senha) {
    this.nome = nome;
    this.CPF = CPF;
    this.dataNascimento = dataNascimento;
    this.login = login;
    this.senha = senha;
  }

  // getters da classe
  public String getNome() {
     return this.nome;
  }

  public String getCPF() {
    return this.CPF;
  }

  public String getDataNascimento() {
    return this.dataNascimento;
  }

  public String getLogin() {
    return this.login;
  }

  // verificação simples se a senha foi informada
  public boolean verificarSenha(String senhaInformada) {
    return this.senha.equals(senhaInformada);
  }

  // metodo abstrato para exibir os dados da pessoa.
  public abstract void exibirPessoa();

}


  // interface de permissao; permissoes de usuarios
  // pensei em dois metodos, acessarSistema() e exibirPermissoes()
  public interface Permissao {
    void acessarSistema();
    void exibirPermissoes();

  }






