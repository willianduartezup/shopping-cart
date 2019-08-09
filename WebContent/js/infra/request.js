var request = {};

request.getType = function(){
    return new XMLHttpRequest();
};

request.getStatusSuccess = function(){
    return [200, 201, 202, 203, 204, 205, 206, 207, 208, 226];
};

request.post = function(url, callback, callbackError){
    var type = request.getType();
    type.onreadystatechange = function() {
        if (this.readyState === 4 && request.getStatusSuccess().includes(this.status)) {
            callback(this.responseText);
        }else{
            callbackError(this.responseText)
        }
    };
    type.open("POST", url, true);
    type.send();
};