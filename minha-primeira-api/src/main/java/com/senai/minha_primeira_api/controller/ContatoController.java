package com.senai.minha_primeira_api.controller;

import com.senai.minha_primeira_api.model.Contato;
import com.senai.minha_primeira_api.service.ContatoService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/contatos")
public class ContatoController {
    private final ContatoService contatoService;

    public ContatoController(ContatoService contatoService){
        this.contatoService = contatoService;
    }

    @PostMapping
    public ContatoRespostaDto postContato(
            @RequestBody ContatoRequisicaoDto requisicaoDto){
        try{
            ContatoRespostaDto repostaDto = contatoService.criarContato(requisicaoDto);
            return repostaDto;
        }catch (SQLException | RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping
    public List<Contato> buscarContatos(){
        try{
            List<Contato> contatos = contatoService.obterContatos();
            return contatos;
        }catch (SQLException | RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Contato buscarContatoPorId(@PathVariable Long id){
        try{
            Contato contato = contatoService.buscarPorId(id);
            return contato;
        }catch (SQLException | RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Contato AlterarContato(@PathVariable Long id, @RequestBody Contato contato){
        try{
            contatoService.atualizarContato(id,contato);
            return contato;
        }catch (SQLException | RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deletarPorId(@PathVariable Long id){
        try{
            contatoService.deletarContatoPorId(id);
        }catch (SQLException | RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
