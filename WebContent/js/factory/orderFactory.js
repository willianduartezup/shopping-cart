const orderFactory = {};

orderFactory.get = function (idOrder, callback) {
    request.get('sales/'+ idOrder, null, callback)
};

orderFactory.create = function (idUser, credit_id, callBack) {
    request.post('sales/'+ idUser, {credit_id : credit_id}, callBack)
};

orderFactory.list = function (idUser, callback) {
    if(idUser){
        request.get('history/'+ idUser, null, callback);
    }else{
        request.get('history', null, callback);
    }
};
