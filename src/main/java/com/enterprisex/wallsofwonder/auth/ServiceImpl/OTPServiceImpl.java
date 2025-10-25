package com.enterprisex.wallsofwonder.auth.ServiceImpl;


import com.enterprisex.wallsofwonder.auth.Services.OTPService;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Random;

@Service
public class OTPServiceImpl implements OTPService {
    @Override
    public String generateOtp(String phoneNumber) {
        return getRandomOTP();
    }

    @Override
    public Boolean verifyOtp(String phoneNumber, String Otp) {
        return null;
    }

    public String getRandomOTP() {
        // TODO Auto-generated method stub
        String otp =  new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
//	        otpCache.put(phoneNo,otp);
        int intOtp = Integer.parseInt(otp);
        if(String.valueOf(intOtp).length()==5){
            int addOtp = intOtp +100000;
            return  String.valueOf(addOtp);
        }
        return otp;
    }
}
