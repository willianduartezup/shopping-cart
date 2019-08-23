const creditCardFactory = {};

creditCardFactory.list = function(user_id, callback){
    request.get('creditCart', {userIdt: user_id}, callback);
};

creditCardFactory.create = function (credit_card, callback) {
    request.post('creditCart', credit_card, callback);
};