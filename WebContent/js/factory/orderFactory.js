const orderFactory = {};

orderFactory.get = function (idUser, callback) {
    request.get('sales/'+ idUser, {}, callback)
};

orderFactory.create = function (idUser, callBack) {
    request.post('sales/'+ idUser, {}, callBack)
};

orderFactory.list = function (idUser, callback) {
    if(idUser){
        request.get('history/'+ idUser, null, callback);
    }else{
        request.get('history', null, callback);
    }
};
