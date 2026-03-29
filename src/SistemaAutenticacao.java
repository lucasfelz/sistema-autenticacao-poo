public class SistemaAutenticacao {
    private String nomeSistema;

    public SistemaAutenticacao(String nomeSistema) {
        this.nomeSistema = nomeSistema;
    }

    public void autenticar(Permissao usuario, String login, String senha) {
        System.out.println("[" + nomeSistema + "] Verificando acesso...");
        if (usuario.acessarSistema(login, senha)) {
            System.out.println("Acesso autorizado!");
            usuario.exibirPermissoes();
        } else {
            System.out.println("Acesso negado! Login ou senha incorretos.");
        }
    }

    // SOBRECARGA - mesmo nome, parâmetros diferentes
    public void autenticar(Permissao usuario) {
        System.out.println("[" + nomeSistema + "] Sessão já autenticada. Exibindo permissões:");
        usuario.exibirPermissoes();
    }
}
