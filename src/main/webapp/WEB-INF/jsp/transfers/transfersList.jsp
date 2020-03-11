<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="transfers">
	<h2>All tranfers</h2>

	<table id="transferTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;">Id</th>
				<th style="width: 150px;">Account Number</th>
				<th style="width: 150px;">Amount</th>
				<th style="width: 200px;">Destination</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${transfers}" var="transfer">
				<tr>
					<td><c:out value="${transfer.id}" /></td>
					<td><c:out value="${transfer.accountNumber}" /></td>
					<td><c:out value="${transfer.amount}" /></td>
					<td><c:out value="${transfer.destination}" /></td>

				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>