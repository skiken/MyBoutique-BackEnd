package com.trainting.MyBoutique.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.trainting.MyBoutique.dto.CategoryDto;
import com.trainting.MyBoutique.persistence.Category;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trainting.MyBoutique.dto.ProductDto;
import com.trainting.MyBoutique.persistence.Product;
import com.trainting.MyBoutique.persistence.enums.ProductStatus;
import com.trainting.MyBoutique.repository.CategoryRepository;
import com.trainting.MyBoutique.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ProductService {
	
	@Resource
	private ProductRepository productRepository;
	@Resource
	private CategoryRepository categoryRepository;
	
	public List<ProductDto> findAll() {
		log.debug("Request to get all Products");
		return this.productRepository.findAll()
		.stream()
		.map(ProductService::mapToDto)
		.collect(Collectors.toList());
		}
	
		@Transactional(readOnly = true)
		public ProductDto findById(Long id) {
		log.debug("Request to get Product : {}", id);
		return this.productRepository.findById(id).map(ProductService::mapToDto).orElse(null);
		}


	public List<ProductDto> findAllByCategoryId(Long id) {
		log.debug("Request to get Product by categoryId : {}", id);
		Optional<Category> category=categoryRepository.findById(id);
		List<ProductDto> productList =this.productRepository.findAllByCategory(category).stream().map(ProductService::mapToDto)
				.collect(Collectors.toList());
		for(ProductDto productDto:productList){
			if(productDto.getQuantity()==productDto.getSalesCounter()){
				productDto.setStatus("DISCONTINUED");
				updateProduct(productDto.getId(),productDto);
			}
		}
		return this.productRepository.findAllByCategory(category).stream().map(ProductService::mapToDto)
				.collect(Collectors.toList());
	}

		
		public ProductDto create(ProductDto productDto) {
            if(!this.productRepository.existsByName(productDto.getName())){
				return mapToDto(productRepository.save(
						new Product(
								productDto.getName(),
								productDto.getDescription(),
								productDto.getImgPath(),
								productDto.getPrice(),
								productDto.getQuantity(),
								ProductStatus.valueOf(productDto.getStatus()),
								productDto.getSalesCounter(),
								this.categoryRepository.findById(productDto.getCategoryId()).orElse(null),
								Collections.emptySet(),
								Collections.emptySet()
						)
				));
			}

            return null;
		}
		
		public void delete(Long id) {
			log.debug("Request to delete Product : {}", id);
			this.productRepository.deleteById(id);
			}

	public ProductDto updateProduct(Long id, ProductDto productDto){
		Product newProduct=this.productRepository.findById(id).get();
		newProduct.setName(productDto.getName()!=null?productDto.getName():newProduct.getName());
		newProduct.setDescription((productDto.getDescription()!=null?productDto.getDescription():newProduct.getDescription()));
		newProduct.setPrice(productDto.getPrice()!=null?productDto.getPrice():newProduct.getPrice());
		newProduct.setQuantity(productDto.getQuantity()!=null?productDto.getQuantity():newProduct.getQuantity());
		newProduct.setSalesCounter(productDto.getSalesCounter()!=null?productDto.getSalesCounter():newProduct.getSalesCounter());
		newProduct.setStatus(productDto.getStatus()!=null?ProductStatus.valueOf(productDto.getStatus()):newProduct.getStatus());
		newProduct.setImgPath(productDto.getImgPath()!=null?productDto.getImgPath():newProduct.getImgPath());
		return mapToDto(this.productRepository.save(newProduct));

	}
	
	public static ProductDto mapToDto(Product product) {
		if(product!=null) {
			return new ProductDto(product.getId(),
								product.getName(),
								product.getDescription(),
								product.getPrice(),
								product.getQuantity(),
								product.getStatus().name(),
								product.getSalesCounter(),
					            product.getImgPath(),
					            product.getCategory().getId(),
								product.getSetReview().stream().map(ReviewService::mapToDto).collect(Collectors.toSet()),
								product.getSetOrderItems().stream().map(OrderItemService::mapToDto).collect(Collectors.toSet())

								);
		}return null;
	}



}
