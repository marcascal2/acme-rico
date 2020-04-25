<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="loans">
	<h2>Loans</h2>

	<table id="loans" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;">Description</th>
				<th style="width: 150px;">Minimum Amount</th>
				<th style="width: 150px;">Minimum Client Income</th>
				<th style="width: 200px;">Number of Deadlines</th>
				<th style="width: 200px;">Opening Price</th>
				<th style="width: 200px;">Monthly Fee</th>
				<th style="width: 200px;">Single Loan</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${loans}" var="loan">
				<tr>
					<td><spring:url value="/grantedLoans/{loanId}" var="loanUrl">
							<spring:param name="loanId" value="${loan.id}" />
						</spring:url> <a href="${fn:escapeXml(loanUrl)}"><c:out value="${loan.description}" /></a>
					</td>
					
					<td><c:out value="${loan.minimum_amount}" /></td>
					<td><c:out value="${loan.minimum_income}" /></td>
					<td><c:out value="${loan.number_of_deadlines}" /></td>
					<td><c:out value="${loan.opening_price}" /></td>
					<td><c:out value="${loan.monthly_fee}" /></td>
					<td><c:choose>
							<c:when test="${loan.single_loan}">
								<c:out value="Yes" />
							</c:when>
							<c:when test="${!loan.single_loan}">
								<c:out value="No" />
							</c:when>
						</c:choose></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="buttons-group">
		<form action="/grantedLoans/new">
			<button class="btn btn-default" type="submit">Create new
				loan</button>
		</form>
	</div>
</petclinic:layout>