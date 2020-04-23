Feature: Accept and refuse loan applications
	I can do login in the system with my credentials and accept or refuse loan applications
	
	Scenario: Successful login as director and accept a loan application (Positive)
	Given I am not logged in the system as director1
	When I try to login as a director1 and accept a loan application
	Then The loan application is accepted
	
	
	Scenario: Successful login as worker and refuse a loan application (Positive)
	Given I am not logged in the system as worker
	When I try to login as a worker and refuse a loan application
	Then The loan application is refused
	
	
	Scenario: Successful login as client and accept or refuse a loan application (Negative)
	Given I am not logged in the system as client
	When I try to login as a client and accept or refuse a loan application
	Then The system does not permit accept or refuse a loan application