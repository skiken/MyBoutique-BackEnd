package com.trainting.MyBoutique.services;


import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;


import com.trainting.MyBoutique.persistence.Address;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trainting.MyBoutique.dto.CustomerDto;
import com.trainting.MyBoutique.persistence.Customer;
import com.trainting.MyBoutique.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor

public class CustomerService {

	@Resource
	private  CustomerRepository customerRepository;

	
	public CustomerDto create(CustomerDto customerDto) throws Exception {
		Address address= new Address("","","","");
		log.debug("request to create customer :{}",customerDto);
		if(verifiyExistEmail(customerDto)) {
			throw new Exception("there is an account with this email adress :  "+customerDto.getEmail()) ;
		}
		else
		return mapToDto(
			    this.customerRepository.save
				(new Customer(
			    customerDto.getUsername(),
			    customerDto.getPassword(),
				customerDto.getRoles(),	
				customerDto.getFirstName(),
				customerDto.getLastName(),
				customerDto.getEmail(),
				customerDto.getTelephone(),
				Collections.emptySet(),
				Boolean.TRUE,
								address


				
				
				)
								)
						);
	}
	
	private Boolean verifiyExistEmail(CustomerDto customerDto) {
		return	customerRepository.findByEmail(customerDto.getEmail())!=null;
		}
	
	public List<CustomerDto> findAll(){
		log.debug("request to find all customers");
		return this.customerRepository.findAll().stream().map(CustomerService::mapToDto).collect(Collectors.toList());
		
	}
	public List<CustomerDto> findAllActive() {
		log.debug("Request to get all Customers");
		return this.customerRepository.findAllByEnabled(true)
		.stream()
		.map(CustomerService::mapToDto)
		.collect(Collectors.toList());
		}
	
	public List<CustomerDto> findAllInactive() {
		log.debug("Request to get all Customers");
		return this.customerRepository.findAllByEnabled(false)
		.stream()
		.map(CustomerService::mapToDto)
		.collect(Collectors.toList());
		}
	
	public CustomerDto findCustomerById(Long id) {
		log.debug("request to find customer:{}",id);
		return this.customerRepository.findById(id).map(CustomerService::mapToDto).orElseThrow(()->new IllegalStateException(
				"the customer don't exist"));
		
	}
	public CustomerDto findCustomerByOrderId(Long id ) {
		log.debug("request to find customer:{}");
		return this.customerRepository.findCustomerByOrderId(id).map(CustomerService::mapToDto).orElseThrow(()->new IllegalStateException(
				"the customer don't exist"));

	}


	public List<CustomerDto>findAllByRole(String role){
		log.debug("Request to get all Customers by Role:{}",role);
          return this.customerRepository.findAllByRoles(role).stream().map(CustomerService::mapToDto).collect(Collectors.toList());

	}
	
	

	public void deleteCustomer(Long id) {
		log.debug("request to delete customer : {}",id);
		this.customerRepository.findById(id).orElseThrow(()->new IllegalStateException(
				"the customer don't exist"));
		
		customerRepository.deleteById(id);
		
	}
	
	public static CustomerDto mapToDto(Customer customer) {
		if(customer!=null) {
			return new CustomerDto(customer.getId(), 
					customer.getFirstName(), 
					customer.getLastName(),
					customer.getEmail(),
					customer.getTelephone(),
					customer.getUsername(),
					customer.getPassword(),
					customer.getPassword(),
					customer.getRoles(),
					customer.getAddress()
					);
		}
		return null;
	}

	public CustomerDto update(CustomerDto newCustomer, String username){
		Optional<Customer> modifiedCustomer=customerRepository.findByUsername(username);
		Customer customer=modifiedCustomer.get();
		customer.setFirstName(newCustomer.getFirstName());
		customer.setLastName((newCustomer.getLastName()));
		customer.setEmail(newCustomer.getEmail()==null?null:newCustomer.getEmail());
		customer.setTelephone(newCustomer.getTelephone()==null?null:newCustomer.getTelephone());
		customer.setUsername(newCustomer.getUsername());
		customer.setPassword(newCustomer.getPassword());
		customer.setAddress(newCustomer.getAddress()==null?null:newCustomer.getAddress());

		return mapToDto(customerRepository.save(customer));
	}
	public CustomerDto findCustomerByUsername(String username) {
		log.debug("request to find customer by username:{}",username);
		return this.customerRepository.findByUsername(username).map(CustomerService::mapToDto).orElseThrow(()->new IllegalStateException(
				"the customer don't exist"));

	}

}
