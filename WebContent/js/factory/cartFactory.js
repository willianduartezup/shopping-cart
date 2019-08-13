const cartFactory = {};

cartFactory.get = function(userId, callback){
    request.get("cart/"+ userId, null, callback);
};

cartFactory.add = function (item, callback) {
    request.post('cart', item, callback);
};