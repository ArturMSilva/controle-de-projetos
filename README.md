# Sistema de Controle de Projetos

Um sistema de gerenciamento de projetos desenvolvido em Java que permite criar, gerenciar e acompanhar projetos e suas respectivas tarefas, com histórico completo de alterações.

## ✨ Características

- ✅ Gerenciamento completo de projetos
- ✅ Controle de tarefas por projeto
- ✅ Histórico de alterações detalhado
- ✅ Interface de linha de comando intuitiva
- ✅ Persistência de dados com PostgreSQL
- ✅ Arquitetura MVC bem estruturada

## 🚀 Tecnologias Utilizadas

- **Java 17** - Linguagem de programação
- **Maven** - Gerenciamento de dependências e build
- **PostgreSQL** - Banco de dados relacional
- **JDBC** - Conectividade com banco de dados

## 📦 Pré-requisitos

Antes de executar o projeto, certifique-se de ter instalado:

- [Java JDK 17+](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Maven 3.6+](https://maven.apache.org/download.cgi)
- [PostgreSQL 12+](https://www.postgresql.org/download/)

## 🔧 Instalação

1. **Clone o repositório:**
```bash
git clone https://github.com/ArturMSilva/controle-de-projetos.git
cd controle-de-projetos
```

2. **Instale as dependências:**
```bash
mvn clean install
```

## 🗄️ Configuração do Banco de Dados

1. **Crie o banco de dados PostgreSQL:**
```sql
CREATE DATABASE ControleProjetos;
```

2. **Configure as credenciais de acesso:**
   - Abra o arquivo `src/main/java/com/controle/dao/Conexao.java`
   - Ajuste as configurações conforme seu ambiente:
```java
private final String url = "jdbc:postgresql://localhost:5432/ControleProjetos";
private final String usuario = "seu_usuario";
private final String senha = "sua_senha";
```

3. **Crie as tabelas necessárias:**
```sql
-- Tabela de Projetos
CREATE TABLE projetos (
    projeto_id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    data_inicio DATE,
    data_fim DATE,
    status VARCHAR(50) DEFAULT 'Em andamento'
);

-- Tabela de Tarefas
CREATE TABLE tarefas (
    tarefa_id SERIAL PRIMARY KEY,
    projeto_id INTEGER REFERENCES projetos(projeto_id),
    titulo VARCHAR(255) NOT NULL,
    responsavel VARCHAR(255),
    prazo DATE,
    concluida BOOLEAN DEFAULT FALSE
);

-- Tabela de Histórico de Alterações
CREATE TABLE historico_alteracoes (
    historico_id SERIAL PRIMARY KEY,
    tarefa_id INTEGER REFERENCES tarefas(tarefa_id),
    data_alteracao DATE NOT NULL,
    descricao_alteracao TEXT NOT NULL
);
```

## ▶️ Como Executar

1. **Compile o projeto:**
```bash
mvn clean compile
```

2. **Execute a aplicação:**
```bash
mvn exec:java -Dexec.mainClass="com.controle.Main"
```

Ou execute diretamente pelo Java:
```bash
java -cp target/classes com.controle.Main
```

## 🎯 Funcionalidades

### 📊 Gerenciamento de Projetos
- ➕ Criar novos projetos
- 📝 Listar todos os projetos
- 🔍 Buscar projeto por ID
- ✏️ Atualizar informações do projeto
- 🗑️ Remover projetos

### 📋 Gerenciamento de Tarefas
- ➕ Criar tarefas vinculadas a projetos
- 📝 Listar tarefas por projeto
- 🔍 Buscar tarefas específicas
- ✏️ Atualizar informações das tarefas
- ✅ Marcar tarefas como concluídas
- 🗑️ Remover tarefas

### 📈 Histórico e Relatórios
- 📊 Visualizar histórico completo de alterações
- 📈 Relatórios de progresso por projeto
- 🔍 Filtrar alterações por período

## 🏗️ Estrutura do Projeto

```
src/
├── main/
│   └── java/
│       └── com/
│           └── controle/
│               ├── Main.java                 # Classe principal
│               ├── dao/                      # Data Access Objects
│               │   ├── Conexao.java         # Configuração do banco
│               │   ├── ProjetoDao.java      # DAO para projetos
│               │   ├── TarefaDao.java       # DAO para tarefas
│               │   └── HistoricoAlteracaoDao.java # DAO para histórico
│               ├── model/                    # Modelos de dados
│               │   ├── Projeto.java         # Entidade Projeto
│               │   ├── Tarefa.java          # Entidade Tarefa
│               │   └── HistoricoAlteracao.java # Entidade Histórico
│               └── ui/                       # Interface do usuário
│                   ├── Menu.java            # Menu principal
│                   ├── MenuProjeto.java     # Menu de projetos
│                   ├── MenuTarefa.java      # Menu de tarefas
│                   ├── MenuHistorico.java   # Menu de histórico
│                   └── TarefaUtil.java      # Utilitários para tarefas
└── pom.xml                                  # Configuração do Maven
```

## 📊 Modelo de Dados

### Projeto
- `projetoId` (Integer) - Identificador único
- `nome` (String) - Nome do projeto
- `descricao` (String) - Descrição detalhada
- `dataInicio` (LocalDate) - Data de início
- `dataFim` (LocalDate) - Data de término prevista
- `status` (String) - Status atual do projeto

### Tarefa
- `tarefaId` (Integer) - Identificador único
- `projetoId` (Integer) - ID do projeto vinculado
- `titulo` (String) - Título da tarefa
- `responsavel` (String) - Responsável pela tarefa
- `prazo` (LocalDate) - Prazo para conclusão
- `concluida` (Boolean) - Status de conclusão

### Histórico de Alteração
- `historicoId` (Integer) - Identificador único
- `tarefaId` (Integer) - ID da tarefa relacionada
- `dataAlteracao` (LocalDate) - Data da alteração
- `descricaoAlteracao` (String) - Descrição da mudança

## 🤝 Contribuição

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## 👨‍💻 Autor

**Artur Silva**
- GitHub: [@ArturMSilva](https://github.com/ArturMSilva)

---

⭐ Se este projeto foi útil para você, considere dar uma estrela no repositório!
