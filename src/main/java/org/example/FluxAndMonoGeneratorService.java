package org.example;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple4;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class FluxAndMonoGeneratorService {
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
                .filter(s -> s.length() == len)
                .log();
        return stringFlux;
    }

    public Flux<String> namesFlux_flatmap(int len) {
        Flux<String> stringFlux = Flux.fromIterable(List.of("One", "Two", "Three"))
                .map(String::toUpperCase)
                .filter(s -> s.length() == len)
                .flatMap(s -> Flux.fromArray(s.split("")))
                .log();
        return stringFlux;
    }

    public Flux<String> namesFlux_flatmap_async(int len) {
        Flux<String> stringFlux = Flux.fromIterable(List.of("One", "Two", "Three"))
                .map(String::toUpperCase)
                .filter(s -> s.length() == len)
                .flatMap(s -> splitString_with_delay(s))
                .log();
        return stringFlux;
    }

    public Flux<String> namesFlux_concatmap(int len) {
        Flux<String> stringFlux = Flux.fromIterable(List.of("One", "Two", "Three"))
                .map(String::toUpperCase)
                .filter(s -> s.length() == len)
                .concatMap(s -> splitString_with_delay(s))
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

    public Flux<String> explore_concat() {

        var abcFlux = Flux.just("A", "B", "C");
        var defFlux = Flux.just("D", "E", "F");

        return Flux.concat(abcFlux, defFlux).log();
    }

    public Flux<String> explore_concatWith() {

        var aMono = Mono.just("A");
        var bMono = Mono.just("B");

        return aMono.concatWith(bMono).log();   // create Flux A, B
    }

    public Flux<String> explore_merge() {

        var abcFlux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(100)).log(); // 100 - A, 200 - B, 300 - C
        var defFlux = Flux.just("D", "E", "F")
                .delayElements(Duration.ofMillis(125)).log(); // 125 - D, 250 - E, 375 - F

        return Flux.merge(abcFlux, defFlux).log();
    }

    public Flux<String> explore_mergeWith() {

        var abcFlux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(100)).log(); // 100 - A, 200 - B, 300 - C
        var defFlux = Flux.just("D", "E", "F")
                .delayElements(Duration.ofMillis(125)).log(); // 125 - D, 250 - E, 375 - F

        return abcFlux.mergeWith(defFlux).log();
    }

    public Flux<String> explore_mergeWith_mono() {

        var aMono = Mono.just("A");
        var bMono = Mono.just("B");

        return aMono.mergeWith(bMono).log();
    }

    public Flux<String> explore_sequential() {

        var abcFlux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(100)).log(); //
        var defFlux = Flux.just("D", "E", "F")
                .delayElements(Duration.ofMillis(125)).log(); //

        return Flux.mergeSequential(abcFlux, defFlux).log();
    }

    public Flux<String> explore_zip() {

        var abcFlux = Flux.just("A", "B", "C").log(); //
        var defFlux = Flux.just("D", "E", "F").log(); //

        return Flux.zip(abcFlux, defFlux, (first, second) -> first+second).log();
    }

    public Flux<String> explore_zip4() {

        var abcFlux = Flux.just("A", "B", "C").log(); //
        var defFlux = Flux.just("D", "E", "F").log(); //
        var _123Flux = Flux.just("1", "2", "3").log(); //
        var _456Flux = Flux.just("4", "5", "6").log(); //

        return Flux.zip(abcFlux, defFlux, _123Flux, _456Flux).map(t4 -> t4.getT1()+t4.getT2()+t4.getT3()+t4.getT4()).log();
    }
}