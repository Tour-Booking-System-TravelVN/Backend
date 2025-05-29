package com.travelvn.tourbookingsytem.controller.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.travelvn.tourbookingsytem.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.payos.PayOS;
import vn.payos.type.Webhook;
import vn.payos.type.WebhookData;

@Slf4j
@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
  private final PayOS payOS;
  private final BookingService bookingService;

//  public PaymentController(PayOS payOS) {
//    super();
//    this.payOS = payOS;
//
//  }

  @PostMapping(path = "/payos_transfer_handler")
  public ObjectNode payosTransferHandler(@RequestBody ObjectNode body)
      throws JsonProcessingException, IllegalArgumentException {

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode response = objectMapper.createObjectNode();
    Webhook webhookBody = objectMapper.treeToValue(body, Webhook.class);

    log.info("Chạy vào webhook");

    try {
      // Init Response
      response.put("error", 0);
      response.put("message", "Webhook delivered");
      response.set("data", null);

      WebhookData data = payOS.verifyPaymentWebhookData(webhookBody);

      //Lấy ra chữ ký
      String signature = webhookBody.getSignature();

      log.info("Signature: {}", signature);
      log.info(String.valueOf(data.getOrderCode()));

      //Lưu dữ liệu thanh toán thành công
      //Cập nhật booking và tourunit
      bookingService.payBooking(data.getOrderCode());

      // Check trạng thái
//      if (webhookBody.getSuccess()) {
//        // Lưu booking vào database
//
//        // Ví dụ:
////        String orderId = data.getOrderCode(); // bạn đã dùng orderCode để mapping với client
////        bookingService.saveBookingAfterPayment(orderId);
//      } else {
//        // Xử lý các trạng thái khác như "CANCELLED", "FAILED", v.v.
////        System.out.println("Payment status: " + data.getStatus());
//      }

      System.out.println(data);
      return response;
    } catch (Exception e) {
      e.printStackTrace();
      response.put("error", -1);
      response.put("message", e.getMessage());
      response.set("data", null);
      return response;
    }
  }
}
