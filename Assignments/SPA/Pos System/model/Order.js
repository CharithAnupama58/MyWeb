function Order(oID, custID, date, itemList) {
    var oID = oID;
    var custID = custID;
    var date = date;
    var itemList = itemList;

    this.getoID = function () {
        return oID;
    }

    this.getCustomerID = function () {
        return custID;
    }

    this.getdate = function () {
        return date;
    }

    this.getitemList = function () {
        return itemList;
    }

    this.setoID = function (v) {
       oID = v;
    }

    this.setCustomerID = function (v) {
        custID = v;
    }

    this.setdate = function (v) {
       date = v;
    }

    this.setitemList = function (v) {
       itemList = v;
    }


}