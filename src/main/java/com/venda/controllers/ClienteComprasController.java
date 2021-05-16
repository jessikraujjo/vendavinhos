
package com.venda.controllers;

import java.util.List;

import com.venda.models.ClienteCompra;
import com.venda.service.ClienteCompraService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jessica
 */
@RestController
public class ClienteComprasController {
    @Autowired
	private ClienteCompraService clientecomprasservice;
	
	@RequestMapping(value = "/maiorvalor")
	public ResponseEntity<List<ClienteCompra>> obterClienteMaiorValor() throws JSONException {
		
		List<ClienteCompra> cliente = this.clientecomprasservice.ListarClientesMaiorValor(); 

		return ResponseEntity.ok(cliente);
	}
	
	@RequestMapping(value = "/maiorvalordata")
	public ResponseEntity<List<ClienteCompra>> obterClienteMaiorValorData() throws JSONException {
		
		List<ClienteCompra> cliente = this.clientecomprasservice.ListarClientesMaiorValorData();

		return ResponseEntity.ok(cliente);
	}
	@RequestMapping(value = "/fieis")
	public ResponseEntity<List<String>> obterClientesFieis() throws JSONException {
		
		List<String> cliente = this.clientecomprasservice.ListarClientesFieis();

		return ResponseEntity.ok(cliente);
	}
	
}