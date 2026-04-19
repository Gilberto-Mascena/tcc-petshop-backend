package br.com.gilbertodev.apipetshop.dtos.servico;

import br.com.gilbertodev.apipetshop.entities.Servico;
import br.com.gilbertodev.apipetshop.enums.TipoServico;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ServicoRequestDTO {

    @NotNull(message = "O tipo de serviço é obrigatório")
    private TipoServico tipo;

    private String observacoes;

    @NotNull(message = "O valor base é obrigatório")
    @Positive(message = "O valor deve ser maior que zero")
    private BigDecimal valorBase;

    public Servico toEntity() {
        Servico servico = new Servico();
        servico.setTipo(this.tipo);
        servico.setObservacoes(this.observacoes);
        servico.setValorBase(this.valorBase);
        return servico;
    }
}