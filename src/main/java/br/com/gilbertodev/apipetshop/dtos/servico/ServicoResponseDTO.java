package br.com.gilbertodev.apipetshop.dtos.servico;

import br.com.gilbertodev.apipetshop.entities.Servico;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ServicoResponseDTO {

    private final Long id;
    private final String tipoDescricao;
    private final BigDecimal valor;
    private final LocalDateTime dataHora;
    private final String nomePet;

    public ServicoResponseDTO(Servico entidade) {
        this.id = entidade.getId();
        this.tipoDescricao = (entidade.getTipo() != null) ? entidade.getTipo().getDescricao() : null;
        this.valor = entidade.getValor();
        this.dataHora = entidade.getDataHora();
        this.nomePet = (entidade.getPet() != null) ? entidade.getPet().getNome() : "Não informado";
    }
}
