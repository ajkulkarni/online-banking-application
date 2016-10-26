$().ready(function(){
	$("#chooseTransferMethodContainer").show();
	$("#externaltransferpanel").hide();
	$("#internaltransferpanel").hide();
	$("#emailphonetransferpanel").hide();
	
	function clearExternalTransferPanel(){
		$("#etpselectPayee").val('Select Payee');
		$("#etpselectPayerAccount").val('Select Account');
		$("#etpinputAmount").val('');
		$("#etpinputAmount").attr("placeholder","Enter Amount");
		$("#etpdatetimepicker_result").val('');
	}
	
	function clearInternalTransferPanel(){
		$("#itpselectPayee").val('Select Account');
		$("#itpselectPayerAccount").val('Select Account');
		$("#itpinputAmount").val('');
		$("#itpinputAmount").attr("placeholder","Enter Amount");
		$("#itpdatetimepicker_result").val('');		
	}
	
	function clearEmailphoneTransferPanel(){
		$("#eptpModeOfTransfer").val('Select Mode of Transfer');
		$("#eptpselectPayerAccount").val('Select Account');
		$("#eptpinputMode").val('');
		$("#eptpinputMode").attr("placeholder","Enter Payee's Email/Phone");
		$("#eptpinputAmount").val('');
		$("#eptpinputAmount").attr("placeholder","Enter Amount");
		$("#eptpdatetimepicker_result").val('');			
	}
	
	function displayExternalTransferPanel(){
		clearExternalTransferPanel();
		clearInternalTransferPanel();
		clearEmailphoneTransferPanel();
		$("#chooseTransferMethodContainer").hide();
		$("#internaltransferpanel").hide();
		$("#emailphonetransferpanel").hide();
		$("#externaltransferpanel").show();
	}
	
	function displayInternalTransferPanel(){
		clearExternalTransferPanel();
		clearInternalTransferPanel();
		clearEmailphoneTransferPanel();
		$("#chooseTransferMethodContainer").hide();
		$("#emailphonetransferpanel").hide();
		$("#externaltransferpanel").hide();
		$("#internaltransferpanel").show();
	}
	
	function displayEmailphoneTransferPanel(){
		clearExternalTransferPanel();
		clearInternalTransferPanel();
		clearEmailphoneTransferPanel();
		$("#chooseTransferMethodContainer").hide();
		$("#externaltransferpanel").hide();
		$("#internaltransferpanel").hide();
		$("#emailphonetransferpanel").show();
	}
	
	$("#extTfrBtn").click(function(){
		displayExternalTransferPanel();
		//$("#tfrfundsPageContent").html(new String($("#externaltransferpanel").clone()[0].outerHTML));
	});
	
	$("#intTfrBtn").click(function(){
		displayInternalTransferPanel();
		//$("#tfrfundsPageContent").html(new String($("#internaltransferpanel").clone()[0].outerHTML));	
	});
	
	$("#emailphoneTfrBtn").click(function(){
		displayEmailphoneTransferPanel();
		//$("#tfrfundsPageContent").html(new String($("#emailphonetransferpanel").clone()[0].outerHTML));
	});
	
	$("#etfrTab").click(function(){
		displayExternalTransferPanel();
		//$("#tfrfundsPageContent").html(new String($("#externaltransferpanel").clone()[0].outerHTML));
	});
	
	$("#itfrTab").click(function(){
		displayInternalTransferPanel();
		//$("#tfrfundsPageContent").html(new String($("#internaltransferpanel").clone()[0].outerHTML));	
	});
	
	$("#eptfrTab").click(function(){
		displayEmailphoneTransferPanel();
		//$("#tfrfundsPageContent").html(new String($("#emailphonetransferpanel").clone()[0].outerHTML));
	});
})