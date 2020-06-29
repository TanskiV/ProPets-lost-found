package lostfound.service;

import lostfound.dto.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;
@Service
public interface LostFoundService {
    ResponseEntity<String> addLostPet(LostFindDto lostFindDto,String token);

    ResponseEntity<String> addFindPet(LostFindDto lostFindDto, String token);

    ResponseEntity<PostsOfLostFindPets> getPostsOfLostPets(PageRequest pageRequest, String token);

    ResponseEntity<PostsOfLostFindPets> getPostsOfFindPets(PageRequest pageRequest, String token);

    ResponseEntity<PostByIdDto> getPostByIdFindLostPets(int id, String token);

    ResponseEntity<FindLostPetsResponse> searchFindPets(PageRequest pageRequest,
                                                          FindPetDto findPetDto, String token);

    ResponseEntity<FindLostPetsResponse> searchLostPets(PageRequest pageRequest,
                                                          FindPetDto findPetDto, String token);

    ResponseEntity<String> changeLostFindPet(int id, ChangePetDto data, String token);

    ResponseEntity<ChangesOfFindLostPetDel> delPets(int id, String token);

    ResponseEntity<Set<String>> getTagsAndColors(String urlPicture, String token) throws IOException;

}
