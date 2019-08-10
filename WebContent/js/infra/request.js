var request = {};

request.getType = function(callback, callbackError){
    var type = new XMLHttpRequest();
    type.onreadystatechange = function() {
        if (this.readyState === 4){
            var res = JSON.parse(this.responseText);

            if (res.message){
                alert(res.message);
            }

            if (request.getStatusSuccess().includes(this.status)) {
                if (request.isFunction(callback)){
                    callback(this.responseText);
                }
            }else{
                if (request.isFunction(callbackError)) {
                    callbackError(this.responseText);
                }
            }
        }
    };

    return type;
};

request.getStatusSuccess = function(){
    return [200, 201, 202, 203, 204, 205, 206, 207, 208, 226];
};

request.isFunction = function(fn){
    return fn && {}.toString.call(fn) === '[object Function]';
}

request.post = function(url, body, callback, callbackError){
    var type = request.getType(callback, callbackError);

    type.open("POST", url, true);
    type.setRequestHeader("Content-Type", "application/json");
    type.send(JSON.stringify(body));
};