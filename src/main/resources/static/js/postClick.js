function onPostDataReceived(data) {

    document.getElementById("post-modal").style.display="block";
    document.getElementById("post-modal").style.zIndex= "10";
    document.getElementById("post-modal-image").src = data.image;
    document.getElementById("post-modal-text").innerText = data.text;
    document.getElementById("post-modal-avgRating").innerText = data.media + '⭐';
    document.getElementById("post-modal-ratingAmount").innerText = data.numerototalval + '🧐';

    var dim = document.createElement("div");
    dim.id = "page-mask";
    document.body.appendChild(dim);
}


function onPostClicked(e) {

    const postID = e.dataset.postid;
    const url = "https://localhost:8080/posts/" + postID;

    var req = new XMLHttpRequest();
    req.open('GET', url, true);
    req.onload  = () => {
        var jsonResponse = JSON.parse(req.responseText);
        onPostDataReceived(jsonResponse);
    };
    req.send(null);

}

document.addEventListener("click", e => {
    var modal = document.getElementById("post-modal");

    if (modal.style.display === "block" && (e.target.matches(".button-close-modal") || !e.target.closest(".modal")))
        modal.style.display = "none";
        modal.style.zIndex = "-1";
        document.getElementById("page-mask").remove();

    }

)