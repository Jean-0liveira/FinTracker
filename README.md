# 💰 FinTrack

Sistema de controle de finanças pessoais via console, desenvolvido em **Java** como projeto prático de um curso EAD. O objetivo é aplicar e evoluir conceitos de **Programação Orientada a Objetos (POO)** ao longo do curso, partindo de uma versão simples em console até futuras expansões com relatórios gráficos, persistência em banco de dados e integração com APIs.

> 🚧 Projeto em desenvolvimento contínuo — este README será atualizado conforme novas funcionalidades forem adicionadas.

## 📋 Sobre o projeto

O **FinTrack** permite ao usuário:

- ✅ Cadastrar entradas (receitas) e saídas (despesas)
- ✅ Listar todas as transações
- ✅ Exibir o saldo atual
- ✅ Remover uma transação

## 🖥️ Menu do sistema

```
===== FINTRACK - SEU CONTROLE FINANCEIRO =====
1. Adicionar nova transação
2. Listar transações
3. Mostrar saldo atual
4. Remover transação
5. Sair
```

O menu funciona em loop: após executar uma ação, ele é exibido novamente até que o usuário selecione a opção **Sair**.

## 🛠️ Tecnologias e conceitos aplicados

- **Java puro**, sem frameworks ou bibliotecas externas
- `Scanner` para entrada de dados via console
- Estruturas condicionais (`if`, `switch`) e de repetição (`for`, `while`)
- `ArrayList` para armazenamento das transações
- Manipulação de `String` e `double`

### Programação Orientada a Objetos

- **Encapsulamento**: uso de getters e setters nas classes de modelo
- **Polimorfismo**: `Transacao` como classe base e `TransacaoMensal` como subclasse (representando transações recorrentes, como um salário fixo)
- **Tratamento de exceções**: uso de `try/catch` e exceção personalizada para entradas inválidas

## 📁 Estrutura do projeto

```
fintrack/
├── app/
│   └── Main.java                  → Classe principal com o menu e execução
├── controller/
│   └── FinTracker.java            → Lógica principal para gerenciar as transações
├── model/
│   ├── Transacao.java             → Classe base para qualquer tipo de transação
│   └── TransacaoMensal.java       → Subclasse que representa uma transação recorrente
├── exceptions/
│   └── EntradaInvalidaException.java → Exceção personalizada
└── utils/
    └── Formatador.java            → Funções auxiliares para formatar datas, valores, etc.
```

### Principais métodos (`FinTracker`)

| Método | Descrição |
|---|---|
| `adicionarTransacao()` | Adiciona uma nova transação (entrada ou saída) |
| `listarTransacoes()` | Lista todas as transações cadastradas |
| `removerTransacao()` | Remove uma transação existente |
| `calcularSaldoTotal()` | Calcula o saldo total com base nas transações |

## ▶️ Como executar

Pré-requisitos: ter o **JDK** instalado (Java 8 ou superior).

```bash
# Compilar
javac -d bin $(find . -name "*.java")

# Executar
java -cp bin app.Main
```

> Substitua `app.Main` pelo caminho completo da classe principal, caso o nome ou pacote seja diferente.

## 🚀 Roadmap (próximas evoluções)

- [ ] Relatórios gráficos
- [ ] Persistência em banco de dados
- [ ] Integração com APIs

## 📚 Contexto

Este projeto faz parte das atividades práticas de um curso EAD, com foco no aprendizado progressivo de Java. A cada novo módulo do curso, o FinTrack ganha novas funcionalidades e refinamentos.

---

Feito com 💻 e ☕ durante o curso.
