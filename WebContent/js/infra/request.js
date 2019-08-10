var request = {};

request.getType = function(callback, callbackError){
    var type = new XMLHttpRequest();
    type.onreadystatechange = function() {
        if (this.readyState === 4){
            try{
                var res = JSON.parse(this.responseText);
            }catch (e) {
                var res = {}
            }

            if (request.getStatusSuccess().includes(this.status)) {
                if (res.message){
                    alert(res.message);
                }

                if (request.isFunction(callback)){
                    callback(this.responseText);
                }
            }else{
                if (this.status === 400){
                    var message = res.message +': ';

                    res.fields.map(function (obj) {
                        message += '- '+ obj.field +': '+ obj.message;
                    });

                    alert(message);
                }else{
                    alert('We were unable to respond to your request. Please try again later.');
                }

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
};

request.formatParams = function(queryParam){
    return "?" + Object
        .keys(queryParam)
        .map(function(key){
            return key+"="+encodeURIComponent(queryParam[key])
        })
        .join("&");
};

request.get = function(url, queryParam, callback, callbackError){
    var type = request.getType(callback, callbackError);
    if(queryParam){
        var urlEncode = url + request.formatParams(queryParam);
    }else{
        var urlEncode = url;
    }

    type.open("GET", urlEncode, true);
    type.send();
};

request.post = function(url, body, callback, callbackError){
    var type = request.getType(callback, callbackError);

    type.open("POST", url, true);
    type.setRequestHeader("Content-Type", "application/json");
    type.send(JSON.stringify(body));
};