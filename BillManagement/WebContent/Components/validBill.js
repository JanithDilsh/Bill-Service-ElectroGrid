$(document).ready(function() 
{  
		$("#alertSuccess").hide();  
	    $("#alertError").hide(); 
}); 
 
 
// SAVE ============================================ 
$(document).on("click", "#btnSave", function(event) 
{  
	// Clear alerts---------------------  
	$("#alertSuccess").text("");  
	$("#alertSuccess").hide();  
	$("#alertError").text("");  
	$("#alertError").hide(); 
 
	// Form validation-------------------  
	var status = validateBillForm();  
	if (status != true)  
	{   
		$("#alertError").text(status);   
		$("#alertError").show();   
		return;  
	} 
 
	// If valid------------------------  
	var type = ($("#hidBillIDSave").val() == "") ? "POST" : "PUT"; 

	$.ajax( 
	{  
			url : "BillManageAPI",  
			type : type,  
			data : $("#formBill").serialize(),  
			dataType : "text",  
			complete : function(response, status)  
			{   
				onBillSaveComplete(response.responseText, status);  
			} 
	}); 
}); 


function onBillSaveComplete(response, status) 
{  
	if (status == "success")  
	{   
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")   
		{    
			$("#alertSuccess").text("Successfully saved.");    
			$("#alertSuccess").show(); 

			$("#bill").html(resultSet.data);   
		} else if (resultSet.status.trim() == "error")   
		{    
			$("#alertError").text(resultSet.data);    
			$("#alertError").show();   
		} 

	} else if (status == "error")  
	{   
		$("#alertError").text("Error while saving.");   
		$("#alertError").show();  
	} else  
	{   
		$("#alertError").text("Unknown error while saving..");   
		$("#alertError").show();  
	} 

	$("#hidBillIDSave").val("");  
	$("#formBill")[0].reset(); 
} 

 
// UPDATE========================================== 
$(document).on("click", ".btnUpdate", function(event) 
{     
	$("#hidBillIDSave").val($(this).closest("tr").find('#hidBillIDUpdate').val());     
	$("#customerName").val($(this).closest("tr").find('td:eq(0)').text());     
	$("#billId").val($(this).closest("tr").find('td:eq(1)').text());
	$("#cardNo").val($(this).closest("tr").find('td:eq(2)').text());
	$("#ccv").val($(this).closest("tr").find('td:eq(3)').text());
	$("#expiredDate").val($(this).closest("tr").find('td:eq(4)').text());
	    
}); 




//REMOVE===========================================
$(document).on("click", ".btnRemove", function(event) 
{  
	$.ajax(  
	{   
		url : "BillManageAPI",   
		type : "DELETE",   
		data : "bID=" + $(this).data("bid"),   
		dataType : "text",   
		complete : function(response, status)   
		{    
			onBillDeleteComplete(response.responseText, status);   
		}  
	}); 
}); 

function onBillDeleteComplete(response, status) 
{  
	if (status == "success")  
	{   
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")   
		{    
			
			$("#alertSuccess").text("Successfully deleted.");    
			$("#alertSuccess").show(); 
		
			$("#bill").html(resultSet.data); 
			
		} else if (resultSet.status.trim() == "error")   
		{    
			$("#alertError").text(resultSet.data);    
			$("#alertError").show();   
		}
		

	} else if (status == "error")  
	{   
		$("#alertError").text("Error while deleting.");   
		$("#alertError").show();  
	} else  
	{   
		$("#alertError").text("Unknown error while deleting..");   
		$("#alertError").show();  
	}
}
 
// CLIENT-MODEL========================================================================= 
function validateBillForm() 
{  
	// ACCcustomerNameOUNT  
	if ($("#customerName").val().trim() == "")  
	{   
		return "Insert customerName.";  
	}

	// BillID------------------------  
	if ($("#billId").val().trim() == "")  
	{   
		return "Insert billId.";  
	} 
	
	// CardNo------------------------  
	if ($("#cardNo").val().trim() == "")  
	{   
		return "Insert cardNo.";  
	}
	
	// CCV------------------------  
	if ($("#ccv").val().trim() == "")  
	{   
		return "Insert ccv.";  
	}
	
	// ExpiredDate------------------------  
	if ($("#expiredDate").val().trim() == "")  
	{   
		return "Insert expiredDate.";  
	}
	

	return true; 
}

