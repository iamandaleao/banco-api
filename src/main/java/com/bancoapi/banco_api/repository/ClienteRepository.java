package com.bancoapi.banco_api.repository;

import com.bancoapi.banco_api.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Indica que esta interface é um componente de acesso ao banco (DAO)
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // JpaRepository fornece operações prontas:
    // salvar, buscar, listar, deletar, paginação, etc.

    Optional<Cliente> findByCpf(String cpf);
    // Cria automaticamente uma consulta para buscar Cliente pelo CPF.
    // Retorna Optional para evitar NullPointerException.

    Optional<Cliente> findByEmail(String email);
    // Cria consulta automática para buscar pelo email.

    boolean existsByCpf(String cpf);
    // Verifica se já existe um cliente com esse CPF no banco.

    boolean existsByEmail(String email);
    // Verifica se já existe um cliente com esse email.
}
