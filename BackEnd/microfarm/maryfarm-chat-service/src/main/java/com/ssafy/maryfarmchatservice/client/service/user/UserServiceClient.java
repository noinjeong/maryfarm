package com.ssafy.maryfarmchatservice.client.service.user;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="maryfarm-user-service")
public interface UserServiceClient {

}
