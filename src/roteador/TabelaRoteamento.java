package roteador;

import java.net.InetAddress;

public class TabelaRoteamento {
    /*Implemente uma estrutura de dados para manter a tabela de roteamento. 
     * A tabela deve possuir: IP Destino, Métrica e IP de Saída.
    */
    public LinkedList table = new LinkedList();
    
    public TabelaRoteamento(String ipDestino, String metrica, String ipSaida){
        Map<String,String> tableRow = new HashMap<String,String>();
        tableRow.set("destino", ipDestino );
        tableRow.set("metrica", metrica);
        tableRow.set("saida", ipSaida );
        this.table.add(tableRow);
    }
    
    
    public void update_tabela(String tabela_s,  InetAddress IPAddress){
        /* Atualize a tabela de rotamento a partir da string recebida. */
        
        System.out.println( IPAddress.getHostAddress() + ": " + tabela_s);
    
    }
    
    public String get_tabela_string(){
        String tabela_string = ""; /* Tabela de roteamento vazia conforme especificado no protocolo */
        if(this.table.length <= 0){
            return "!";
        }

        for(int i = 0; i < this.table.length ; i++){
            tabela_string += "*";
            tabela_string += this.table[i].get("destino");
            tabela_string += ";";
            tabela_string += this.table[i].get("metrica");
        }
        return tabela_string;
    }
    

    
}
