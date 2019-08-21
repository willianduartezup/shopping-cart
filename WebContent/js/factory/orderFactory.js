const orderFactory = {};

orderFactory.create = function (idUser, callBack) {
    request.post('order/'+ idUser, {}, callBack)
};