function getUser(){
    const user_id = url.findGetParameter("user_id");

    if (user_id){
        alert(user_id);
    }
}