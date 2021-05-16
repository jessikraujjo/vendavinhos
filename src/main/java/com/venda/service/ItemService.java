package com.venda.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.venda.models.Compras;
import com.venda.models.Item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class ItemService {
	@Autowired
	private WebClient webClientCompras;

	Gson gson = new Gson();
	private List<Compras> listacompras;

	public String VinhoRecomendado() { // conversar com API e recuperar seus dados
		Mono<String> monocompras = this.webClientCompras.method(HttpMethod.GET).uri("/compras").retrieve()
				.bodyToMono(new ParameterizedTypeReference<String>() {
				});
		String compras = monocompras.block(); // armazenar em string

		Type comprasListType = new TypeToken<ArrayList<Compras>>() {
		}.getType();
		gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
		listacompras = gson.fromJson(compras.toString(), comprasListType); // armazenado a string json e transformar para objeto e armazenar em lista

		List<Item> listaprodutos = new ArrayList<Item>();
		List<String> listavinhos = new ArrayList<String>();

		for (Compras item : listacompras) { // pegar produtos das compras e add em lista
			listaprodutos.addAll(item.getItens());
		}
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (Item categoriaVinho : listaprodutos) {
			listavinhos.add(categoriaVinho.getCategoria()); // pegar categorias dos vinhos e add em lista
		}
		for (String nome : listavinhos) { // percorrer lista de categorias e add no hashmap
			if (map.containsKey(nome)) {
				map.put(nome, map.get(nome) + 1);
			} else {
				map.put(nome, 1);
			}
		}
		String vinhoRecomendado = "";

		for (String m : map.keySet()) {
			// verificar frequecia desses vinhos
			if (map.get(m) >= 50) {
				vinhoRecomendado = m; // recomendar o mais comprado
			}
		}
		return vinhoRecomendado;
	}

}
