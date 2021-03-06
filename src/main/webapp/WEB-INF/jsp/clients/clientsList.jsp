<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="clients">
	<h2>Clients</h2>

	<table id="clientsTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;">Name</th>
				<th style="width: 150px;">Username</th>
				<th style="width: 120px">City</th>
				<th style="width: 120px">Age</th>
				<th style="width: 120px">Job</th>
				<th style="width: 120px">Last Employ Date</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${selections}" var="client">
				<tr>
					<td><spring:url value="/clients/{clientId}" var="clientUrl">
							<spring:param name="clientId" value="${client.id}" />
						</spring:url> <a href="${fn:escapeXml(clientUrl)}"><c:out
								value="${client.firstName} ${client.lastName}" /></a></td>
					<td><c:out value="${client.user.username}" /></td>
					<td><c:out value="${client.city}" /></td>
					<td><c:out value="${client.age}" /></td>
					<td><c:out value="${client.job}" /></td>
					<td><c:out value="${client.lastEmployDate}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>
