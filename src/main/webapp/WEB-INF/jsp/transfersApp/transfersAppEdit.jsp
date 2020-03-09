<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="transferApps">
	<h2>
		Transfer Application
	</h2>
	<form:form modelAttribute="transfer_app" class="form-horizontal"
		id="add-transferapp-form">
		<div class="form-group has-feedback">
			<petclinic:inputField label="Id" name="id" readonly="true" />
			<petclinic:inputField label="Status" name="status" />
			<petclinic:inputField label="Amount" name="amount" readonly="true" />
			<petclinic:inputField label="Account Number" name="account_number"
				readonly="true" />

		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button class="btn btn-default" type="submit">Update
					Transfer Application</button>
			</div>
		</div>
	</form:form>
</petclinic:layout>