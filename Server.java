import java.net.Socket;
import java.net.ServerSocket;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.IOException;

public class Server{
    private static int PORTA= 1234;
    private static int MAXIMO_JOGADERES= 2;


    public static void main(String[] args) throws IOException {
        //Criando servidor: 
   
        ServerSocket serverSocket = new ServerSocket(PORTA);

        try{
            System.out.println("Servidor disponível na porta: " + PORTA);
            
        } catch (Exception e) {
            System.out.println("Erro na inicialização do servidor");
            System.out.println("Erro: " + e.getMessage());
            return;
        }                
        
        // Após muitos tentivas e erros, pedimos ajudas ao ChatGPT,
        // onde ele recomendou o uso de velotes visto que são multiplas conexões

        Socket[] socketClient = new Socket[MAXIMO_JOGADERES];
        PrintWriter[] saidasDados = new PrintWriter[MAXIMO_JOGADERES];
        BufferedReader[] leiturasDados = new BufferedReader[MAXIMO_JOGADERES];

        // Um laço de repetição que espera até que o número máximo de jogadores se conecte
        int clienteConectados = 0;
        while(clienteConectados < MAXIMO_JOGADERES){
            try {
            //No cliente se conecta ao servidor     
            Socket clienteNovo = serverSocket.accept();

            //incluimos o novo cliente que aceitou no vetor de clientes
            socketClient[clienteConectados] = clienteNovo;
            
            /* As PrintWriter e BufferedReader são respectivamente, classes para escrita e leitura de dados
            * via console ou arquivos. Nessa aplicação vamos usar interação via console 
            * carregando nos vetores de saida e leitura de dados dos jogadores */ 

            saidasDados[clienteConectados] = new PrintWriter(clienteNovo.getOutputStream(), true);
            leiturasDados[clienteConectados] = new BufferedReader(new InputStreamReader(clienteNovo.getInputStream()));

            System.out.println("Jogador " + (clienteConectados + 1) + " se conectou.");
            clienteConectados++;

            }catch (Exception e) {
                System.out.println("Erro na conexão dos clientes ao servidor");
            }
        } 

        /*Criamos a Thread responsavel pelo controle do jogo
        * nessa passamos os parametros dos clientes carregados nós vetores*/

        System.out.println("Carregando jogo...");
        Jogo jogo = new Jogo(socketClient, saidasDados, leiturasDados);
        jogo.run();
        
        
    }
}