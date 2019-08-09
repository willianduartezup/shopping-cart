<%@ page contentType="text/html;charset=UTF-8"%>
<form action="product" method="post">
    <table>
        <tr>
            <td>name:</td>
            <td>
                <label>
                    <input type="text" name="name"/>
                </label>
                <input type="text" name="name" id="name"/>
            </td>
        </tr>
        <tr>
            <td>price:</td>
            <td>
                <label>
                    <input type="text" name="price"/>
                </label>
                <input type="text" name="price" id="price"/>
            </td>
        </tr>
        <tr>
            <td>unity:</td>
            <td>
                <label>
                    <input type="text" name="unity"/>
                </label>
                <input type="text" name="unid" id="unid"/>
            </td>
        </tr>
        <tr>
            <td>quantity:</td>
            <td>
                <label>
                    <input type="text" name="quantity"/>
                </label>
                <input type="text" name="quantity" id="quantity"/>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <button style="float: right" type="submit">add</button>
            </td>
        </tr>
    </table>
