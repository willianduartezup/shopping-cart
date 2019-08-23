const creditCardFactory = {};

creditCardFactory.list = function(user_id, callback){
    request.get('creditCart/'+ user_id, null, callback);
};

creditCardFactory.create = function (credit_card, callback) {
    request.post('creditCart', credit_card, callback);
};