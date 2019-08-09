<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form action="product" method="post">
    <table>
        <tr>
            <td>name:</td>
            <td>
                <input type="text" name="name" id="name"/>
            </td>
        </tr>
        <tr>
            <td>price:</td>
            <td>
                <input type="text" name="price" id="price"/>
            </td>
        </tr>
        <tr>
            <td>unid:</td>
            <td>
                <input type="text" name="unid" id="unid"/>
            </td>
        </tr>
        <tr>
            <td>quantity:</td>
            <td>
                <input type="text" name="quantity" id="quantity"/>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <button style="float: right" type="submit">add</button>
            </td>
        </tr>
    </table>
</form>