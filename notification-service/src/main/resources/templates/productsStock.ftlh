<html lang="en">
<head>
    <meta charset="UTF-8">
    <style>
    table, th, td {
      border: 1px solid black;
    }
    </style>
</head>

<body>
    <h4>${message}</h4>
    <table style="width:50%">
        <tr>
            <td>ean</td>
            <td>balance</td>
        </tr>
        <#foreach productStock in productsStock>
        <tr>
            <td>${productStock.ean}</td>
            <td>${productStock.balance}</td>
        </tr>
        </#foreach>
    </table>
</body>
</html>