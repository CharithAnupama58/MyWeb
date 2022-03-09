
$("#btnSaveItem").click(function () {
    $("#itemBody>tr").off("click");
    loadAllItems();
    checkIfValidItem();


    $("#itemBody>tr").click(function(){

        let itemId=$(this).children(":eq(0)").text();
        let itemName=$(this).children(":eq(1)").text();
        let qty=$(this).children(":eq(2)").text();
        let price=$(this).children(":eq(3)").text();

        console.log(itemId,itemName,qty,price);

        $("#txtItemId1").val(itemId);
        $("#txtItemName1").val(itemName);
        $("#txtItemQty1").val(qty);
        $("#txtItemPrice1").val(price);
    });
});
function saveItem() {
    let itemId = $("#txtItemID").val();
    let itemName = $("#txtItemName").val();
    let itemQty = $("#txtItemQty").val();
    let itemPrice = $("#txtPrice").val();

    if (isItemExists(itemId)) {
        alert("Already Exists.");
    } else {
        var itemObject = new Item(itemId, itemName, itemQty, itemPrice);
        itemDB.push(itemObject);
    }
}
function loadAllItems() {
    $("#itemBody").empty();
    for (var i in itemDB){
        let row = `<tr><td>${itemDB[i].getItemID()}</td><td>${itemDB[i].getItemName()}</td><td>${itemDB[i].getItemQty()}</td><td>${itemDB[i].getItemPrice()}</td></tr>`;
        $("#itemBody").append(row);
    }
}
$("#btnItemSearch").click(function () {
    var id=$("#txtItemSearch").val();
    var response=searchItem(id);
    if (response){
        $("#txtItemId1").val(response.getItemID());
        $("#txtItemName1").val(response.getItemName());
        $("#txtItemQty1").val(response.getItemQty());
        $("#txtItemPrice1").val(response.getItemPrice());
    }else {
        alert("Invalid ID.")
    }
});
$("#btnItemDelete").click(function () {
    var searchID=$("#txtItemId1").val();
    searchItemElement(searchID);

});
$("#btnItemUpdate").click(function () {
    let itemId=$("#txtItemId1").val();
    let itemName=$("#txtItemName1").val();
    let qty=$("#txtItemQty1").val();
    let price=$("#txtItemPrice1").val();
    var itemObj=new Item(itemId,itemName,qty,price);
    if (confirm("Do you want to update this Customer?")){
        updateItem(itemObj);
        loadAllItems();
        alert("Updated.");

    }
});
function updateItem(obj) {
    for (let i=0;i<itemDB.length;i++){
        if(itemDB[i].getItemID()==obj.getItemID()){
            itemDB[i].setItemID(obj.getItemID());
            itemDB[i].setItemName(obj.getItemName());
            itemDB[i].setItemQty(obj.getItemQty());
            itemDB[i].setItemPrice(obj.getItemPrice());
        }
    }
}
function clearItemFields() {
    $("#txtItemId1").val("");
    $("#txtItemName1").val("");
    $("#txtItemPrice1").val("");
    $("#txtItemQty1").val("");
}
function searchItem(id) {
    for (let i=0;i<itemDB.length;i++){
        if(itemDB[i].getItemID()==id){
            return itemDB[i];
        }
    }
}
function searchItemElement(id){
    for (let i=0;i<itemDB.length;i++){
        if(itemDB[i].getItemID()==id){
            if ( confirm("Do you want to delete this Item?")) {
                itemDB.splice(i, 1);
                loadAllItems();
                clearItemFields();
            }
        }
    }

}
function isItemExists(id){
    for (let i=0;i<itemDB.length;i++){
        if(itemDB[i].getItemID()==id){
            return true;
        }
    }

}



var regExItemId=/^(I00-)[0-9]{3,4}$/;
var regExItemName=/^[A-z]{4,10}$/;
var regExItemQty=/^[0-9]{1,2}$/;
var regExItemPrice=/^[0-9]{2,5}[.][0]{2}$/;

$('#txtItemID,#txtItemName,#txtItemQty,#txtPrice').on('keydown', function (eventOb) {
    if (eventOb.key == "Tab") {
        eventOb.preventDefault(); // stop execution of the button
    }
});

$('#txtItemID,#txtItemName,#txtItemQty,#txtPrice').on('blur', function () {
    formValidItem();
});

//focusing events
$("#txtItemID").on('keyup', function (eventOb) {
    setButtonItem();
    if (eventOb.key == "Enter") {
        checkIfValidItem();
    }
});

$("#txtItemName").on('keyup', function (eventOb) {
    setButtonItem();
    if (eventOb.key == "Enter") {
        checkIfValidItem();
    }
});

$("#txtItemQty").on('keyup', function (eventOb) {
    setButtonItem();
    if (eventOb.key == "Enter") {
        checkIfValidItem();
    }
});

$("#txtPrice").on('keyup', function (eventOb) {
    setButtonItem();
    if (eventOb.key == "Enter") {
        checkIfValidItem();
    }
});
// focusing events end
$("#btnSaveItem").attr('disabled', true);

function clearAllItem() {
    $('#txtItemID,#txtItemName,#txtItemQty,#txtPrice').val("");
    $('#txtItemID,#txtItemName,#txtItemQty,#txtPrice').css('border', '2px solid #ced4da');
    $('#txtItemID').focus();
    $("#btnSaveItem").attr('disabled', true);
    loadAllItems();
    $("#error4,#error5,#error6,#error7").text("");
}

function formValidItem(){
    var itemID = $("#txtItemID").val();
    $("#txtItemID").css('border', '2px solid green');
    $("#error4").text("");
    if (regExItemId.test(itemID)) {
        var itemName = $("#txtItemName").val();
        if (regExItemName.test(itemName)) {
            $("#txtItemName").css('border', '2px solid green');
            $("#error5").text("");
            var qty = $("#txtItemQty").val();
            if (regExItemQty.test(qty)) {
                var price = $("#txtPrice").val();
                var resp = regExItemPrice.test(price);
                $("#txtItemQty").css('border', '2px solid green');
                $("#error6").text("");
                if (resp) {
                    $("#txtPrice").css('border', '2px solid green');
                    $("#error7").text("");
                    return true;
                } else {
                    $("#txtPrice").css('border', '2px solid red');
                    $("#error7").text("Item Price is a required field : Pattern 100.00 or 100");
                    return false;
                }
            } else {
                $("#txtItemQty").css('border', '2px solid red');
                $("#error6").text("Item Name is a required field : Mimum 7");
                return false;
            }
        } else {
            $("#txtItemName").css('border', '2px solid red');
            $("#error5").text("Item Name is a required field : Mimimum 5, Max 20, Spaces Allowed");
            return false;
        }
    } else {
        $("#txtItemID").css('border', '2px solid red');
        $("#error4").text("Item ID is a required field : Pattern I00-000");
        return false;
    }
}

function checkIfValidItem() {
    var itemID = $("#txtItemID").val();
    if (regExItemId.test(itemID)) {
        $("#txtItemName").focus();
        var itemName = $("#txtItemName").val();
        if (regExItemName.test(itemName)) {
            $("#txtItemQty").focus();
            var qty = $("#txtItemQty").val();
            if (regExItemQty.test(qty)) {
                $("#txtPrice").focus();
                var price = $("#txtPrice").val();
                var resp = regExItemPrice.test(price);
                if (resp) {
                    let res = confirm("Do you really need to add this Item..?");
                    if (res) {
                        saveItem();
                        clearAllItem();
                    }
                } else {
                    $("#txtPrice").focus();
                }
            } else {
                $("#txtItemQty").focus();
            }
        } else {
            $("#txtItemName").focus();
        }
    } else {
        $("#txtItemID").focus();
    }
}

function setButtonItem() {
    let b = formValidItem();
    if (b) {
        $("#btnSaveItem").attr('disabled', false);
    } else {
        $("#btnSaveItem").attr('disabled', true);
    }
}