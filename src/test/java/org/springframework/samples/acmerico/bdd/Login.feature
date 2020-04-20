Feature: Client Login
    I can do login in the system with my credentials

    Scenario: Successfull login as client (Positive)
        Given I am not logged in the system
        When I tray to login as a client
        Then Client name is shown as the current user