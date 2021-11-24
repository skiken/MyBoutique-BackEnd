package com.trainting.MyBoutique.web;

import java.util.List;

import com.trainting.MyBoutique.dto.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.trainting.MyBoutique.dto.CategoryDto;
import com.trainting.MyBoutique.services.CategoryService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api"+"/categories")
public class CategoryEndpoint {
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/create")
    public CategoryDto create(@RequestBody CategoryDto categoryDto) {
    	return categoryService.create(categoryDto);
    }
	
	@GetMapping
	public List<CategoryDto> findAll(){
		return categoryService.findAll();
	}
	
	@GetMapping("/id/{id}")
	public CategoryDto findById(@PathVariable Long id) {
		return categoryService.findCategoryById(id);
	}

	@GetMapping("/name/{name}")
	public CategoryDto findByName(@PathVariable String name) {
		return categoryService.findCategoryByName(name);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		this.categoryService.delete(id);
	}

	@PutMapping("/{id}")
	public CategoryDto updateCategory(@PathVariable  Long id, @RequestBody CategoryDto categoryDto){
	 return this.categoryService.updateCategory(id,categoryDto);
	}

}
