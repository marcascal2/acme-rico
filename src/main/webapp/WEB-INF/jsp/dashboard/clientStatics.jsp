<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

<petclinic:layout pageName="dashboard">
<body>
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

</body>
</petclinic:layout>