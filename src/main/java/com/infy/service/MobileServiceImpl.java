package com.infy.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

import com.infy.validator.Validator;
import com.infy.dao.MobileServiceDAO;
import com.infy.dao.MobileServiceDAOImpl;
import com.infy.exception.MobileServiceException;
import com.infy.model.ServiceReport;
import com.infy.model.ServiceRequest;
import com.infy.model.Status;

public class MobileServiceImpl implements MobileService{
	
	private MobileServiceDAO dao =  new MobileServiceDAOImpl();
    private Validator validator=new Validator();

	@Override
	public ServiceRequest registerRequest(ServiceRequest service) throws MobileServiceException {
		validator.validate(service);
		float cost = this.calculateEstimateCost(service.getIssues());
		
		if(cost <= 0) {
			throw new MobileServiceException("Sorry, we do not provide that service");
		} else {
			service.setServiceFee(cost);
			service.getStatus();
			service.setStatus(Status.ACCEPTED);
			service.setTimeOfRequest(LocalDateTime.now());
			
			dao.registerRequest(service);
			return service;
		}
	}

	@Override
	public Float calculateEstimateCost(List<String> issues) throws MobileServiceException {
		float fee = 0f;
		for(String iss : issues) {
			if(iss.equalsIgnoreCase("SCREEN")) {
				fee += 15f;
			} else if(iss.equalsIgnoreCase("CAMERA")) {
				fee += 10f;
			} else if(iss.equalsIgnoreCase("BATTERY")) {
				fee += 5f;
			} else {
				fee += 0f;
			}
		}
		return fee;
	}

	@Override
	public List<ServiceReport> getServices(Status status) throws MobileServiceException { 
		
		List<ServiceReport> serviceReports = new ArrayList<>();

        // Invoke getServices() from DAO to get the list of ServiceRequests
        List<ServiceRequest> serviceRequests = dao.getServices();

        // Filter and populate the List<ServiceReport>
//        serviceReports = serviceRequests.stream()
//                .filter(request -> request.getStatus() == status)
//                .collect(Collectors.toList());
        
        for(ServiceRequest request : serviceRequests) {
        	if(request.getStatus().ACCEPTED == status || request.getStatus().REJECTED == status || request.getStatus().IN_PROGRESS == status || request.getStatus().COMPLETED == status) {
        		ServiceReport report = new ServiceReport(request.getServiceId(), request.getBrand(), request.getIssues(), request.getServiceFee());
        		serviceReports.add(report);
        	}
        }
        
        // Check if the List is empty and throw exception if needed
        if (serviceReports.isEmpty()) {
            throw new MobileServiceException("Sorry, we did not find any records for your query.");
        }

        return serviceReports;
	}

}
