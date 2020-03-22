<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE >
<html>
<head>
    <title>Upload files</title>
</head>
<body>

<div style="padding:5px; color:red;font-style:italic;">
    ${errorMessage}
</div>

<h2>Upload Files</h2>

<form method="post" action='<c:url value="/uploadFile" />'
      enctype="multipart/form-data">

    Select file to upload:
    <br />
    <input type="file" name="file"  />
    <br />
    <br />
    <input type="hidden" name="impOrExp" value="${impOrExp}">
    <input type="submit" value="Upload" />
</form>

</body>
</html>
