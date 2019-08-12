function onSubmit(usersForm) {

    try {
        let user = {};
        user.name = usersForm.name.value;
        user.email = usersForm.email.value;
        userFactory.addUser(user);
        return false;
    }catch (e) {
        console.log(e)
        return false;
    }
}
