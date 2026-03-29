
public class Operador extends Pessoa implements Permissao {

    public Operador(String nome, String CPF, String dataNascimento, String login, String senha) {
        super(nome, CPF, dataNascimento, login, senha);
    }

    @Override
    public void exibirPermissoes() {
        System.out.println("Operador tem acesso limitado às operações do sistema.");
    }

    //simplificacao de exibirPessoa após uso de toString na classe Pessoa
    @Override
    public void exibirPessoa() {
        System.out.println("Operador: " + toString());
    }
}
