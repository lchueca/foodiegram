package main.rest.controller;

import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.api.payments.Transaction;
import main.application.service.SponsorService;
import main.domain.resource.PatrocinioResource;
import main.paypal.PaymentDetails;
import main.application.service.PaypalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/sponsor")
public class SponsorController {

    // TIPOS DE PATROCINIO (duracion)
    //
    // type=1: 1 mes
    // type=2: 3 meses
    // type=3: 6 meses
    // type=4: 12 meses

    @Autowired
    private SponsorService sponsorService;

    @Autowired
    private PaypalService paypalService;


    // OBTENER PATROCINIO
    //
    // devuelve un patrocinio especifico buscado por id de colaborador
    // devuelve null si no existe
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<PatrocinioResource> getSponsorship(@PathVariable String id) {

        PatrocinioResource sponsorship = sponsorService.getSponsorship(Integer.parseInt(id));
        return sponsorship != null ? ResponseEntity.ok(sponsorship) : ResponseEntity.notFound().build();
    }


    // AUTORIZAR EL PAGO
    //
    // se encarga de autorizar el pago por paypal
    // devuelve el link a la pagina para que haga login en paypal
    // despues redirige a una pagina para continuar/cancelar el proceso (esto es de paypal)
    @RequestMapping(value="/payment/auth", method = RequestMethod.POST)
    public ResponseEntity<String> authorizePayment(@RequestPart("sponsorType") String sponsorType, @RequestPart("subtotal") String subtotal,
                                              @RequestPart("shipping") String shipping, @RequestPart("tax") String tax,
                                              @RequestPart("total") String total) throws Exception {

        try {
            PaymentDetails payment = new PaymentDetails(sponsorType, subtotal, shipping, tax, total);
            String approvalLink = paypalService.authorizePayment(payment);

            return new ResponseEntity<>(approvalLink, HttpStatus.OK);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + ex.getMessage());
        }
    }

    // REVISAR EL PAGO
    //
    // se encarga de hacer la fase intermedia de mostrar el pago al cliente
    // devuelve datos necesarios para construir la pagina donde se muestra la transaccion
    @RequestMapping(value="/payment/review", method = RequestMethod.GET)
    public ResponseEntity<?> reviewPayment(@RequestParam("paymentId") String paymentId,
                                           @RequestParam("PayerID") String payerId) throws Exception {

        try {

            Payment payment = paypalService.getPaymentDetails(paymentId);

            PayerInfo payerInfo = payment.getPayer().getPayerInfo();
            Transaction transaction = payment.getTransactions().get(0);
            ShippingAddress shippingAddress = transaction.getItemList().getShippingAddress();

            // se mete informacion en una lista para poder utilizar los datos en clases superiores
            List<Object> data = new ArrayList<Object>();
            data.add(paymentId);
            data.add(payerId);
            data.add(payerInfo);
            data.add(transaction);
            data.add(shippingAddress);

            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    // EJECUTA EL PAGO
    //
    // se encarga de ejecutar el pago de paypal, despues de
    // ejecutarse con exito se encarga de crear/modificar el patrocinio del colaborador
    // devuelve los datos del patrocinio que se ha creado/modificado
    @RequestMapping(value="/payment/execute", method = RequestMethod.POST)
    public ResponseEntity<?> executePayment(@RequestPart("paymentId") String paymentId,
                                            @RequestPart("PayerID") String payerId, @RequestPart("id") String id,
                                            @RequestPart("type") String type, @RequestPart("money") String money) throws Exception {

        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            PatrocinioResource sponsorship = null;
            // se obtiene el patrocinio actual del colaborador
            if(sponsorService.getSponsorship(Integer.parseInt(id)) == null) { // se crea el patrocinio
                sponsorship = sponsorService.obtain(Integer.parseInt(id), Integer.parseInt(type), Float.parseFloat(money));
            }
            else { // se modifica el patrocinio
                sponsorship = sponsorService.modify(Integer.parseInt(id), Integer.parseInt(type), Float.parseFloat(money));
            }

            return sponsorship != null ? ResponseEntity.ok(sponsorship) : ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}