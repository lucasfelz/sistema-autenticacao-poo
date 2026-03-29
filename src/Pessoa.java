public abstract class Pessoa {
    private String nome;
    private String CPF;
    private String dataNascimento;
    private String login;
    private String senha;

    public Pessoa(String nome, String CPF, String dataNascimento, String login, String senha) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
        if (!CPF.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
            throw new IllegalArgumentException("CPF inválido. Use o formato 000.000.000-00");
        }
        this.nome = nome;
        this.CPF = CPF;
        this.dataNascimento = dataNascimento;
        this.login = login;
        this.senha = gerarHash(senha);
    }

    public String getNome() { return this.nome; }
    public String getCPF() { return this.CPF; }
    public String getDataNascimento() { return this.dataNascimento; }
    public String getLogin() { return this.login; }

    @Override
    public String toString() {
        return getNome() +
               " | CPF: " + getCPF() +
               " | Data de Nascimento: " + getDataNascimento();
    }

    public boolean acessarSistema(String login, String senha) {
        return getLogin().equals(login) && verificarSenha(senha);
    }

    private String gerarHash(String texto) {
    try {
        java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(texto.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    } catch (Exception e) {
        throw new RuntimeException("Erro ao gerar hash.", e);
    }
}

    public boolean verificarSenha(String senhaInformada) {
        return this.senha.equals(gerarHash(senhaInformada));
    }

    public abstract void exibirPessoa();
}
