let userFactory = {};

userFactory.addUser = function (user, callback) {
    request.post('user', user, callback);
};