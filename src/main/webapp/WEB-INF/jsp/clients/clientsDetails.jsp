<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<petclinic:layout pageName="clients">

	<h2>Client Information</h2>


	<table class="table table-striped">
		<tr>
			<th>Name</th>
			<td><b><c:out value="${client.firstName} ${client.lastName}" /></b></td>
		</tr>
		<tr>
			<th>Address</th>
			<td><c:out value="${client.address}" /></td>
		</tr>
		<tr>
			<th>Birth Date</th>
			<td><c:out value="${client.birthDate}" /></td>
		</tr>
		<tr>
			<th>City</th>
			<td><c:out value="${client.city}" /></td>
		</tr>
		<tr>
			<th>Marital Status</th>
			<td><c:out value="${client.maritalStatus}" /></td>
		</tr>
		<tr>
			<th>Salary Per Year</th>
			<td><c:out value="${client.salaryPerYear}" /></td>
		</tr>
		<tr>
			<th>Age</th>
			<td><c:out value="${client.age}" /></td>
		</tr>
		<tr>
			<th>Job</th>
			<td><c:out value="${client.job}" /></td>
		</tr>
		<tr>
			<th>Last Employ Date</th>
			<td><c:out value="${client.lastEmployDate}" /></td>
		</tr>
	</table>
	
	<spring:url value="{clientId}/edit" var="editUrl">
		<spring:param name="clientId" value="${client.id}" />
	</spring:url>
	<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit
		Client</a>
	
		</br>
		</br>
		
	<h2>Bank Customer Information</h2>
	<table class="table table-striped">
		<tr>
			<th>Number of Bank Accounts</th>
			<td><c:out value="${countAccounts}"/></td>
		</tr>
		<tr>
			<th>Total Amount Borrowed</th>
			<td><c:out value="${borrowedAmount}" /></td>
		</tr>
		
		<tr>
			<th>Total Amount in Bank</th>
			<td><c:out value="${totalAmount}"/></td>
		</tr>
		
	</table>
	
	<h3>Loan Applications Information</h3>
	 <table id="loanAppsForClientTable" class="table table-striped">
        <thead>
        <tr>
        	<th style="width: 150px;">Purpose of the loan</th>
            <th style="width: 150px;">Requested amount</th>
            <th style="width: 150px;">Ammount paid</th>
        </tr>
        </thead>
        <c:forEach items="${clientLoans}" var="loanApps">
            <tr>
                <td>
<%--                     <spring:url value="/clients/{clientId}" var="clientUrl"> --%>
<%--                         <spring:param name="clientId" value="${client.id}"/> --%>
<%--                     </spring:url> --%>
<%--                     <a href="${fn:escapeXml(clientUrl)}"> --%>
                    <c:out value="${loanApps.purpose}"/></a>
                </td>
                <td>
                    <c:out value="${loanApps.amount}"/>
                </td>
                <td>
                    <c:out value="${loanApps.amount_paid}"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
	
</petclinic:layout>