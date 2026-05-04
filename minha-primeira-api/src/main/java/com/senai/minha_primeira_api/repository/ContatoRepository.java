package com.senai.minha_primeira_api.repository;

import com.senai.minha_primeira_api.model.Contato;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ContatoRepository {
    Contato saveContato(Contato contato) throws SQLException;
    List<Contato> findAllContatos() throws SQLException;
    Optional<Contato> findContatoPorId(Long id) throws SQLException;
    boolean existsPorId(Long id) throws SQLException;
    void atualizarContato(Contato contato) throws SQLException;
    void deletePorId(Long id) throws SQLException;
}
