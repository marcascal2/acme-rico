<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="loanapps">

	<h2>Loan Application Information</h2>

	<table class="table table-striped">
		<tr>
			<th>Loan description</th>
			<td><b><c:out value="${loan_app.loan.description}" /></b></td>
		</tr>
		<tr>
			<th>Amount</th>
			<td><c:out value="${loan_app.amount}" /></td>
		</tr>
		<tr>
			<th>Purpose</th>
			<td><c:out value="${loan_app.purpose}" /></td>
		</tr>
		<tr>
			<th>Status</th>
			<td><c:out value="${loan_app.status}" /></td>
		</tr>
		<tr>
			<th>Amount paid</th>
			<td><c:out value="${loan_app.amount_paid}" /></td>
		</tr>
		<tr>
			<th>Bank account</th>
			<td><c:out value="${loan_app.bankAccount.accountNumber}" /></td>
		</tr>
		<tr>
			<th>Client</th>
			<td><c:out value="${loan_app.client.user.username}" /></td>
		</tr>
		<tr>
			<th>Is paid</th>
			<td><c:choose>
					<c:when test="${isPaid}">
						<c:out value="Yes" />
					</c:when>
					<c:when test="${!isPaid}">
						<c:out value="No" />
					</c:when>
				</c:choose></td>
		</tr>
	</table>

	<sec:authorize
		access="hasAuthority('director') || hasAuthority('worker')">
		<c:choose>
			<c:when test="${loan_app.status == 'PENDING'}">
				<div class="buttons-group">
					<form method="get" action="/loanapps/${loan_app.id}/accept">
						<button class="btn btn-default">Accept Loan Application</button>
					</form>

					<form method="get" action="/loanapps/${loan_app.id}/refuse">
						<button class="btn btn-default">Refuse Loan Application</button>
					</form>
				</div>
			</c:when>
		</c:choose>
	</sec:authorize>
</petclinic:layout>