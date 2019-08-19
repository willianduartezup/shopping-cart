function getUser() {
    const id = url.findGetParameter('id');

    if (id) {
        userFactory.get(id, function (res) {
            const user = JSON.parse(res);
            document.getElementById("nameBox").value = user.name;
            document.getElementById("emailBox").value = user.email;
        });
    }
}

function onSubmit(usersForm) {
    const id = url.findGetParameter('id');

    let user = {};
    user.name = usersForm.name.value;
    user.email = usersForm.email.value;
    user.password = usersForm.password.value;

    if (id){
        user.id = id;
        userFactory.update(user, function () {
            window.location.href="index.jsp?page=user/usersList"
        });
    }else{
        userFactory.add(user, function () {
            document.getElementById("usersForm").reset();

        });

    }

    return false;

}

getUser();
