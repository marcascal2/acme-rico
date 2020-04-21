<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->

<petclinic:layout pageName="clients">

	<div class="credit-card-app-container">
		<img src="<spring:url value="/resources/images/pending-icon.png" htmlEscape="true" />"  class="img-fluid credit-card-app-img" alt="Credit card app created">
    	<h3 class="credit-card-app-text">Your have to wait until we accept your pending credit card applications.</h3>
	</div>
	
</petclinic:layout>
