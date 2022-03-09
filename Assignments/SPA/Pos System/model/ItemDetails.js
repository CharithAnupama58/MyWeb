function ItemDetails(id,name,price,qty,total){
    var itemId=id;
    var itemName=name;
    var itemPrice=price;
    var itemQty=qty;

    this.getItemsID=function () {
        return itemId;

    }
    this.getItemsName=function () {
        return itemName;

    }
    this.getItemsQty=function () {
        return itemQty;

    }
    this.getItemsPrice=function () {
        return itemPrice;

    }

    this.setItemsID = function (v) {
        itemId=v;
    }

    this.setItemsName = function (v) {
        itemName=v;
    }

    this.setItemsQty = function (v) {
        itemQty=v;
    }

    this.setItemsPrice = function (v) {
        itemPrice=v;
    }
}