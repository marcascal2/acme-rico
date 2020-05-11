Feature: Client Loan Applications Information

   As a client I would like to be able to access the
    information of my loans to know when I have to pay

    Scenario: Successful login as client and correct information about outstanding loans and their information (Positive)
         Given I am not logged in the system as a client
         When I tray to login as a client and look my loan applications information if I have
         Then The information about loans is shown as the current user
       
    Scenario: Unsuccessful login as client and correct information about outstanding loans and their information (Negative)
    	 Given A user logged in as a client
    	 When He does not meet the necessary conditions to contract a loan
    	 Then He will not be able to obtain information about him 
     
    

  
