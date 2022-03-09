$("#btnSaveCustomer").click(function () {
    $("#customerBody>tr").off("click");
    loadAllCustomers();
    checkIfValid();



    $("#customerBody>tr").click(function(){

        let custId=$(this).children(":eq(0)").text();
        let custName=$(this).children(":eq(1)").text();
        let custAge=$(this).children(":eq(2)").text();
        let custTp=$(this).children(":eq(3)").text();

        console.log(custId,custName,custAge,custTp);

        $("#txtCustomerId1").val(custId);
        $("#txtCustomerName1").val(custName);
        $("#txtCustomerAge1").val(custAge);
        $("#txtCustomerTP1").val(custTp);
    });
});
function saveCustomer() {
    let customerId=$("#txtCustomerID").val();
    let customerName=$("#txtCustomerName").val();
    let customerAge=$("#txtCustomerAge").val();
    let customerTP=$("#txtCustomerTP").val();
    if (isCustomerExists(customerId)){
        alert("Already Exists.");
    }else{
        var customerObject=new Customer(customerId,customerName,customerAge,customerTP);
        customerDB.push(customerObject);

    }

}
function loadAllCustomers() {
    $("#customerBody").empty();
    for (var i in customerDB){
        let row = `<tr><td>${customerDB[i].getCustID()}</td><td>${customerDB[i].getCustName()}</td><td>${customerDB[i].getCustAge()}</td><td>${customerDB[i].getCustTP()}</td></tr>`;
        $("#customerTable").append(row);
    }
}
$("#btnSearch").click(function () {
    var searchID=$("#txtCustomerSearch").val();
    var response=searchCustomer(searchID);
    if (response){
        $("#txtCustomerId1").val(response.getCustID());
        $("#txtCustomerName1").val(response.getCustName());
        $("#txtCustomerAge1").val(response.getCustAge());
        $("#txtCustomerTP1").val(response.getCustTP());
    }else {
        alert("Invalid ID.")
    }
});

$("#btnDeleteCustomer").click(function () {
    var searchID=$("#txtCustomerId1").val();
   searchCustomerElement(searchID);

});
$("#btnUpdateCustomer").click(function () {
    let customerId=$("#txtCustomerId1").val();
    let customerName=$("#txtCustomerName1").val();
    let customerAge=$("#txtCustomerAge1").val();
    let customerTP=$("#txtCustomerTP1").val();
    var CustObj=new Customer(customerId,customerName,customerAge,customerTP);
    if (confirm("Do you want to update this Customer?")){
        updateCustomer(CustObj);
        loadAllCustomers();
        alert("Updated.");

    }
});
function updateCustomer(obj) {
    for (let i=0;i<customerDB.length;i++){
        if(customerDB[i].getCustID()==obj.getCustID()){
            customerDB[i].setCustID(obj.getCustID());
            customerDB[i].setCustName(obj.getCustName());
            customerDB[i].setCustAge(obj.getCustAge());
            customerDB[i].setCustTP(obj.getCustTP());
        }
    }
}

function clearCustomerFields() {
    $("#txtCustomerId1").val("");
    $("#txtCustomerName1").val("");
    $("#txtCustomerAge1").val("");
    $("#txtCustomerTP1").val("");
    $("#txtCustomerID").val("");
    $("#txtCustomerName").val("");
    $("#txtCustomerAge").val("");
    $("#txtCustomerTP").val("");
}

function searchCustomer(id) {
    for (let i=0;i<customerDB.length;i++){
        if(customerDB[i].getCustID()==id){
            return customerDB[i];
        }
    }
}
function searchCustomerElement(id){
    for (let i=0;i<customerDB.length;i++){
        if(customerDB[i].getCustID()==id){
            if ( confirm("Do you want to delete this customer?")) {
                customerDB.splice(i, 1);
                loadAllCustomers();
                clearCustomerFields();
            }
        }
    }
}
function isCustomerExists(id){
    for (let i=0;i<customerDB.length;i++){
        if(customerDB[i].getCustID()==id){
            return true;
        }
    }

}

var regExCustId=/^(C00-)[0-9]{3,4}$/;
var regExCustName=/^[A-z]{4,10}$/;
var regExCustAge=/^[0-9]{1,2}$/;
var regExCustTel=/^[0][0-9]{9}$/;
$('#txtCustomerID,#txtCustomerName,#txtCustomerAge,#txtCustomerTP').on('keydown', function (eventOb) {
    if (eventOb.key == "Tab") {
        eventOb.preventDefault(); // stop execution of the button
    }
});

$('#txtCustomerID,#txtCustomerName,#txtCustomerAge,#txtCustomerTP').on('blur', function () {
    formValid();
});

//focusing events
$("#txtCustomerID").on('keyup', function (eventOb) {
    setButton();
    if (eventOb.key == "Enter") {
        checkIfValid();
    }
});

$("#txtCustomerName").on('keyup', function (eventOb) {
    setButton();
    if (eventOb.key == "Enter") {
        checkIfValid();
    }
});

$("#txtCustomerAge").on('keyup', function (eventOb) {
    setButton();
    if (eventOb.key == "Enter") {
        checkIfValid();
    }
});

$("#txtCustomerTP").on('keyup', function (eventOb) {
    setButton();
    if (eventOb.key == "Enter") {
        checkIfValid();
    }
});
// focusing events end
$("#btnSaveCustomer").attr('disabled', true);

function clearAll() {
    $('#txtCustomerID,#txtCustomerName,#txtCustomerAge,#txtCustomerTP').val("");
    $('#txtCustomerID,#txtCustomerName,#txtCustomerAge,#txtCustomerTP').css('border', '2px solid #ced4da');
    $('#txtCustomerID').focus();
    $("#btnSaveCustomer").attr('disabled', true);
    loadAllCustomers();
    $("#error,#error1,#error2,#error3").text("");
}

function formValid(){
    var cusID = $("#txtCustomerID").val();
    $("#txtCustomerID").css('border', '2px solid green');
    $("#error").text("");
    if (regExCustId.test(cusID)) {
        var cusName = $("#txtCustomerName").val();
        if (regExCustName.test(cusName)) {
            $("#txtCustomerName").css('border', '2px solid green');
            $("#error1").text("");
            var cusAge = $("#txtCustomerAge").val();
            if (regExCustAge.test(cusAge)) {
                var cusTP = $("#txtCustomerTP").val();
                var resp = regExCustTel.test(cusTP);
                $("#txtCustomerAge").css('border', '2px solid green');
                $("#error2").text("");
                if (resp) {
                    $("#txtCustomerTP").css('border', '2px solid green');
                    $("#error3").text("");
                    return true;
                } else {
                    $("#txtCustomerTP").css('border', '2px solid red');
                    $("#error3").text("Cus Salary is a required field : Pattern 100.00 or 100");
                    return false;
                }
            } else {
                $("#txtCustomerAge").css('border', '2px solid red');
                $("#error2").text("Cus Name is a required field : Mimum 7");
                return false;
            }
        } else {
            $("#txtCustomerName").css('border', '2px solid red');
            $("#error1").text("Cus Name is a required field : Mimimum 5, Max 20, Spaces Allowed");
            return false;
        }
    } else {
        $("#txtCustomerID").css('border', '2px solid red');
        $("#error").text("Cus ID is a required field : Pattern C00-000");
        return false;
    }
}

function checkIfValid() {
    var cusID = $("#txtCustomerID").val();
    if (regExCustId.test(cusID)) {
        $("#txtCustomerName").focus();
        var cusName = $("#txtCusName").val();
        if (regExCustName.test(cusName)) {
            $("#txtCustomerAge").focus();
            var cusAge = $("#txtCustomerAge").val();
            if (regExCustAge.test(cusAge)) {
                $("#txtCustomerTP").focus();
                var cusTell = $("#txtCustomerTP").val();
                var resp = regExCustTel.test(cusTell);
                if (resp) {
                    let res = confirm("Do you really need to add this Customer..?");
                    if (res) {
                        saveCustomer();
                        clearAll();
                    }
                } else {
                    $("#txtCustomerTP").focus();
                }
            } else {
                $("#txtCustomerAge").focus();
            }
        } else {
            $("#txtCustomerName").focus();
        }
    } else {
        $("#txtCustomerID").focus();
    }
}

function setButton() {
    let b = formValid();
    if (b) {
        $("#btnSaveCustomer").attr('disabled', false);
    } else {
        $("#btnSaveCustomer").attr('disabled', true);
    }
}