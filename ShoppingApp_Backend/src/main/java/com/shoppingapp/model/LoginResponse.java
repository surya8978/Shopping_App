package com.shoppingapp.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginResponse {
	private String authToken;
	private String loginId;
	private String role;

}
