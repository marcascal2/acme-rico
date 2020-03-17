<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="cardApps">
    <h2>My Credit Cards Applications</h2>

    <table id="cardAppsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 50%;">Status</th>
            <th style="width: 50%;">Username</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${cardApps}" var="cardApp">
            <tr>
                <td>
                    <!-- <spring:url value="/cards/{cardId}/show" var="cardUrl">
                        <spring:param name="cardId" value="${card.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(cardUrl)}"><c:out value="${card.number}"/></a>-->
                    <c:out value="${cardApp.status}"/>
                </td>
                <td>
                	<c:out value="${clientUser}"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>