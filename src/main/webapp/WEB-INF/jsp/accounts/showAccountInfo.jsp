<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="accounts">
    <h2>
        <c:if test="${bankAccount['new']}">New </c:if> Bank Account
    </h2>
    <form:form modelAttribute="bankAccount" class="form-horizontal" id="add-client-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Account Number" name="accountNumber" readonly="true"/>
            <petclinic:inputField label="Amount" name="amount" readonly="true"/>
            <petclinic:inputField label="Alias" name="alias" readonly="true"/>
		</div>
    </form:form>
</petclinic:layout>