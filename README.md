# QuickBuy
A QuickBuy é uma API de gerenciamento de produtos desenvolvida com Spring Boot, projetada para facilitar o controle de estoque, cadastro de produtos e operações de compra/venda. A solução oferece endpoints RESTful para consulta, criação, atualização e exclusão de produtos, garantindo validações robustas e tratamento de erros personalizado.

# Funcionalidades
✅ Cadastro de produtos – Inclui nome, preço e quantidade em estoque com validações automáticas.

✅ Consulta paginada – Lista produtos com suporte a paginação e ordenação.

✅ Atualização de estoque – Diminuição controlada do estoque com verificação de disponibilidade.

✅ Tratamento de erros – Respostas padronizadas para erros comuns (registro não encontrado, dados inválidos, etc.).

✅ Documentação automática – Integração com Swagger para fácil visualização dos endpoints.

# Tecnologias Utilizadas
Java 17 – Linguagem principal
Spring Boot 3 – Framework para desenvolvimento da API
Spring Data JPA – Persistência de dados com Hibernate
MapStruct – Mapeamento automático entre DTOs e entidades
H2 Database – Banco de dados em memória para desenvolvimento
Lombok – Redução de código boilerplate
Swagger (OpenAPI 3) – Documentação interativa da API
JUnit 5 & Mockito – Testes unitários e de integração
