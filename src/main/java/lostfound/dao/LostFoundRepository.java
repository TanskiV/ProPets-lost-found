package lostfound.dao;

import lostfound.model.LostFoundPet;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Circle;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LostFoundRepository extends MongoRepository<LostFoundPet, Integer> {
    List<LostFoundPet> findByStatus(boolean status, Pageable pageable);
    List<LostFoundPet> findByStatus(boolean status);
    List<LostFoundPet> findByTypeAndStatusAndPositionWithin(String type,boolean status, Circle circle);
    List<LostFoundPet> findByTypeAndStatusAndPositionWithin(String type,boolean status, Circle circle, Pageable pageable);

    //Tests
    List<LostFoundPet> findByTypeAndStatus(String type, boolean status);
}
