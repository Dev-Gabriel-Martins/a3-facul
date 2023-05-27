import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static String IP_SERVIDOR = "localhost";
    private static int PORTA = 1234;

    public static void main(String[] args) throws IOException {
    
        try{
            Socket socketClient = new Socket(IP_SERVIDOR, PORTA);

            //Saida de dadoss do cliente
            PrintWriter saidasDados = new PrintWriter(socketClient.getOutputStream(), true);
            
            //Entrada de dados vinda do servidor
            BufferedReader entradaDados = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            
            while (true){
                // Requisição do servidor: 
                String entrada = entradaDados.readLine();
                if(entrada == null){
                    break;
                }
                System.out.println(entrada);

                /* Se entrada de dados vindo do servidor terminar com "Escolha"
                habilitamos o buffer de digitação de saidas de dados do cliente*/

                if(entrada.endsWith("Escolha:")){
                    String saida = console.readLine();
                    saidasDados.println(saida);
                }
            }

            saidasDados.close();
            entradaDados.close();
            console.close();
            socketClient.close();

        }
        catch (Exception e) {
            System.out.println("Erro no cliente ao se conectado ao servidor: " + e.getMessage());
            return;
        }
    }
}
