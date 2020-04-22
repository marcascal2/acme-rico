<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ page import="java.util.Arrays"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="loans">
<c:if test="${!loan['new']}">
	<h2>Loan Information</h2>
	<table class="table table-striped">
		<tr>
			<th>Description</th>
			<td><b><c:out value="${loan.description}" /></b></td>
		</tr>
		<tr>
			<th>Minimum amount</th>
			<td><c:out value="${loan.minimum_amount}" /></td>
		</tr>
		<tr>
			<th>Minimum income</th>
			<td><c:out value="${loan.minimum_income}" /></td>
		</tr>
		<tr>
			<th>Number of deadlines</th>
			<td><c:out value="${loan.number_of_deadlines}" /></td>
		</tr>
		<tr>
			<th>Opening price</th>
			<td><c:out value="${loan.opening_price}" /></td>
		</tr>
		<tr>
			<th>Monthly fee</th>
			<td><c:out value="${loan.monthly_fee}" /></td>
		</tr>
		<tr>
			<th>Single loan</th>
			<td><c:out value="${loan.single_loan}" /></td>
		</tr>
	</table>

	<sec:authorize access="hasAuthority('client')">
		<c:choose>
			<c:when test="${clienSingleLoan}">

				<button class="btn btn-default"
					onclick="document.getElementById('demo').innerHTML = 'No puedes aplicar a este loan'">
					Apply for this loan</button>

				<p id="demo"></p>
			</c:when>
			<c:when test="${!clienSingleLoan}">

				<form action="/loanapps/${loan.id}/new/${bankAccountId}">
					<button class="btn btn-default" type="submit">Apply for
						this loan</button>
				</form>
			</c:when>
		</c:choose>
	</sec:authorize>
	</c:if>
	<c:if test="${loan['new']}">
		<form:form modelAttribute="loan" class="form-horizontal"
			id="add-loan-form">
			<div class="form-group has-feedback">
				<petclinic:inputField label="Description" name="description" />
				<petclinic:inputField label="Minimun Amount" name="minimum_amount" />
				<petclinic:inputField label="Minimun Client Income"
					name="minimum_income" />
				<petclinic:inputField label="Number of Deadlines"
					name="number_of_deadlines" />
				<petclinic:inputField label="Opening Price" name="opening_price" />
				<petclinic:inputField label="Monthly Fee" name="monthly_fee" />
				<petclinic:selectField label="Single Loan" name="single_loan"
					names='<%=Arrays.asList("Yes", "No")%>' size="1" />
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button class="btn btn-default" type="submit">Add Loan</button>
				</div>
			</div>
		</form:form>
	</c:if>
</petclinic:layout>
