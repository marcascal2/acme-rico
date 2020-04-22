<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page import="java.util.Arrays" %>

<petclinic:layout pageName="loans">
    <form:form modelAttribute="loan" class="form-horizontal" id="add-client-form">
        <div class="form-group has-feedback">
        	<petclinic:inputField label="Description" name="description"/>
            <petclinic:inputField label="Minimun Amount" name="minimum_amount"/>
            <petclinic:inputField label="Minimun Client Income" name="minimum_income"/>
            <petclinic:inputField label="Number of Deadlines" name="number_of_deadlines"/>
            <petclinic:inputField label="Opening Price" name="opening_price"/>
            <petclinic:inputField label="Monthly Fee" name="monthly_fee"/>
            <petclinic:selectField label="Single Loan" name="single_loan" names='<%= Arrays.asList("Yes", "No") %>' size="1"/>
        </div>
    </form:form>
    </sec:authorize>
    <c:choose>
		<c:when test = "${clienSingleLoan}">
			
            <button class="btn btn-default" onclick="document.getElementById('demo').innerHTML = 'No puedes aplicar a este loan'">
                Apply for this loan</button>

            <p id="demo"></p>
		 </c:when>
		<c:when test = "${!clienSingleLoan}">
        
        <form action="/loanapps/${loan.id}/new/${bankAccountId}">
            <button class="btn btn-default" type="submit">Apply for this loan</button>
        </form>
	     </c:when>
	</c:choose>
   
</petclinic:layout>
