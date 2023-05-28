package roteador;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Roteador {

    public static void main(String[] args) throws IOException {
        /* Lista de endereço IPs dos vizinhos */
        ArrayList<String> ip_list = new ArrayList<>();

        /* Le arquivo de entrada com lista de IPs dos roteadores vizinhos. */
        try (BufferedReader inputFile = new BufferedReader(new FileReader("IPVizinhos.txt"))) {
            String ip;

            while ((ip = inputFile.readLine()) != null) {
                ip_list.add(ip);
            }

        } catch (FileNotFoundException ex) {
            System.out.println("Arquivo IPVizinhos.txt não encontrado.");
            Logger.getLogger(Roteador.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        // imprime lista de IPs dos vizinhos
        System.out.println("Lista de IPs dos vizinhos:");
        for (String ip : ip_list) {
            System.out.println(ip);
        }

        TabelaRoteamento tabela = new TabelaRoteamento();
        /*
         * Cria instâncias da tabela de roteamento e das threads de envio e recebimento
         * de mensagens.
         */

        // Adiciona vizinhos na tabela de roteamento
        for (String ip : ip_list) {
            tabela.add_vizinho(ip);
        }

        Thread sender = new Thread(new MessageReceiver(tabela));
        Thread receiver = new Thread(new MessageSender(tabela, ip_list));

        sender.start();
        receiver.start();

    }

}
