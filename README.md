# Laboratorio_docker_compose_api_spring_angular (FullStack)

## Stack
Banco - PostgreSQL 16 Alpine
Backend - Java 21 + Spring Boot 3
Frontend - Angular 17 + Nginx
Orquestração - Docker Compose

## Estrutura do Projeto
```
lab-docker/
├── docker-compose.yml      # Orquestração de todos os serviços
├── postgres/
│   └── init.sql            # Script de criação do banco e seed inicial
├── backend/                # API REST CRUD de usuários (Spring Boot)
│   ├── Dockerfile
│   └── src/
└── frontend/               # SPA de cadastro de usuários (Angular)
├── Dockerfile
└── src/
```