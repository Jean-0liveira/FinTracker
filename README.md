# 💰 FinTrack

Sistema de controle de finanças pessoais em **Java**, desenvolvido como projeto prático de um curso EAD. Começou como uma aplicação de console e evoluiu para uma aplicação com **interface gráfica JavaFX**, persistência em **SQLite** e testes automatizados com **JUnit**.

> 🚧 Projeto em desenvolvimento contínuo. Este README é atualizado conforme novas funcionalidades são adicionadas.

## 📋 Funcionalidades

- ✅ Cadastrar receitas e despesas, com descrição, valor, tipo e data
- ✅ Listar as transações em uma tabela
- ✅ Remover uma transação
- ✅ Exibir relatório com total de receitas, total de despesas, saldo e quantidade de transações
- ✅ Gravar as transações em banco de dados (os dados continuam ao fechar o programa)

## 🖼️ Telas

| Tela | Descrição |
|---|---|
| Principal | Tabela de transações, com receitas em verde e despesas em vermelho, e os botões de nova transação, relatório e remoção |
| Nova transação | Formulário com validação de descrição, valor (aceita vírgula), tipo e data |
| Relatório | Resumo financeiro, com saldo positivo em verde e negativo em vermelho |

As telas são descritas em arquivos `.fxml` (criados para uso com o Scene Builder), com a lógica nos controllers e a aparência em `css/style.css`.

## 🛠️ Tecnologias e conceitos aplicados

- **Java 17+** e **Maven**
- **JavaFX 21**, com **FXML** e **CSS**
- **JDBC** com **SQLite** (`sqlite-jdbc`)
- **JUnit 5** para testes unitários
- **Generics**: `RepositorioGenerico<T>`, com os curingas `?`, `? extends T` e `? super T`
- `enum` para o tipo da transação (`Tipo`) e `LocalDate` para datas
- **POO**: encapsulamento, herança (`Transacao` e `TransacaoMensal`), tratamento de exceções com `EntradaInvalidaException` e `PersistenciaException`
- Padrão **DAO** para o acesso ao banco

## 📁 Estrutura do projeto

```
FinTracker/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/
    │   │   ├── app/            → Launcher, MainFX (interface gráfica) e Main (console)
    │   │   ├── controller/     → FinTracker e controllers das telas
    │   │   ├── dao/            → TransacaoDAO (JDBC)
    │   │   ├── database/       → Conexao e criação da tabela
    │   │   ├── exceptions/     → EntradaInvalidaException e PersistenciaException
    │   │   ├── model/          → Tipo, Transacao e TransacaoMensal
    │   │   ├── repository/     → RepositorioGenerico<T>
    │   │   └── utils/          → Formatador
    │   └── resources/
    │       ├── view/           → telas .fxml
    │       └── css/            → style.css
    └── test/java/              → testes de model, repository, dao e controller
```

## 🗄️ Banco de dados

O arquivo `fintrack.db` é criado automaticamente na pasta de execução, junto com a tabela:

```sql
CREATE TABLE IF NOT EXISTS transacoes (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  descricao VARCHAR(100),
  valor DECIMAL(10,2),
  tipo VARCHAR(10),
  data DATE
);
```

No SQLite, a data é guardada como texto no formato `AAAA-MM-DD`.

## ▶️ Como executar

Pré-requisitos: **JDK 17 ou superior**.

**Pelo NetBeans:** abra a pasta como projeto Maven e execute com `F6`.

**Pelo terminal** (requer Maven):

```bash
mvn compile exec:java
```

A classe principal configurada é `app.Launcher`, que abre a interface gráfica. A versão de console continua disponível em `app.Main`:

```bash
mvn compile exec:java -Dexec.mainClass=app.Main
```

## 🧪 Testes

```bash
mvn test
```

Os testes cobrem `Transacao`, `TransacaoMensal`, `Tipo`, `RepositorioGenerico`, `TransacaoDAO` (usando SQLite em memória) e o cálculo de saldo e a persistência do `FinTracker`.

## 🚀 Roadmap

- [x] Generics
- [x] Interface gráfica com JavaFX, FXML e CSS
- [x] Persistência em banco de dados (JDBC + SQLite)
- [x] Testes unitários com JUnit
- [ ] Relatórios gráficos
- [ ] Edição de transações
- [ ] Integração com APIs

## 📚 Contexto

Este projeto faz parte das atividades práticas de um curso EAD, com foco no aprendizado progressivo de Java. A cada novo módulo do curso, o FinTrack ganha novas funcionalidades e refinamentos.

---

Feito com 💻 e ☕ durante o curso.
