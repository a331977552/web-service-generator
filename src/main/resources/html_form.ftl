<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title}</title>
</head>
<body>
<form action="${className?uncap_first}/add" method="POST">
	<#list fields as field>
	<input type="text"  placeholder="${field.name}" name="${field.name}"><br/>
	</#list>
	<input type="submit" value="submit">
</form>


</body>
</html>