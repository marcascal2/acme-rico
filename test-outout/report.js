$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("file:src/test/java/org/springframework/samples/acmerico/bdd/BankAccountInfo.feature");
formatter.feature({
  "name": "Client Bank Account Information",
  "description": "    I can do login in the system with my credentials and look at the information of my bank account",
  "keyword": "Feature"
});
formatter.scenario({
  "name": "Successfull login as client and find the information (Positive)",
  "description": "",
  "keyword": "Scenario"
});
formatter.step({
  "name": "I am not logged in the system",
  "keyword": "Given "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.step({
  "name": "I tray to login as a client and look my bank account information",
  "keyword": "When "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.step({
  "name": "The information is shown as the current user",
  "keyword": "Then "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.uri("file:src/test/java/org/springframework/samples/acmerico/bdd/Login.feature");
formatter.feature({
  "name": "Client Login",
  "description": "    I can do login in the system with my credentials",
  "keyword": "Feature"
});
formatter.scenario({
  "name": "Successfull login as client (Positive)",
  "description": "",
  "keyword": "Scenario"
});
formatter.step({
  "name": "I am not logged in the system",
  "keyword": "Given "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.step({
  "name": "I tray to login as a client",
  "keyword": "When "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.step({
  "name": "Client name is shown as the current user",
  "keyword": "Then "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
});