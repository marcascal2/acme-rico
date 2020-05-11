Feature: Director Granted Loans List
    I can do login in the system with my credentials and see the granted loans

    Scenario: Successful login as director and see the granted loans (Positive)
        Given I am not logged in the system as director
        When I tray to login as a director and see the granted loans
        Then The list of granted loans is shown
        
    Scenario: Unsuccessful login as client and see the granted loans (Negative)
    	Given An user is not logged as a client
    	When They want to obtain information about the loans granted in the bank
    	Then The system will deny the access