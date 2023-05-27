import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class Jogo implements Runnable {

    private Socket[] socketsClient;
    private PrintWriter[] saidasDados;
    private BufferedReader[] leiturasDados;
    private Socket socketClient;
    private PrintWriter saidaDados;
    private BufferedReader leituraDados;
    

    /*Dentro do nosso jogo temos:
    / 0 -> pedra
    / 1 -> papel
    / 2 -> tesoura
    / Abaixo temos uma matriz que mostra as compinações vencedoras:
    */  
    private static int[][] COMBI_VENCEDORAS = {{0, 2}, {1, 0}, {2, 1}};
    private static int MAXIMO_JOGADERES= 2;

    //Construtores com os parametros coletados da classe servidor:
    public Jogo(Socket[] socketsClient, PrintWriter[] saidasDados, BufferedReader[] leiturasDados) {
        this.socketsClient = socketsClient;
        this.saidasDados = saidasDados;
        this.leiturasDados = leiturasDados;
    }
    public Jogo(Socket socketClient, PrintWriter saidaDados, BufferedReader leituraDados) {
        this.socketClient = socketClient;
        this.saidaDados = saidaDados;
        this.leituraDados = leituraDados;
    }

    // Aqui se concentra o nucleo principal da lógica do jogo
    
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

    private static boolean getValidade(int[] numeros, int escolhaClient) {
        if (escolhaClient < numeros[0] || escolhaClient > numeros[1]) {
            return false;
        }
        return true;
    }
    
    @Override
    public void run() {
        if (socketClient == null && saidaDados == null && leituraDados == null) {
            while (true) {
                int[] escolhasClient = new int[MAXIMO_JOGADERES];
    
                // Recebe as escolhas dos jogadores
                for (int i = 0; i < MAXIMO_JOGADERES; i++) {
                    saidasDados[i].println("Escolhas sua opção: \n 0 -> pedra \n 1 -> papel \n 2 -> tesoura ");
                    saidasDados[i].println("Sua vez de jogar. Escolha:");
                    try {
                        escolhasClient[i] = Integer.parseInt(leiturasDados[i].readLine());
                    } catch (IOException e) {
                        System.out.println("Jogador " + (i + 1) + " digitou uma escolha inválida.");
                        escolhasClient[i] = -1;
                    }
                }
    
                // Verifica se as escolhas dos jogadores são válidas
                boolean flag = false;
                for (int i = 0; i < MAXIMO_JOGADERES; i++) {
                    flag = getValidade(new int[] {0, 2}, escolhasClient[i]);
                }
                // Se alguma escolha for inválida, pula para a próxima rodada
                if (!flag) {
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

        else if (socketsClient == null && saidasDados == null && leiturasDados == null) {
            while(true) {
                saidaDados.println("Escolhas sua opção: \n 0 -> pedra \n 1 -> papel \n 2 -> tesoura ");
                saidaDados.println("Sua vez de jogar. Escolha:");
                int escolhaClient = -1;
                try {
                    escolhaClient = Integer.parseInt(leituraDados.readLine());
                } catch (IOException e) {
                    System.out.println("Jogador 0 digitou uma escolha inválida.");
                }

                // Verifica se as escolhas dos jogadores são válidas
                boolean flag = getValidade(new int[] {0, 2}, escolhaClient);
                // Se alguma escolha for inválida, pula para a próxima rodada
                if (!flag) {
                    continue;
                }

                Random rand = new Random();
                int num = rand.nextInt(3);

                int resultado = getResultado(escolhaClient, num);

                if (resultado == -1) {
                    saidaDados.println("Empate!");
                }
                else if (resultado == 0) {
                    saidaDados.println("Você venceu!");
                }
                else {
                    saidaDados.println("A máquina venceu!");
                }
            }
        }
        }
    }