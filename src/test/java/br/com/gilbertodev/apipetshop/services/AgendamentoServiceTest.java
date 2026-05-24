package br.com.gilbertodev.apipetshop.services;

import br.com.gilbertodev.apipetshop.dtos.agendamento.AgendamentoRequestDTO;
import br.com.gilbertodev.apipetshop.dtos.agendamento.AgendamentoResponseDTO;
import br.com.gilbertodev.apipetshop.entities.Agendamento;
import br.com.gilbertodev.apipetshop.entities.Pet;
import br.com.gilbertodev.apipetshop.entities.Servico;
import br.com.gilbertodev.apipetshop.enums.StatusAgendamento;
import br.com.gilbertodev.apipetshop.exceptions.BusinessException;
import br.com.gilbertodev.apipetshop.mapper.AgendamentoMapper;
import br.com.gilbertodev.apipetshop.messages.AgendamentoMessages;
import br.com.gilbertodev.apipetshop.repositories.AgendamentoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgendamentoServiceTest {

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @Mock
    private ServicoService servicoService;

    @Mock
    private PetService petService;

    @Mock
    private AgendamentoMapper agendamentoMapper;

    @InjectMocks
    private AgendamentoService agendamentoService;

    @Test
    @DisplayName("Deve lançar exceção quando houver conflito de horário")
    void deveLancarExcecaoQuandoConflitoDeHorario() {
        LocalDateTime dataHora = LocalDateTime.of(2026, 6, 15, 14, 0);
        AgendamentoRequestDTO requestDto = new AgendamentoRequestDTO(dataHora, 1L, 1L, "Sem observações");

        when(agendamentoRepository.existsByDataHoraAndStatusNot(dataHora, StatusAgendamento.CANCELADO))
                .thenReturn(true);

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            agendamentoService.salvar(requestDto);
        });

        assertEquals(AgendamentoMessages.HORARIO_INDISPONIVEL.getMensagem(), exception.getMessage());
        verify(agendamentoRepository, never()).save(any(Agendamento.class));
        verify(petService, never()).buscarEntidadePorId(anyLong());
        verify(servicoService, never()).buscarEntidadePorId(anyLong());
    }

    @Test
    @DisplayName("Deve permitir o agendamento quando o horário estiver disponível")
    void devePermitirAgendamentoQuandoHorarioDisponivel() {
        LocalDateTime dataHora = LocalDateTime.of(2026, 6, 15, 14, 0);
        AgendamentoRequestDTO requestDto = new AgendamentoRequestDTO(dataHora, 1L, 1L, "Sem observações");

        Pet petMock = mock(Pet.class);
        Servico servicoMock = mock(Servico.class);
        Agendamento agendamentoMock = mock(Agendamento.class);
        AgendamentoResponseDTO responseDtoMock = mock(AgendamentoResponseDTO.class);

        when(responseDtoMock.status()).thenReturn(StatusAgendamento.AGENDADO);
        when(agendamentoRepository.existsByDataHoraAndStatusNot(dataHora, StatusAgendamento.CANCELADO)).thenReturn(false);
        when(petService.buscarEntidadePorId(1L)).thenReturn(petMock);
        when(servicoService.buscarEntidadePorId(1L)).thenReturn(servicoMock);
        when(agendamentoMapper.toEntity(requestDto, petMock, servicoMock)).thenReturn(agendamentoMock);
        when(agendamentoRepository.save(any(Agendamento.class))).thenReturn(agendamentoMock);
        when(agendamentoMapper.toResponseDTO(agendamentoMock)).thenReturn(responseDtoMock);

        AgendamentoResponseDTO resultado = agendamentoService.salvar(requestDto);

        assertNotNull(resultado);
        assertEquals(StatusAgendamento.AGENDADO, resultado.status());
        verify(agendamentoRepository, times(1)).save(any(Agendamento.class));
    }
}