package br.com.alurafood.pagamentos.service;

import br.com.alurafood.pagamentos.dto.PagamentoDTO;
import br.com.alurafood.pagamentos.http.PedidoClient;
import br.com.alurafood.pagamentos.model.Pagamento;
import br.com.alurafood.pagamentos.model.Status;
import br.com.alurafood.pagamentos.repository.PagamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PagamentoService {
    private final PagamentoRepository pagamentoRepository;
    private final ModelMapper modelMapper;
    private final PedidoClient pedidoClient;

    public PagamentoService(PagamentoRepository pagamentoRepository, ModelMapper modelMapper, PedidoClient pedidoClient) {
        this.pagamentoRepository = pagamentoRepository;
        this.modelMapper = modelMapper;
        this.pedidoClient = pedidoClient;
    }

    public Page<PagamentoDTO> obterTodosPagamentos(Pageable paginacao) {
        return pagamentoRepository.findAll(paginacao)
                .map(p -> modelMapper.map(p, PagamentoDTO.class));
    }

    public PagamentoDTO obterPorId(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return modelMapper.map(pagamento, PagamentoDTO.class);
    }

    public PagamentoDTO criarPagamento(PagamentoDTO dto) {
        Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
        pagamento.setStatus(Status.CRIADO);
        pagamentoRepository.save(pagamento);

        return modelMapper.map(pagamento, PagamentoDTO.class);
    }

    public PagamentoDTO atualizarPagamento(Long id, PagamentoDTO dto) {
        Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
        pagamento.setId(id);
        pagamentoRepository.save(pagamento);

        return modelMapper.map(pagamento, PagamentoDTO.class);
    }

    public void excluirPagamento(Long id) {
        pagamentoRepository.deleteById(id);
    }

    public void confirmarPagamento(Long id) {
        Optional<Pagamento> pagamento = pagamentoRepository.findById(id);

        if(!pagamento.isPresent()) {
            throw new EntityNotFoundException();
        }

        pagamento.get().setStatus(Status.CONFIRMADO);
        pagamentoRepository.save(pagamento.get());
        pedidoClient.atualizaPagamento(pagamento.get().getPedidoId());
    }

    public void alterarStatus(Long id) {
        Optional<Pagamento> pagamento = pagamentoRepository.findById(id);

        if(!pagamento.isPresent()) {
            throw new EntityNotFoundException();
        }

        pagamento.get().setStatus(Status.CONFIRMADO_SEM_INTEGRACAO);
        pagamentoRepository.save(pagamento.get());
    }
}
