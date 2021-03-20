<!DOCTYPE html>
<html>
<head>
    <title>Catalog Info!</title>
</head>
<body>

<h1>Catalog information!</h1>

<#list catalog.getMediaItems() as mediaItem>
    <#list mediaItem.getMetadata() as metadata>
        <br>
        ${metadata}
    </#list>
    <br>
</#list>

</body>
</html>