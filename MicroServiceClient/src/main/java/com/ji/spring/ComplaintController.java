package com.ji.spring;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class ComplaintController {
	
	RestTemplate restTemplate = new RestTemplate();
	
	@Autowired
	DiscoveryClient discoveryClient;
	
	/**
	 * COMPLAINTS
	 */
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(@ModelAttribute Complaint complaints)
	{
	    ModelAndView mv = new ModelAndView();
	    mv.setViewName("index");
	    
	    List<ServiceInstance> instances = discoveryClient.getInstances("COMPLAINT-MICROSERVICE");
	    
	    if (!instances.isEmpty()) {
	        ServiceInstance service = instances.get(0);
	        URI uri = service.getUri();
	    
        ResponseEntity<Complaint[]> responseEntity = restTemplate.getForEntity(uri + "/complaints", Complaint[].class);
        Complaint[] complaintsArray = responseEntity.getBody();
        List<Complaint> complaintList = Arrays.asList(complaintsArray);
	    
	    mv.addObject("complaints", complaintList);
	    
	    } else {
	        mv.addObject("complaints", Collections.emptyList());
	    }
	    return mv;
	}
	
	/**
	 * 
	 * @param bookId
	 * @return
	 */
	@RequestMapping(value = "/complaint-delete/{complaintId}", method = RequestMethod.POST)
	public ModelAndView deleteComplaint(@PathVariable("complaintId") int complaintId) {
		ModelAndView mv = new ModelAndView();
		
	    List<ServiceInstance> instances = discoveryClient.getInstances("COMPLAINT-MICROSERVICE");

	    if (!instances.isEmpty()) {
	        ServiceInstance service = instances.get(0);
	        URI uri = service.getUri();

	        restTemplate.delete(uri + "/complaint/" + complaintId);
	    } else {
	        mv.addObject("message", "Failed to delete complaint");
	    }
	    mv.setViewName("redirect:/");
	    return mv;
	}
	
	@RequestMapping(value = "/complaint-edit/{complaintId}", method = RequestMethod.GET)
  public ModelAndView showComplaintEdit(@PathVariable("complaintId") int complaintId, @ModelAttribute Complaint complaint, BindingResult bindingResult) {
      List<ServiceInstance> instances = discoveryClient.getInstances("COMPLAINT-MICROSERVICE");

      if (!instances.isEmpty()) {
          ServiceInstance service = instances.get(0);
          URI uri = service.getUri();

          ResponseEntity<Complaint> responseEntity = restTemplate.getForEntity(uri + "/complaint/" + complaintId, Complaint.class);
          complaint = responseEntity.getBody();

          ModelAndView mv = new ModelAndView();
          mv.setViewName("complaint-edit");
          mv.addObject("complaint", complaint);
          return mv;
      } else {
          ModelAndView mv = new ModelAndView("error");
          mv.addObject("message", "Complaint microservice not available");
          return mv;
      }
  }
	
	@RequestMapping(value = "/complaint-update", method = RequestMethod.POST)
	public ModelAndView complaintUpdate(@ModelAttribute Complaint complaint) {
      List<ServiceInstance> instances = discoveryClient.getInstances("COMPLAINT-MICROSERVICE");

      ModelAndView mv = new ModelAndView();
      
      if (!instances.isEmpty()) {
          ServiceInstance service = instances.get(0);
          URI uri = service.getUri();
          
	      HttpHeaders headers = new HttpHeaders();
	      headers.setContentType(MediaType.APPLICATION_JSON);
	      
          restTemplate.postForObject(uri + "/complaint-edit", new HttpEntity<>(complaint, headers), Complaint.class);
          
          mv.setViewName("/");
          return mv;
      } else {
      	ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Complaint microservice not available");
          return mv;
      }
  }
	
	@RequestMapping(value = "/complaint-add", method = RequestMethod.GET)
	public ModelAndView complaintAdd(@ModelAttribute Complaint complaints)
	{
	    ModelAndView mv = new ModelAndView();
	    mv.setViewName("complaint-add");
	    return mv;
	}
	
	@RequestMapping(value = "/complaint-add", method = RequestMethod.POST)
	public ModelAndView addCustomer(@ModelAttribute Complaint complaint) {            
	    
	    ModelAndView mv = new ModelAndView();
	    
	    List<ServiceInstance> instances = discoveryClient.getInstances("COMPLAINT-MICROSERVICE");

	    if (!instances.isEmpty()) {
	        
	        ServiceInstance service = instances.get(0);
	        URI uri = service.getUri();

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);

	        restTemplate.postForObject(uri + "/complaint", new HttpEntity<>(complaint, headers), Complaint.class);
	        
	    } else {
	        mv.setViewName("/");
	        mv.addObject("message", "Failed to create a new customer");
	    }

	    mv.setViewName("/");
	    return mv;
	}

	
//	@RequestMapping(value = "/edit-profile", method = RequestMethod.POST)
//	public ModelAndView updateCustomer(@RequestBody Customer customer) {            
//		
//	    ModelAndView mv = new ModelAndView();
//
//	    if (!checkCurrentCustomer()) {
//	        mv.addObject("customer", currentCustomer);
//	        mv.setViewName("index");
//	        return mv;
//	    }
//	    
//      List<ServiceInstance> instances = discoveryClient.getInstances("COMPLAINT-MICROSERVICE");
//
//	    if (!instances.isEmpty()) {
//	        
//	        ServiceInstance service = instances.get(0);
//	        URI uri = service.getUri();
//
//	        HttpHeaders headers = new HttpHeaders();
//	        headers.setContentType(MediaType.APPLICATION_JSON);
//
//	        Customer updatedCustomer = restTemplate.postForObject(uri + "/edit-customer", new HttpEntity<>(customer, headers), Customer.class);
//	        currentCustomer = updatedCustomer;
//	        
//	    } else {
//	        mv.setViewName("login");
//	        mv.addObject("customer", currentCustomer);
//	        mv.addObject("message", "Failed to update customer information");
//	    }
//
//        mv.setViewName("view-profile");
//        mv.addObject("customer", currentCustomer);
//        return mv;
//	}
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @param customer
	 * @return
	 */
//	@RequestMapping(value = "/login", method = RequestMethod.POST)
//	public ModelAndView login(@RequestParam("username") String username, @RequestParam("password") String password) 
//	{   
//	    ModelAndView mv = new ModelAndView();
//
//	    List<ServiceInstance> instances = discoveryClient.getInstances("CUSTOMER-MICROSERVICE");
//
//	    if (!instances.isEmpty()) {
//	    	
//	        ServiceInstance service = instances.get(0);
//	        URI uri = service.getUri();
//
//	        MultiValueMap<String, String> requestData = new LinkedMultiValueMap<>();
//	        requestData.add("username", username);
//	        requestData.add("password", password);
//
//	        ResponseEntity<Customer> responseEntity = restTemplate.postForEntity(uri + "/customer/{username}/{password}", requestData, Customer.class, username, password);
//
//	        Customer retrievedCustomer = responseEntity.getBody();
//
//	        if (retrievedCustomer != null) {
//	            username = retrievedCustomer.getUsername();
//	            currentCustomer = retrievedCustomer;
//	            mv.setViewName("index");
//	            mv.addObject("customer", currentCustomer);
//	            mv.addObject("message", "Welcome back " + currentCustomer.getFirstName() + " " +  currentCustomer.getLastName() + "!");
//	        } else {
//	            mv.setViewName("login");
//	            mv.addObject("customer", currentCustomer);
//	            mv.addObject("message", "Login failed. Please check your credentials.");
//	        }
//	    } else {
//            mv.setViewName("login");
//            mv.addObject("customer", currentCustomer);
//            mv.addObject("message", "Login failed. Please check your credentials.");
//	    }
//
//	    return mv;
//	}
	
	/**
	 * 
	 * @param customer
	 * @return
	 */
//	@RequestMapping(value = "/profile", method = RequestMethod.GET)
//	public ModelAndView profile() 
//	{   
//		
//		ModelAndView mv = new ModelAndView();
//
//		if (!checkCurrentCustomer())
//		{
//			mv.setViewName("index");
//			return mv; 
//		}
//		
//        mv.setViewName("view-profile");
//        mv.addObject("customer", currentCustomer);
//        mv.addObject("message", "Welcome back " + currentCustomer.getFirstName() + " " +  currentCustomer.getLastName() + "!");
//	    return mv;
//	}
	
	/**
	 * 
	 * @param customerId
	 * @param Customer
	 * @return
	 */
//	@RequestMapping(value = "/edit-profile", method = RequestMethod.GET)
//	public ModelAndView editCustomer() 
//	{
//	    ModelAndView mv = new ModelAndView();
//	    
//	    if (!checkCurrentCustomer()) {
//	        mv.setViewName("index");
//	        return mv;
//	    }
//	    
//	    mv.addObject("customer", currentCustomer);
//	    mv.setViewName("edit-customer");
//	    return mv;
//	}
	
	/**
	 * 
	 * @return
	 */
//	@RequestMapping(value = "/edit-profile", method = RequestMethod.POST)
//	public ModelAndView updateCustomer(@RequestBody Customer customer) {            
//		
//	    ModelAndView mv = new ModelAndView();
//
//	    if (!checkCurrentCustomer()) {
//	        mv.addObject("customer", currentCustomer);
//	        mv.setViewName("index");
//	        return mv;
//	    }
//	    
//	    List<ServiceInstance> instances = discoveryClient.getInstances("CUSTOMER-MICROSERVICE");
//
//	    if (!instances.isEmpty()) {
//	        
//	        ServiceInstance service = instances.get(0);
//	        URI uri = service.getUri();
//
//	        HttpHeaders headers = new HttpHeaders();
//	        headers.setContentType(MediaType.APPLICATION_JSON);
//
//	        Customer updatedCustomer = restTemplate.postForObject(uri + "/edit-customer", new HttpEntity<>(customer, headers), Customer.class);
//	        currentCustomer = updatedCustomer;
//	        
//	    } else {
//	        mv.setViewName("login");
//	        mv.addObject("customer", currentCustomer);
//	        mv.addObject("message", "Failed to update customer information");
//	    }
//
//        mv.setViewName("view-profile");
//        mv.addObject("customer", currentCustomer);
//        return mv;
//	}

	/**
	 * 
	 * @return
	 */
//	@RequestMapping(value = "/new-customer", method = RequestMethod.GET)
//	public ModelAndView newCustomer() 
//	{
//	    ModelAndView mv = new ModelAndView();
//	    
//	    mv.addObject("customer", currentCustomer);
//	    mv.setViewName("new-customer");
//	    return mv;
//	}
	
	/**
	 * 
	 * @return
	 */
//	@RequestMapping(value = "/new-customer", method = RequestMethod.POST)
//	public ModelAndView addCustomer(@RequestBody Customer customer) {            
//	    
//	    ModelAndView mv = new ModelAndView();
//	    
//	    List<ServiceInstance> instances = discoveryClient.getInstances("CUSTOMER-MICROSERVICE");
//
//	    if (!instances.isEmpty()) {
//	        
//	        ServiceInstance service = instances.get(0);
//	        URI uri = service.getUri();
//
//	        HttpHeaders headers = new HttpHeaders();
//	        headers.setContentType(MediaType.APPLICATION_JSON);
//
//	        Customer createdCustomer = restTemplate.postForObject(uri + "/customer", new HttpEntity<>(customer, headers), Customer.class);
//	        currentCustomer = createdCustomer;
//	        
//	    } else {
//	        mv.setViewName("login");
//	        mv.addObject("customer", currentCustomer);
//	        mv.addObject("message", "Failed to create a new customer");
//	    }
//
//	    mv.setViewName("view-profile");
//	    mv.addObject("customer", currentCustomer);
//	    return mv;
//	}

	
	/**
	 * 
	 * @param customer
	 * @return
	 */
//	@RequestMapping(value = "/customers", method = RequestMethod.GET)
//    public ModelAndView customers() {
//        ModelAndView mv = new ModelAndView();
//        mv.setViewName("customers");
//
//        List<ServiceInstance> instances = discoveryClient.getInstances("CUSTOMER-MICROSERVICE");
//
//        if (!instances.isEmpty()) {
//            ServiceInstance service = instances.get(0);
//            URI uri = service.getUri();
//
//            ResponseEntity<Customer[]> responseEntity = restTemplate.getForEntity(uri + "/customers", Customer[].class);
//            Customer[] customerArray = responseEntity.getBody();
//            List<Customer> customerList = Arrays.asList(customerArray);
//            mv.addObject("customerList", customerList);
//        } else {
//            mv.addObject("customerList", Collections.emptyList());
//        }
//        
//        mv.addObject("customer", currentCustomer);
//        return mv;
//    }
	
	/**
	 * 
	 * @return
	 */
//	@RequestMapping(value = "/transactions", method = RequestMethod.GET)
//    public ModelAndView transactions() {
//        ModelAndView mv = new ModelAndView();
//        mv.setViewName("transactions");
//
//        List<ServiceInstance> instances = discoveryClient.getInstances("TRANSACTION-MICROSERVICE");
//
//        if (!instances.isEmpty()) {
//            ServiceInstance service = instances.get(0);
//            URI uri = service.getUri();
//
//            ResponseEntity<Transaction[]> responseEntity = restTemplate.getForEntity(uri + "/transactions", Transaction[].class);
//            Transaction[] transactionArray = responseEntity.getBody();
//            List<Transaction> transactionList = Arrays.asList(transactionArray);
//            mv.addObject("transactionList", transactionList);
//        } else {
//            mv.addObject("transactionList", Collections.emptyList());
//        }
//
//        mv.addObject("customer", currentCustomer);
//        return mv;
//    }
	
//	@RequestMapping(value = "/transaction", method = RequestMethod.POST)
//	public ModelAndView addTransaction(@RequestBody Transaction transaction) {
//	    ModelAndView mv = new ModelAndView();
//	    mv.setViewName("transactions");
//
//	    RestTemplate restTemplate = new RestTemplate();
//
//	    List<ServiceInstance> instances = discoveryClient.getInstances("TRANSACTION-MICROSERVICE");
//
//	    if (!instances.isEmpty()) {
//	        ServiceInstance service = instances.get(0);
//	        URI uri = service.getUri();
//	        
//	        HttpHeaders headers = new HttpHeaders();
//	        headers.setContentType(MediaType.APPLICATION_JSON);
//
//	        HttpEntity<Transaction> requestEntity = new HttpEntity<>(transaction, headers);
//	        ResponseEntity<Void> postResponse = restTemplate.postForEntity(uri + "/transaction", requestEntity, Void.class);
//	    }
//
//	    mv.addObject("customer", currentCustomer);
//	    return mv;
//	}

	/**
	 *  get add-customer
	 * @param customer
	 * @return
	 */
//	@RequestMapping(value = "/customers/add", method = RequestMethod.GET)
//	public ModelAndView showAddCustomer(@ModelAttribute Customer customer)
//	{
//	    ModelAndView mv = new ModelAndView();
//	    mv.setViewName("add-customer");
//	    mv.addObject("customer", currentCustomer);
//	    return mv;
//	}
	
	
	/**
	 * 
	 * @return
	 */
//	@RequestMapping(value = "/books", method = RequestMethod.GET)
//	public ModelAndView books() {
//	    ModelAndView mv = new ModelAndView();
//	    mv.setViewName("books");
//
//	    List<ServiceInstance> instances = discoveryClient.getInstances("BOOK-MICROSERVICE");
//	    List<ServiceInstance> instances1 = discoveryClient.getInstances("TRANSACTION-MICROSERVICE");
//
//	    if (!instances.isEmpty()) {
//	        ServiceInstance service = instances.get(0);
//	        URI uri = service.getUri();
//
//	        ResponseEntity<Book[]> responseEntity = restTemplate.getForEntity(uri + "/books", Book[].class);
//	        Book[] bookArray = responseEntity.getBody();
//	        List<Book> bookList = Arrays.asList(bookArray);
//
//	        ServiceInstance service1 = instances1.get(0);
//	        URI uri1 = service1.getUri();
//
//	        for (Book book : bookList) {
//	            ResponseEntity<String> responseEntity1 = restTemplate.getForEntity(uri1 + "/book-availability/" + book.getBookId(), String.class);
//	            String trxnType = responseEntity1.getBody();
//	            if ("CHECK_OUT".equals(trxnType)) {
//	            	book.setAvailability(false);
//	            } else {
//	            	book.setAvailability(true);
//	            }	            
//	        }
//
//	        mv.addObject("books", bookList);
//	        mv.addObject("customer", currentCustomer);
//	    } else {
//	        mv.addObject("books", Collections.emptyList());
//	    }
//
//	    return mv;
//	}

	
	/**
	 * 
	 * @param book
	 * @return
	 */
//	@RequestMapping(value = "/books/add", method = RequestMethod.GET)
//	public ModelAndView showAddBook(@ModelAttribute Book book)
//	{
//	    ModelAndView mv = new ModelAndView();
//	    mv.addObject("customer", currentCustomer);
//	    mv.setViewName("add-book");
//	    return mv;
//	}
//	
//	@RequestMapping(value = "/books/add", method = RequestMethod.POST)
//    public ModelAndView addBook(@ModelAttribute Book book) {
//        List<ServiceInstance> instances = discoveryClient.getInstances("BOOK-MICROSERVICE");
//
//	    ModelAndView mv = new ModelAndView();
//	    mv.addObject("customer", currentCustomer);
//        
//        if (!instances.isEmpty()) {
//            ServiceInstance service = instances.get(0);
//            URI uri = service.getUri();
//
//            restTemplate.postForEntity(uri + "/books/add", book, String.class);
//            
//    	    List<ServiceInstance> instances1 = discoveryClient.getInstances("TRANSACTION-MICROSERVICE");
//	        ServiceInstance service1 = instances1.get(0);
//	        URI uri1 = service1.getUri();
//	        
//            ResponseEntity<Book[]> responseEntity = restTemplate.getForEntity(uri + "/books", Book[].class);
//            Book[] bookArray = responseEntity.getBody();
//            List<Book> bookList = Arrays.asList(bookArray);
//            
//	        for (Book b : bookList) {
//	            ResponseEntity<String> responseEntity1 = restTemplate.getForEntity(uri1 + "/book-availability/" + book.getBookId(), String.class);
//	            String trxnType = responseEntity1.getBody();
//	            if ("CHECK_OUT".equals(trxnType)) {
//	            	b.setAvailability(false);
//	            } else {
//	            	b.setAvailability(true);
//	            }	            
//	        }
//            
//            mv.addObject("books", bookList);
//            mv.addObject("customer", currentCustomer);
//            mv.setViewName("books");
//            return mv;
//        } else {
//        	ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Book microservice not available");
//        	mv.setViewName("books");
//            return mv;
//        }
//    }
//
//	@RequestMapping(value = "/books/edit/{BookId}", method = RequestMethod.GET)
//    public ModelAndView showEditBook(@PathVariable("BookId") int BookId) {
//        List<ServiceInstance> instances = discoveryClient.getInstances("BOOK-MICROSERVICE");
//
//        if (!instances.isEmpty()) {
//            ServiceInstance service = instances.get(0);
//            URI uri = service.getUri();
//
//            ResponseEntity<Book> responseEntity = restTemplate.getForEntity(uri + "/books/" + BookId, Book.class);
//            Book book = responseEntity.getBody();
//
//            ModelAndView mv = new ModelAndView();
//            mv.setViewName("edit-book");
//            mv.addObject("book", book);
//            mv.addObject("customer", currentCustomer);
//            return mv;
//        } else {
//            ModelAndView mv = new ModelAndView("error");
//            mv.addObject("message", "Book microservice not available");
//            return mv;
//        }
//    }
//
//	@RequestMapping(value = "/books/edit", method = RequestMethod.POST)
//    public ModelAndView editBook(@ModelAttribute Book book) {
//        List<ServiceInstance> instances = discoveryClient.getInstances("BOOK-MICROSERVICE");
//
//        ModelAndView mv = new ModelAndView();
//        
//        if (!instances.isEmpty()) {
//            ServiceInstance service = instances.get(0);
//            URI uri = service.getUri();
//
//            restTemplate.put(uri + "/books/edit", book);
//
//            mv.setViewName("books");
//            return mv;
//        } else {
//        	ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Book microservice not available");
//            return mv;
//        }
//    }
//	
//	@RequestMapping(value = "/books/delete/{Bookid}", method = RequestMethod.POST)
//	public ModelAndView deleteBook(@PathVariable("Bookid") int bookId) {
//		ModelAndView mv = new ModelAndView();
//		
//	    List<ServiceInstance> instances = discoveryClient.getInstances("BOOK-MICROSERVICE");
//
//	    if (!instances.isEmpty()) {
//	        ServiceInstance service = instances.get(0);
//	        URI uri = service.getUri();
//
//	        restTemplate.delete(uri + "/books/delete/" + bookId);
//	    } else {
//	        mv.addObject("message", "Failed to delete book");
//	    }
//	    mv.setViewName("books");
//	    return mv;
//	}
//	
//	@RequestMapping(value = "/books/lend/{bookid}", method = RequestMethod.GET)
//    public ModelAndView showLendBook(@PathVariable("bookid") int BookId) {
//		
//        List<ServiceInstance> instances = discoveryClient.getInstances("BOOK-MICROSERVICE");
//
//        if (!instances.isEmpty()) {
//            ServiceInstance service = instances.get(0);
//            URI uri = service.getUri();
//
//            ResponseEntity<Book> responseEntity = restTemplate.getForEntity(uri + "/books/" + BookId, Book.class);
//            Book book = responseEntity.getBody();
//
//            ModelAndView mv = new ModelAndView();
//            mv.setViewName("lend-book");
//            mv.addObject("book", book);
//            mv.addObject("customer", currentCustomer);
//            return mv;
//        } else {
//            ModelAndView mv = new ModelAndView("error");
//            mv.addObject("message", "Book microservice not available");
//            return mv;
//        }
//    }
//	
//	@RequestMapping(value = "/books/return-transaction/{bookId}", method = RequestMethod.POST)
//	public ModelAndView returnBook(@PathVariable int bookId) {
//		
//	    ModelAndView mv = new ModelAndView();
//	    mv.setViewName("transactions");
//
//	    RestTemplate restTemplate = new RestTemplate();
//
//	    List<ServiceInstance> instances = discoveryClient.getInstances("TRANSACTION-MICROSERVICE");
//
//	    if (!instances.isEmpty()) {
//	        ServiceInstance service = instances.get(0);
//	        URI uri = service.getUri();
//	        
//	        HttpHeaders headers = new HttpHeaders();
//	        headers.setContentType(MediaType.APPLICATION_JSON);
//
//            ResponseEntity<String> responseCustomerEntity = restTemplate.getForEntity(uri + "/last-customer/" + bookId, String.class);
//            String customerId = responseCustomerEntity.getBody();
//            System.out.println("Customer ID: " + customerId);
//	        
//        	Transaction trans = new Transaction(Integer.parseInt(customerId), bookId, new java.sql.Date(System.currentTimeMillis()), TrxnType.CHECK_IN);
//        	System.out.println("Transaction ID: " + trans.toString());
//        	
//	        HttpEntity<Transaction> requestEntity = new HttpEntity<>(trans, headers);
//	        ResponseEntity<Void> postResponse = restTemplate.postForEntity(uri + "/transaction", requestEntity, Void.class);
//	        System.out.println(trans.toString());
//        }
//
//	    mv.addObject("customer", currentCustomer);
//	    return mv;
//	}
	
}
