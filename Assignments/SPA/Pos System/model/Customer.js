function Customer(id,name,age,tel){
    var customerId=id;
    var customerName=name;
    var customerAge=age;
    var customerTP=tel;

    this.getCustID=function () {
        return customerId;

    }
    this.getCustName=function () {
        return customerName;

    }
    this.getCustAge=function () {
        return customerAge;

    }
    this.getCustTP=function () {
        return customerTP;

    }
    this.setCustID = function (v) {
        customerId=v;
    }

    this.setCustName = function (v) {
        customerName=v;
    }

    this.setCustAge = function (v) {
        customerAge=v;
    }

    this.setCustTP = function (v) {
        customerTP=v;
    }
}