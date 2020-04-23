<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

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

	<sec:authorize access="hasAuthority('director') || hasAuthority('worker')">
		<c:choose>
			<c:when test="${transfer_application.status == 'PENDING'}">
				<div class="buttons-group">
					<form method="get" action="/transferapps/${transfer_application.id}/accept/${transfer_application.bankAccount.id}">
						<button class="btn btn-default" id="accept-transfer-button">Accept Transfer</button>
				   </form>
				   
				   <form method="get" action="/transferapps/${transfer_application.id}/refuse/${transfer_application.bankAccount.id}">
						<button class="btn btn-default" id="reject-transfer-button">Refuse Transfer</button>
				   </form>
				</div>
			</c:when>
		</c:choose>
	</sec:authorize>


</petclinic:layout>