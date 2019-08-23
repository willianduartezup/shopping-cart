const cartFactory = {};

cartFactory.get = function(userId, callback){
    request.get("cart", {userIdt:userId}, callback);
};

cartFactory.add = function (user_id, item, callback) {
    request.post('cart/'+ user_id, item, callback);
};

cartFactory.update = function (item, callback) {
    request.put('cart', item, callback);
};

cartFactory.removeItem = function(id, callback){
    request.delete('cart/'+ id, null, callback)
};