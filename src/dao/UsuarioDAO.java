package dao;

import model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // ── LISTAR TODOS ──────────────────────────────────────────────────────────
    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT codigo, login, senha, sexo FROM usuario ORDER BY codigo";

        Connection conn = Conexao.conectar();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Usuario u = new Usuario(
                    rs.getInt("codigo"),
                    rs.getString("login"),
                    rs.getString("senha"),
                    rs.getString("sexo").charAt(0)
                );
                lista.add(u);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Erro ao listar usuários: " + e.getMessage());
        } finally {
            Conexao.fechar(conn);
        }
        return lista;
    }

    // ── INSERIR ───────────────────────────────────────────────────────────────
    public boolean inserir(Usuario usuario) {
        String sql = "INSERT INTO usuario (login, senha, sexo) VALUES (?, ?, ?)";

        Connection conn = Conexao.conectar();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, usuario.getLogin());
            ps.setString(2, usuario.getSenha());
            ps.setString(3, String.valueOf(usuario.getSexo()));

            int linhas = ps.executeUpdate();
            ps.close();
            return linhas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir usuário: " + e.getMessage());
            return false;
        } finally {
            Conexao.fechar(conn);
        }
    }

    // ── ATUALIZAR ─────────────────────────────────────────────────────────────
    public boolean atualizar(Usuario usuario) {
        String sql = "UPDATE usuario SET login = ?, senha = ?, sexo = ? WHERE codigo = ?";

        Connection conn = Conexao.conectar();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, usuario.getLogin());
            ps.setString(2, usuario.getSenha());
            ps.setString(3, String.valueOf(usuario.getSexo()));
            ps.setInt(4, usuario.getCodigo());

            int linhas = ps.executeUpdate();
            ps.close();
            return linhas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar usuário: " + e.getMessage());
            return false;
        } finally {
            Conexao.fechar(conn);
        }
    }

    // ── EXCLUIR ───────────────────────────────────────────────────────────────
    public boolean excluir(int codigo) {
        String sql = "DELETE FROM usuario WHERE codigo = ?";

        Connection conn = Conexao.conectar();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, codigo);

            int linhas = ps.executeUpdate();
            ps.close();
            return linhas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir usuário: " + e.getMessage());
            return false;
        } finally {
            Conexao.fechar(conn);
        }
    }

    // ── BUSCAR POR CÓDIGO (auxiliar para o Atualizar) ─────────────────────────
    public Usuario buscarPorCodigo(int codigo) {
        String sql = "SELECT codigo, login, senha, sexo FROM usuario WHERE codigo = ?";
        Usuario u = null;

        Connection conn = Conexao.conectar();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, codigo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                u = new Usuario(
                    rs.getInt("codigo"),
                    rs.getString("login"),
                    rs.getString("senha"),
                    rs.getString("sexo").charAt(0)
                );
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Erro ao buscar usuário: " + e.getMessage());
        } finally {
            Conexao.fechar(conn);
        }
        return u;
    }
}
