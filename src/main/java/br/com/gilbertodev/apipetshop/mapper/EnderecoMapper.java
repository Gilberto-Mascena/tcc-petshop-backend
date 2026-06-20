package br.com.gilbertodev.apipetshop.mapper;

import br.com.gilbertodev.apipetshop.dtos.endereco.EnderecoRequestDTO;
import br.com.gilbertodev.apipetshop.entities.Endereco;
import org.springframework.stereotype.Component;

@Component
public class EnderecoMapper {

    public void mapearEndereco(EnderecoRequestDTO dto, Endereco endereco) {
        if (dto == null || endereco == null) return;

        if (dto.uf() != null) endereco.setUf(dto.uf());
        if (dto.cidade() != null) endereco.setCidade(dto.cidade());
        if (dto.bairro() != null) endereco.setBairro(dto.bairro());
        if (dto.logradouro() != null) endereco.setLogradouro(dto.logradouro());
        if (dto.numero() != null) endereco.setNumero(dto.numero());
        if (dto.complemento() != null) endereco.setComplemento(dto.complemento());
        if (dto.cep() != null) endereco.setCep(dto.cep());
    }
}
