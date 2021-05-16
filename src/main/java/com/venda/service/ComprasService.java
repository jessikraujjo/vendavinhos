
package com.venda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.venda.models.Compras;

import java.lang.reflect.Type;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;

@Service
public class ComprasService {

    @Autowired
    private WebClient webClientCompras;

    Gson gson = new Gson();

    public List<Compras> ListarCompras() {
        Mono<String> monoCompra = this.webClientCompras.method(HttpMethod.GET).uri("/compras").retrieve()
                .bodyToMono(new ParameterizedTypeReference<String>() {
                });
        String compras = monoCompra.block();

        Type comprasListType = new TypeToken<List<Compras>>() {
        }.getType();
        gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
        List<Compras> compraArray = gson.fromJson(compras.toString(), comprasListType);

        return compraArray;
    }

   
    
}