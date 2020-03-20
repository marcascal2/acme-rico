<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu: home, owners, vets or error"%>

<nav class="navbar navbar-default" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand"
				href="<spring:url value="/" htmlEscape="true" />"><span></span></a>
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#main-navbar">
				<span class="sr-only"><os-p>Toggle navigation</os-p></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
		<div class="navbar-collapse collapse" id="main-navbar">
			<ul class="nav navbar-nav">

				<petclinic:menuItem active="${name eq 'home'}" url="/"
					title="home page">
					<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
					<span>Home</span>
				</petclinic:menuItem>
				
		<!-- MENU CLIENTES -->
		<sec:authorize access="hasAuthority('client')">
			<petclinic:menuItem active="${name eq 'clients'}" url="/accounts"
				title="my accounts">
				<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
				<span>My accounts</span>
			</petclinic:menuItem>
		</sec:authorize>
        
        <sec:authorize access="hasAuthority('client')">
			<petclinic:menuItem active="${name eq 'clients'}" url="/mycreditcardapps"
				title="my cardApps">
				<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
				<span>My Cards applications</span>
			</petclinic:menuItem>
		</sec:authorize>
		
		<sec:authorize access="hasAuthority('client')">
			<petclinic:menuItem active="${name eq 'clients'}" url="/cards"
				title="my cards">
				<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
				<span>My cards</span>
			</petclinic:menuItem>
		</sec:authorize>
        
        
        <!-- MENU DIRECTOR COMPARTIDO CON WORKER -->
		  <sec:authorize access="hasAuthority('director') || hasAuthority('worker')">
	          <petclinic:menuItem active="${name eq 'clients'}" url="/clients/find" title="find clients">
		          <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
		          <span>Find clients</span>
	          </petclinic:menuItem>
	          
	          <petclinic:menuItem active="${name eq 'creditcardapps'}" url="/creditcardapps" title="creditCardAppsList">
		          <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
		          <span>Credit Card Applications List</span>
	          </petclinic:menuItem>
		  </sec:authorize>
				
        <!-- MENU DIRECTOR -->
				<sec:authorize access="hasAuthority('director')">
					<petclinic:menuItem active="${name eq 'director'}"
						url="/employees/find" title="find employees">
						<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
						<span>Find employees</span>
					</petclinic:menuItem>
				</sec:authorize>

				<petclinic:menuItem active="${name eq 'error'}" url="/oups"
					title="trigger a RuntimeException to see how it is handled">
					<span class="glyphicon glyphicon-warning-sign" aria-hidden="true"></span>
					<span>Error</span>
				</petclinic:menuItem>

			</ul>

		<!-- MENÚ DERECHO -->
			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="!isAuthenticated()">
					<li><a href="<c:url value="/login" />">Login</a></li>
					<li><a href="<c:url value="/users/new" />">Register</a></li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span>
							<strong><sec:authentication property="name" /></strong> <span
							class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu">
							<li>
								<div class="navbar-login">
									<div class="row" id="info-content">
										<div class="col-lg-4" id="info-icon">
											<p class="text-center">
												<span class="glyphicon glyphicon-user icon-size"></span>
											</p>
										</div>
										<div class="col-lg-8" id="info-name">
											<p class="text-left">
												<strong><sec:authentication property="name" /></strong>
											</p>
										</div>
										<div id="info-funct">
											<sec:authorize access="hasAuthority('client')">
												<p class="text-left">
													<a
														href="/personalData/<sec:authentication property="name" />"
														class="btn btn-primary btn-block btn-sm"><c:out
															value="Personal Information" /></a>
												</p>
											</sec:authorize>
											<sec:authorize access="!hasAuthority('client')">
												<p class="text-left">
													<a
														href="/personalDataEmployee/<sec:authentication property="name" />"
														class="btn btn-primary btn-block btn-sm"><c:out
															value="Employee Information" /></a>
												</p>
											</sec:authorize>

											<p class="text-left">
												<a href="<c:url value="/logout" />"
													class="btn btn-primary btn-block btn-sm">Logout</a>
											</p>
										</div>
									</div>
								</div>
							</li>
							<li class="divider"></li>
							<!-- 							
                            <li> 
								<div class="navbar-login navbar-login-session">
									<div class="row">
										<div class="col-lg-12">
											<p>
												<a href="#" class="btn btn-primary btn-block">My Profile</a>
												<a href="#" class="btn btn-danger btn-block">Change
													Password</a>
											</p>
										</div>
									</div>
								</div>
							</li>
-->
						</ul></li>
				</sec:authorize>
			</ul>
		</div>



	</div>
</nav>
