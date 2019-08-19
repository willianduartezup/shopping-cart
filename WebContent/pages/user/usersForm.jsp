<%@ page contentType="text/html;charset=UTF-8"%>
<form method="post" id="usersForm" onsubmit="return onSubmit(this)">
    <table>
        <tr>
            <td>Name:</td>
            <td>
                <label>
                    <input type="text" name="name" id="nameBox" required/>
                </label>
            </td>
        </tr>
        <tr>
            <td>Email:</td>
            <td>
                <label>
                    <input type="text" name="email" id="emailBox" required/>
                </label>
            </td>
        </tr>
        <tr>
            <td>Password:</td>
            <td>
                <label>
                    <input type="password" name="password" id="passwordBox" required/>
                </label>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <button style="float: right" type="submit">add</button>
            </td>
        </tr>
    </table>
</form>
<script type="text/javascript" src="js/page/user/usersForm.js"></script>
