<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

<petclinic:layout pageName="dashboard">

	<div>
		<canvas id="canvas1" width="750" height="400"></canvas>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			var data = {
				labels : [<jstl:forEach var="label" items="${labels}">
						       <jstl:out value="${label}" escapeXml="false"/>,
						  </jstl:forEach> ],
				datasets : [ {
					label : 'Money',
					data : [<jstl:forEach var = "money" items = "${moneyPerDays}">
								<jstl:out value="${money}" escapeXml="false"/>,
							</jstl:forEach> ],
					borderColor : [ '#FF8C00']
				} ]
			};

			var options = {
				scales : {
					yAxes : [ {
						ticks : {
							suggestedMin : 0.0,
							suggestedMax : 10.0
						}
					} ]
				},
				legend : {
					display : true
				}
			};

			var canvas, context;

			canvas = document.getElementById('canvas1');
			context = canvas.getContext('2d');
			new Chart(context, {
				type : 'line',
				data : data,
				options : options
			});
		});
	</script>
</petclinic:layout>