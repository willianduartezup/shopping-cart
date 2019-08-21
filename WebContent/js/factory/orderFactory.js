const orderFactory = {};

orderFactory.get = function (idUser, callback) {
    request.get('sales/'+ idUser, {}, callback)
};