package org.example;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

public class MainFluxAndMonoGeneratorServiceTest {

    FluxAndMonoGeneratorService fluxAndMonoGeneratorService =
            new FluxAndMonoGeneratorService();
    @Test
    void namesFlux() {

        var namesFlux = fluxAndMonoGeneratorService.namesFlux();

        StepVerifier.create(namesFlux)
                //.expectNext("One","Two", "Three")
                .expectNextCount(3)
                .verifyComplete();
    }
}
