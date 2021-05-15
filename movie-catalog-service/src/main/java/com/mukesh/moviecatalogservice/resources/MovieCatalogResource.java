package com.mukesh.moviecatalogservice.resources;

import com.mukesh.moviecatalogservice.models.CatalogItem;
import com.mukesh.moviecatalogservice.models.Movie;
import com.mukesh.moviecatalogservice.models.Rating;
import com.mukesh.moviecatalogservice.models.UserRating;
import com.mukesh.moviecatalogservice.services.MovieInfo;
import com.mukesh.moviecatalogservice.services.UserRatingInfo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {



    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    MovieInfo movieInfo;

    @Autowired
    UserRatingInfo userRatingInfo;

    //@HystrixCommand(fallbackMethod = "getFallbackCatalog")

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
        //RestTemplate restTemplate = restTemplateComponent.getRestTemplate();
        /*List<Rating> ratings = Arrays.asList(
                new Rating("1234", 4),
                new Rating("4567", 3)
        );*/

         /*Movie movie = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8082/movies/" + rating.getMovieId())
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block();*/

        UserRating userRating = userRatingInfo.getUserRating(userId);

        return userRating.getUserRating().stream().map(rating -> movieInfo.getCatalogItem(rating)).collect(Collectors.toList());

        /*return Collections.singletonList(
            new CatalogItem("Transformers", "Test", 4)
        );*/
    }





    private List<CatalogItem> getFallbackCatalog(@PathVariable("userId") String userId){
        return Arrays.asList(new CatalogItem("No movie", "", 0));
    }
}
