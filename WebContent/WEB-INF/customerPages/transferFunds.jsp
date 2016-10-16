<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="customerHeader.jsp" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/css/bootstrap-datetimepicker.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.11.2/moment.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript">
        $(function () {
            $('#etpdatetimepicker').datetimepicker();
        });
</script>
<script type="text/javascript">
        $(function () {
            $('#itpdatetimepicker').datetimepicker();
        });
</script>
<script type="text/javascript">
        $(function () {
            $('#eptpdatetimepicker').datetimepicker();
        });
</script>

<script src="<c:url value="/resources/js/transferfunds.js" />"></script>

	<div class="content-wrapper">
		<div class="col-md-12" id="tfrfundsPageContent">
			<div class="container" id="chooseTransferMethodContainer" style="display:none;">
			  
			  
			    <form class="form-horizontal">
			   
  				  <fieldset>
					<div class="form-group">
					<div class="col-lg-12">
					  <legend>Choose Method of Transfer</legend>
					  <a href="#" class="btn btn-success btn-lg" id="extTfrBtn">External Transfer</a>
					  <a href="#" class="btn btn-success btn-lg" id="intTfrBtn">Internal Transfer</a>
					  <a href="#" class="btn btn-success btn-lg" id="emailphoneTfrBtn">Email/Phone Transfer</a>
					</div>
					</div>
			      </fieldset>
			    </form>
			  
			</div>		
		
			<div class="panel panel-primary" id="externaltransferpanel" style="display:none;">
			  <div class="panel-heading">
			    <h3 class="panel-title">Transfer Money - Someone's Account</h3>
			  </div>
			  <div class="panel-body">
			    <form class="form-horizontal">
  				  <fieldset>
  				  	<div class="form-group">
  				    <label for="etpselectPayee" class="col-lg-2 control-label">Transfer To : </label>
  				      <div class="col-lg-5 input-group">
			            <select class="form-control" id="etpselectPayee" >
			              <option>Select Payee</option>
			              <option>User 1</option>
			              <option>User 2</option>
			              <option>User 3</option>
			              <option>User 4</option>
			              <option>User 5</option>
			            </select>
			          </div>
			        </div>
			        <div class="form-group">
			          <label for="etpselectPayerAccount" class="col-lg-2 control-label">Transfer From : </label>
			          <div class="col-lg-5 input-group">
			            <select class="form-control" id="etpselectPayerAccount">
			              <option>Select Account</option>
			              <option>Checking Account - 12345678</option>
			              <option>Checking Account - 12346789</option>
			              <option>Savings Account - 23154743</option>
			            </select>
			          </div>
			        </div>
			        <div class="form-group">
			          <label for="etpinputAmount" class="col-lg-2 control-label">Amount : </label>
				      <div class="col-lg-5 input-group">
				        <input type="text" class="form-control" id="etpinputAmount" placeholder="Enter Amount">
				      </div>
			        </div>
			        <div class="form-group">
			          <label for="etpdatetimepicker" class="col-lg-2 control-label" >Date : </label>
					    <div id="etpdatetimepicker" class="col-lg-5 input-group date">
		                <input data-format="MM/dd/yyyy HH:mm:ss PP" type="text" id="etpdatetimepicker_result" class="form-control"></input>
	                    <span class="input-group-addon">
	                        <span class="glyphicon glyphicon-calendar"></span>
	                    </span>
	                    </div>
	                </div>
	                <div class="form-group">
	                  <label class="col-lg-2 control-label"></label>
			          <div class="col-lg-5 input-group">
			            <button type="submit" class="btn btn-primary">Submit</button>
			          </div>
			        </div>
			      </fieldset>
			    </form>
			  </div>
			</div>
			
			<div class="panel panel-primary" id="internaltransferpanel" style="display:none;">
			  <div class="panel-heading">
			    <h3 class="panel-title">Transfer Money - Within Account</h3>
			  </div>
			  <div class="panel-body">
			    <form class="form-horizontal">
  				  <fieldset>
  				  	<div class="form-group">
  				    <label for="itpselectPayee" class="col-lg-2 control-label">Transfer To : </label>
  				      <div class="col-lg-5 input-group">
			            <select class="form-control" id="itpselectPayee" >
			              <option>Select Account</option>
			              <option>Checking Account - 12345678</option>
			              <option>Checking Account - 12346789</option>
			              <option>Savings Account - 23154743</option>
			            </select>
			          </div>
			        </div>
			        <div class="form-group">
			          <label for="itpselectPayerAccount" class="col-lg-2 control-label">Transfer From : </label>
			          <div class="col-lg-5 input-group">
			            <select class="form-control" id="itpselectPayerAccount">
			              <option>Select Account</option>
			              <option>Checking Account - 12345678</option>
			              <option>Checking Account - 12346789</option>
			              <option>Savings Account - 23154743</option>
			            </select>
			          </div>
			        </div>
			        <div class="form-group">
			          <label for="itpinputAmount" class="col-lg-2 control-label">Amount : </label>
				      <div class="col-lg-5 input-group">
				        <input type="text" class="form-control" id="itpinputAmount" placeholder="Enter Amount">
				      </div>
			        </div>
			        <div class="form-group">
			          <label for="itpdatetimepicker" class="col-lg-2 control-label" >Date : </label>
					    <div id="itpdatetimepicker" class="col-lg-5 input-group date">
		                <input data-format="MM/dd/yyyy HH:mm:ss PP" type="text" id="itpdatetimepicker_result" class="form-control"></input>
	                    <span class="input-group-addon">
	                        <span class="glyphicon glyphicon-calendar"></span>
	                    </span>
	                    </div>
	                </div>
	                <div class="form-group">
	                  <label class="col-lg-2 control-label"></label>
			          <div class="col-lg-5 input-group">
			            <button type="submit" class="btn btn-primary">Submit</button>
			          </div>
			        </div>
			      </fieldset>
			    </form>
			  </div>
			</div>
			
			<div class="panel panel-primary" id="emailphonetransferpanel" style="display:none;">
			  <div class="panel-heading">
			    <h3 class="panel-title">Email/Phone Transfer</h3>
			  </div>
			  <div class="panel-body">
			    <form class="form-horizontal">
  				  <fieldset>
  				  	<div class="form-group">
  				    <label for="eptpModeOfTransfer" class="col-lg-2 control-label">Transfer Via : </label>
  				      <div class="col-lg-5 input-group">
			            <select class="form-control" id="eptpModeOfTransfer" >
			              <option>Select Mode of Transfer</option>
			              <option>Email</option>
			              <option>Phone</option>
			            </select>
			          </div>
			        </div>
			        <div class="form-group">
			          <label for="eptpinputMode" class="col-lg-2 control-label">Transfer To : </label>
				      <div class="col-lg-5 input-group">
				        <input type="text" class="form-control" id="eptpinputMode" placeholder="Enter Payee's Email/Phone">
				      </div>
			        </div>
			        <div class="form-group">
			          <label for="eptpselectPayerAccount" class="col-lg-2 control-label">Transfer From : </label>
			          <div class="col-lg-5 input-group">
			            <select class="form-control" id="eptpselectPayerAccount">
			              <option>Select Account</option>
			              <option>Checking Account - 12345678</option>
			              <option>Checking Account - 12346789</option>
			              <option>Savings Account - 23154743</option>
			            </select>
			          </div>
			        </div>
			        <div class="form-group">
			          <label for="eptpinputAmount" class="col-lg-2 control-label">Amount : </label>
				      <div class="col-lg-5 input-group">
				        <input type="text" class="form-control" id="eptpinputAmount" placeholder="Enter Amount">
				      </div>
			        </div>
			        <div class="form-group">
			          <label for="eptpdatetimepicker" class="col-lg-2 control-label" >Date : </label>
					    <div id="eptpdatetimepicker" class="col-lg-5 input-group date">
		                <input data-format="MM/dd/yyyy HH:mm:ss PP" type="text" id="eptpdatetimepicker_result" class="form-control"></input>
	                    <span class="input-group-addon">
	                        <span class="glyphicon glyphicon-calendar"></span>
	                    </span>
	                    </div>
	                </div>
	                <div class="form-group">
	                  <label class="col-lg-2 control-label"></label>
			          <div class="col-lg-5 input-group">
			            <button type="submit" class="btn btn-primary">Submit</button>
			          </div>
			        </div>
			      </fieldset>
			    </form>
			  </div>
			</div>
			
		</div>
	</div> <!-- .content-wrapper -->
	
</main> 

<script type="text/javascript">
	$(document).ready(function() {
		sideNavigationSettings();
	});
</script>

</body>
</html>