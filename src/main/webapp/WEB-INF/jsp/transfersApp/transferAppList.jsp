<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="transfersApp">
	<h2>Transfer Applications</h2>

	<table id="transfersAppTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;">Id</th>
				<th style="width: 150px;">User</th>
				<th style="width: 150px;">Status</th>
				<th style="width: 150px;">Amount</th>
				<th style="width: 200px;">Account Number</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${transfers_app}" var="transfer_app">
				<tr>
					<td><spring:url value="/transferapps/{transferappsId}"
							var="transferAppUrl">
							<spring:param name="transferappsId" value="${transfer_app.id}" />
						</spring:url> <a href="${fn:escapeXml(transferAppUrl)}"><c:out
								value="${transfer_app.id}" /></a></td>
					<td><c:out value="${transfer_app.client.user.username}" /></td>
					<td><c:out value="${transfer_app.status}" /></td>
					<td><c:out value="${transfer_app.amount}" /></td>
					<td><c:out value="${transfer_app.account_number_destination}" /></td>

				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>