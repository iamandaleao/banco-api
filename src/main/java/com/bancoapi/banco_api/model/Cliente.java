package com.bancoapi.banco_api.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data // Gera automaticamente getters, setters, toString, equals e hashCode
@Entity // Indica que essa classe é uma entidade JPA (tabela no banco)
@Table(name = "clientes") // Define o nome da tabela no banco de dados
public class Cliente {

    @Id // Marca o campo como chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Faz o banco gerar o ID automaticamente (auto-increment)
    private Long id;

    @NotBlank(message = "Nome é obrigatório") // Validação: o nome não pode ser vazio ou só espaços
    @Column(nullable = false) // No banco: a coluna não pode ser nula
    private String nome;

    @NotBlank(message = "CPF é obrigatório") // Validação: não pode estar em branco
    @Column(nullable = false, unique = true, length = 11) // No banco: obrigatório, único e com limite de 11 caracteres
    private String cpf;

    @Email(message = "Email inválido") // Validação: precisa ter formato de email
    @NotBlank(message = "Email é obrigatório") // Validação: não pode ser vazio
    @Column(nullable = false, unique = true) // No banco: obrigatório e único
    private String email;

    @Column(name = "data_nascimento") // Define o nome da coluna no banco
    private LocalDate dataNascimento; // Representa uma data sem horário

    @Column(length = 15) // Limita o tamanho da coluna no banco para 15 caracteres
    private String telefone;
}
