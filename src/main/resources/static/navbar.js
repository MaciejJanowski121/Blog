document.addEventListener("DOMContentLoaded", async function () {
    const navbarContainer = document.getElementById("navbar-container");
    navbarContainer.innerHTML = `
        <nav class="navbar">
            <a href="home.html">Home</a>
            <a href="allPosts.html">All Posts</a>
            <a href="addPost.html">Create a Post</a>
            <a href="register.html" id="register-link">Register</a>
            <a href="login.html" id="auth-link">Login</a>
        </nav>
    `;

    try {
        const response = await fetch("/auth/check-auth", {
            method: "GET",
            credentials: "include"
        });

        if (response.ok) {
            document.getElementById("auth-link").textContent = "Logout";
            document.getElementById("auth-link").href = "#";
            document.getElementById("auth-link").addEventListener("click", async () => {
                await fetch("/auth/logout", { method: "POST", credentials: "include" });
                window.location.href = "login.html";
            });

            document.getElementById("register-link").style.display = "none";
        }
    } catch (error) {
        console.error("Auth check failed:", error);
    }
});