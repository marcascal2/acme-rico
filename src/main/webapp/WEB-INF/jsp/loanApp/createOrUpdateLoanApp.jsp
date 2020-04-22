<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page import="java.util.Arrays" %>

<petclinic:layout pageName="loanApp">
    <form:form modelAttribute="loan_app" class="form-horizontal" id="add-loanApp-form">
        <div class="form-group has-feedback">
        	<petclinic:inputField label="Amount" name="amount"/>
            <petclinic:inputField label="Purpose" name="purpose"/>
            <petclinic:inputField label="Status" name="status" readonly = "true"/>
            <petclinic:inputField label="Amount Paid" name="amount_paid" readonly = "true"/>
        </div>

       
        <div class="form-group" >
            <div class="col-sm-offset-2 col-sm-10">
            	<button class="btn btn-default" type="submit">Request application</button>
            </div>
        </div>

    </form:form>
</petclinic:layout>
