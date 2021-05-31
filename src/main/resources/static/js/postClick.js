function setUpPostData(data) {

    document.getElementById("post-modal").style.display="block";
    document.getElementById("post-modal").style.zIndex= "10";
    document.getElementById("post-modal-image").src = data.image;
    document.getElementById("post-modal-text").innerText = data.text;
    document.getElementById("post-modal-avgRating").innerText = data.media + '⭐';
    document.getElementById("post-modal-ratingAmount").innerText = data.numerototalval + '🧐';

    let dim = document.createElement("div");
    dim.id = "page-mask";
    document.body.appendChild(dim);
}

function generateComment(comment) {
    let temp = document.getElementsByTagName("template")[0];
    let clon = temp.content.cloneNode(true);

    $.get("/users?id=" + comment.iduser, userInfo => {

        if (userInfo.image)
            clon.children[0].children[0].children[0].children[0].src = userInfo.image;
        else
            clon.children[0].children[0].children[0].children[0].src = "https://icon-library.com/images/no-profile-pic-icon/no-profile-pic-icon-11.jpg";

        clon.children[0].children[0].children[1].children[0].innerHTML = `<p class="username">${userInfo.name}: <span class="comment"> ${comment.text}</span></p>`;
        document.getElementById("post-modal-comments").appendChild(clon);

    });



}

function loadComments(comments) {
    var com;
    for (com of comments) {
        generateComment(com);
    }
}

function onPostClicked(e) {
    $('#post-modal-comments').empty()
    $.get("/posts/" + e.dataset.postid, setUpPostData);
    $.get("/posts/" + e.dataset.postid + "/comments", loadComments);


}

document.addEventListener("click", e => {
    let modal = document.getElementById("post-modal");

    if (modal.style.display === "block" && !e.target.closest(".modal-click-box")) {
        modal.style.display = "none";
        modal.style.zIndex = "-1";
        document.getElementById("page-mask").remove();
    }


    }

)