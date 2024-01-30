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
            <td>expiration date</td>
        </tr>
        <#foreach productStock in productStocks>
        <tr>
            <td>${productStock.ean}</td>
            <td>${productStock.balance}</td>
            <td>${(productStock.expirationDate).format("yyyy-MM-dd")}</td>
        </tr>
        </#foreach>
    </table>
</body>
</html>