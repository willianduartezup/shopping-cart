<%@ page contentType="text/html;charset=UTF-8"%>
<form method="post">
    <table>
        <tr>
            <td>name:</td>
            <td>
                <label>
                    <input type="text" name="name" id="name"/>
                </label>
            </td>
        </tr>
        <tr>
            <td>price:</td>
            <td>
                <label>
                    <input type="text" name="price" id="price"/>
                </label>
            </td>
        </tr>
        <tr>
            <td>unity:</td>
            <td>
                <label>
                    <input type="text" name="unid" id="unid"/>
                </label>
            </td>
        </tr>
        <tr>
            <td>quantity:</td>
            <td>
                <label>
                    <input type="text" name="quantity" id="quantity"/>
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