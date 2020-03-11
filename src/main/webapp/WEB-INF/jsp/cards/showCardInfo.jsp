<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="cards">
    <h2>
        Credit Card
    </h2>
    <form:form modelAttribute="creditCard" class="form-horizontal" id="add-client-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Card Number" name="number" readonly="true"/>
            <petclinic:inputField label="Deadline" name="deadline" readonly="true"/>
            <petclinic:inputField label="Cvv" name="cvv" readonly="true"/>
		</div>
    </form:form>
</petclinic:layout>