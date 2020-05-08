<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="employees">

	<h2>Employee Information</h2>


	<table class="table table-striped">
		<tr>
			<th>Name</th>
			<td><b><c:out
						value="${employee.firstName} ${employee.lastName}" /></b></td>
		</tr>
		<tr>
			<th>Salary</th>
			<td><c:out value="${employee.salary}" /></td>
		</tr>
	</table>

	<div class="buttons-group">
		<spring:url value="{employeeId}/edit" var="editUrl">
			<spring:param name="employeeId" value="${employee.id}" />
		</spring:url>
		<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit
			Worker</a>

		<c:if test="${!personalData}">
			<spring:url value="{employeeId}/delete" var="deleteUrl">
				<spring:param name="employeeId" value="${employee.id}" />
			</spring:url>
			<a id="delete-worker" href="${fn:escapeXml(deleteUrl)}" class="btn btn-default">Delete
				Worker</a>
		</c:if>
	</div>

</petclinic:layout>