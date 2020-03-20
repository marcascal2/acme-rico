<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="accounts">
    <h2>My Accounts</h2>

    <table id="accountsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 50%;">Account Number</th>
            <th style="width: 50%;">Amount</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${accounts}" var="account">
            <tr>
                <td>
                    <spring:url value="/accounts/{accountId}" var="accountUrl">
                        <spring:param name="accountId" value="${account.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(accountUrl)}"><c:out value="${account.accountNumber}"/></a>
                </td>
                <td>                
                    <c:out value="${account.amount}"/>
                </td>  
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <form method="get" action="/accounts/${clientId}/new">
     	<button class="btn btn-default">Add new bank account</button>
    </form>
    <form method="get" action="/transferapps_mine/${clientId}">
        <button class="btn btn-default">My transfer application list</button>
   </form>
</petclinic:layout>