<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="loans">
    <form:form modelAttribute="loan" class="form-horizontal" id="add-client-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Minimun Amount" name="minimum_amount"/>
            <petclinic:inputField label="Minimun Client Income" name="minimum_income"/>
            <petclinic:inputField label="Number of Deadlines" name="number_of_deadlines"/>
            <petclinic:inputField label="Opening Price" name="opening_price"/>
            <petclinic:inputField label="Monthly Fee" name="monthly_fee"/>
            <petclinic:inputField label="Single Loan" name="single_loan"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
            	<button class="btn btn-default" type="submit">Add Loan</button>
            </div>
        </div>
    </form:form>
</petclinic:layout>
