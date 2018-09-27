package com.example.dealership;

import com.example.dealership.query.datamodel.CarOfferQuickDescriptionDTO;
import com.example.dealership.query.repo.CarOffersRepo;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest({"spring.datasource.driver-class-name=org.h2.Driver",
                "spring.datasource.url=jdbc:h2:mem:db;DB_CLOSE_DELAY=-1",
                "spring.datasource.username=sa",
                "spring.datasource.password=sa",
                "spring.activemq.broker-url=vm://embedded?broker.persistent=false,useShutdownHook=false",
                "spring.activemq.in-memory=true"
})
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class QueryRepoITest {

    public static final CarOfferQuickDescriptionDTO audi = new CarOfferQuickDescriptionDTO("1", "audi");

    @Autowired
    CarOffersRepo carsForSaleRepo;

    @Test
    public void canaryTest() {
        assertThat(carsForSaleRepo).isNotNull();
    }

    @Test
    public void fetchCarsForSale_listInFutureNotNull() {
        final Flux<CarOfferQuickDescriptionDTO> carForSaleDTOFlux = carsForSaleRepo.findAll();

        StepVerifier
                .create(carForSaleDTOFlux)
                .expectSubscription()
                .assertNext((car) -> Assertions.assertThat(car.id).isEqualTo("1"))
                .verifyComplete();
    }

    @Before
    public void putDataInCache() {
        carsForSaleRepo.save(audi).subscribe();
    }

    @SpringBootApplication
    public static class DummyStarter {
    }
}
