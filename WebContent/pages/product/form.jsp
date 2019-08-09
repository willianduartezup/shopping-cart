<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form action="index.jsp">
    <table>
        <tr>
            <td>name:</td>
            <td>
                <input type="text" name="name"/>
            </td>
        </tr>
        <tr>
            <td>price:</td>
            <td>
                <input type="text" name="price"/>
            </td>
        </tr>
        <tr>
            <td>unid:</td>
            <td>
                <input type="text" name="unid"/>
            </td>
        </tr>
        <tr>
            <td>quantity:</td>
            <td>
                <input type="text" name="quantity"/>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <button style="float: right" type="submit">add</button>
            </td>
        </tr>
    </table>
</form>