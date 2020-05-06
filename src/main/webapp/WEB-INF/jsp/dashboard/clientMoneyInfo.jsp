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
				labels : [<jstl:forEach var = "month" items = "${months}">
						 	  <jstl:out value="'${month}'" escapeXml="false"/>,
						  </jstl:forEach>],
				datasets : [ {
					label : 'Amounts to pay in next five motnhs',
					data : [<jstl:forEach var = "amountToPay" items = "${amountsToPay}">
								<jstl:out value="${amountToPay}" escapeXml="false"/>,
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
	
	<br>
	<br>
	<br>
	<br>
	<br>
	
	<div>
		<canvas id="canvas2" width="750" height="400"></canvas>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			var data = {
				labels : ['Accepted', 'Rejected', 'Pending'],
				datasets : [ {
					label : 'My Loan Applications Status',
					data : [<jstl:forEach var = "status" items = "${loanAppsStatus}">
								<jstl:out value="${status}" escapeXml="false"/>,
							</jstl:forEach> ],
					backgroundColor : ['#12A401', '#12A401', '#12A401']
				},{
					label : 'My CreditCard Applications Status',
					data : [<jstl:forEach var = "status" items = "${creditCardAppsStatus}">
								<jstl:out value="${status}" escapeXml="false"/>,
							</jstl:forEach> ],
					backgroundColor : ['#014EA4', '#014EA4', '#014EA4']
				},{
					label : 'My Transfer Applications Status',
					data : [<jstl:forEach var = "status" items = "${transferAppsStatus}">
								<jstl:out value="${status}" escapeXml="false"/>,
							</jstl:forEach> ],
					backgroundColor : ['#EE6F06', '#EE6F06', '#EE6F06']
				}
				]
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

			canvas = document.getElementById('canvas2');
			context = canvas.getContext('2d');
			new Chart(context, {
				type : 'bar',
				data : data,
				options : options
			});
		});
	</script>
	
</petclinic:layout>