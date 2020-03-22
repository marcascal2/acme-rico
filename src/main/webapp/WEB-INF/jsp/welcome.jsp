<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<petclinic:layout pageName="home">
			<spring:url value="/resources/images/slider1.png" htmlEscape="true" var="slider1"/>
			<spring:url value="/resources/images/slider2.png" htmlEscape="true" var="slider2"/>
			<spring:url value="/resources/images/slider3.png" htmlEscape="true" var="slider3"/>
    <div class="row">
        <div class="col-md-12">
              <div id="myCarousel" class="carousel slide" data-ride="carousel">
				  <!-- Indicators -->
				  <ol class="carousel-indicators">
				    <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
				    <li data-target="#myCarousel" data-slide-to="1"></li>
				    <li data-target="#myCarousel" data-slide-to="2"></li>
				  </ol>
				
				  <!-- Wrapper for slides -->
				  <div class="carousel-inner">
				    <div class="item active">
				      <img src="${slider1}" alt="Chania">
				      <div class="carousel-caption">
				        <h3>AcmeRico</h3>
				        <p>Esay, fun, and for the whole family!</p>
				      </div>
				    </div>
				
				    <div class="item">
				      <img src="${slider2}" alt="Chicago">
				      <div class="carousel-caption">
				        <h3>Credit Cards</h3>
				        <p>We manage your money as you would like to</p>
				      </div>
				    </div>
				
				    <div class="item">
				      <img src="${slider3}" alt="New York">
				      <div class="carousel-caption">
				        <h3>Personal Banking</h3>
				        <p>The way you log in is changing</p>
				      </div>
				    </div>
				  </div>
				
				  <!-- Left and right controls -->
				  <a class="left carousel-control" href="#myCarousel" data-slide="prev">
				    <span class="glyphicon glyphicon-chevron-left"></span>
				    <span class="sr-only">Previous</span>
				  </a>
				  <a class="right carousel-control" href="#myCarousel" data-slide="next">
				    <span class="glyphicon glyphicon-chevron-right"></span>
				    <span class="sr-only">Next</span>
				  </a>
				</div>
        </div>
    </div>
    
	<div class="row row-thumbnails">
      <div class="col-sm-6 col-md-4">
        <div class="thumbnail">
          <img data-src="holder.js/100%x200" alt="100%x200" src="https://www.barclays.co.uk/content/dam/lifestyle-images/personal/miscellaneous/helpandsupport_16_9.xxsmall.medium_quality.jpg" data-holder-rendered="true" style="height: 200px; width: 100%; display: block;">
          <div class="caption">
            <h3>Spring Budget 2020</h3>
            <p>From support for those affected by coronavirus to National Insurance contributions changes and a fuel duty freeze, the government latest spending plans are out.</p>
            <p><a href="#" class="btn btn-primary" style="margin-top: 10px;" role="button">More info</a></p>
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-md-4">
        <div class="thumbnail">
          <img data-src="holder.js/100%x200" alt="100%x200" src="https://www.barclays.co.uk/content/dam/lifestyle-images/personal/insurance/home-insurance_16_9.xxsmall.medium_quality.jpg" data-holder-rendered="true" style="height: 200px; width: 100%; display: block;">
          <div class="caption">
            <h3>Banking, travel and events during the coronavirus outbreak</h3>
            <p>Think the coronavirus outbreak might affect your banking and plans for travel and events? Find out how we can help.</p>
            <p><a href="#" class="btn btn-primary" style="margin-top: 10px;" role="button">More info</a></p>
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-md-4">
        <div class="thumbnail">
          <img data-src="holder.js/100%x200" alt="100%x200" src="https://www.barclays.co.uk/content/dam/lifestyle-images/personal/ways-to-bank/onlinebanking_16_9.xxsmall.medium_quality.jpg" data-holder-rendered="true" style="height: 200px; width: 100%; display: block;">
          <div class="caption">
            <h3>Bank online</h3>
            <p>Online Banking gives you quick, secure and convenient access to your accounts. Enjoy your online bank accounts in AcmeRico</p>
            <p><a href="/users/new" class="btn btn-primary" style="margin-top: 10px;" role="button">Register now</a></p>
          </div>
        </div>
      </div>
    </div>
    
	<div class="jumbotron">
	  <h2>Important information</h2>
	  <ol><li class="global-legal-item"> <p>Terms and conditions apply. You must have a current account with us, be aged 16 or over and have a mobile number to use the Barclays app.</p>
</li><li class="global-legal-item"> <p>Voucher offer terms, conditions and exclusions<br>
a) Restrictions apply, see conditions<br>
b) Argos e-Gift cards can be redeemed online and in stores.<br>
c) Restrictions apply, see terms<br>
d) Only one voucher is available per household, in the UK or Isle of Man only.<br>
e) Valid for quotes between 24 February 2020 and 26 April 2020, where the policy starts within 90 days of the quote date.<br>
f) Quotes amended after 26 April 2020 are no longer eligible for the voucher offer.<br>
g) Offer available to new home insurance customers only whose policy includes buildings or contents cover, including landlord cover.<br>
h) Existing customers renewing their insurance wont be eligible for the voucher.<br>
i) Offer excludes starter contents, personal items, bike, gadget, sports equipment, legal services and home emergency cover when bought on a standalone basis, and Premium Home Insurance.<br>
j) Policies cancelled with 14 days of the start date or with unpaid premiums wont be eligible for the voucher.<br>
k) Within 30 days of the policy starting, customers will be contacted by email or post with details of how to select and receive their voucher.<br>
l) Offer may be withdrawn or amended at any time.<br>
m) The promoter is Barclays Bank UK PLC, 1 Churchill Place, London E14 5HP.</p>
</li></ol>
	</div>
</petclinic:layout>
