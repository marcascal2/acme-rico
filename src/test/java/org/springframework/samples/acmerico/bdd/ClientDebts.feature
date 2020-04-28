Feature: Show the debts from clients in the system
	I can do login in the system with my credentials and see the debts
	
	Scenario: Successful login as worker and see the debts (Positive)
	Given I am not logged in the system as worker1
	When I try to login as a worker1 and can see the button debts
	Then The debts can be seen in the table
	
	Scenario: Successful login as client and not see the debts (negative)
	Given I am not logged in the system as client1
	When I try to login as a client1
	Then The button debts can not be seen
	