package br.com.gilbertodev.apipetshop.dtos.servico;

import br.com.gilbertodev.apipetshop.entities.Servico;
import br.com.gilbertodev.apipetshop.enums.TipoServico;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ServicoResponseDTO {

    private Long id;
    private TipoServico tipo;
    private String observacoes;
    private BigDecimal valorBase;

    public ServicoResponseDTO(Servico servico) {
        this.id = servico.getId();
        this.tipo = servico.getTipo();
        this.observacoes = servico.getObservacoes();
        this.valorBase = servico.getValorBase();
    }
}