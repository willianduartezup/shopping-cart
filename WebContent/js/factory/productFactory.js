var productFactory = {};

productFactory.add = function (product, callback) {
    request.post('product', product, callback);
};

productFactory.list = function (callback){
    request.get('product', null, callback);
};

productFactory.get = function (id, callback) {
    request.get('product/'+ id, null, callback);
};