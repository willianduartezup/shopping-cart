function loadUsers(){
    const tableRef = document.getElementById('listUser').getElementsByTagName('tbody')[0];

    tableRef.innerHTML = "";

    const newRowNotFound = tableRef.insertRow();

    let cell = newRowNotFound.insertCell(0);

    cell.style.textAlign = "center";
    cell.colSpan = 5;
    cell.innerHTML = "No users found";

    userFactory.list(function (res) {
        const list = JSON.parse(res);

        if (list.length > 0){
            tableRef.innerHTML = "";

            list.map(function (user) {
                const newRow   = tableRef.insertRow();

                let name  = newRow.insertCell(0);
                name.style.textAlign = "center";
                name.innerHTML = user.name;

                let email  = newRow.insertCell(1);
                email.style.textAlign = "center";
                email.innerHTML = user.email;

                let actions  = newRow.insertCell(2);
                actions.style.textAlign = "center";
                actions.innerHTML = "<a href=\"index.jsp?page=user/usersForm&id="+ user.id +"\">update</a>\n" +
                    "<a href=\"index.jsp?page=cart/manager&user_id="+ user.id +"\">cart</a>";
            });
        }
    });
}

loadUsers();