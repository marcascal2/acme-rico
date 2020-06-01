package org.springframework.samples.acmerico.projections;


public interface ClientLoanApp {
	Integer getId();
	
	Double getAmount();
	
    String getPurpose();
	
	String getStatus();
	
	Double getAmount_paid();
	
	Integer getPayedDeadlines();
	
	Integer getDeadlines();

	String getLoanDescription();
}
