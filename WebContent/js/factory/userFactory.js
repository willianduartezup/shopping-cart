let userFactory = {};

userFactory.add = function (user, callback) {
    request.post('user', user, callback);
};

userFactory.update = function (product, callback) {
    request.put('user', product, callback);
};

userFactory.list = function (callback){
    request.get('user', null, callback);
};

userFactory.get = function (id, callback) {
    request.get('user/'+ id, null, callback);
};