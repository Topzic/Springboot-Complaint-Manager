package com.ji.spring;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Transactional;


@RestController
public class ComplaintControllerREST {

	/**
	 * COMPLAINTS
	 */
	@Autowired
	ComplaintRepository complaintsRepository;
	
	/**
	 * 
	 * @return List<complaints>
	 */
	@RequestMapping(value = "/complaints", method = RequestMethod.GET)
    public List<Complaint> getAllComplaints() {
        return complaintsRepository.findAll();
    }
	
	/**
	 * 
	 * @param id
	 * @return complaint
	 * @throws Exception
	 */
    @RequestMapping(value = "/complaint/{id}", method = RequestMethod.GET)
    Optional<Complaint> getComplaint(@PathVariable("id") int id) throws Exception {
    	if(complaintsRepository.findById(id).isPresent()) {    		
    		return complaintsRepository.findById(id);
    	} else {
            throw new Exception("complaint is not found");
        }
    }
	
    /**
     * 
     * @param complaint
     * @throws Exception
     */
	@RequestMapping(value = "/complaint", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    void addComplaint(@RequestBody Complaint complaint) throws Exception {
    	complaintsRepository.save(complaint);
    }
	
	/**
	 * 
	 * @param complaint
	 * @throws Exception
	 */
	@RequestMapping(value = "/complaint/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    void removeComplaint(@PathVariable("id") int id) throws Exception {
    	complaintsRepository.deleteById(id);
    }
	
	/**
	 * 
	 * @param complaint
	 * @throws Exception
	 */
	@RequestMapping(value = "/complaint-edit", method = RequestMethod.PATCH)
    @ResponseStatus(value = HttpStatus.OK)
	@Transactional
    void updateComplaint(@RequestBody Complaint complaint) throws Exception {
        Complaint existingcomplaint = complaintsRepository.findById(complaint.getComplaintId())
                .orElseThrow(() -> new Exception("complaint not found"));

        existingcomplaint.setComplaintId(complaint.getComplaintId()); // complaintId
        existingcomplaint.setDescription(complaint.getDescription()); // description
        existingcomplaint.setDate(complaint.getDate()); // date
        existingcomplaint.setComplaintHandler(complaint.getComplaintHandler()); // complaint_handler_id
        existingcomplaint.setStatus(complaint.getStatus()); // status

        complaintsRepository.save(existingcomplaint);
    }
	
	/**
	 * COMPLAINT HANDLER
	 */
	@Autowired
	ComplaintHandlerRepository complaintsHandlerRepository;
	
	/**
	 * 
	 * @return List<complaintHandlers>
	 */
	@RequestMapping(value = "/complaint-handlers", method = RequestMethod.GET)
    public List<ComplaintHandler> getAllcomplaintHandlers() {
        return complaintsHandlerRepository.findAll();
    }
	
	/**
	 * 
	 * @param id
	 * @return complaint
	 * @throws Exception
	 */
    @RequestMapping(value = "/complaint-handler/{id}", method = RequestMethod.GET)
    Optional<ComplaintHandler> getComplaintHandler(@PathVariable("id") int id) throws Exception {
    	if(complaintsRepository.findById(id).isPresent()) {    		
    		return complaintsHandlerRepository.findById(id);
    	} else {
            throw new Exception("complaint is not found");
        }
    }
	
    /**
     * 
     * @param complaint
     * @throws Exception
     */
	@RequestMapping(value = "/complaint-handler", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    void addComplaintHanlder(@RequestBody ComplaintHandler complaintHandler) throws Exception {
		complaintsHandlerRepository.save(complaintHandler);
    }
	
	/**
	 * 
	 * @param complaint
	 * @throws Exception
	 */
	@RequestMapping(value = "/complaint-handler/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    void removeComplaintHandler(@PathVariable("id") int id) throws Exception {
		complaintsHandlerRepository.deleteById(id);
    }
	
}
