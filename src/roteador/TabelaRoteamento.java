package roteador;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class TabelaRoteamento {

    /*
     * Implemente uma estrutura de dados para manter a tabela de roteamento. A tabela deve possuir: IP Destino, Métrica e IP de Saída.
     */
    public LinkedList<Map<String, String>> table;
    public String myIp = "192.168.0.106";

    public TabelaRoteamento() {
        /* Cria tabela de roteamento vazia. */
        this.table = new LinkedList<>();
    }

    public TabelaRoteamento(String ipDestino, String metrica, String ipSaida) {
        Map<String, String> tableRow = new HashMap<>();
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
        String ipSaida = this.myIp;

        // verifica se o ip já existe na tabela
        boolean ipExistente = false;
        for (Map<String, String> tableRow : this.table) {
            if (tableRow.get("destino").equals(ip)) {
                ipExistente = true;
                break;
            }
        }

        // se o ip não existe na tabela, adiciona
        if (!ipExistente) {
            Map<String, String> tableRow = new HashMap<>();
            tableRow.put("destino", ipDestino);
            tableRow.put("metrica", metrica);
            tableRow.put("saida", ipSaida);
            // adiciona na tabela de roteamento
            this.table.add(tableRow);
        }

        // Imprime a tabela de roteamento
        System.out.println("Tabela de roteamento inicializada");
        for (Map<String, String> tableRow : this.table) {
            System.out.println(tableRow.get("destino") + " " + tableRow.get("metrica") + " " + tableRow.get("saida"));
        }
    } 

    public void update_tabela(String tabela_s, InetAddress endereco_roteador) {
        String aux = tabela_s.substring(1);

        // separa a string em linhas
        String[] listaStrings = aux.split("\\*");

        for (String listaString : listaStrings) {
            // pega IP e métrica
            String[] ip_metrica = listaString.split(";");

            String ip = ip_metrica[0];
            String metrica = ip_metrica[1];

            if(!ip.equals(this.myIp)){
                // verifica se o IP de Destino já existe na tabela
                boolean ipExistente = false;
                for (Map<String, String> tableRow : this.table) {
                    if (tableRow.get("destino").equals(ip)) {
                        ipExistente = true;
                        int metricaAtual = Integer.parseInt(tableRow.get("metrica"));
                        int novaMetrica = Integer.parseInt(metrica.trim());

                        // Verifica se a Métrica recebida é menor do que a Métrica atual
                        if (novaMetrica < metricaAtual) {
                            tableRow.put("metrica", String.valueOf(novaMetrica));
                            tableRow.put("saida", endereco_roteador.getHostAddress());
                        }
                        break;
                    }
                }

                // Se o IP de Destino não existe na tabela, adiciona
                if (!ipExistente) {
                    Map<String, String> tableRow = new HashMap<>();
                    tableRow.put("destino", ip);
                    tableRow.put("metrica", String.valueOf(Integer.parseInt(metrica.trim()) + 1));
                    tableRow.put("saida", endereco_roteador.getHostAddress());
                    this.table.add(tableRow);
                }
            }
        }

        // Imprime a tabela de roteamento
        System.out.println("Tabela de roteamento atualizada:");
        for (Map<String, String> tableRow : this.table) {
            System.out.println("IP Destino: " + tableRow.get("destino") + " | Métrica: " + tableRow.get("metrica")
                    + " | IP Saída: " + tableRow.get("saida"));
        }
    }

    public String get_tabela_string() {
        String tabela_string = ""; /* Tabela de roteamento vazia conforme especificado no protocolo */
        if (this.table.size() <= 0) {
            return "!";
        }

        for (Map<String, String> tableRow : this.table) {
            // padrão de retorno: *192.168.1.2;1*192.168.1.3;1
            tabela_string += "*" + tableRow.get("destino") + ";" + tableRow.get("metrica");
        }
        System.out.println("Tabela de roteamento: " + tabela_string);

        return tabela_string;
    }

    public void remover_rotas_vizinho(String ipVizinho) {
         for (int i = 0; i < this.table.size(); i++) {
            Map<String, String> tableRow = this.table.get(i);
            if (tableRow.get("saida").equals(ipVizinho)) {
                this.table.remove(i);
                i--;
            }
        }
        System.out.println("Rotas removidas!");
    }

    public void imprimir_tabela() {
        System.out.println("Tabela de roteamento:");
        for (Map<String, String> tableRow : this.table) {
            System.out.println("IP Destino: " + tableRow.get("destino") + " | Métrica: " + tableRow.get("metrica")
                    + " | IP Saída: " + tableRow.get("saida"));
        }
    }
}

