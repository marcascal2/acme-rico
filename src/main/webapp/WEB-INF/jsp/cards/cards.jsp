<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="cards">
    <h2>My Credit Cards</h2>

    <table id="cardsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 50%;">Card Number</th>
            <th style="width: 50%;">Deadline</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${cards}" var="card">
            <tr>
                <td>
                    <spring:url value="/cards/{cardId}/show" var="cardUrl">
                        <spring:param name="cardId" value="${card.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(cardUrl)}"><c:out value="${card.number}"/></a>
                </td>
                <td>                
                    <c:out value="${card.deadline}"/>
                </td>  
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <%-- <form method="get" action="/cards/${clientId}/new">
     	<button class="btn btn-default">Add new credit card</button>
	</form> --%>
</petclinic:layout>