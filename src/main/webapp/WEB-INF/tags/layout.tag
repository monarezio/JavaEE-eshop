<%@tag description="Page layout template" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Shop</title>
    <link href="assets/dist/bundle.css" rel="stylesheet" type="text/css">
    <script src="assets/dist/bundle.js" type="text/javascript"></script>
</head>
<body>
<header class="position-fixed w-100">
    <nav class="navbar navbar-expand-lg navbar-light shadow-sm">
        <a class="navbar-brand" href="#">Furniture</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="#">Beds<span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Kitchens</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Sinks</a>
                </li>
            </ul>
            <ul class="navbar-nav">
                <li class="nav-item mr-2">
                    <a class="nav-link" href="#">Login</a>
                </li>
                <li>
                    <a class="btn btn-primary" href="#">Register</a>
                </li>
            </ul>
        </div>
    </nav>
</header>
<div class="min-vh-100 pt-5 d-flex flex-column">
    <main class="container mt-3 flex-grow-1">
        <jsp:doBody/>
    </main>
    <footer class="container py-2 border-top">
        &copy; Samuel Kodytek
    </footer>
</div>
</body>
</html>
