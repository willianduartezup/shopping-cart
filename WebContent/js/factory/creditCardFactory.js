const creditCardFactory = {};

creditCardFactory.list = function(user_id, callback){
    request.get('credit_card/'+ user_id, null, callback);
};

creditCardFactory.create = function (credit_card, callback) {
    request.post('credit_card', credit_card, callback);
};