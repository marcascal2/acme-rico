<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="accounts">
	<h2>Money income</h2>
	<form:form modelAttribute="bankAccount" class="form-horizontal"
		id="add-client-form">
		<div class="form-group has-feedback">
			<petclinic:inputField label="Account Number" name="accountNumber"
				readonly="true" />
			<div class="form-group ">
				<label class="col-sm-2 control-label">Amount to enter</label>
				<div class="col-sm-10">
					<form:input class="form-control" label="Amount to enter"
						name="amount" path="amount" value="0.0" />
				</div>
			</div>
		</div>
		<div class="form-group">
			<button class="btn btn-default" type="submit">Make deposit</button>
		</div>
	</form:form>
</petclinic:layout>