
package com.venda.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author jessica
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClienteCompra implements Comparable<ClienteCompra>{

    private String nome;
    private Double valorTotal;
    private String codigo;
    private String ano;

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
   
    public Double getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String getAno() {
        return ano;
    }
    public void setAno(String ano) {
        this.ano = ano;
    }
    @Override
    public int compareTo(ClienteCompra clicompra) {
        if (this.valorTotal > clicompra.getValorTotal()) { 
            return -1; 
        } if (this.valorTotal < clicompra.getValorTotal()) { 
            return 1; 
        } 
            return 0; 
        
    }
   
}
