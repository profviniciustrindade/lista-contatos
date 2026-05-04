package com.senai.minha_primeira_api.service;

import com.senai.minha_primeira_api.model.Contato;
import com.senai.minha_primeira_api.repository.ContatoRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ContatoService {
    private final ContatoRepository contatoRepository;

    public ContatoService(ContatoRepository contatoRepository){
        this.contatoRepository=contatoRepository;
    }

    public Contato criarContato(Contato contato) throws SQLException {
        contatoRepository.saveContato(contato);

        return contato;
    }

    public List<Contato> obterContatos() throws SQLException{
        List<Contato> contatos = contatoRepository.findAllContatos();

        return contatos;
    }

    public Contato buscarPorId(Long id) throws SQLException{
        Contato contato = contatoRepository.findContatoPorId(id)
                .orElseThrow(() -> new RuntimeException("O contato não foi encontrado!"));

        return contato;
    }

    public Contato atualizarContato(Long id, Contato contato)throws SQLException{
        if(!contatoRepository.existsPorId(id)){
            throw new RuntimeException("Usuário não encontrado!");
        }

        contato.setId(id);
        contatoRepository.atualizarContato(contato);

        return contato;
    }

    public void deletarContatoPorId(Long id) throws SQLException{
        if(!contatoRepository.existsPorId(id)){
            throw new RuntimeException("Usuário não encontrado!");
        }

        contatoRepository.deletePorId(id);
    }
}
