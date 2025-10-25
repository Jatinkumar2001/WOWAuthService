package com.enterprisex.wallsofwonder.auth.Services;

public interface OTPService {
    String generateOtp(String  phoneNumber);
    Boolean verifyOtp(String phoneNumber,String Otp);

}
