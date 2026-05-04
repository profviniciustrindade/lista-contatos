package com.senai.minha_primeira_api.repository;

import com.senai.minha_primeira_api.infraecstruture.ConnectionFactory;
import com.senai.minha_primeira_api.model.Contato;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ContatoRepositoryImpl implements ContatoRepository{

    public Contato saveContato(Contato contato) throws SQLException {
        String command = """
                INSERT INTO contato
                (nome, numero)
                VALUES
                (?,?)
                """;

        try(Connection conn = ConnectionFactory.conectar();
            PreparedStatement stmt = conn.prepareStatement(command, Statement.RETURN_GENERATED_KEYS)){

            stmt.setString(1, contato.getNome());
            stmt.setString(2, contato.getNumero());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if(rs.next()){
                Long id = rs.getLong(1);
                contato.setId(id);
                return contato;
            }
        }

        throw new RuntimeException("Erro ao salvar o contato!");
    }

    public List<Contato> findAllContatos() throws SQLException{
        List<Contato> contatos = new ArrayList<>();
        String query = """
                    SELECT id
                          ,nome
                          ,numero
                    FROM contato
                """;
        try(Connection conn = ConnectionFactory.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Long id = rs.getLong("id");
                String nome = rs.getString("nome");
                String numero = rs.getString("numero");

                contatos.add(new Contato(
                   id,
                   nome,
                   numero
                ));
            }
        }

        return contatos;
    }

    public Optional<Contato> findContatoPorId(Long id) throws SQLException{
        String query = """
                    SELECT nome
                          ,numero
                    FROM contato
                    WHERE id = ?
                """;
        try(Connection conn = ConnectionFactory.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setLong(1,id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                String nome = rs.getString("nome");
                String numero = rs.getString("numero");

                return Optional.of(
                        new Contato(id
                                ,nome
                                ,numero));
            }
        }

        return Optional.empty();
    }

    public boolean existsPorId(Long id) throws SQLException{
        String query = """
                    SELECT COUNT(0) AS resultado
                    FROM contato
                    WHERE id = ?
                """;
        try(Connection conn = ConnectionFactory.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setLong(1,id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                int resultado = rs.getInt("resultado");
                if(resultado == 1){
                    return true;
                }else{
                    return false;
                }
            }
        }

        throw new RuntimeException("Erro ao buscar se usuário existe!");
    }

    public void atualizarContato(Contato contato) throws SQLException{
        String command = """
                UPDATE contato
                set nome = ?,
                numero = ?
                WHERE id = ?
                """;
        try(Connection conn = ConnectionFactory.conectar();
            PreparedStatement stmt = conn.prepareStatement(command)){

            stmt.setString(1, contato.getNome());
            stmt.setString(2, contato.getNumero());
            stmt.setLong(3,contato.getId());
            stmt.executeUpdate();
        }
    }

    public void deletePorId(Long id) throws SQLException{
        String command = """
                DELETE FROM contato
                WHERE id = ?
                """;

        try(Connection conn = ConnectionFactory.conectar();
            PreparedStatement stmt = conn.prepareStatement(command)){

            stmt.setLong(1,id);
            stmt.execute();
        }
    }
}
