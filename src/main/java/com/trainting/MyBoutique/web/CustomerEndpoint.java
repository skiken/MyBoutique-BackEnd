package com.trainting.MyBoutique.web;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.trainting.MyBoutique.persistence.Customer;
import com.trainting.MyBoutique.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.trainting.MyBoutique.dto.CustomerDto;
import com.trainting.MyBoutique.services.CustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api"+"/customer")
@Slf4j
public class CustomerEndpoint {
	
	@Autowired
	CustomerService customerService;

	@PostMapping("/create")
	public CustomerDto create(@RequestBody @Valid CustomerDto customerDto) throws Exception {
		log.debug("request to the root of creation new customer ");
		
			return customerService.create(customerDto);	
			
	}
	
	@GetMapping()
	public List<CustomerDto> findAll(){
		return customerService.findAll();
		
	}

	@GetMapping("/role/{role}")
	public List<CustomerDto> findAllByRole(@PathVariable("role") String role){
		return customerService.findAllByRole(role);

	}
	
	@GetMapping("/{id}")
	public CustomerDto findById(@PathVariable("id") Long id) {
		return customerService.findCustomerById(id);
		
	}
	@GetMapping("/idOrder/{idOrder}")
	public CustomerDto findCustomerByOrderId(@PathVariable("idOrder")Long idOrder) {
		return customerService.findCustomerByOrderId(idOrder);

	}

	@GetMapping("/username/{username}")
	public CustomerDto findByUserName(@PathVariable("username") String username) {
		return customerService.findCustomerByUsername(username);

	}
	
	@DeleteMapping("/Id/{id}")
	public void delete (@PathVariable("id") Long id, @RequestParam(value="aziz") Long f ) {
		 this.customerService.deleteCustomer(id);
		
	}



	@PutMapping("/username/{username}")
	public CustomerDto update(@PathVariable("username") String username, @RequestBody @Valid CustomerDto newCustomer){
		return customerService.update(newCustomer,username);

	}


	
	
}
