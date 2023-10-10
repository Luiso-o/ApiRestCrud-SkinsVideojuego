package Skin.VideoGame.repositories;

import Skin.VideoGame.documents.SkinDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Luis
 */
@Repository
public interface SkinRepository extends MongoRepository<SkinDocument, String> {
}
