function loadUsers(){
    const tableRef = document.getElementById('listUser').getElementsByTagName('tbody')[0];

    const newRowNotFound = tableRef.insertRow();

    let cell = newRowNotFound.insertCell(0);

    cell.style.textAlign = "center";
    cell.colSpan = 5;
    cell.innerHTML = "No users found";

    userFactory.list(function (res) {
        const list = JSON.parse(res);

        if (list.length > 0){
            tableRef.deleteRow(0);

            list.map(function (user) {
                const newRow   = tableRef.insertRow();

                const name  = newRow.insertCell(0);
                name.style.textAlign = "center";
                name.innerHTML = user.name;

                const quantity  = newRow.insertCell(1);
                quantity.style.textAlign = "center";
                quantity.innerHTML = user.email;

                const actions  = newRow.insertCell(2);
                actions.style.textAlign = "center";
                actions.innerHTML = "<a href=\"index.jsp?page=product/form&id="+ user.id +"\">update</a>\n"/* +
                    "<a href=\"index.jsp?page=product/list\">remove</a>"*/;
            });
        }
    });
}

loadUsers();