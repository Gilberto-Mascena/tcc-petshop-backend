package br.com.gilbertodev.apipetshop.mapper;

import br.com.gilbertodev.apipetshop.dtos.funcionario.FuncionarioRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.funcionario.FuncionarioResponseDTO;
import br.com.gilbertodev.apipetshop.entities.Endereco;
import br.com.gilbertodev.apipetshop.entities.Funcionario;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("DuplicatedCode")
public class FuncionarioMapper {

    private final EnderecoMapper enderecoMapper;

    public FuncionarioMapper(EnderecoMapper enderecoMapper) {
        this.enderecoMapper = enderecoMapper;
    }

    public Funcionario toEntity(FuncionarioRequestDTO funcionarioRequestDTO) {
        if (funcionarioRequestDTO == null) return null;

        Funcionario funcionario = new Funcionario();
        funcionario.setNome(funcionarioRequestDTO.nome());
        funcionario.setEmail(funcionarioRequestDTO.email());
        funcionario.setCpf(funcionarioRequestDTO.cpf());
        funcionario.setTelefone(funcionarioRequestDTO.telefone());
        funcionario.setCelular(funcionarioRequestDTO.celular());

        if (funcionarioRequestDTO.endereco() != null) {
            funcionario.setEndereco(new Endereco());
            enderecoMapper.mapearEndereco(funcionarioRequestDTO.endereco(), funcionario.getEndereco());
        }

        return funcionario;
    }

    public FuncionarioResponseDTO toResponseDTO(Funcionario funcionario) {
        if (funcionario == null) return null;

        return new FuncionarioResponseDTO(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getEmail(),
                funcionario.getTelefone(),
                funcionario.getCelular()
        );
    }

    public void atualizarDados(FuncionarioRequestDTO dto, Funcionario funcionario) {
        if (dto == null || funcionario == null) return;

        if (dto.nome() != null) funcionario.setNome(dto.nome());
        if (dto.email() != null) funcionario.setEmail(dto.email());
        if (dto.celular() != null) funcionario.setCelular(dto.celular());

        if (dto.endereco() != null) {
            if (funcionario.getEndereco() == null) {
                funcionario.setEndereco(new Endereco());
            }
            enderecoMapper.mapearEndereco(dto.endereco(), funcionario.getEndereco());
        }
    }
}
