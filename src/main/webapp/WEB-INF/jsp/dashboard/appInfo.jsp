<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

<div>
	<canvas id="canvas1"></canvas>
</div>

<script type="text/javascript">
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
			type : "line",
			data : data,
			options : options
		});
	});
</script>
