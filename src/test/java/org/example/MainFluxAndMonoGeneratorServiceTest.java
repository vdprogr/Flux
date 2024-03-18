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

}
