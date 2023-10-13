package Skin.VideoGame.repositories;

import Skin.VideoGame.documents.PlayerDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends MongoRepository<PlayerDocument,String> {
}
