package edu.baoss.resourceservice.services.import_default;

import edu.baoss.resourceservice.model.Cable;
import edu.baoss.resourceservice.repositories.CableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CablesImportService extends DefaultResourcesImportService<Cable> {

    private final CableRepository cableRepository;

    @Override
    protected String getFileName() {
        return "resources/cables.json";
    }

    @Override
    protected MongoRepository<Cable, String> getRepository() {
        return cableRepository;
    }

    @Override
    protected Class<Cable> getConcreteClass() {
        return Cable.class;
    }

    @Override
    protected boolean resourceIsEmpty() {
        return cableRepository.findAll().isEmpty();
    }
}
