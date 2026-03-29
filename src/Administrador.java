
public class Administrador extends Pessoa implements Permissao {

    public Administrador(String nome, String CPF, String dataNascimento, String login, String senha) {
        super(nome, CPF, dataNascimento, login, senha);
    }

    @Override
    public void exibirPermissoes() {
        System.out.println("Administrador pode gerenciar usuários, relatórios e configurações do sistema.");
    }
//simplificando exibir pessoa após uso de toString na classe Pessoa
    @Override
    public void exibirPessoa() {
        System.out.println("Administrador: " + this.toString());
    }
}
