package com.enterprisex.wallsofwonder.auth.Controllers;

import com.enterprisex.wallsofwonder.auth.DTO.Requests.UserLoginRequest;
import com.enterprisex.wallsofwonder.auth.DTO.Requests.UserRegisterRequest;
import com.enterprisex.wallsofwonder.auth.DTO.Response.ResponseHandler;
import com.enterprisex.wallsofwonder.auth.DTO.UserDTO;
import com.enterprisex.wallsofwonder.auth.Enums.SignupType;
import com.enterprisex.wallsofwonder.auth.ServiceImpl.UserDetail;
import com.enterprisex.wallsofwonder.auth.Services.OTPService;
import com.enterprisex.wallsofwonder.auth.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;


    @Autowired
    private OTPService otpService;


    @PostMapping("user/register")
    public ResponseEntity<ResponseHandler> userRegister(@RequestBody UserRegisterRequest request) {

        try {
            String otp = otpService.generateOtp(request.getPhone());
            userService.userRegister(request, SignupType.PHONE_NUMBER,otp);
            ResponseHandler responseHandler = new ResponseHandler();
            responseHandler.setMessage("Registered Successfully");
            responseHandler.setData(null);
            responseHandler.setIsSuccess(true);
            return new ResponseEntity<>(responseHandler, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseHandler responseHandler = new ResponseHandler();
            responseHandler.setMessage(e.getMessage());
            responseHandler.setIsSuccess(false);
            return new ResponseEntity<>(responseHandler, HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("user/login")
    public ResponseEntity<?> userLogin(@RequestBody UserLoginRequest request) {

        ResponseHandler responseHandler = new ResponseHandler();
        try {
            UserDTO response = userService.userLogin(request);
            responseHandler.setData(response);
            responseHandler.setMessage("Successfully Login");
            return new ResponseEntity<>(responseHandler, HttpStatus.OK);

        }catch (BadCredentialsException e){
            e.printStackTrace();
            responseHandler.setStatus(HttpStatus.FORBIDDEN.value());
            responseHandler.setMessage(e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            responseHandler.setMessage(e.getMessage());
        }
        return new ResponseEntity<>(responseHandler, HttpStatus.OK);
    }

    @GetMapping("user/profile")
    public ResponseEntity<?> userProfile() {

        ResponseHandler responseHandler = new ResponseHandler();
        try {
            UserDTO response = userService.userProfile();
            responseHandler.setData(response);
            responseHandler.setMessage("Successfully Retrieved");
            return new ResponseEntity<>(responseHandler, HttpStatus.OK);

        }catch (BadCredentialsException e){
            e.printStackTrace();
            responseHandler.setStatus(HttpStatus.FORBIDDEN.value());
            responseHandler.setMessage(e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            responseHandler.setMessage(e.getMessage());
        }
        return new ResponseEntity<>(responseHandler, HttpStatus.OK);
    }


    @PostMapping("admin/login")
    public ResponseEntity<?> adminLogin(@RequestBody UserLoginRequest request) {

        ResponseHandler responseHandler = new ResponseHandler();
        try {
            UserDTO response = userService.adminLogin(request);
            responseHandler.setData(response);
            responseHandler.setIsSuccess(true);
            responseHandler.setMessage("Successfully Retrieved");
            return new ResponseEntity<>(responseHandler, HttpStatus.OK);

        }catch (BadCredentialsException e){
            e.printStackTrace();
            responseHandler.setStatus(HttpStatus.FORBIDDEN.value());
            responseHandler.setIsSuccess(false);
            responseHandler.setMessage(e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            responseHandler.setIsSuccess(false);
            responseHandler.setMessage(e.getMessage());
        }
        return new ResponseEntity<>(responseHandler, HttpStatus.OK);
    }

    @GetMapping("auth/validate")
    public ResponseEntity<?> validateToken() {
        ResponseHandler responseHandler = new ResponseHandler();
        try {
            UserDetail response = userService.validateToken();
           if(response!=null){
               responseHandler.setIsSuccess(true);
               responseHandler.setMessage("Token Validated");
               responseHandler.setData(response);
           }else {
               responseHandler.setIsSuccess(false);
               responseHandler.setMessage("Token is Invalid or Expire ");
           }

            return new ResponseEntity<>(responseHandler, HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            responseHandler.setIsSuccess(false);
            responseHandler.setMessage(e.getMessage());
        }
        return new ResponseEntity<>(responseHandler, HttpStatus.OK);
    }
    @GetMapping("admin/get/allUsers")
    public ResponseEntity<?> getAllUsers() {

        ResponseHandler responseHandler = new ResponseHandler();
        try {
            List<UserDTO> response = userService.getAllUsers();
            responseHandler.setData(response);
            responseHandler.setMessage("Successfully Retrieved");
            return new ResponseEntity<>(responseHandler, HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            responseHandler.setMessage(e.getMessage());
        }
        return new ResponseEntity<>(responseHandler, HttpStatus.OK);
    }

}
