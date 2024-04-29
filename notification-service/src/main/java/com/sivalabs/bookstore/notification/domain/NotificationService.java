package com.sivalabs.bookstore.notification.domain;

import com.sivalabs.bookstore.notification.ApplicationProperties;
import com.sivalabs.bookstore.notification.domain.model.OrderCancelledEvent;
import com.sivalabs.bookstore.notification.domain.model.OrderCreatedEvent;
import com.sivalabs.bookstore.notification.domain.model.OrderDeliveredEvent;
import com.sivalabs.bookstore.notification.domain.model.OrderErrorEvent;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final JavaMailSender emailSender;
    private final ApplicationProperties properties;

    public NotificationService(JavaMailSender emailSender, ApplicationProperties properties) {
        this.emailSender = emailSender;
        this.properties = properties;
    }

    public void sendOrderCreatedNotification(OrderCreatedEvent event) {
        var message =
                """
                ========================================
                Order Created Notification
                ----------------------------------------
                Dear %s,
                Your order with number %s has been created successfully.

                Thanks,
                BookStore Team
                ========================================
                """
                        .formatted(event.customer().name(), event.orderNumber());

        log.info("\n{}", message);
        sendEmail(event.customer().email(), "Order Created Notification", message);
    }

    public void sendOrderDeliveredNotification(OrderDeliveredEvent event) {
        var message =
                """
                ========================================
                Order Delivered Notification
                ----------------------------------------
                Dear %s,
                Your order with number %s has been delivered successfully.

                Thanks,
                BookStore Team
                ========================================
                """
                        .formatted(event.customer().name(), event.orderNumber());

        log.info("\n{}", message);
        sendEmail(event.customer().email(), "Order Delivered Notification", message);
    }

    public void sendOrderCancelledNotification(OrderCancelledEvent event) {
        var message =
                """
                ========================================
                Order Cancelled Notification
                ----------------------------------------
                Dear %s,
                Your order with number %s has been cancelled.
                Reason: %s

                Thanks,
                BookStore Team
                ========================================
                """
                        .formatted(event.customer().name(), event.orderNumber(), event.reason());

        log.info("\n{}", message);
        sendEmail(event.customer().email(), "Order Cancelled Notification", message);
    }

    public void sendOrderErrorNotification(OrderErrorEvent event) {
        var message =
                """
                ========================================
                Order Processing Failure Notification
                ----------------------------------------
                Hi %s,
                The order processing failed for order number %s.
                Reason: %s

                Thanks,
                BookStore Team
                ========================================
                """
                        .formatted(properties.supportEmail(), event.orderNumber(), event.reason());

        log.info("\n{}", message);
        sendEmail(properties.supportEmail(), "Order Processing Failure Notification", message);
    }

    private void sendEmail(String recipient, String subject, String content) {
        try {
            var mimeMessage = emailSender.createMimeMessage();
            var helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setFrom(properties.supportEmail());
            helper.setTo(recipient);
            helper.setSubject(subject);
            helper.setText(content);

            emailSender.send(mimeMessage);
            log.info("Email sent to {}", recipient);
        } catch (MessagingException e) {
            throw new RuntimeException("Error while sending email", e);
        }
    }
}
