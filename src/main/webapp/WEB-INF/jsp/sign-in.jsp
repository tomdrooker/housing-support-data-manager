<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Please sign in</title>
</head>
<body>

    <h1>Sign in</h1>

    <form:form modelAttribute="userDetails">

        <label for="username">Enter your username</label>
        <input path="username" type="text" id="username" name="username">

        <label for="password">Enter your password</label>
        <input path="password" type="password" id="password" name="password">

        <input type="submit" value="Sign in">

    </form:form>

</body>
</html>
