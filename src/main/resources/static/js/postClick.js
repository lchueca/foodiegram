function loadPublication(data) {

    document.getElementById("post-modal-image").src = data.image;
    document.getElementById("post-modal-text").innerText = data.text;
    document.getElementById("post-modal-ratings").innerHTML = data.media + '<i class="bi bi-star"></i>' + ' ' + data.numerototalval + '<i class="bi bi-person"></i>';
    document.getElementById("post-modal-date").innerText = data.fecha;

    let xx = document.getElementById("post-modal-loc");
    if (data.pais) {

        xx.innerText = data.pais;

        if (data.ciudad)
            xx.innerText += `, ` + data.ciudad;
    }

    else
        xx.innerText = "";



    document.getElementById("page-mask").style.display = "flex";

    document.getElementById("post-modal-comment-input-field").dataset.postid = data.id;
}

function generateComment(comment) {
    let temp = document.getElementsByTagName("template")[0];
    let clon = temp.content.cloneNode(true);

    if (comment.pfp)
        clon.children[0].children[0].children[0].children[0].src = comment.pfp;

    clon.children[0].children[0].children[1].children[0].innerHTML = `<p class="username">${comment.user}: <span class="comment"> ${comment.text}</span></p>`;
    document.getElementById("post-modal-comments").appendChild(clon);

}

function loadComments(comments) {
    // Se vacian los comentarios que habia antes en el modal.
    $('#post-modal-comments').empty();
    comments.forEach(generateComment);}

function onPostClicked(e) {

    document.getElementById("post-modal-comment-input-field").value = "";

    // Se llama a /posts/postID para obtener la info de la publicacion
    $.get("/posts/" + e.dataset.postid, loadPublication);

    // Se llama a /posts/postId/comments para obtener la lista de los comentarios
    $.get("/posts/" + e.dataset.postid + "/comments", loadComments);


}

function sendComment(event) {

    if (!event.keyCode || event.keyCode === 13) {

        let inputField = document.getElementById("post-modal-comment-input-field");

        if (inputField.value.length !== 0) {

            let newComment = {text: inputField.value};
            $.post("/posts/" + inputField.dataset.postid + "/comments", newComment, () =>  $.get("/posts/" + inputField.dataset.postid + "/comments", loadComments));
            inputField.value = "";

        }
    }


}


document.addEventListener("click", e => {

    let pageMask = document.getElementById("page-mask");

    if (pageMask.style.display !== "none" && !e.target.closest(".modal-click-box"))
        document.getElementById("page-mask").style.display = "none";


})

document.addEventListener("keydown", e => {

    if (e.key === "Escape") {
        let pageMask = document.getElementById("page-mask");

        if (pageMask.style.display !== "none")
            document.getElementById("page-mask").style.display = "none";
    }
})