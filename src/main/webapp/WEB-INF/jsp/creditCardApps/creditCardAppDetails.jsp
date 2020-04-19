<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="creditcardapps">

	<h2>Credit Card Application Information</h2>

	<table class="table table-striped">
		<tr>
			<th>Id</th>
			<td><b><c:out value="${credit_card_app.id}" /></b></td>
		</tr>
		<tr>
			<th>Status</th>
			<td><c:out value="${credit_card_app.status}" /></td>
		</tr>
		<tr>
			<th>Client</th>
			<td><c:out value="${credit_card_app.client.user.username}" /></td>
		</tr>
		<tr>
			<th>Account Number</th>
			<td><c:out value="${credit_card_app.bankAccount.accountNumber}" /></td>
		</tr>
	</table>

	<c:choose>
		<c:when test="${credit_card_app.status == 'PENDING'}">
			<div class="buttons-group">
				<form method="get"
					action="/creditcardapps/${credit_card_app.id}/accept">
					<button class="btn btn-default">Accept Credit Card Application</button>
				</form>

				<form method="get"
					action="/creditcardapps/${credit_card_app.id}/refuse">
					<button class="btn btn-default">Refuse Credit Card Application</button>
				</form>
			</div>
		</c:when>
	</c:choose>

</petclinic:layout>