package roteador;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageReceiver implements Runnable {
    private TabelaRoteamento tabela;
    private long lastReceivedTime;
    private long inactivityTimeout = 30000; // Tempo limite de inatividade em milissegundos (30 segundos)

    public MessageReceiver(TabelaRoteamento t) {
        tabela = t;
        lastReceivedTime = System.currentTimeMillis();
    }

    @Override
    public void run() {
        DatagramSocket serverSocket = null;

        try {
            /* Inicializa o servidor para aguardar datagramas na porta 5000 */
            serverSocket = new DatagramSocket(5000);
        } catch (SocketException ex) {
            System.out.println("Erro ao criar socket para recebimento de mensagens - Receiver.");
            Logger.getLogger(MessageReceiver.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        byte[] receiveData = new byte[1024];

        while (true) {
            System.out.println("Chegou no while do receiver");

            /* Cria um DatagramPacket */
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            try {
                /* Aguarda o recebimento de uma mensagem */
                serverSocket.receive(receivePacket);
                lastReceivedTime = System.currentTimeMillis(); // Atualiza o tempo da última mensagem recebida

                /* Transforma a mensagem em string */
                String tabela_string = new String(receivePacket.getData());

                /* Obtem o IP de origem da mensagem */
                InetAddress IPAddress = receivePacket.getAddress();

                // imprime tabela
                System.out.println("Tabela de roteamento recebida de " + IPAddress.toString());
                System.out.println(tabela_string);

                tabela.update_tabela(tabela_string, IPAddress);

                // Verifica se o roteador vizinho está inativo há mais de 30 segundos
                if (System.currentTimeMillis() - lastReceivedTime > inactivityTimeout) {
                    String ipVizinho = IPAddress.getHostAddress();
                    tabela.remover_rotas_vizinho(ipVizinho);
                    System.out.println("Roteador vizinho " + ipVizinho + " está inativo. Rotas removidas.");
                }
            } catch (IOException ex) {
                System.out.println("Erro ao receber mensagem.");
                Logger.getLogger(MessageReceiver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}