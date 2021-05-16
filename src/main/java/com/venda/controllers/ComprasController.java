
package com.venda.controllers;

import java.util.List;

import com.venda.models.Compras;
import com.venda.service.ComprasService;

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
public class ComprasController {
    @Autowired
	private ComprasService comprasservice;
	
	@RequestMapping(value = "/compras")
	public ResponseEntity<List<Compras>> obterCompras() throws JSONException {
		
		List<Compras> compras = this.comprasservice.ListarCompras();

		return ResponseEntity.ok(compras);
	}
	
}