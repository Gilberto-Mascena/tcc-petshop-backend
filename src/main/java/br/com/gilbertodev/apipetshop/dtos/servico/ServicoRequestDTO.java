package br.com.gilbertodev.apipetshop.dtos.servico;

import br.com.gilbertodev.apipetshop.entities.Servico;
import br.com.gilbertodev.apipetshop.enums.TipoServico;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ServicoRequestDTO {

    @NotNull(message = "O tipo de serviço é obrigatório")
    private TipoServico tipo;

    @NotNull(message = "A data e hora são obrigatórias")
    @Future(message = "A data do serviço deve ser no futuro")
    private LocalDateTime dataHora;

    private String observacoes;

    @NotNull(message = "O ID do pet é obrigatório")
    private Long petId;

    public Servico toEntity() {
        Servico entidade = new Servico();
        entidade.setTipo(this.tipo);
        entidade.setDataHora(this.dataHora);
        entidade.setObservacoes(this.observacoes);
        return entidade;
    }
}
