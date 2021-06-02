package main.paypal;

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

    public String getSponsorType() {

        return sponsorType;
    }

    public String getSubtotal() {
        return String.format("%.2f", subtotal);
    }

    public String getShipping() {
        return String.format("%.2f", shipping);
    }

    public String getTax() {
        return String.format("%.2f", tax);
    }

    public String getTotal() {
        return String.format("%.2f", total);
    }
}
