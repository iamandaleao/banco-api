package com.bancoapi.banco_api.repository;

import com.bancoapi.banco_api.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

    Optional<Conta> findByNumeroConta(String numeroConta);

    List<Conta> findByClienteId(Long clienteId);

    boolean existsByNumeroConta(String numeroConta);

    List<Conta> findByAtivaTrue();

    List<Conta> findByClienteIdAndAtivaTrue(Long clienteId);
}