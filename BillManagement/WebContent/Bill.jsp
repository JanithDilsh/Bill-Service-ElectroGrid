<%@ page import="com.BillManage"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Bill Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css"> 
<script src="Components/jquery-3.4.1.min.js"></script> 
<script src="Components/validBill.js"></script> 
</head>
<body>

	<div class="container"> 
		<div class="row">  
			<div class="col"> 
				<h1>Bill Management</h1>
				<br>
					<form id="formBill" name="formBill" method="post" action="Bill.jsp">  
						Customer Name:  
	 	 				<input id="customerName" name="customerName" type="text"  class="form-control form-control-sm">
						<br>Bill ID:   
	  					<input id="billId" name="billId" type="text" class="form-control form-control-sm" >   
	  					<br>Card No:   
	  					<input id="cardNo" name="cardNo" type="text"  class="form-control form-control-sm">
						<br>CVV:
						<input id="ccv" name="ccv" type="text"  class="form-control form-control-sm">
						<br>Expired Date:
						<input id="expiredDate" name="expiredDate" type="text"  class="form-control form-control-sm">
						<br>
						
						<div id="alertSuccess" class="alert alert-success"> </div>				
				   		<div id="alertError" class="alert alert-danger"></div>
				   		 
						<input id="btnSave" name="btnSave" type="button" value="SAVE" class="btn btn-primary">  
						<input type="hidden" id="hidBillIDSave" name="hidBillIDSave" value=""> 
					</form>
					
					
				   <br>
					<div id="bill">
						<%
						BillManage billObj = new BillManage();
						out.print(billObj.readBill());
						%>
					</div>
					
					 
				</div>
			</div>
	</div>

</body>
</html>