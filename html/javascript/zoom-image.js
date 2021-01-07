let images = document.querySelectorAll(".search-results ul li img");

for (let image of images) {
    image.addEventListener("mouseover", () => {
        image.classList.toggle("hover-over");
    })

    image.addEventListener("mouseout", () => {
        image.classList.toggle("hover-over");
    })
}