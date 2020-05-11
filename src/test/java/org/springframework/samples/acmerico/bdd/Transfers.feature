Feature: Client Transfer Application
    I can send bank transfers of more than € 100 to make payments to other users
    
    Scenario: Successful transfer sending as client and payment made (Positive)
        Given I am a user logged in as a client
        When I need to make a transfer
        Then The system sends it to its destination online once the employee has accepted it
       
    Scenario: Unsuccessful transfer sending as client and payment not made (Negative)
        Given A client trying to make a transfer 
				When Money is not enough in the account 
				Then The system does not allow to create the request