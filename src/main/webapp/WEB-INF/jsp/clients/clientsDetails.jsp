<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="clients">

    <h2>Client Information</h2>


    <table class="table table-striped">
        <tr>
            <th>Name</th>
            <td><b><c:out value="${client.firstName} ${client.lastName}"/></b></td>
        </tr>
        <tr>
            <th>Address</th>
            <td><c:out value="${client.address}"/></td>
        </tr>
        <tr>
            <th>Birth Date</th>
            <td><c:out value="${client.birthDate}"/></td>
        </tr>
        <tr>
            <th>City</th>
            <td><c:out value="${client.city}"/></td>
        </tr>
    </table>

    <spring:url value="{clientId}/edit" var="editUrl">
        <spring:param name="clientId" value="${client.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit Client</a>

</petclinic:layout>