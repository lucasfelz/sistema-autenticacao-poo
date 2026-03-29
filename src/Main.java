public class Main {
    public static void main(String[] args) {
        Administrador admin = new Administrador(
                "Carlos Silva",
                "123.456.789-00",
                "01/01/1990",
                "admin",
                "1234"
        );
        Operador operador = new Operador(
                "João Souza",
                "987.654.321-00",
                "02/02/2000",
                "operador",
                "abcd"
        );
        System.out.println("=== DADOS DOS USUÁRIOS ===");
        admin.exibirPessoa();
        operador.exibirPessoa();

        SistemaAutenticacao sistema = new SistemaAutenticacao("Sistema de Permissões");

        System.out.println("\n=== TESTE DE LOGIN ADMINISTRADOR ===");
        sistema.autenticar(admin, "admin", "1234");

        System.out.println("\n=== TESTE DE LOGIN OPERADOR ===");
        sistema.autenticar(operador, "operador", "abcd");

        System.out.println("\n=== TESTE DE LOGIN INCORRETO ===");
        sistema.autenticar(operador, "operador", "9999");

        System.out.println("\n=== SOBRECARGA - SESSÃO JÁ AUTENTICADA ===");
        sistema.autenticar(admin);

        System.out.println("\n=== TESTE DE VALIDAÇÃO ===");
        try {
            Administrador invalido = new Administrador("", "000.000.000-00", "01/01/1990", "x", "x");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro capturado: " + e.getMessage());
        }
        System.out.println("\n=== TESTE DE HASH DE SENHA ===");
        System.out.println("Senha correta: " + admin.verificarSenha("1234"));
        System.out.println("Senha errada:  " + admin.verificarSenha("0000"));
    }
}
