package com.travelvn.tourbookingsytem.type;

import com.travelvn.tourbookingsytem.dto.request.BookingRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CreatePaymentLinkRequestBody {
  private String productName;
  private String description;
  private String returnUrl;
  private int price;
  private String cancelUrl;
  private BookingRequest bookingRequest;
}