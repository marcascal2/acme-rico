<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

<petclinic:layout pageName="dashboard">

	<div class="charts-container">
		<div class="chart-container"> 
			<canvas id="canvas1"></canvas>
		</div>
		<div class="chart-container">
			<canvas id="canvas2" ></canvas>
		</div>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			var container = document.querySelector('.charts-container');
			
			var data = [{
				labels : ['Accepted', 'Rejected', 'Pending'],
				datasets : [ {
					label : 'My Loan Applications Status',
					data : [<jstl:forEach var = "status" items = "${loanAppsStatus}">
								<jstl:out value="${status}" escapeXml="false"/>,
							</jstl:forEach> ],
					backgroundColor : ['#8506EE', '#8506EE', '#8506EE']
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
			},{
				labels : [<jstl:forEach var = "month" items = "${months}">
			 	  <jstl:out value="'${month}'" escapeXml="false"/>,
						  </jstl:forEach>],
				datasets : [ {
					label : 'Amounts to pay in next five motnhs',
					data : [<jstl:forEach var = "amountToPay" items = "${amountsToPay}">
								<jstl:out value="${amountToPay}" escapeXml="false"/>,
							</jstl:forEach> ],
					borderColor : [ '#EE0606']
				} ]
			}];

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
			
			var i = 0;
			
			data.forEach(function(data) {
				if (i == 1){
					typeChart = 'line';
				} else {
					typeChart = 'bar';
				}
				
				i++;
					
				var div = document.createElement('div');
				div.classList.add('chart-container');
	
				var canvas = document.createElement('canvas');
				div.appendChild(canvas);
				container.appendChild(div);
	
				var ctx = canvas.getContext('2d');
				new Chart(ctx, {
					type : typeChart,
					data : data,
					options : options
				});
			});
		});
	</script>
	
</petclinic:layout>