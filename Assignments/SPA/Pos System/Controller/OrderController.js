function loadAllCustomerIds() {
    $("#cmbCustomer").empty();
    for (var i = 0; i < customerDB.length; i++) {
        $("#cmbCustomer").append(new Option(customerDB[i].getCustID()));
    }

}
function loadAllItemIds() {
    $("#cmbItem").empty();
    for (var i = 0; i < itemDB.length; i++) {
        $("#cmbItem").append(new Option(itemDB[i].getItemID()));
    }
}
function generateOrderID() {
    if (orderDB.length !== 0) {
        let id = orderDB[(orderDB.length) - 1].getoID();
        const txt = id.split('O', 2);
        let newOID = parseInt(txt[1]) + 1;
        console.log(newOID);

        if (newOID <= 9) {
            $("#orderId").val("O00" + newOID);
        } else if (newOID <= 99) {
            $("#orderId").val("O0" + newOID);
        } else if (newOID <= 999) {
            $("#orderId").val("O" + newOID);
        }
    } else {
        $("#orderId").val("O001");
    }
}
$("#cmbCustomer").click(function () {
    var id=$("#cmbCustomer").val();
    for (let i=0;i<customerDB.length;i++){
        if(customerDB[i].getCustID()==id){
            $("#txtCustName").val(customerDB[i].getCustName());
            $("#txtCustAge").val(customerDB[i].getCustAge());
            $("#txtCustTell").val(customerDB[i].getCustTP());
        }
    }
});
$("#cmbItem").click(function () {
    var id=$("#cmbItem").val();
    for (let i=0;i<itemDB.length;i++){
        if(itemDB[i].getItemID()==id){
            $("#txtIName").val(itemDB[i].getItemName());
            $("#txtIPrice").val(itemDB[i].getItemPrice());
            $("#txtIQty").val(itemDB[i].getItemQty());
        }
    }
});
$("#btnAddItem").click(function () {
    $("#itemDetailsBody>tr").off("click");
    let IQty=$("#txtOQty").val();
    let SQty=$("#txtIQty").val();
    let b=checkQty(IQty,SQty);
    if (b){
        alert("Not Have Enough Quantity");
    }else{
        saveItemDetails();
        loadAllItemDetails();
        clearTextFields();
        var total=setTotal();
        $("#txtTotal").val(total);
        balanceDiscount();
    }



    $("#itemDetailsBody>tr").click(function(){
        $("#btnUpdateItem").attr('disabled', false);
        let itemId=$(this).children(":eq(0)").text();
        let name=$(this).children(":eq(1)").text();
        let price=$(this).children(":eq(2)").text();
        let qty=$(this).children(":eq(3)").text();

        for (let i=0;i<itemDB.length;i++){
            if(itemDB[i].getItemID()==itemId){
                $("#txtIQty").val(itemDB[i].getItemQty());
            }
        }

        console.log(itemId,name,price,qty);

        $("#cmbItem").val(itemId);
        $("#txtIName").val(name);
        $("#txtOQty").val(qty);
        $("#txtIPrice").val(price);
    });
});
$("#btnUpdateItem").click(function () {
    $("#itemDetailsBody>tr").off("click");
    let Id=$("#cmbItem").find(":selected").text();
    let IName=$("#txtIName").val();
    let IPrice=$("#txtIPrice").val();
    let IQty=$("#txtOQty").val();
    var IList=new ItemDetails(Id,IName,IPrice,IQty);
    var Tot=updateTotal(Id);
    let newTot=parseInt(Tot)+parseInt(IPrice*IQty);
    $("#txtTotal").val(newTot);
    balanceDiscount();
    updateItemDetails(IList);

    alert("Updated.");
    loadAllItemDetails();
    clearTextFields();
    $("#itemDetailsBody>tr").click(function(){
        $("#btnUpdateItem").attr('disabled', false);

        let itemId=$(this).children(":eq(0)").text();
        let name=$(this).children(":eq(1)").text();
        let price=$(this).children(":eq(2)").text();
        let qty=$(this).children(":eq(3)").text();

        for (let i=0;i<itemDB.length;i++){
            if(itemDB[i].getItemID()==itemId){
                $("#txtIQty").val(itemDB[i].getItemQty());
            }
        }

        console.log(itemId,name,price,qty);

        $("#cmbItem").val(itemId);
        $("#txtIName").val(name);
        $("#txtOQty").val(qty);
        $("#txtIPrice").val(price);
    });
});
$("#btnPurchase").click(function () {
    placeOrder();
    clearCustFields();
    generateOrderID();
})

function saveItemDetails() {
    let Id=$("#cmbItem").find(":selected").text();
    let IName=$("#txtIName").val();
    let IPrice=$("#txtIPrice").val();
    let IQty=$("#txtOQty").val();
    let SQty=$("#txtIQty").val();
    $("#txtIQty").val(SQty-IQty);
    var IList=new ItemDetails(Id,IName,IPrice,IQty);
    itemList.push(IList);
}
function loadAllItemDetails() {
    $("#itemDetailsBody").empty();
    for (var i in itemList){
        let row = `<tr><td>${itemList[i].getItemsID()}</td><td>${itemList[i].getItemsName()}</td><td>${itemList[i].getItemsPrice()}</td><td>${itemList[i].getItemsQty()}</td></tr>`;
        $("#itemDetailsBody").append(row);
    }
}
function updateItemDetails(obj) {
    for (let i=0;i<itemList.length;i++){
        if(itemList[i].getItemsID()==obj.getItemsID()){
            itemList[i].setItemsID(obj.getItemsID());
            itemList[i].setItemsName(obj.getItemsName());
            itemList[i].setItemsQty(obj.getItemsQty());
            itemList[i].setItemsPrice(obj.getItemsPrice());
        }
    }
}
function setTotal(){
        var total=0;
        for (var i of itemList) {
            total=total+(i.getItemsPrice()*i.getItemsQty());
        }
        return total+"Rs/=";

}
function updateTotal(Id){
    var response=searchItemDetails(Id);
    let qty=parseInt(response.getItemsQty());
    let price=parseInt(response.getItemsPrice());
    let pTot=parseInt($("#txtTotal").val());
    var nTot=pTot-qty*price;
    return nTot+"Rs/=";
}
function searchItemDetails(id) {
    for (let i=0;i<itemList.length;i++){
        if(itemList[i].getItemsID()==id){
            return itemList[i];
        }
    }

}
function balanceDiscount() {
    var tot=parseInt($("#txtTotal").val());
    if (tot>=1000){
        var discount=tot*0.1;
        $("#txtDiscount").val(parseInt(discount));
    }else{
        $("#txtDiscount").val(0);
    }

}
$("#txtCash").on('keyup', function (eventOb) {
    if (eventOb.key == "Enter") {
        let tot = parseInt($("#txtTotal").val());
        let dis = parseInt($("#txtDiscount").val());
        let cash = parseInt($("#txtCash").val());
        let aDiscount=tot-dis;
        let balance =cash - aDiscount;
        $("#txtBalance").val(parseInt(balance));
        $("#btnPurchase").attr('disabled', false);
    }
});
function placeOrder() {
    let oID = $("#orderId").val();
    let date = $("#txtDate").val();
    let custID = $('#cmbCustomer').find(":selected").text();
   if(confirm("Do you want to place this Order....?")) {
        var a = itemList;
        updateItemQTY();
        var order = new Order(oID, custID, date, a);
        orderDB.push(order);
         itemList = [];
    }
}

function updateItemQTY() {
    for (var i of itemList) {
        for (var j of itemDB) {
            if (j.getItemID()===i.getItemsID()){
                j.setItemQty(j.getItemQty()-i.getItemsQty());
            }

        }

    }
}
$("#txtOQty").click(function () {
    $("#btnAddItem").attr('disabled', false);
});
function clearTextFields() {
    $("#cmbItem,#txtIName,#txtIPrice,#txtIQty,#txtOQty").val("");
    $("#btnAddItem").attr('disabled', true);
    $("#btnUpdateItem").attr('disabled', true);
}
function clearCustFields() {
    $("#cmbCustomer,#txtCustName,#txtCustAge,#txtCustTell").val("");
    $("#btnAddItem").attr('disabled', true);
    $("#btnUpdateItem").attr('disabled', true);
    $("#btnPurchase").attr('disabled', true);
    $("#txtTotal,#txtDiscount,#txtCash,#txtBalance").val("");
    $("#itemDetailsBody").empty();
}
function checkQty(iQty,sQty){
    if (iQty>sQty){
        return true;
    }else{
        return false;
    }
}


