<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<tags:layout>
    <jsp:body>
        <form class="w-50 mx-auto">
            <h2 class="my-3">Register</h2>
            <div class="form-group">
                <label for="name">Full name</label>
                <input class="form-control" type="text" id="name"/>
            </div>
            <div class="form-group">
                <label for="email">Email</label>
                <input class="form-control" type="email" id="email"/>
            </div>
            <div class="form-group mt-5">
                <label for="password">Password</label>
                <input class="form-control" type="password" id="password"/>
            </div>
            <div class="form-group">
                <label for="password_confirmation">Password Confirmation</label>
                <input class="form-control" type="password" id="password_confirmation"/>
            </div>
            <div class="text-right">
                <button class="btn btn-primary">Register</button>
            </div>
        </form>
    </jsp:body>
</tags:layout>