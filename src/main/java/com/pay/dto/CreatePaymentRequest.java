package com.pay.dto;

public class CreatePaymentRequest {
    private double amount;
    private String currency;
    private String paymentMethod;   // e.g., "card"
    private String paymentMethodId; // REQUIRED from frontend
    private String cardHolder;
    private String description;

    // getters/setters
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getPaymentMethodId() { return paymentMethodId; }
    public void setPaymentMethodId(String paymentMethodId) { this.paymentMethodId = paymentMethodId; }
    public String getCardHolder() { return cardHolder; }
    public void setCardHolder(String cardHolder) { this.cardHolder = cardHolder; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}