package com.mukesh.moviecatalogservice.services;

import com.mukesh.moviecatalogservice.models.Rating;
import com.mukesh.moviecatalogservice.models.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class UserRatingInfo {

    @Autowired
    private RestTemplate restTemplate;

    /*@HystrixCommand(
            fallbackMethod = "getFallbackMethods",
            threadPoolKey = "movieInfoPool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "20"),
                    @HystrixProperty(name = "maxQueueSize", value = "10")
            }
    )*/

    @HystrixCommand(fallbackMethod = "getFallbackUserRating",
        commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value = "5"),
                @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value = "50"),
                @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value = "5000")
        }
    )
    public UserRating getUserRating(String userId) {
        UserRating userRating= restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/" + userId, UserRating.class);
        return userRating;
    }

    public UserRating getFallbackUserRating(String userId) {
        UserRating userRating = new UserRating();
        userRating.setUserId(userId);
        userRating.setUserRating(Arrays.asList(
                new Rating("0",0)
        ));
        return userRating;
    }
}
