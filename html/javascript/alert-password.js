let input = document.querySelector("input[type='password']");
input.addEventListener("focus", () => {
    confirm("This website is not properly secured. Please select an unique password that will not compromise any of your other passwords.");
});