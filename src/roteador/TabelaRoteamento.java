package roteador;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class TabelaRoteamento {
    /*
     * Implemente uma estrutura de dados para manter a tabela de roteamento.
     * A tabela deve possuir: IP Destino, Métrica e IP de Saída.
     */
    public LinkedList table = new LinkedList();

    public TabelaRoteamento() {
        /* Cria tabela de roteamento vazia. */
    }

    public TabelaRoteamento(String ipDestino, String metrica, String ipSaida) {
        Map<String, String> tableRow = new HashMap<String, String>();
        tableRow.put("destino", ipDestino);
        tableRow.put("metrica", metrica);
        tableRow.put("saida", ipSaida);
        // adiciona na tabela de roteamento
        this.table.add(tableRow);
    }

    // Update inicial da tabela de roteamento
    public void add_vizinho(String ip) {
        /* Atualize a tabela de rotamento a partir da string recebida. */
        // padrão de recebimento: 192.168.1.2

        String ipDestino = ip;
        String metrica = "1";
        String ipSaida = "Direta";

        // verifica se o ip já existe na tabela
        for (int j = 0; j < this.table.size(); j++) {
            Map<String, String> tableRow = (Map<String, String>) this.table.get(j);
            if (tableRow.get("destino").equals(ip)) {
                break;
            }
        }

        // se o ip não existe na tabela, adiciona
        Map<String, String> tableRow = new HashMap<String, String>();
        tableRow.put("destino", ipDestino);
        tableRow.put("metrica", metrica);
        tableRow.put("saida", ipSaida);
        // adiciona na tabela de roteamento
        this.table.add(tableRow);

        // Imprime a tabela de roteamento
        System.out.println("Tabela de roteamento inicializada");
        for (int i = 0; i < this.table.size(); i++) {
            Map<String, String> tableRow2 = (Map<String, String>) this.table.get(i);
            System.out
                    .println(tableRow2.get("destino") + " " + tableRow2.get("metrica") + " " + tableRow2.get("saida"));
        }

    }

    public void update_tabela(String tabela_s, InetAddress IPAddress) {
        /* Atualize a tabela de rotamento a partir da string recebida. */
        // padrão de recebimento: *192.168.1.2;1*192.168.1.3;1

       String aux = tabela_s.substring(1);

        // separa a string em linhas
        String[] listaStrings = aux.split("//*");
        //192.168.1.2;1 192.168.1.3;1
        // para cada linha
        for (int i = 0; i < listaStrings.length; i++) {
            // pega Ip e métrica
            String[] ip_metrica = listaStrings[i].split(";");

            // separa o ip e a métrica
            System.out.println("IP: " + ip_metrica[0] );
            String ip = ip_metrica[0];
            String metrica = ip_metrica[1];

            // verifica se o ip já existe na tabela
            for (int j = 0; j < this.table.size(); j++) {
                Map<String, String> tableRow = (Map<String, String>) this.table.get(j);
                if (tableRow.get("destino").equals(ip)) {
                    break;
                }
                else {
                    // se o ip não existe na tabela, adiciona
                    tableRow.put("destino", ip);
                    tableRow.put("metrica", metrica);
                    tableRow.put("saida", IPAddress.getHostAddress());
                    // adiciona na tabela de roteamento
                    this.table.add(tableRow);
                }
            }
            
        }

        // System.out.println( IPAddress.getHostAddress() + ": " + tabela_s);

        // Imprime a tabela de roteamento
        System.out.println("Tabela de roteamento: ");
        for (int i = 0; i < this.table.size(); i++) {
            // padrão de retorno: *
            Map<String, String> tableRow = (Map<String, String>) this.table.get(i);
            // concatena IP de Destino e Métrica
            System.out.println("IP Destino: " + tableRow.get("destino") + " | Métrica: " + tableRow.get("metrica")
                    + " | IP Saída: " + tableRow.get("saida"));

        }
    }

    public String get_tabela_string() {
        String tabela_string = ""; /* Tabela de roteamento vazia conforme especificado no protocolo */
        if (this.table.size() <= 0) {
            return "Tabela de roteamento vazia!";
        }

        for (int i = 0; i < this.table.size(); i++) {
            // padrão de retorno: *192.168.1.2;1*192.168.1.3;1
            Map<String, String> tableRow = (Map<String, String>) this.table.get(i);
            // concatena IP de Destino e Métrica
            tabela_string += "*" + tableRow.get("destino") + ";" + tableRow.get("metrica");
        }
        System.out.println("Tabela de roteamento: " + tabela_string);

        return tabela_string;
    }

}
