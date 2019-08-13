const cartFactory = {};

cartFactory.get = function(userId, callback){
    request.get("cart/"+ userId, null, callback);
};