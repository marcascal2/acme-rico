<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="loanApps">
	<h2>Loans</h2>

	<table id="loanApps" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;">Id</th>
				<th style="width: 150px;">Amount</th>
				<th style="width: 150px;">Purpose</th>
				<th style="width: 150px;">Status</th>
				<th style="width: 150px;">Amount Paid</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${loanApps}" var="loanApp">
				<tr>
					<td><c:out value="${loanApp.id}" /></td>
					<td><c:out value="${loanApp.amount}" /></td>
					<td><c:out value="${loanApp.purpose}" /></td>
					<td><c:out value="${loanApp.status}" /></td>
					<td><c:out value="${loanApp.amount_paid}" /></td>
					
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
</petclinic:layout>