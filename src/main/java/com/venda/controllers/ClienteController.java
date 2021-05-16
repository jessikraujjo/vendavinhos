
package com.venda.controllers;

import java.util.List;

import com.venda.models.Cliente;
import com.venda.service.ClienteService;

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
public class ClienteController {
    @Autowired
	private ClienteService clienteservice;
	
	@RequestMapping(value = "/clientes")
	public ResponseEntity<List<Cliente>> obterClientes() throws JSONException {
		
		List<Cliente> clientes = this.clienteservice.ListarClientes();

		return ResponseEntity.ok(clientes);
	}
		
}