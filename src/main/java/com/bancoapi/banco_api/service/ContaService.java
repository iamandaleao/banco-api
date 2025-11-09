package com.bancoapi.banco_api.service;

import com.bancoapi.banco_api.model.Cliente;
import com.bancoapi.banco_api.model.Conta;
import com.bancoapi.banco_api.repository.ClienteRepository;
import com.bancoapi.banco_api.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    // Criar conta
    public Conta criarConta(Conta conta, Long clienteId) {

        // Buscar cliente pelo ID (vem da URL)
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // Verificar se número da conta já existe
        if (contaRepository.existsByNumeroConta(conta.getNumeroConta())) {
            throw new RuntimeException("Número da conta já existe");
        }

        // Validar saldo inicial
        if (conta.getSaldo().compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Saldo inicial não pode ser negativo");
        }

        // Definir cliente na conta antes de salvar
        conta.setCliente(cliente);

        return contaRepository.save(conta);
    }

    // Listar todas as contas
    public List<Conta> listarTodas() {
        return contaRepository.findAll();
    }

    // Listar contas ativas
    public List<Conta> listarAtivas() {
        return contaRepository.findByAtivaTrue();
    }

    // Buscar conta por ID
    public Optional<Conta> buscarPorId(Long id) {
        return contaRepository.findById(id);
    }

    // Buscar conta por número
    public Optional<Conta> buscarPorNumero(String numeroConta) {
        return contaRepository.findByNumeroConta(numeroConta);
    }

    // Listar contas de um cliente
    public List<Conta> listarContasPorCliente(Long clienteId) {
        return contaRepository.findByClienteId(clienteId);
    }

    // Listar contas ativas de um cliente
    public List<Conta> listarContasAtivasPorCliente(Long clienteId) {
        return contaRepository.findByClienteIdAndAtivaTrue(clienteId);
    }

    // Consultar saldo
    public BigDecimal consultarSaldo(Long contaId) {
        Conta conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        return conta.getSaldo();
    }

    // Desativar conta
    public void desativarConta(Long contaId) {
        Conta conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        if (conta.getSaldo().compareTo(BigDecimal.ZERO) != 0) {
            throw new RuntimeException("Não é possível desativar conta com saldo");
        }

        conta.setAtiva(false);
        contaRepository.save(conta);
    }

    // Reativar conta
    public void reativarConta(Long contaId) {
        Conta conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        conta.setAtiva(true);
        contaRepository.save(conta);
    }
}
