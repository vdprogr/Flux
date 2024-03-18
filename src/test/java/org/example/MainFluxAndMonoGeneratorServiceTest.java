package org.example;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

public class MainFluxAndMonoGeneratorServiceTest {

    FluxAndMonoGeneratorService fluxAndMonoGeneratorService =
            new FluxAndMonoGeneratorService();
    @Test
    void namesFlux() {

        var namesFlux = fluxAndMonoGeneratorService.namesFlux(3);

        StepVerifier.create(namesFlux)
                //.expectNext("One","Two", "Three")
                //.expectNextCount(3)
                .expectNext("ONE")
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void namesFlux_flatmap() {
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_flatmap(3);

        StepVerifier.create(namesFlux)
                .expectNext("O")
                .expectNextCount(5)
                .verifyComplete();
    }

    @Test
    void namesFlux_flatmap_async() {
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_flatmap_async(3);

        StepVerifier.create(namesFlux)
                .expectNextCount(6)
                .verifyComplete();
    }

    @Test
    void namesFlux_concatmap() {
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_concatmap(3);

        StepVerifier.create(namesFlux)
                .expectNext("O")
                .expectNextCount(5)
                .verifyComplete();
    }

    @Test
    void explore_concat() {
        var namesFlux = fluxAndMonoGeneratorService.explore_concat();

        StepVerifier.create(namesFlux)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void explore_concatWith() {
        var namesFlux = fluxAndMonoGeneratorService.explore_concatWith();

        StepVerifier.create(namesFlux)
                .expectNext("A", "B")
                .verifyComplete();
    }

    @Test
    void explore_merge() {
        var namesFlux = fluxAndMonoGeneratorService.explore_merge();

        StepVerifier.create(namesFlux)
                .expectNext("A", "D", "B", "E", "C", "F")
                .verifyComplete();
    }

    @Test
    void explore_mergeWith() {
        var namesFlux = fluxAndMonoGeneratorService.explore_mergeWith();

        StepVerifier.create(namesFlux)
                .expectNext("A", "D", "B", "E", "C", "F")
                .verifyComplete();
    }

    @Test
    void explore_merge_sequential() {
        var namesFlux = fluxAndMonoGeneratorService.explore_sequential();

        StepVerifier.create(namesFlux)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void explore_merge_zip() {
        var namesFlux = fluxAndMonoGeneratorService.explore_zip();

        StepVerifier.create(namesFlux)
                .expectNext("AD", "BE", "CF")
                .verifyComplete();
    }

    @Test
    void explore_merge_zip4() {
        var namesFlux = fluxAndMonoGeneratorService.explore_zip4();

        StepVerifier.create(namesFlux)
                .expectNext("AD14", "BE25", "CF36")
                .verifyComplete();
    }
}
