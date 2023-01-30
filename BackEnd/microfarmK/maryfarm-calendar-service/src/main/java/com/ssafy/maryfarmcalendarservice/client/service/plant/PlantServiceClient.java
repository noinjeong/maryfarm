package com.ssafy.maryfarmcalendarservice.client.service.plant;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="maryfarm-plant-service")
public class PlantServiceClient {
}
