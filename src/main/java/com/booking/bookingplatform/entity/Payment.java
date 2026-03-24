package com.booking.bookingplatform.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    private String transactionId;

    private LocalDateTime createdAt;

    // 🔥 SCALABLE DESIGN
    private Long referenceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "reference_type", columnDefinition = "VARCHAR(20)")
    private PaymentReferenceType referenceType;


    public Payment() {}

    // ===== GETTERS & SETTERS =====

    public Long getId() { return id; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }

    public PaymentMethod getMethod() { return method; }
    public void setMethod(PaymentMethod method) { this.method = method; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Long getReferenceId() { return referenceId; }
    public void setReferenceId(Long referenceId) { this.referenceId = referenceId; }

    public PaymentReferenceType getReferenceType() { return referenceType; }
    public void setReferenceType(PaymentReferenceType referenceType) {
        this.referenceType = referenceType;
    }
}
