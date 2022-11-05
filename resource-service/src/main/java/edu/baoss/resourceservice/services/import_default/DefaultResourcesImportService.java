package edu.baoss.resourceservice.services.import_default;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.baoss.resourceservice.model.ConnectedAddress;
import edu.baoss.resourceservice.repositories.ConnectedBuildingsRepository;
import edu.baoss.resourceservice.services.ConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@RequiredArgsConstructor
public abstract class DefaultResourcesImportService<T> implements ApplicationRunner {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${baoss.resources.import-default}")
    protected boolean importDefault;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (importDefault && resourceIsEmpty()) {
            System.out.println("Generation for " + getConcreteClass().getName() + "is started");
            List<T> defaultResources;
            try {
                InputStream inputStream = new ClassPathResource(getFileName()).getInputStream();
                defaultResources = objectMapper.readValue(inputStream, new TypeReference<>() {});
            } catch (IOException e) {
                throw new RuntimeException("Wrong JSON for import", e);
            }
            MongoRepository<T, String> repository = getRepository();
            defaultResources.stream()
                    .map(resource -> getConcreteClass().isInstance(resource)
                            ? getConcreteClass().cast(resource)
                            : objectMapper.convertValue(resource, getConcreteClass()))
                    .forEach(repository::save);
        }
    }

    protected abstract String getFileName();

    protected abstract MongoRepository<T, String> getRepository();

    protected abstract Class<T> getConcreteClass();

    protected abstract boolean resourceIsEmpty();
}
