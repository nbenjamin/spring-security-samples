<#import "/spring.ftl" as spring>
<html>
<head></head>
<body>
    <ul>
        <#list books as book>
            <li> ${book.name}</li>
        </#list>
    </ul>
</body>
</html>