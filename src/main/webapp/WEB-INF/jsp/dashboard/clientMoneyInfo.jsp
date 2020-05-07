<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

<petclinic:layout pageName="dashboard">

	<div class="charts-container"></div>

	<script type="text/javascript">
		$(document).ready(function() {
			function getRandomColor() {
				var letters = '0123456789ABCDEF';
				var color = '#';
				for (var i = 0; i < 6; i++) {
					color += letters[Math.floor(Math.random() * 16)];
				}
				return color;
			}
			
			var container = document.querySelector('.charts-container');
			var presets = window.chartColors;
			var utils = Samples.utils;
			
			var data = [{
				labels : ['Accepted', 'Rejected', 'Pending'],
				datasets : [ {
					label : 'My Loan Applications Status',
					data : [<jstl:forEach var = "status" items = "${loanAppsStatus}">
								<jstl:out value="${status}" escapeXml="false"/>,
							</jstl:forEach> ],
					backgroundColor : [presets.yellow, presets.yellow, presets.yellow]
				},{
					label : 'My CreditCard Applications Status',
					data : [<jstl:forEach var = "status" items = "${creditCardAppsStatus}">
								<jstl:out value="${status}" escapeXml="false"/>,
							</jstl:forEach> ],
					backgroundColor : [presets.green, presets.green, presets.green]
				},{
					label : 'My Transfer Applications Status',
					data : [<jstl:forEach var = "status" items = "${transferAppsStatus}">
								<jstl:out value="${status}" escapeXml="false"/>,
							</jstl:forEach> ],
					backgroundColor : [presets.orange, presets.orange, presets.orange]
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
					backgroundColor : utils.transparentize(presets.red),
					borderColor : ['#EE0606']
				} ]
			},{
				labels : ['Money To Debt', 'My Money'],
				datasets : [ {
					label : 'Money To debt',
					data : [${moneyToDebt}, ${moneyInBankAccounts}],
					backgroundColor : [presets.red, presets.blue]
				} ]
			},{
				labels : [<jstl:forEach var = "alia" items = "${alias}">
				 	  		  <jstl:out value="'${alia}'" escapeXml="false"/>,
					  	  </jstl:forEach>],
				datasets : [ {
					label : 'My money in my bank accounts',
					data : [<jstl:forEach var = "bankAccountAmount" items = "${bankAccountAmounts}">
								<jstl:out value="${bankAccountAmount}" escapeXml="false"/>,
							</jstl:forEach>],
					backgroundColor : [<jstl:forEach var = "bankAccountAmount" items = "${bankAccountAmounts}">
										  <jstl:out value="getRandomColor()" escapeXml="false"/>,
									  </jstl:forEach>]
				} ]
			}];

			var options = {
				elements : {
					line : {
						tension : 0.000001
					}
				},
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
				if(i == 3){
					typeChart = 'pie';
				}else if(i == 2){
					typeChart = 'pie';
				} else if (i == 1){
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