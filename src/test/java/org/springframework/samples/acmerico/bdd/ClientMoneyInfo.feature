Feature: Client Money Information
    I can do login in the system with my credentials and look at the information of my money

    Scenario: Successfull login as client and find the information (Positive)
        Given I am not logged in the system as client in the system
        When I tray to login as a client and look money statistics as client
        Then The statistics is shown as the current client
        
    Scenario: Successfull login as worker and not find the information (Negative)
        Given I am not logged in the system as worker in the system
        When I tray to login as a worker and look money statistics as worker
        Then The button for the statistics no exist
        