<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="clients">
	<jsp:attribute name="customScript">
        <script>
			$(function() {
				$("#lastEmployDate").datepicker({
					dateFormat : 'yy/mm/dd'
				});
				$("#birthDate").datepicker({
					dateFormat : 'yy/mm/dd'
				});
			});
		</script>
    </jsp:attribute>
	<jsp:body>
		<h2>
			<c:if test="${client['new']}">New </c:if>
			Client
		</h2>
		<form:form enctype="multipart/form-data" modelAttribute="client"
				class="form-horizontal" id="add-client-form">
			<div class="form-group has-feedback">
				<petclinic:inputField label="First Name" name="firstName" />
				<petclinic:inputField label="Last Name" name="lastName" />
				<petclinic:inputField label="Address" name="address" />
				<petclinic:inputField label="Birth Date" name="birthDate" />
				<petclinic:inputField label="City" name="city" />
				<petclinic:inputField label="Marital Status" name="maritalStatus" />
				<petclinic:inputField label="Salary Per Year" name="salaryPerYear" />
				<petclinic:inputField label="Age" name="age" />
				<petclinic:inputField label="Job" name="job" />
				<petclinic:inputField label="Last Employ Date" name="lastEmployDate" />
				<petclinic:inputField label="Username" name="user.username" />
				<petclinic:inputField label="Password" name="user.password" />
				<div class="dni-form">
					<div class="dni-form-and-label">
						<label class="col-sm-2 control-label">DNI: </label> <input
								type="file" name="dniFile" path="dniFile" id="dniFile" />
					</div>
					<form:errors path="dniFile" cssClass="dni-error" />
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<c:choose>
						<c:when test="${client['new']}">
							<button class="btn btn-default" type="submit">Add Client</button>
						</c:when>
						<c:otherwise>
							<button class="btn btn-default" type="submit">Update
								Client</button>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</form:form>
	</jsp:body>
</petclinic:layout>
