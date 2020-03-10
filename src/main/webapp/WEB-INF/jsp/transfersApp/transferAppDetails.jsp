<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="transferapps">

	<h2>Transfer Application Information</h2>


	<table class="table table-striped">
		<tr>
			<th>Id</th>
			<td><b><c:out value="${transfer_application.id}" /></b></td>
		</tr>
		<tr>
			<th>Status</th>
			<td><c:out value="${transfer_application.status}" /></td>
		</tr>
		<tr>
			<th>Amount</th>
			<td><c:out value="${transfer_application.amount}" /></td>
		</tr>
		<tr>
			<th>Account Number</th>
			<td><c:out value="${transfer_application.account_number_destination}" /></td>
		</tr>

	</table>
	<c:choose>
	<c:when test="${transfer_application.status == 'PENDING'}">
	<form method="get" action="/transferapps/${transfer_application.id}/accept">
     	<button class="btn btn-default">Accept Transfer</button>
	</form>
	
	<form method="get" action="/transferapps/${transfer_application.id}/refuse">
     	<button class="btn btn-default">Refuse Transfer</button>
	</form>
	 </c:when>
	 </c:choose>
</petclinic:layout>