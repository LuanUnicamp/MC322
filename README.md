# Naruto: Shinobi Legacy

## Descrição
Naruto: Shinobi Legacy é um jogo desenvolvido em Java como projeto da disciplina **MC322 - Programação Orientada a Objetos**. No jogo, o usuário escolhe um herói do anime Naruto, para lutar contra os maiores vilões da saga! O objetivo é derrotar o inimigo utilizando cartas que representam habilidades do personagem.

---

## Mecânica
O jogador possui um baralho de cartas com habilidades de ataque e defesa. A cada turno, o jogador possui 3 pontos de chakra(energia), que são utilizados para ativar diferentes ataques, escudos e efeitos. As cartas que compoem o jogo são:

* Rasengan  
* Kurama 
* Clone das Sombras  
* Sharingan  
* Jutsu de Substituição  
* Chidori  
* Jutsu Dragão de Água  
* Parede de Terra  
* Defesa de Shukaku  
* Susano'o Perfeito  
* Gamabunta: Banho de Óleo  
* Katsuyu: Rede de Cura  
* Oito Portões Internos  
* Absorção de Chakra  

O jogador pode acessar o Menu de Cartas para ler a descrição e o custo de cada carta que está em sua mão.

**Sistema de efeitos**

Algumas cartas aplicam efeitos especiais, tanto para o heroi, quanto para o inimigo:

* **Veneno** - causa dano contínuo à entidade afetada no final de cada turno.
* **Regen (Regeneração)** - gera pontos de escudo/vida no início de cada turno.
* **Dano em Área** - técnicas com chance de acerto crítico ou falha (dano reduzido).
* **Roubo de Vida** - causa dano ao oponente e restaura a vida do usuário simultaneamente.
* **Sacrifício** - ataques poderosos que causam dano massivo ao alvo, mas aplicam dano ao próprio usuário.

O jogador pode acessar o Menu de Efeitos para visualizar os efeitos ativos no herói e no inimigo, acompanhando a duração (acúmulos) de cada um.

**Os inimigos também possuem diferentes movimentos!**

Após o turno do jogador, as cartas são enviadas para a pilha de descarte e o inimigo realiza automaticamente sua ação. Quando o baralho se esgota, a pilha de descarte é embaralhada e reutilizada como nova pilha de compra. O jogo termina quando a vida de um dos personagens chega a zero.

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
