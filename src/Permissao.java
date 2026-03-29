public interface Permissao {
    boolean acessarSistema(String login, String senha);
    void exibirPermissoes();
}
