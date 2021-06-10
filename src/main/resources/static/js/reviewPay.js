
function volver() {
    window.location.href = "/pruebas/collab";
}

function aceptar() {

    let det = JSON.parse(document.getElementById("paymentDetails").textContent);
    let url = `/sponsor/payment/execute?paymentID=${det.paymentID}&payerID=${det.payerID}`;

    $.post(url, volver);
}
