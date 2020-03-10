<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="transfers">
	<jsp:body>
        <h2>Transfer</h2>
		<!-- Por el model attribute le pasamos el objeto de la vista -->
        <form:form modelAttribute="transfer" class="form-horizontal" action="/transfers/save">
            <div class="form-group has-feedback">
            <!-- Este account number hay que traerselo a la hora de crear la transferencia por el cliente -->
             <petclinic:inputField label="Account Number:"
					name="accountNumber" />
                <petclinic:inputField label="Amount:"
					name="amount" />
                <petclinic:inputField label="Destination Account:"
					name="destination" />
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id"
						value="${transfer.id}" />
                    <button class="btn btn-default" type="submit">Save Transfer</button>
                </div>
            </div>
        </form:form>

    </jsp:body>

</petclinic:layout>