package br.com.gilbertodev.apipetshop.dtos.servico;

import br.com.gilbertodev.apipetshop.entities.Servico;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ServicoResponseDTO {

    private Long id;
    private String tipo;
    private String observacoes;
    private BigDecimal valorBase;

    public ServicoResponseDTO(Servico servico) {
        this.id = servico.getId();
        this.tipo = servico.getTipo().getDescricao();
        this.observacoes = servico.getObservacoes();
        this.valorBase = servico.getValorBase();
    }
}