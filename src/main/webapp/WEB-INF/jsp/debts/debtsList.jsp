<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="debts">
    <h2>Pending debts</h2>

    <table id="debtsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 25%;">User</th>
            <th style="width: 25%;">Refresh date</th>
            <th style="width: 25%;">Loan application</th>
            <th style="width: 25%">Amount</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${debts}" var="debt">
            <tr>
                <td>
                    <spring:url value="/clients/{clientId}" var="clientUrl">
                        <spring:param name="clientId" value="${debt.client.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(clientUrl)}"><c:out value="${debt.client.firstName} ${debt.client.lastName}"/></a>
                </td>
                <td>
                    <c:out value="${debt.refreshDate}"/>
                </td>
                <td>
                    <c:out value="${debt.loanApplication.purpose}"/>
                </td>
                <td>
                    <c:out value="${debt.amount}"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>