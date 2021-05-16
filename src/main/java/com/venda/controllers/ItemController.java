
package com.venda.controllers;

import com.venda.service.ItemService;

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
public class ItemController {
    @Autowired
	private ItemService itemservice;
	
	@RequestMapping(value = "/vinho")
	public ResponseEntity<String> obterItems() throws JSONException {
		
		String items = this.itemservice.VinhoRecomendado();

		return ResponseEntity.ok(items);
	}
	
}