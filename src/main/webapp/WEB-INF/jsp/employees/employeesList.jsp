<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="employees">
    <h2>Employees</h2>

    <table id="clientsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Name</th>
            <th style="width: 200px;">Address</th>
            <th style="width: 120px">Salary</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${selections}" var="employee">
            <tr>
                <td>
                    <spring:url value="/employees/{employeeId}" var="employeeUrl">
                        <spring:param name="employeeId" value="${employee.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(employeeUrl)}"><c:out value="${employee.firstName} ${employee.lastName}"/></a>
                </td>
                <td>
                    <c:out value="${employee.salary}"/>
                </td>
                
<!--
                <td> 
                    <c:out value="${owner.user.username}"/> 
                </td>
                <td> 
                   <c:out value="${owner.user.password}"/> 
                </td> 
-->
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
