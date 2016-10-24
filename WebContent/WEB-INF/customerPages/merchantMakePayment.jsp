<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="customerHeader.jsp" %>
<<script type="text/javascript">
	$(function () {
	    function callServlet() {
	        var amount = $("#etpinputAmount").val();
	        var card = $("#etpinputCard").val();
	        var cvv = $("#etpinputCvv").val();
	        var month = $("#etpinputMonth").val();
	        var year = $("#etpinputYear").val();
	        var merchant_secret = $("#ms").attr("data");
	        var myData = {
	            
	                "card_no": card,
	                "amount" : amount,
	                "descripton" : "merchant payment",
	                "month": month,
	                "year" : year,
	                "merchant_secret" : merchant_secret
	                
	        };
	        $.ajax({
	        	headers: { 
	                'Accept': 'application/json',
	                'Content-Type': 'application/json' 
	            },
	        	method: "POST",
	            url: 'make_payment',
	            data: JSON.stringify(myData), 
	            dataType:"html",
	            success: function (data) {
	                alert("success")
	            },
	            error: function (textStatus, errorThrown) {
	                alert("Invalid")
	            }
	        });
	    }
	    
	    $('#calcBtn').click(function() {
	    	event.preventDefault();
	        callServlet();
	    });
	});
</script>
	<div class="content-wrapper">
		<div class="col-md-12" id="page-content">
		
			<div id="ms" data="${merchant_secret}"></div>
			<div class="panel panel-primary" id="externaltransferpanel">
			  <div class="panel-heading">
			    <h3 class="panel-title">Make payment</h3>
			  </div>
			  <div class="panel-body">
			  	
			    <form class="form-horizontal">
  				  <fieldset>

			        <div class="form-group">
			          <label for="etpAmount" class="col-lg-2 control-label">Amount : </label>

				      <div class="col-lg-5 input-group">
				        <input type="text" class="form-control" id="etpinputAmount" placeholder="Enter Amount">
				      </div>
				      <label for="etpCard" class="col-lg-2 control-label">Card : </label>

				      <div class="col-lg-5 input-group">
				        <input type="text" class="form-control" id="etpinputCard" placeholder="Enter Card">
				      </div>
				      
				      <label for="etpCvv" class="col-lg-2 control-label">Cvv : </label>

				      <div class="col-lg-5 input-group">
				        <input type="text" class="form-control" id="etpinputCvv" placeholder="Enter Cvv">
				      </div>
				      
				      <label for="etpMonth" class="col-lg-2 control-label">Month : </label>

				      <div class="col-lg-5 input-group">
				        <input type="text" class="form-control" id="etpinputMonth" placeholder="Enter Month">
				      </div>
				      
				      <label for="etpYear" class="col-lg-2 control-label">Year : </label>

				      <div class="col-lg-5 input-group">
				        <input type="text" class="form-control" id="etpinputYear" placeholder="Enter Year">
				      </div>
			        </div>
	                <div class="form-group">
	                  <label class="col-lg-2 control-label"></label>
			          <div class="col-lg-5 input-group">
			            <button id="calcBtn" class="btn btn-primary">Submit</button>
			          </div>
			        </div>
			      </fieldset>
			    </form>
			  </div>
			</div>
			</div>
			</div>
			
</body>
</html>