let username = document.getElementById("username");
let password = document.getElementById("password");
let passwordConfirmation = document.getElementById("confirm-password");
let submit = document.querySelector("input[type='submit']"  );
let paragraph = document.querySelector(".signin p");

let formdata = {};

// Add username and password to formdata
for (let input of [username, password]) {
    input.addEventListener('input', (e) => {
        formdata[e.target.name] = e.target.value; 
    });
};

// Handle password confirmation
passwordConfirmation.addEventListener("input", (e) => {
    if (notNull(formdata["password"]) && notNull(formdata["username"])) {
        if (formdata["password"] == passwordConfirmation.value) {
            submit.style.visibility = "visible";
            paragraph.style.visibility = "hidden";
        } else {
            submit.style.visibility = "hidden";
            paragraph.style.visibility = "visible";
        }
    }
});

// Utility
function notNull(string) {
    return string.length > 0;
}