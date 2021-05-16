
package com.venda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.venda.models.Cliente;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.core.ParameterizedTypeReference;

@Service
public class ClienteService {

    @Autowired
    private WebClient webClientClientes;
    Gson gson = new Gson();
    private List<Cliente> userArray;

    public List<Cliente> ListarClientes() throws JSONException {
        Mono<String> monoCliente = this.webClientClientes
                .method(HttpMethod.GET)
                .uri("/clientes")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<String>() {
                });
        String clientes = monoCliente.block();
       
        Type userListType = new TypeToken<ArrayList<Cliente>>() {
        }.getType();

        userArray = gson.fromJson( clientes.toString(), userListType);
        
        return userArray;
    }
    
    
}

