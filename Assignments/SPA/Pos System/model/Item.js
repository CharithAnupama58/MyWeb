function Item(id,name,qty,price){
    var itemId=id;
    var itemName=name;
    var itemQty=qty;
    var itemPrice=price;

    this.getItemID=function () {
        return itemId;

    }
    this.getItemName=function () {
        return itemName;

    }
    this.getItemQty=function () {
        return itemQty;

    }
    this.getItemPrice=function () {
        return itemPrice;

    }
    this.setItemID = function (v) {
        itemId=v;
    }

    this.setItemName = function (v) {
        itemName=v;
    }

    this.setItemQty = function (v) {
        itemQty=v;
    }

    this.setItemPrice = function (v) {
       itemPrice=v;
    }
}