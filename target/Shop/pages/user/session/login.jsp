<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix = "h" uri = "http://java.sun.com/jsf/html" %>
<tags:layout>
    <jsp:body>
        <h:form class="w-50 mx-auto">
            <h2 class="my-3">Login</h2>
            <div class="form-group">
                <label for="email">Email</label>
                <inputText require="true" class="form-control" type="email" id="email"/>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input class="form-control" type="password" id="password"/>
            </div>
            <div class="text-right">
                <button class="btn btn-primary">Login</button>
            </div>
        </h:form>
    </jsp:body>
</tags:layout>
