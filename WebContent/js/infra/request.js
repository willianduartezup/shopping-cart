const request = {};

request.getType = function(callback, callbackError){
    let type = new XMLHttpRequest();
    type.onreadystatechange = function() {
        let res;
        if (this.readyState === 4){
            try{
                res = JSON.parse(this.responseText);
            }catch (e) {
                res = {
                    fields: undefined
                };
            }

            if (request.getStatusSuccess().includes(this.status)) {
                if (res.message){
                    alert(res.message);
                }
                if (request.isFunction(callback)){
                    callback(this.responseText);
                }
            }else{
                if (res.message){
                    alert(res.message);
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
    let urlEncode;
    const type = request.getType(callback, callbackError);
    if(queryParam){
        urlEncode = url + request.formatParams(queryParam);
    }else{
        urlEncode = url;
    }

    type.open("GET", urlEncode, false);
    type.send();
};

request.post = function(url, body, callback, callbackError){
    const type = request.getType(callback, callbackError);

    type.open("POST", url, false);
    type.setRequestHeader("Content-Type", "application/json");
    type.send(JSON.stringify(body));
};

request.put = function(url, body, callback, callbackError){
    const type = request.getType(callback, callbackError);

    type.open("PUT", url, false);
    type.setRequestHeader("Content-Type", "application/json");
    type.send(JSON.stringify(body));
};

request.delete = function(url, queryParam, callback, callbackError){
    let urlEncode;
    const type = request.getType(callback, callbackError);
    if(queryParam){
        urlEncode = url + request.formatParams(queryParam);
    }else{
        urlEncode = url;
    }

    type.open("DELETE", urlEncode, false);
    type.send();
};