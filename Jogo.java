import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Jogo implements Runnable {

    private Socket[] socketClient;
    private PrintWriter[] saidasDados;
    private BufferedReader[] leiturasDados;
    

    /*Dentro do nosso jogo temos:
    / 0 -> pedra
    / 1 -> papel
    / 2 -> tesoura
    / Abaixo temos uma matriz que mostra as compinações vencedoras:
    */  
    private static int[][] COMBI_VENCEDORAS = {{0, 2}, {1, 0}, {2, 1}};
    private static int MAXIMO_JOGADERES= 2;

    // Aqui se concentra o nucleu principal da lógica do jogo
    
    private static int getResultado(int escolhasClient1 , int escolhasClient2) {
        //Se as escolhas forem igual resulta em empate
        if (escolhasClient1 == escolhasClient2) {
            return -1;
            
        } else {

            //Verificar combinações do jogadores com matriz de combinações vendedoreas: 
            for (int[] verificarCombi : COMBI_VENCEDORAS) {

                if (verificarCombi[0] == escolhasClient1 && verificarCombi[1] == escolhasClient2) {
                    return 0;

                } else if (verificarCombi[0] == escolhasClient2 && verificarCombi[1] == escolhasClient1) {
                    return 1;
                }
            }
            return -1;
        }
    }

    // Embaixo pegamos as escolhas dos clientes: 
    
    //Construtor com os parametos coletados da classe servidor:
    public Jogo(Socket[] socketClient, PrintWriter[] saidasDados, BufferedReader[] leiturasDados) {
        this.socketClient = socketClient;
        this.saidasDados = saidasDados;
        this.leiturasDados = leiturasDados;
    }

    @Override
    public void run() {

        for (int i = 0; i < MAXIMO_JOGADERES; i++) {
            saidasDados[i].println("***Bem vindo ao jogo Pedra, Papel e Teoura***");
            saidasDados[i].println("Escolhas sua opção: \n 0 -> pedra \n 1 -> papel \n 2 -> tesoura ");
        }
   
        while (true) {
            int[] escolhasClient = new int[MAXIMO_JOGADERES];

            

            // Recebe as escolhas dos jogadores
            for (int i = 0; i < MAXIMO_JOGADERES; i++) {
                saidasDados[i].println("Sua vez de jogar. Digite:");
                try {
                    escolhasClient[i] = Integer.parseInt(leiturasDados[i].readLine());
                } catch (IOException e) {
                    System.out.println("Jogador " + (i + 1) + " digitou uma escolha inválida.");
                    escolhasClient[i] = -1;
                }
            }

            // Verifica se as escolhas dos jogadores são válidas
            boolean escolhaInvalida = false;
            for (int i = 0; i < MAXIMO_JOGADERES; i++) {
                if (escolhasClient[i] < 0 || escolhasClient[i] > 2) {
                    saidasDados[i].println("Escolha inválida. Tente novamente.");
                    escolhaInvalida = true;
                }
            }

            // Se alguma escolha for inválida, pula para a próxima rodada
            if (escolhaInvalida) {
                continue;
            }

            // Calcula o resultado do jogo
            int resultado = getResultado(escolhasClient[0], escolhasClient[1]);
            String saidaResultado = "";
            if (resultado == -1) {
              
                saidaResultado = "Empate!";
                } else {
                
                saidaResultado = "Jogador " + (resultado + 1) + " venceu!";
                }

        // Envia a String do resultado do jogo para cada jogador
        for (int i = 0; i < MAXIMO_JOGADERES; i++) {
            saidasDados[i].println(saidaResultado);
        }
      }
    }

    
}
