function getUser() {
    const id = url.findGetParameter('id');

    if (id) {
        userFactory.get(id, function (res) {
            const product = JSON.parse(res);
            document.getElementById("name").value = product.name;
            document.getElementById("price").value = product.price;

        });
    }
}

function onSubmit(usersForm) {
    const id = url.findGetParameter('id');

    let user = {};
    user.name = usersForm.name.value;
    user.email = usersForm.email.value;
    userFactory.add(user);

    if (id){
        userFactory.update(user);
    }else{
        userFactory.add(user);
    }
    return false;

}

getUser();
