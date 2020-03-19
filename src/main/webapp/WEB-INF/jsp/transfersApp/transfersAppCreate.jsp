<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="transferApps">
<jsp:body>
	<h2>Transfer Application</h2>

	<form:form modelAttribute="transfer_app" class="form-horizontal"
		id="add-transferApp-form" >
		<div class="form-group has-feedback">
			<petclinic:inputField label="Amount" name="amount" />
			<petclinic:inputField label="Destination Account" name="account_number_destination" />
			<petclinic:inputField label="Status" name="status" readonly = "true"/>
		</div>
		
		<form:errors path="*" cssClass="errorblock" element="div" />
		
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
						<button class="btn btn-default" type="submit">Add Transfer</button>
			</div>
		</div>
		
	</form:form>
	
	
  </jsp:body>
</petclinic:layout>
