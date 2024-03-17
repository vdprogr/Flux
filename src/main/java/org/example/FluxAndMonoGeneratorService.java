package org.example;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class FluxAndMonoGeneratorService
{
    public static void main(String[] args) {
//       InputStream inputStream = null;
//        System.out.println("here");

        FluxAndMonoGeneratorService mainFluxAndMono = new FluxAndMonoGeneratorService();
        mainFluxAndMono.namesFlux()
                .subscribe(num -> {
                    System.out.println("name is " + num);
                });
        mainFluxAndMono.namesMono()
                .subscribe(num -> {
                    System.out.println("mono name is " + num);
                });
    }
    public Flux<String> namesFlux() {
        Flux<String> stringFlux = Flux.fromIterable(List.of("One", "Two", "Three")).log();
        return stringFlux;
    }

    public Mono<String> namesMono() {
        return Mono.just("mono").log();
    }
}