<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

<div>
	<canvas id="canvas1"></canvas>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.6.0/Chart.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0">
	$(document).ready(function(){
		var data = {
				labels: [
					<jstl:forEach var="label" items="${labels}">
					<jstl:out value="${label}" escapeXml="false"/>,
					</jstl:forEach>
				],
				datasets : [
					{
						label: "Money",
						data: [
							<jstl:forEach var = "money" items = "${moneyPerDays}">
								<jstl:out value="${money}" escapeXml="false"/>,
							</jstl:forEach>
						],backgroundColor: ["#FF8C00"]	
					}
				]		
		};
		
		var options = {
				scales : {
					yAxes : [
						{
							ticks : {
								suggestedMin : 0.0,
								suggestedMax : 10.0
							}
						}
					]
				},
				legend : {
					display : true
				}
		};
		
		var canvas, context;
		
		canvas = document.getElementById("canvas1");
		context = canvas.getContext("2d");
		new Chart(context, {
			type : "bar",
			data : data,
			options : options
		});
	});
</script>