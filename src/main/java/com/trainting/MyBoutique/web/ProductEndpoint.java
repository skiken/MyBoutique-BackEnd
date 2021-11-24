package com.trainting.MyBoutique.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.trainting.MyBoutique.dto.ProductDto;
import com.trainting.MyBoutique.services.ProductService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api"+"/products")
public class ProductEndpoint {
	
	@Autowired
	private  ProductService productService;
	
	@GetMapping
	public List<ProductDto> findAll() {
	return this.productService.findAll();
	}

	@GetMapping("/productId/{id}")
	public ProductDto findById(@PathVariable("id") Long id) {
	return this.productService.findById(id);
	}

	@GetMapping("/categoryId/{idCategory}")
	public List<ProductDto> findAllByCategoryId(@PathVariable("idCategory") Long idCategory) {
		return this.productService.findAllByCategoryId(idCategory);
	}

	@PostMapping
	public ProductDto create(@RequestBody ProductDto productDto) {
	return this.productService.create(productDto);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
	this.productService.delete(id);
	}

	@PutMapping("/{id}")
	public ProductDto updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto){
		return this.productService.updateProduct(id,productDto);
	}
}
