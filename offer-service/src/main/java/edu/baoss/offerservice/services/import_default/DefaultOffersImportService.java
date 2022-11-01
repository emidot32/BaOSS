package edu.baoss.offerservice.services.import_default;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.baoss.offerservice.model.Tariff;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@RequiredArgsConstructor
public abstract class DefaultOffersImportService<T> implements ApplicationRunner {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${baoss.offers.import-default}")
    private boolean importDefault;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (importDefault && offersAreEmpty()) {
            List<T> defaultOffers;
            try {
                InputStream inputStream = new ClassPathResource(getFileName()).getInputStream();
                defaultOffers = objectMapper.readValue(inputStream, new TypeReference<>() {});
            } catch (IOException e) {
                throw new RuntimeException("Wrong JSON for import", e);
            }
            MongoRepository<T, String> repository = getRepository();
            defaultOffers.stream()
                    .map(offer -> getConcreteClass().isInstance(offer)
                            ? getConcreteClass().cast(offer)
                            : objectMapper.convertValue(offer, getConcreteClass()))
                    .forEach(repository::save);
            //repository.saveAll(defaultOffers);
        }
    }

    protected abstract String getFileName();

    protected abstract MongoRepository<T, String> getRepository();

    protected abstract Class<T> getConcreteClass();

    protected abstract boolean offersAreEmpty();
}
