package com.senai.minha_primeira_api.service;

import com.senai.minha_primeira_api.model.Contato;
import com.senai.minha_primeira_api.repository.ContatoRepository;
import org.springframework.stereotype.Service;
import com.senai.minha_primeira_api.mapper;

import java.sql.SQLException;
import java.util.List;

@Service
public class ContatoService {
    private final ContatoRepository contatoRepository;
    private final ContatoMapper contatoMapper;

    public ContatoService(
            ContatoRepository contatoRepository,
            ContatoMapper contatoMapper){
        this.contatoRepository=contatoRepository;
        this.contatoMapper=contatoMapper;
    }

    public ContatoRespostaDto criarContato(
            ContatoRequisicaoDto contatoRequisicaoDto) throws SQLException {
        Contato contato
                = contatoMapper.paraEntidade(contatoRequisicaoDto);

        contatoRepository.saveContato(contato);

        return contatoMapper.paraRespostaDto(contato);
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
