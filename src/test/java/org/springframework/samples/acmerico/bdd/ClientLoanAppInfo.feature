Feature: Client Loan Applications Information

   As a client I would like to be able to access the
    information of my loans to know when I have to pay

    Scenario: Successful login as client and correct information about outstanding loans and their information.(Positive)
         Given I am not logged in the system as a client
         When I tray to login as a client and look my loan applications information if I have
         Then The information about loans is shown as the current user
         
     
    Scenario: Successful login as client and no information about outstanding loans.(Negative)
    Given I am not logged in the system as a clientt
    When I tray to login as a client and look my loan applications information but but I do not have
    Then no information about my loan applications is displayed

  
