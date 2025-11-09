# 🎴 Historical TCG

## 🧭 Resumo
Desenvolvido pelos alunos **Mateus Cardoso** e **Gabriel Saueressig** para a disciplina de **Paradigmas de Programação**, o *Historical TCG* nasceu de um hobby em comum: a paixão por colecionar cartas de Pokémon e o interesse por figuras históricas.

A proposta central é **transformar o aprendizado em algo divertido**, recompensando o acerto de questões com **cartas colecionáveis** de grandes personalidades da história humana.

---

## ⚙️ Desenvolvimento
O projeto foi desenvolvido **majoritariamente em Java**, utilizando a biblioteca **LibGDX** para o desenvolvimento do jogo.  
Além disso, foi implementada uma **API em Python**, responsável por gerir as informações de um banco de dados **SQLite**, através de requisições **GET** e **POST**.

Buscou-se seguir princípios de **Programação Orientada a Objetos (POO)** e aplicar uma metodologia ágil inspirada no **Extreme Programming (XP)**, priorizando ciclos curtos de implementação, testes e melhorias constantes,
além de um grande uso da técnica de **Pair Programming**.

---

## 🃏 Cartas
A arte das cartas foi uma das primeiras preocupações da equipe, já que nenhum dos integrantes possuía grande experiência em design gráfico.  
A solução encontrada foi **dividir a criação das imagens em duas frentes**:

- 🧠 **Geração por IA**: utilizada em cartas especiais de conjunto, onde duas figuras históricas interagem entre si.
- 🖼️ **Imagens do Pinterest**: adotadas para cartas comuns, explorando estilos variados de ilustração.

Para definir quais personalidades seriam retratadas, contamos com a ajuda do **ChatGPT**, que gerou listas com nomes de grandes figuras em diferentes áreas do conhecimento humano.

---

## 🌱 Primeiros Passos

A primeira ação no desenvolvimento foi a **criação de um diagrama de classes de domínio**.  
Logo percebemos que — seja por **falta de experiência** ou por **ansiedade em começar a programar** — o diagrama **não representava fielmente a realidade do projeto**.

Durante a implementação, diversas mudanças surgiram:
- 🧩 **Atributos** foram adicionados ou removidos;
- 🔁 **Lógicas internas** foram completamente alteradas;
- 🗑️ **Classes inteiras** foram removidas, enquanto outras foram criadas do zero.

Mesmo assim, o diagrama cumpriu um papel fundamental: serviu **como guia e referência estrutural** para o restante do desenvolvimento, ajudando a organizar o raciocínio sobre a arquitetura do jogo.

O primeiro código concreto escrito foi o das **classes base**, como `Jogador`, `Carta` e outras entidades essenciais para dar forma ao *Historical TCG*.

---

## 🗄️ Banco de Dados

Essa foi nossa **primeira grande dificuldade** no projeto.  
A ideia inicial era utilizar o **SQLite** diretamente no jogo — o que funcionou perfeitamente nas versões **Desktop** e **Mobile**.

Porém, surgiu um grande obstáculo: ao exportar o projeto para **Web**, o LibGDX converte o código Java para **JavaScript**, e o SQLite **não é compatível** com essa tradução.

### 💭 Primeiras Tentativas
Nosso primeiro pensamento foi criar **dois códigos diferentes**, um para cada versão (Desktop e Web).  
Mas, para manter o projeto **simples, coeso e sustentável**, decidimos **buscar uma solução única**.

A alternativa seguinte foi tentar usar o **WebAssembly (WASM)** para converter os dados do banco e armazená-los em cache no navegador.  
Levamos essa ideia adiante por vários dias, testando diferentes geradores e implementações, mas enfrentamos **diversos erros** e não conseguimos fazê-la funcionar corretamente no ambiente Web.

### 🌐 A Solução: API Python
Após muita experimentação, adotamos uma abordagem mais robusta: a criação de uma **API** hospedada em [PyAnyWhere](https://www.pythonanywhere.com/). 
Essa API, escrita em **Python**, recebe requisições **GET** e **POST** do jogo e as traduz em comandos SQL para o banco **SQLite**. 
Os dados trafegam no formato **JSON**, o que nos permitiu **abstrair o acesso direto ao banco de dados** e tornar o código **totalmente multiplataforma** — funcionando de forma idêntica em **Web, Desktop e Mobile**.

Essa solução não apenas resolveu o problema, mas também tornou o sistema mais **modular, seguro e escalável**.

---
