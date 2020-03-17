<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="creditCardsApp">
	<h2>Credit Card Applications</h2>

	<table id="creditCardsAppTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;">Id</th>
				<th style="width: 150px;">Status</th>
				<th style="width: 150px;">Client</th>
				<th style="width: 200px;">Account Number</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${credit_cards_app}" var="credit_card_app">
				<tr>
					<td>
						<spring:url value="/creditcardapps/{creditCardAppId}" var="creditCardAppUrl">
							<spring:param name="creditCardAppId" value="${credit_card_app.id}" />
						</spring:url> 
						<a href="${fn:escapeXml(creditCardAppUrl)}"><c:out value="${credit_card_app.id}"/></a>
					</td>
					<td><c:out value="${credit_card_app.status}" /></td>
					<td><c:out value="${credit_card_app.client.user.username}" /></td>
					<td><c:out value="${credit_card_app.bankAccount.accountNumber}" /></td>

				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>