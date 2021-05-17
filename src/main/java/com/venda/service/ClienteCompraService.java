
package com.venda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.venda.models.Cliente;
import com.venda.models.ClienteCompra;
import com.venda.models.Compras;
import com.venda.util.Utils;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.core.ParameterizedTypeReference;

@Service
public class ClienteCompraService {

    @Autowired
    private WebClient webClientClientes;
    @Autowired
    private WebClient webClientCompras;

    Gson gsoncliente = new Gson();
    Gson gsoncompra = new Gson();

    public List<ClienteCompra> ListarClientesMaiorValor() throws JSONException {
        List<ClienteCompra> listaclientecompra = new ArrayList<ClienteCompra>();
        listaclientecompra = retornarClientesCompras(); //chamando funcao com cliente e compra
        
        Collections.sort(listaclientecompra); //ordenando lista com maior valor total

        return listaclientecompra;
    }

    public List<ClienteCompra> ListarClientesMaiorValorData() throws JSONException {
       
         List<ClienteCompra> listaclientecompra = new ArrayList<ClienteCompra>();
        listaclientecompra = retornarClientesCompras();  //chamando funcao com cliente e compra

        List<ClienteCompra> listaano = new ArrayList<ClienteCompra>();
        
        Collections.sort(listaclientecompra); //ordenando lista com maior valor total

        for (ClienteCompra cli : listaclientecompra) {
            if (cli.getAno().equals("2016")) { //verificar ano se eh 2016
                listaano.add(cli); //add em lista
                break;
            }
        }

        return listaano;
    }

    public List<String> ListarClientesFieis() {
       
        List<ClienteCompra> listaclientecompra = new ArrayList<ClienteCompra>();
        listaclientecompra = retornarClientesCompras(); //chamando funcao com cliente e compra

        List<String> clientefieis = new ArrayList<String>();

        Map<String, Integer> map = new HashMap<String, Integer>(); //criando hash para pegar os clientes que mais compram
        for (ClienteCompra compra : listaclientecompra) {
            clientefieis.add(compra.getNome()); //pegando apenas o nome dos clientes e armazendo em lista
        }   
        for(String nome: clientefieis){ //percorrendo nomes e contar frequencia dos nomes
            if(map.containsKey(nome)){
                map.put(nome, map.get(nome)+1);
            }else{
                map.put(nome, 1);
            }
        }
        List<String> fieisClientes = new ArrayList<String>();
       
        for(String cliente: map.keySet()){ 
            if( map.get(cliente) >= 5){ 
                fieisClientes.add(cliente);//add em lista os que mais compram
            }
        }
        return fieisClientes;    
    }
    
    public List<ClienteCompra> retornarClientesCompras() {
        // conversar com API e recuperar os dados do historico de compra e clientes
        Mono<String> monoCompra = this.webClientCompras.method(HttpMethod.GET).uri("/compras").retrieve()
                .bodyToMono(new ParameterizedTypeReference<String>() { });
        Mono<String> monoCliente = this.webClientClientes.method(HttpMethod.GET).uri("/clientes").retrieve()
                .bodyToMono(new ParameterizedTypeReference<String>() { });

        String clientes = monoCliente.block(); // armazenar json em string
        String compras = monoCompra.block();

        Type compraListType = new TypeToken<ArrayList<Compras>>() { }.getType();
        Type clienteListType = new TypeToken<ArrayList<Cliente>>() { }.getType();

        gsoncompra = new GsonBuilder().setDateFormat("dd-MM-yyyy").create(); //mudar formatacao da data
        // armazenado a string json e transformar para objeto e armazenar em listas
        List<Compras> compraArray = gsoncompra.fromJson(compras.toString(), compraListType);
        List<Cliente> clienteArray = gsoncliente.fromJson(clientes.toString(), clienteListType);

        List<ClienteCompra> listaclicompra = new ArrayList<ClienteCompra>();

        for (Cliente cliente : clienteArray) { // percorrer clientes
            String cpfsempontos = Utils.retiraCaracteresEspeciais(cliente.getCpf());
            cliente.setCpf(cpfsempontos);
            for (Compras compra : compraArray) {    // percorrer compras
                compra.setCliente(compra.getCliente().replaceAll("[^0-9]", "")); //  eliminar pontuacoes do cpf
                if (compra.getCliente().length() == 12) { //erro em cpf no historico de compras um zero a mais
                    compra.setCliente(compra.getCliente().substring(1)); //corrigir cpf em historico de compras
                }
                if (cliente.getCpf().equals(compra.getCliente())) { //comparando os cpfs
                    ClienteCompra clicompra = new ClienteCompra();  //criando objeto ClienteCompra
                    SimpleDateFormat ano = new SimpleDateFormat("yyyy"); //formatando data para pegar apenas o ano
                    String anoformatado = ano.format(compra.getData());
                    clicompra.setCodigo(compra.getCodigo());
                    clicompra.setNome(cliente.getNome());
                    clicompra.setValorTotal(compra.getValorTotal());
                    clicompra.setAno(anoformatado);
                    listaclicompra.add(clicompra); //adicionar cliente e compra na lista
                }

            }
        }
        return listaclicompra; //retornando cliente e compra
    }
}
