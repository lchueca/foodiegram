package main.paypal;

import java.util.Locale;

public class PaymentDetails {

    private String sponsorType;
    private float subtotal;
    private float shipping;
    private float tax;
    private float total;

    public PaymentDetails(String sponsorType, String subtotal,
                       String shipping, String tax, String total) {
        this.sponsorType = sponsorType;
        this.subtotal = Float.parseFloat(subtotal);
        this.shipping = Float.parseFloat(shipping);
        this.tax = Float.parseFloat(tax);
        this.total = Float.parseFloat(total);
    }

    public PaymentDetails(String type) {
        this.sponsorType = type;

        switch(type) {

            case "1":
                this.subtotal = 10;
                break;

            case "2":
                this.subtotal = 25;
                break;


            case "3":
                this.subtotal = 45;
                break;

            case "4":
                this.subtotal = 85;
                break;
        }

        this.tax = this.subtotal * 0.21f;
        this.shipping = 0.0f;
        this.total = this.subtotal + this.tax;

    }

    public String getSponsorType() {

        return sponsorType;
    }

    public String getSubtotal() {
        return String.format(Locale.ENGLISH, "%.2f", subtotal);
    }
    public String getShipping() {
        return String.format(Locale.ENGLISH,"%.2f", shipping);
    }

    public String getTax() {
        return String.format(Locale.ENGLISH,"%.2f", tax);
    }

    public String getTotal() {
        return String.format(Locale.ENGLISH,"%.2f", total);
    }
}
