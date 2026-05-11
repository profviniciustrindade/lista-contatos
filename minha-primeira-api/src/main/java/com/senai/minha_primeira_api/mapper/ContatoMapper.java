package com.senai.minha_primeira_api.mapper;

import com.senai.minha_primeira_api.dto.ContatoRequisicaoDto;
import com.senai.minha_primeira_api.dto.ContatoRespostaDto;
import com.senai.minha_primeira_api.model.Contato;
import org.springframework.stereotype.Component;

@Component
public class ContatoMapper {
    public Contato paraEntidade(
            ContatoRequisicaoDto requisicaoDto
    ){
        return new Contato(
                requisicaoDto.nome(),
                requisicaoDto.numero()
        );
    }

    public ContatoRespostaDto paraRespostaDto(
            Contato contato
    ){
        return new ContatoRespostaDto(
                contato.getId(),
                contato.getNome(),
                contato.getNumero()
        );
    }
}
