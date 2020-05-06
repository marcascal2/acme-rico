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
				href="<spring:url value="/" htmlEscape="true" />"> <img
				src="/resources/images/logo-negativo.png" alt="logo">
			</a>
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
					<li class="nav-item dropdown" id="dropdown-clients"><a
						class="nav-link dropdown-toggle" role="button"
						data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							<span class="glyphicon glyphicon-euro" aria-hidden="true"></span>
							Bank Accounts
					</a>
						<div class="dropdown-menu" aria-labelledby="navbarDropdown">
							<petclinic:menuSubitem active="${name eq 'clients'}"
								url="/accounts" title="my accounts">
								<span id="my-accounts">My accounts</span>
							</petclinic:menuSubitem>
							<div class="dropdown-divider"></div>
							<petclinic:menuSubitem active="${name eq 'clients'}" url="/cards"
								title="my cards">
								<span id="my-credit-card">My credit cards</span>
							</petclinic:menuSubitem>
						</div>
					<li class="nav-item dropdown" id="dropdown-clients-apps"><a
						class="nav-link dropdown-toggle" role="button"
						data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							<span class="glyphicon glyphicon-euro" aria-hidden="true"></span>
							My Applications
					</a>
						<div class="dropdown-menu" aria-labelledby="navbarDropdown">
							<petclinic:menuSubitem active="${name eq 'clients'}"
								url="/mycreditcardapps" title="my cardApps">
								<span id="credit-card-apps">My credit card applications</span>
							</petclinic:menuSubitem>
							<div class="dropdown-divider"></div>
							<petclinic:menuSubitem active="${name eq 'clients'}"
								url="/myloanapps" title="my loanApps">
								<span id="loan-apps">My loan applications</span>
							</petclinic:menuSubitem>
						</div> <petclinic:menuItem active="${name eq 'dashboards'}"
							url="/dashboard" title="find dashboard">
							<span class="glyphicon glyphicon-stats" aria-hidden="true"></span>
							<span id="dashboards">Dashboard</span>
						</petclinic:menuItem>
				</sec:authorize>

				<!-- MENU WORKER -->
				<sec:authorize access="hasAuthority('worker')">
					<petclinic:menuItem active="${name eq 'clients'}"
						url="/clients/find" title="find clients">
						<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
						<span>Clients</span>
					</petclinic:menuItem>
					<li class="nav-item dropdown" id="clients-requests"><a
						class="nav-link dropdown-toggle" id="dropdown-workers"
						role="button" data-toggle="dropdown" aria-haspopup="true"
						aria-expanded="false"> <span class="glyphicon glyphicon-send"
							aria-hidden="true"></span> Clients requests
					</a>
						<div class="dropdown-menu" aria-labelledby="navbarDropdown">
							<petclinic:menuSubitem active="${name eq 'creditcardapps'}"
								url="/creditcardapps" title="creditCardAppsList">
								<span id="card-apps">Credit Card Applications</span>
							</petclinic:menuSubitem>
							<div class="dropdown-divider"></div>
							<petclinic:menuSubitem active="${name eq 'transferapps'}"
								url="/transferapps" title="transferappsList">
								<span id="transfer-apps">Transfer Applications</span>
							</petclinic:menuSubitem>
							<div class="dropdown-divider"></div>
							<petclinic:menuSubitem active="${name eq 'loan apps'}"
								url="/loanapps" title="loan applications">
								<span id="loan-applications">Loan applications</span>
							</petclinic:menuSubitem>
						</div></li>
					<petclinic:menuItem active="${name eq 'debts'}"
						url="/debts/pending" title="find debts">
						<span class="glyphicon glyphicon glyphicon-book"
							aria-hidden="true"></span>
						<span id="debts">Debts</span>
					</petclinic:menuItem>
				</sec:authorize>

				<!-- MENU DIRECTOR -->
				<sec:authorize access="hasAuthority('director')">
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" id="navbarDropdown" role="button"
						data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							<span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
							Manage users
					</a>
						<div class="dropdown-menu" aria-labelledby="navbarDropdown">
							<petclinic:menuSubitem active="${name eq 'director'}"
								url="/employees/find" title="find employees">
								<span>Find employees</span>
							</petclinic:menuSubitem>
							<div class="dropdown-divider"></div>
							<petclinic:menuSubitem active="${name eq 'clients'}"
								url="/clients/find" title="find clients">
								<span>Find clients</span>
							</petclinic:menuSubitem>
						</div></li>
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" id="dropdown-director"
						role="button" data-toggle="dropdown" aria-haspopup="true"
						aria-expanded="false"> <span class="glyphicon glyphicon-send"
							aria-hidden="true"></span> Clients requests
					</a>
						<div class="dropdown-menu" aria-labelledby="navbarDropdown">
							<petclinic:menuSubitem active="${name eq 'creditcardapps'}"
								url="/creditcardapps" title="creditCardAppsList">
								<span>Credit Card Applications</span>
							</petclinic:menuSubitem>
							<div class="dropdown-divider"></div>
							<petclinic:menuSubitem active="${name eq 'loan apps'}"
								url="/loanapps" title="my loan applications">
								<span id="loan-applications">Loan Applications</span>
							</petclinic:menuSubitem>
							<div class="dropdown-divider"></div>
							<petclinic:menuSubitem active="${name eq 'transferapps'}"
								url="/transferapps" title="transferappsList">
								<span id="tranfer-app">Transfer Applications</span>
							</petclinic:menuSubitem>
						</div></li>
					<li class="nav-item dropdown" id="loans-dropdown"><a
						class="nav-link dropdown-toggle" id="navbarDropdown" role="button"
						data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							<span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>
							Loans
					</a>
						<div class="dropdown-menu" aria-labelledby="navbarDropdown">
							<petclinic:menuSubitem active="${name eq 'loans'}"
								url="/grantedLoans" title="loansList">
								<span id="see-loans">Loans list</span>
							</petclinic:menuSubitem>
						</div></li>

					<petclinic:menuItem active="${name eq 'debts'}"
						url="/debts/pending" title="find debts">
						<span class="glyphicon glyphicon glyphicon-book"
							aria-hidden="true"></span>
						<span id="debts">Debts</span>
					</petclinic:menuItem>
				</sec:authorize>
			</ul>

			<!-- MENU DERECHO -->
			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="!isAuthenticated()">
					<li><a href="<c:url value="/login" />" id="login-button">Login</a></li>
					<li><a href="<c:url value="/users/new"/>" id="register-button">Register</a></li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span>
							<strong id="username"><sec:authentication
									property="name" /></strong> <span
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
