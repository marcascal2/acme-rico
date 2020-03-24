<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="exchanges">
	<form:form modelAttribute="container" class="form-horizontal" id="add-client-form">
	    <div class="form-group has-feedback">
	        <petclinic:selectField label="Init Rate" name="initRate" names="${rates}" size="5"/>
	        <petclinic:selectField label="Post Rate" name="postRate" names="${rates}" size="5"/>
	        <petclinic:inputField label="Amount" name="amount"/>
	        <c:if test = "${isPost}">
	            <petclinic:inputField label="Result Amount" name="resultAmount" readonly="true" />
	    	</c:if>
	    </div>
	    <button class="btn btn-default" type="submit">Calculate</button>
	</form:form>
</petclinic:layout>