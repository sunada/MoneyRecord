<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<head>
    <title>点击表头排序</title>
    <script src="/resources/js/sortTable.js" type="text/javascript"></script>
</head>
<body>
<table id="MyHeadTab" cellspacing="0" onclick="sortColumn(event)">

    <thead>
    <tr>
        <td><a href="#">StringName</a></td>
        <td title="CaseInsensitiveString"> <a href="#">String</a></td>
        <td> <a href="#">Number</a></td>
        <td> <a href="#">Date</a></td>
        <td> <a href="#">No Sort</a></td>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>apple</td>
        <td>Strawberry</td>
        <td>45</td>
        <td>2001-03-13</td>
        <td>Item 0</td>
    </tr>
    <tr>
        <td>Banana</td>
        <td>orange</td>
        <td>7698</td>
        <td>1789-07-14</td>
        <td>Item 1</td>
    </tr>
    <tr>
        <td>orange</td>
        <td>Banana</td>
        <td>4546</td>
        <td>1949-07-04</td>
        <td>Item 2</td>
    </tr>
    <tr>
        <td>Strawberry</td>
        <td>apple</td>
        <td>987</td>
        <td>1975-08-19</td>
        <td>Item 3</td>
    </tr>
    <tr>
        <td>Pear</td>
        <td>blueberry</td>
        <td>98743</td>
        <td>2001-01-01</td>
        <td>Item 4</td>
    </tr>
    <tr>
        <td>blueberry</td>
        <td>Pear</td>
        <td>4</td>
        <td>2001-04-18</td>
        <td>Item 5</td>
    </tr>
    </tbody>
</table>
</body>
</html>