Feature: Director Granted Loans List
    I can do login in the system with my credentials and see the granted loans

    Scenario: Successfull login as director and see the granted loans (Positive)
        Given I am not logged in the system as director
        When I tray to login as a director and see the granted loans
        Then The list of granted loans is shown
        
    Scenario: Unsuccessfull login as client and see the granted loans (Negative)
    	Given an user logged in as a client
    	When they want to obtain information about the granted loans in the bank
    	Then the system will deny them access