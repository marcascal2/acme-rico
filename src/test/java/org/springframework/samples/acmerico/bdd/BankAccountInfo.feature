Feature: Client Bank Account Information
    I can do login in the system with my credentials and look at the information of my bank account

    Scenario: Successfull login as client and find the information (Positive)
        Given I am not logged in the system how client
        When I tray to login as a client and look my bank account information
        Then The information is shown as the current user
        
    Scenario: Successfull login as worker or director and not find the button (Negative)
        Given I am not logged in the system how worker or director
        When I tray to login as a worker or director and look my bank account information
        Then The button ins't shown in the menu bar