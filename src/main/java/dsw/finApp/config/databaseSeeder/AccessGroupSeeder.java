package dsw.finApp.config.databaseSeeder;

import dsw.finApp.exception.util.ExceptionUtils;
import dsw.finApp.v1.accesGroup.domain.AccessGroupEntity;
import dsw.finApp.v1.accesGroup.repository.AccessGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AccessGroupSeeder implements CommandLineRunner {

    @Autowired
    AccessGroupRepository accessGroupRepository;

    @Override
    public void run(String... args) {
        try {
            databaseAccessGroupSeeder();
        } catch (Exception e) {
            throw ExceptionUtils.buildNotPersistedException("Error: " + e);
        }
    }

    private void databaseAccessGroupSeeder() {
        if (accessGroupRepository.count() == 0) {
            AccessGroupEntity seeder = new AccessGroupEntity();
            seeder.setName("USERS");
            accessGroupRepository.save(seeder);
        }
    }
}
