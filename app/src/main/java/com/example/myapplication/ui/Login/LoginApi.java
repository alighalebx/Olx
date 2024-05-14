
package com.example.myapplication.ui.Login;


        import retrofit2.Call;
        import retrofit2.http.Body;
        import retrofit2.http.POST;

public interface  LoginApi{

    @POST("login")
    Call<LoginResponse> LoginUser(@Body LoginService loginService);
}
