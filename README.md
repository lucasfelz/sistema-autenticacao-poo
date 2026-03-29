# Sistema de Permissões — Java POO

Projeto desenvolvido como atividade avaliativa da disciplina de Programação Orientada a Objetos.  
Demonstra na prática os principais conceitos de POO em Java através de um sistema de autenticação e controle de permissões de usuários.

---

## Sumário

1. [Estrutura de arquivos](#estrutura-de-arquivos)
2. [Como executar](#como-executar)
3. [Conceitos de POO aplicados](#conceitos-de-poo-aplicados)
4. [Explicação arquivo por arquivo](#explicação-arquivo-por-arquivo)
5. [Saída esperada do programa](#saída-esperada-do-programa)

---

## Estrutura de arquivos

```
src/
├── Pessoa.java              # Classe abstrata base
├── Permissao.java           # Interface de contrato
├── Administrador.java       # Subclasse com permissões totais
├── Operador.java            # Subclasse com permissões limitadas
├── SistemaAutenticacao.java # Objeto responsável pela autenticação
└── Main.java                # Ponto de entrada do programa
```

---

## Como executar

Com o JDK instalado, dentro da pasta `src`:

```bash
javac *.java && java Main
```

`javac *.java` compila todos os arquivos `.java` e gera os `.class`.  
`java Main` executa a classe principal.

---

## Conceitos de POO aplicados

### 1. Classes e Objetos

Uma **classe** é um molde — ela define quais dados e comportamentos um tipo de coisa terá.  
Um **objeto** é uma instância concreta criada a partir desse molde.

```java
// Classe = o molde
public class Administrador { ... }

// Objeto = instância concreta criada com "new"
Administrador admin = new Administrador("Carlos Silva", ...);
```

No projeto há 4 classes concretas (`Administrador`, `Operador`, `SistemaAutenticacao`, `Main`),
uma classe abstrata (`Pessoa`) e uma interface (`Permissao`).

---

### 2. Atributos e Métodos

**Atributos** são as variáveis que pertencem a uma classe — representam o estado do objeto.  
**Métodos** são as funções que pertencem a uma classe — representam o comportamento do objeto.

```java
public abstract class Pessoa {
    // ATRIBUTOS — o que toda pessoa tem
    private String nome;
    private String CPF;
    private String login;
    private String senha;

    // MÉTODOS — o que toda pessoa sabe fazer
    public String getNome() { return this.nome; }
    public boolean verificarSenha(String senhaInformada) { ... }
}
```

---

### 3. Encapsulamento

**Encapsulamento** é o princípio de esconder os dados internos de uma classe, permitindo acesso
somente por métodos controlados. Em Java isso é feito com os modificadores de acesso:

- `private` — acessível apenas dentro da própria classe
- `public` — acessível de qualquer lugar

Os atributos de `Pessoa` são todos `private`. Para lê-los de fora, existem os **getters**:

```java
private String nome;  // ninguém acessa diretamente

public String getNome() {  // acesso controlado via método
    return this.nome;
}
```

Isso impede que código externo altere `nome` diretamente com `admin.nome = "outro"`.  
A senha nunca é exposta — não há getter para ela, apenas o método `verificarSenha()`.

---

### 4. Herança

**Herança** permite que uma classe filha herde atributos e métodos de uma classe pai,
evitando repetição de código. Em Java usa-se a palavra `extends`.

```
Pessoa (pai — classe abstrata)
├── Administrador (filho)
└── Operador (filho)
```

`Administrador` e `Operador` herdam automaticamente de `Pessoa`:
- Todos os atributos (`nome`, `CPF`, `login`, `senha`)
- Todos os métodos (`getNome()`, `acessarSistema()`, `verificarSenha()`, `toString()`)

O construtor filho chama o construtor pai com `super(...)`:

```java
public Administrador(String nome, String CPF, String dataNascimento, String login, String senha) {
    super(nome, CPF, dataNascimento, login, senha);  // chama o construtor de Pessoa
}
```

---

### 5. Classe Abstrata

Uma **classe abstrata** não pode ser instanciada diretamente — ela existe para ser herdada.
Pode ter métodos concretos (com implementação) e métodos abstratos (sem implementação, que
as subclasses são obrigadas a implementar).

```java
public abstract class Pessoa {
    // método abstrato — sem corpo, obriga subclasses a implementar
    public abstract void exibirPessoa();

    // método concreto — já tem implementação, subclasses herdam
    public boolean acessarSistema(String login, String senha) {
        return getLogin().equals(login) && verificarSenha(senha);
    }
}
```

Tentar fazer `new Pessoa(...)` causaria erro de compilação.

---

### 6. Interface

Uma **interface** é um contrato — define quais métodos uma classe deve ter, sem implementar
nenhum deles. Em Java usa-se `interface` para declarar e `implements` para cumprir o contrato.

```java
// contrato — define o que deve existir
public interface Permissao {
    boolean acessarSistema(String login, String senha);
    void exibirPermissoes();
}

// Administrador assina o contrato e é obrigado a implementar os dois métodos
public class Administrador extends Pessoa implements Permissao {
    @Override
    public void exibirPermissoes() {
        System.out.println("Administrador pode gerenciar usuários...");
    }
}
```

A diferença entre classe abstrata e interface: uma classe pode `implements` várias interfaces,
mas só pode `extends` uma classe. Interfaces são contratos de comportamento; classes abstratas
são bases de herança.

---

### 7. Sobrescrita de Método (`@Override`)

**Sobrescrita** ocorre quando uma subclasse redefine um método que já existe na classe pai
(ou em `Object`, a classe raiz de tudo em Java). A anotação `@Override` avisa o compilador,
que valida se o método realmente existe na classe pai.

```java
// Em Object (classe raiz do Java) existe toString() que retorna algo inútil
// Em Pessoa, sobrescrevemos para retornar dados úteis
@Override
public String toString() {
    return getNome() + " | CPF: " + getCPF() + " | Data de Nascimento: " + getDataNascimento();
}
```

```java
// Em Pessoa existe exibirPessoa() como abstrato
// Administrador sobrescreve com sua implementação específica
@Override
public void exibirPessoa() {
    System.out.println("Administrador: " + this.toString());
}
```

---

### 8. Sobrecarga de Método (Overload)

**Sobrecarga** ocorre quando existem dois ou mais métodos com o **mesmo nome** na mesma classe,
mas com **parâmetros diferentes**. O Java escolhe qual versão chamar baseado nos argumentos passados.

```java
public class SistemaAutenticacao {

    // versão 1 — autentica com login e senha
    public void autenticar(Permissao usuario, String login, String senha) {
        System.out.println("[" + nomeSistema + "] Verificando acesso...");
        if (usuario.acessarSistema(login, senha)) {
            System.out.println("Acesso autorizado!");
            usuario.exibirPermissoes();
        } else {
            System.out.println("Acesso negado! Login ou senha incorretos.");
        }
    }

    // versão 2 — SOBRECARGA: mesmo nome, parâmetro diferente
    // usada quando a sessão já está autenticada
    public void autenticar(Permissao usuario) {
        System.out.println("[" + nomeSistema + "] Sessão já autenticada. Exibindo permissões:");
        usuario.exibirPermissoes();
    }
}
```

```java
sistema.autenticar(admin, "admin", "1234"); // chama versão 1
sistema.autenticar(admin);                  // chama versão 2 — sobrecarga
```

---

### 9. Polimorfismo

**Polimorfismo** é a capacidade de tratar objetos de tipos diferentes de forma uniforme.
`SistemaAutenticacao.autenticar()` recebe um `Permissao` — não importa se é `Administrador`
ou `Operador`, o método funciona para qualquer um que implemente a interface.

```java
// o parâmetro é do tipo Permissao (interface)
public void autenticar(Permissao usuario, String login, String senha) {
    usuario.acessarSistema(login, senha);  // funciona para qualquer implementador
    usuario.exibirPermissoes();            // cada um exibe a sua mensagem específica
}
```

Quando `usuario.exibirPermissoes()` é chamado em um `Administrador`, exibe a mensagem de admin.
Quando chamado em um `Operador`, exibe a mensagem de operador. Mesmo método, comportamentos
diferentes — isso é polimorfismo.

---

## Explicação arquivo por arquivo

### `Pessoa.java` — Classe abstrata base

```java
public abstract class Pessoa {
    // atributos privados — encapsulamento
    private String nome;
    private String CPF;
    private String dataNascimento;
    private String login;
    private String senha;

    // construtor com validação de dados
    public Pessoa(String nome, String CPF, String dataNascimento, String login, String senha) {

        // impede criação de objeto com nome vazio
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }

        // impede CPF fora do formato 000.000.000-00
        if (!CPF.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
            throw new IllegalArgumentException("CPF inválido. Use o formato 000.000.000-00");
        }

        this.nome = nome;
        this.CPF = CPF;
        this.dataNascimento = dataNascimento;
        this.login = login;
        this.senha = gerarHash(senha);  // armazena hash, não texto puro
    }

    // getters — única forma de acessar atributos privados de fora
    public String getNome() { return this.nome; }
    public String getCPF() { return this.CPF; }
    public String getDataNascimento() { return this.dataNascimento; }
    public String getLogin() { return this.login; }

    // sobrescrita de Object.toString() — retorna representação útil do objeto
    @Override
    public String toString() {
        return getNome() +
               " | CPF: " + getCPF() +
               " | Data de Nascimento: " + getDataNascimento();
    }

    // implementação central de autenticação — evita duplicação nas subclasses
    public boolean acessarSistema(String login, String senha) {
        return getLogin().equals(login) && verificarSenha(senha);
    }

    // compara hash da senha informada com hash armazenado
    public boolean verificarSenha(String senhaInformada) {
        return this.senha.equals(gerarHash(senhaInformada));
    }

    // gera hash SHA-256 de qualquer string
    // private — método auxiliar interno, não exposto para fora da classe
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

    // método abstrato — cada subclasse DEVE implementar sua própria versão
    public abstract void exibirPessoa();
}
```

---

### `Permissao.java` — Interface (contrato)

```java
public interface Permissao {
    // qualquer classe que implemente Permissao é obrigada a ter esses dois métodos
    boolean acessarSistema(String login, String senha);
    void exibirPermissoes();
}
```

A interface não tem implementação — só declara o contrato.  
`Administrador` e `Operador` assinam esse contrato com `implements Permissao`.

---

### `Administrador.java` — Subclasse com permissões totais

```java
public class Administrador extends Pessoa implements Permissao {

    // construtor chama o pai com super(...)
    public Administrador(String nome, String CPF, String dataNascimento, String login, String senha) {
        super(nome, CPF, dataNascimento, login, senha);
    }

    // implementação do contrato Permissao
    @Override
    public void exibirPermissoes() {
        System.out.println("Administrador pode gerenciar usuários, relatórios e configurações do sistema.");
    }

    // sobrescrita do método abstrato de Pessoa
    // usa this.toString() que vem herdado de Pessoa
    @Override
    public void exibirPessoa() {
        System.out.println("Administrador: " + this.toString());
    }
}
```

`acessarSistema` não precisa ser declarado aqui — é herdado de `Pessoa`, que já o implementa.

---

### `Operador.java` — Subclasse com permissões limitadas

```java
public class Operador extends Pessoa implements Permissao {

    public Operador(String nome, String CPF, String dataNascimento, String login, String senha) {
        super(nome, CPF, dataNascimento, login, senha);
    }

    // mesma estrutura do Administrador, mas com mensagem de permissão diferente
    @Override
    public void exibirPermissoes() {
        System.out.println("Operador tem acesso limitado às operações do sistema.");
    }

    @Override
    public void exibirPessoa() {
        System.out.println("Operador: " + this.toString());
    }
}
```

---

### `SistemaAutenticacao.java` — Objeto responsável pela autenticação

```java
public class SistemaAutenticacao {

    // atributo próprio — o objeto tem identidade
    private String nomeSistema;

    // construtor — recebe o nome do sistema
    public SistemaAutenticacao(String nomeSistema) {
        this.nomeSistema = nomeSistema;
    }

    // versão completa — verifica credenciais
    public void autenticar(Permissao usuario, String login, String senha) {
        System.out.println("[" + nomeSistema + "] Verificando acesso...");
        if (usuario.acessarSistema(login, senha)) {
            System.out.println("Acesso autorizado!");
            usuario.exibirPermissoes();
        } else {
            System.out.println("Acesso negado! Login ou senha incorretos.");
        }
    }

    // SOBRECARGA — mesmo nome, parâmetro diferente
    // usada quando sessão já está autenticada
    public void autenticar(Permissao usuario) {
        System.out.println("[" + nomeSistema + "] Sessão já autenticada. Exibindo permissões:");
        usuario.exibirPermissoes();
    }
}
```

---

### `Main.java` — Ponto de entrada do programa

```java
public class Main {
    public static void main(String[] args) {

        // criação dos objetos — construtores validam os dados automaticamente
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

        // exibe dados usando toString() herdado de Pessoa
        System.out.println("=== DADOS DOS USUÁRIOS ===");
        admin.exibirPessoa();
        operador.exibirPessoa();

        // instancia o sistema de autenticação como objeto
        SistemaAutenticacao sistema = new SistemaAutenticacao("Sistema de Permissões");

        // teste de login correto — Administrador
        System.out.println("\n=== TESTE DE LOGIN ADMINISTRADOR ===");
        sistema.autenticar(admin, "admin", "1234");

        // teste de login correto — Operador
        System.out.println("\n=== TESTE DE LOGIN OPERADOR ===");
        sistema.autenticar(operador, "operador", "abcd");

        // teste de login com senha errada
        System.out.println("\n=== TESTE DE LOGIN INCORRETO ===");
        sistema.autenticar(operador, "operador", "9999");

        // demonstração de sobrecarga — chama a versão sem credenciais
        System.out.println("\n=== SOBRECARGA - SESSÃO JÁ AUTENTICADA ===");
        sistema.autenticar(admin);

        // demonstração de validação — tenta criar objeto inválido
        System.out.println("\n=== TESTE DE VALIDAÇÃO ===");
        try {
            Administrador invalido = new Administrador("", "000.000.000-00", "01/01/1990", "x", "x");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro capturado: " + e.getMessage());
        }

        // demonstração de hash — senha correta retorna true, errada retorna false
        System.out.println("\n=== TESTE DE HASH DE SENHA ===");
        System.out.println("Senha correta: " + admin.verificarSenha("1234"));
        System.out.println("Senha errada:  " + admin.verificarSenha("0000"));
    }
}
```

---

## Saída esperada do programa

```
=== DADOS DOS USUÁRIOS ===
Administrador: Carlos Silva | CPF: 123.456.789-00 | Data de Nascimento: 01/01/1990
Operador: João Souza | CPF: 987.654.321-00 | Data de Nascimento: 02/02/2000

=== TESTE DE LOGIN ADMINISTRADOR ===
[Sistema de Permissões] Verificando acesso...
Acesso autorizado!
Administrador pode gerenciar usuários, relatórios e configurações do sistema.

=== TESTE DE LOGIN OPERADOR ===
[Sistema de Permissões] Verificando acesso...
Acesso autorizado!
Operador tem acesso limitado às operações do sistema.

=== TESTE DE LOGIN INCORRETO ===
[Sistema de Permissões] Verificando acesso...
Acesso negado! Login ou senha incorretos.

=== SOBRECARGA - SESSÃO JÁ AUTENTICADA ===
[Sistema de Permissões] Sessão já autenticada. Exibindo permissões:
Administrador pode gerenciar usuários, relatórios e configurações do sistema.

=== TESTE DE VALIDAÇÃO ===
Erro capturado: Nome não pode ser vazio.

=== TESTE DE HASH DE SENHA ===
Senha correta: true
Senha errada:  false
```

---

## Relação entre as classes

```
                    «interface»
                    Permissao
                    + acessarSistema()
                    + exibirPermissoes()
                          ▲
                          │ implements
          ┌───────────────┼───────────────┐
          │                               │
«abstract»│                               │
  Pessoa ─┼── extends ── Administrador    Operador
  - nome  │              + exibirPessoa() + exibirPessoa()
  - CPF   │              + exibirPermissoes() + exibirPermissoes()
  - login │
  - senha │
  + acessarSistema()          SistemaAutenticacao
  + verificarSenha()          - nomeSistema
  + toString()                + autenticar(usuario, login, senha)
  + exibirPessoa() [abstract] + autenticar(usuario)  ← sobrecarga
```
