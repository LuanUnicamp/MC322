# Naruto: Shinobi Legacy

## Descrição
Naruto: Shinobi Legacy é um jogo desenvolvido em Java como projeto da disciplina **MC322 - Programação Orientada a Objetos**. No jogo, o usuário mergulha no universo do anime Naruto, podendo explorar campanhas narrativas ou lutar diretamente contra os maiores vilões da saga! O objetivo é derrotar o inimigo utilizando estratégia, itens e cartas que representam habilidades icônicas dos personagens.

---

## Modos de Jogo

* **Combate Livre:** Escolha rápida de herói e vilão para um duelo direto, ideal para testar as mecânicas das cartas e os efeitos de batalha.
* **Modo História (Shinobi Legacy):** Uma campanha imersiva onde o jogador toma decisões narrativas que ramificam a história. Durante a jornada, o jogador acumula *Shinobi Coins*, que podem ser trocadas no Mercado Shinobi por itens consumíveis (Bandagens, Shurikens e Bandanas). Antes das lutas contra os chefes, o jogador acessa a "Sala de Preparação" para organizar sua mochila e usar itens táticos.

---

## Mecânica de Combate
O jogador possui um baralho de cartas com habilidades de ataque e defesa. A cada turno, o jogador possui 4 pontos de chakra (energia), que são utilizados para ativar diferentes ataques, escudos e efeitos.

O jogador pode acessar o Menu de Cartas para ler a descrição e o custo de cada carta que está em sua mão.

**Sistema de Efeitos**

Algumas cartas aplicam efeitos especiais duradouros, tanto para o herói quanto para o inimigo:

* **Veneno:** Causa dano contínuo à entidade afetada no final de cada turno.
* **Regen (Regeneração):** Gera pontos de escudo/vida no início de cada turno.
* **Dano em Área:** Técnicas com chance de acerto crítico ou falha (dano reduzido).
* **Roubo de Vida:** Causa dano ao oponente e restaura a vida do usuário simultaneamente.
* **Sacrifício:** Ataques poderosos que causam dano massivo ao alvo, mas aplicam dano de recuo ao próprio usuário.

O jogador pode acessar o Menu de Efeitos para visualizar os status ativos, acompanhando a duração (acúmulos) de cada um.

**Ações do Inimigo**

Os inimigos são controlados pelo sistema e também possuem movimentos exclusivos: Shinra Tensei, Jutsu Bola de Fogo, Ninjutsu Médico, Kirin, Edo Tensei, Rotação Hyuga, Manda (Bote Venenoso), Bijuudama, Caminho Naraka, Corte da Samehada e Manto de Chakra.

Após o turno do jogador, as cartas são enviadas para a pilha de descarte e o inimigo realiza automaticamente sua ação. Quando o baralho se esgota, a pilha de descarte é embaralhada e reutilizada como nova pilha de compra. O jogo termina quando a vida de um dos personagens chega a zero.

---

## Destaques Técnicos e Arquitetura (MC322)

Para suportar as lógicas do jogo, foram aplicados conceitos avançados de POO e Estruturas de Dados:
* **Estrutura de Árvore:** O mapa do Modo História foi construído utilizando nós (`DefaultMutableTreeNode`), permitindo criar rotas, caminhos opcionais e bifurcações narrativas completas.
* **Padrão Publisher-Subscriber:** A classe `Combate` gerencia ativamente as entidades em batalha, notificando os "Efeitos" inscritos no início e no fim de cada turno para que eles apliquem seus impactos (ex: deduzir dano de veneno) de forma automatizada.
* **Interface rica no Terminal:** Utilização de formatação via código ANSI para renderizar menus interativos, mapa da campanha, coloração de status e artes dinâmicas de encerramento.

---

## Como compilar e executar
O projeto utiliza o Gradle para automação.

**Compilar o projeto:**
`./gradlew build`

**Executar o jogo:**
`./gradlew run`

O jogo será iniciado no terminal. Durante cada turno, o jogador deve digitar o número correspondente à carta que deseja utilizar.

---
## Documentação Javadoc
O código está documentado utilizando Javadoc, incluindo classes principais como combate, cartas e sistema de efeitos.

Para gerar e visualizar a documentação técnica em HTML, utilize:

`./gradlew javadoc`

Os arquivos serão gerados na pasta `build/docs/javadoc`. Abra o arquivo `index.html` em seu navegador.

---

## Requisitos
* Java JDK 25 ou superior
* Terminal ou prompt de comando
---

## Autores
* **Luan Silva Ribeiro** - RA:259914
* **João Vitor Vieira Lucio** - RA:282601
