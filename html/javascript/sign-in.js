let submit = document.querySelector("form");
submit.addEventListener("submit", function(event) {
    event.preventDefault();
    let information = [
        document.querySelector('input[type="text"]').value,
        document.querySelector('input[type="password"]').value,
        document.querySelector('input[type="checkbox"]').checked
    ]
    for (let i of information) {
        console.log("-> " + i);
    }
})