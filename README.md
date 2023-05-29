# Projeto A3 - Sistemas distribuídos
### Professores Emerson dos Santos Paduan e José de Oliveira Malheiro Netto


## Integrantes 
* Adelle Bueno Dantas - RA: 125111375971
* Gabriel Henrique Dias - RA: 125111343625
* Gabriel Guimarães Martins - RA: 125111363302
* Nicolas Silva Almeida - RA: 1252222895
* Rodrigo Paiva Albuquerque - RA: 125111365272


## O projeto - Jogo jokenpô
Desenvolvemos o jogo pedra, papel e tesoura, conhecido também como "jokenpô". Este jogo deve ter somente dois jogadores e cada um escolherá um elemento (Pedra, papel ou tesoura). A pedra perde para papel (o papel embrulha a pedra); papel perde para tesoura (esta corta o primeiro); e, finalmente, a tesoura perde para a pedra, que a quebra.

No nosso projeto, é possível jogar com outra pessoa ou contra a própria máquina. Para isso, nosso time desenvolveu uma solução em Java utilizando sockets para a comunicação entre clientes (jogadores) e o servidor. Os jogadores se conectam ao servidor e interagem enviando suas escolhas através da rede. O servidor é responsável por receber as jogadas, verificar as regras do jogo e determinar o vencedor. Em seguida, o resultado é enviado de volta aos jogadores para que eles possam ver o resultado e o vencedor.

Além disso, implementamos uma funcionalidade para que o jogador possa jogar contra a máquina. Nesse caso, o jogador faz sua jogada e o servidor simula a jogada da máquina, seguindo uma lógica de escolha aleatória. O resultado é então apresentado ao jogador da mesma forma que nas partidas multiplayer.
