Feature: Client Transfer Application
    I can send bank transfers of more than € 100 to make payments to other users
    
    Scenario: Successfull transfer sending as client and payment made (Positive)
        Given I am a user logged in as a client
        When I need to make a transfer
        Then the system sends it to its destination online once the employee has accepted it
       
    Scenario: Unsuccessfull transfer sending as client and payment not made (Negative)
        Given a client trying to make a transfer 
		When money is not enough in the account 
		Then the system does not allow to create the request