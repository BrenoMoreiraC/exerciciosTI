package principal;

import dao.UsuarioDAO;
import model.Usuario;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Principal {

    static Scanner sc  = new Scanner(System.in);
    static UsuarioDAO dao = new UsuarioDAO();

    public static void main(String[] args) {
        int opcao;

        do {
            exibirMenu();
            opcao = lerInt("Escolha uma opção: ");

            switch (opcao) {
                case 1: listar();   break;
                case 2: inserir();  break;
                case 3: excluir();  break;
                case 4: atualizar(); break;
                case 5: System.out.println("\nSaindo do sistema. Até logo!"); break;
                default: System.out.println("\nOpção inválida! Tente novamente.");
            }

        } while (opcao != 5);
    }

    // ── MENU ──────────────────────────────────────────────────────────────────
    static void exibirMenu() {
        System.out.println("\n╔══════════════════════════════╗");
        System.out.println("║     GERENCIAMENTO DE USUÁRIOS   ║");
        System.out.println("╠══════════════════════════════╣");
        System.out.println("║  1 - Listar                     ║");
        System.out.println("║  2 - Inserir                    ║");
        System.out.println("║  3 - Excluir                    ║");
        System.out.println("║  4 - Atualizar                  ║");
        System.out.println("║  5 - Sair                       ║");
        System.out.println("╚══════════════════════════════╝");
    }

    // ── 1. LISTAR ─────────────────────────────────────────────────────────────
    static void listar() {
        List<Usuario> lista = dao.listar();

        if (lista.isEmpty()) {
            System.out.println("\nNenhum usuário cadastrado.");
            return;
        }

        System.out.println("\n+--------+----------------------+----------------------+------+");
        System.out.println("| Código | Login                | Senha                | Sexo |");
        System.out.println("+--------+----------------------+----------------------+------+");
        for (Usuario u : lista) {
            System.out.println(u);
        }
        System.out.println("+--------+----------------------+----------------------+------+");
    }

    // ── 2. INSERIR ────────────────────────────────────────────────────────────
    static void inserir() {
        System.out.println("\n--- INSERIR USUÁRIO ---");

        System.out.print("Login : ");
        String login = sc.nextLine().trim();

        System.out.print("Senha : ");
        String senha = sc.nextLine().trim();

        char sexo = lerSexo();

        Usuario novoUsuario = new Usuario(login, senha, sexo);
        boolean ok = dao.inserir(novoUsuario);

        System.out.println(ok ? "\nUsuário inserido com sucesso!" : "\nFalha ao inserir usuário.");
    }

    // ── 3. EXCLUIR ────────────────────────────────────────────────────────────
    static void excluir() {
        System.out.println("\n--- EXCLUIR USUÁRIO ---");
        listar();

        int codigo = lerInt("Digite o código do usuário a excluir: ");
        boolean ok = dao.excluir(codigo);

        System.out.println(ok ? "\nUsuário excluído com sucesso!" : "\nUsuário não encontrado.");
    }

    // ── 4. ATUALIZAR ──────────────────────────────────────────────────────────
    static void atualizar() {
        System.out.println("\n--- ATUALIZAR USUÁRIO ---");
        listar();

        int codigo = lerInt("Digite o código do usuário a atualizar: ");
        Usuario existente = dao.buscarPorCodigo(codigo);

        if (existente == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        System.out.println("Usuário atual: " + existente);
        System.out.println("(Pressione ENTER para manter o valor atual)");

        System.out.print("Novo login [" + existente.getLogin() + "]: ");
        String login = sc.nextLine().trim();
        if (!login.isEmpty()) existente.setLogin(login);

        System.out.print("Nova senha [" + existente.getSenha() + "]: ");
        String senha = sc.nextLine().trim();
        if (!senha.isEmpty()) existente.setSenha(senha);

        System.out.print("Novo sexo (M/F) [" + existente.getSexo() + "]: ");
        String sexoStr = sc.nextLine().trim().toUpperCase();
        if (sexoStr.equals("M") || sexoStr.equals("F")) {
            existente.setSexo(sexoStr.charAt(0));
        }

        boolean ok = dao.atualizar(existente);
        System.out.println(ok ? "\nUsuário atualizado com sucesso!" : "\nFalha ao atualizar usuário.");
    }

    // ── AUXILIARES ────────────────────────────────────────────────────────────
    static int lerInt(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                int valor = sc.nextInt();
                sc.nextLine(); // limpar buffer
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("Digite apenas números inteiros!");
                sc.nextLine();
            }
        }
    }

    static char lerSexo() {
        while (true) {
            System.out.print("Sexo (M/F): ");
            String entrada = sc.nextLine().trim().toUpperCase();
            if (entrada.equals("M") || entrada.equals("F")) {
                return entrada.charAt(0);
            }
            System.out.println("Sexo inválido! Digite M ou F.");
        }
    }
}
