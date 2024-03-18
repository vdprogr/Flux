package org.example;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class FluxAndMonoGeneratorService
{
    public static void main(String[] args) {
//       InputStream inputStream = null;
//        System.out.println("here");

        FluxAndMonoGeneratorService mainFluxAndMono = new FluxAndMonoGeneratorService();
        mainFluxAndMono.namesFlux(3)
                .subscribe(num -> {
                    System.out.println("name is " + num);
                });

        mainFluxAndMono.namesFlux_flatmap(3)
                .subscribe(num -> {
                    System.out.println("name is " + num);
                });

        mainFluxAndMono.namesFlux_flatmap_async(3)
                .subscribe(num -> {
                    System.out.println("name is " + num);
                });

        mainFluxAndMono.namesMono()
                .subscribe(num -> {
                    System.out.println("mono name is " + num);
                });
    }
    public Flux<String> namesFlux(int len) {
        Flux<String> stringFlux = Flux.fromIterable(List.of("One", "Two", "Three"))
                .map(String::toUpperCase)
                .filter(s->s.length() == len)
                .log();
        return stringFlux;
    }

    public Flux<String> namesFlux_flatmap(int len) {
        Flux<String> stringFlux = Flux.fromIterable(List.of("One", "Two", "Three"))
                .map(String::toUpperCase)
                .filter(s->s.length() == len)
                .flatMap(s-> Flux.fromArray(s.split("")))
                .log();
        return stringFlux;
    }

    public Flux<String> namesFlux_flatmap_async(int len) {
        Flux<String> stringFlux = Flux.fromIterable(List.of("One", "Two", "Three"))
                .map(String::toUpperCase)
                .filter(s->s.length() == len)
                .flatMap(s-> splitString_with_delay(s))
                .log();
        return stringFlux;
    }

    public Flux<String> namesFlux_concatmap(int len) {
        Flux<String> stringFlux = Flux.fromIterable(List.of("One", "Two", "Three"))
                .map(String::toUpperCase)
                .filter(s->s.length() == len)
                .concatMap(s-> splitString_with_delay(s))
                .log();
        return stringFlux;
    }

    public Mono<String> namesMono() {
        return Mono.just("mono").log();
    }

    public Flux<String> splitString_with_delay(String s) {
        var charArray = s.split("");
        return Flux.fromArray(charArray).delayElements(Duration.ofMillis(new Random().nextInt(1000)));
    }
}