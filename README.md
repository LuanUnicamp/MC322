# Naruto: Shinobi Legacy

## Descrição
Naruto: Shinobi Legacy é um jogo simples desenvolvido em Java como projeto da disciplina **MC322 - Programação Orientada a Objetos**.No jogo, o usuário escolhe um herói do anime Naruto, para lutar contra os maiores vilões da saga! O objetivo é derrotar o inimigo utilizando cartas que representam habilidades do personagem.
---

## Mecânica
O jogador possui um **baralho de cartas** com habilidades de ataque e defesa. A cada turno, o jogador possui **3 pontos de chakra(energia)**, que são utilizados para ativar diferentes ataques ou o escudo, por exemplo:

* **Rasengan** – causa dano ao inimigo (custa 1 de chakra) - Carta de ataque
* **Kurama** – causa grande dano ao inimigo (custa 2 de chakra) - Carta de ataque
* **Clone das Sombras** – gera escudo para proteger o herói (custa 1 de chakra) - Carta de defesa

**Os inimigos também possuem diferentes cartas!**

Após o turno do jogador, as cartas vão para a pilha de descarte e o inimigo realiza um ataque automático.
Quando o baralho acaba a pilha de descarte é embaralhada e reutilizada.
O jogo termina quando a vida de um dos personagens chega a zero.
---

## Como compilar
Clone ou baixe o repositório:

git clone https://github.com/LuanUnicamp/MC322.git

Acesse a pasta do projeto:

`cd MC322`

Compile os arquivos Java:

`javac *.java`
---

## Como executar
Após a compilação, execute o jogo com o comando:

`java App`

O jogo será iniciado no terminal. Durante cada turno, o jogador deve digitar o número correspondente à carta que deseja utilizar.
---

## Requisitos
* Java JDK 25 ou superior
* Terminal ou prompt de comando
---

## Autores
* **Luan Silva Ribeiro** - RA:259914
* **João Vitor Vieira Lucio** - RA:282601
