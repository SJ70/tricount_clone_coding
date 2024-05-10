package com.example.tricount.jwt;

public record JwtToken(String grantType, String accessToken, String refreshToken) {

}
