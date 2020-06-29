package lostfound.controller;

import lostfound.dto.*;
import lostfound.service.LostFoundServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.Set;

@Controller
@RequestMapping(value = "/propets/")
public class LostFoundController {
    @Autowired
    LostFoundServiceImpl lostFoundService;

    @PostMapping("{en}/search/v1/lost")
    ResponseEntity<String> addLostPet(@RequestBody LostFindDto lostFindDto, @RequestHeader("X-Token") String token) {
        return lostFoundService.addLostPet(lostFindDto, token);
    }

    @PostMapping("{en}/search/v1/find")
    ResponseEntity<String> addFindPet(@RequestBody LostFindDto lostFindDto, @RequestHeader("X-Token") String token) {
        return lostFoundService.addFindPet(lostFindDto, token);
    }

    @GetMapping("{en}/search/v1/losts")
    ResponseEntity<PostsOfLostFindPets> getPostsLost(@PathParam("pageNumber") int pageNumber,
                                                     @PathParam("pageSize") int pageSize, @RequestHeader("X-Token") String token) {
        return lostFoundService.getPostsOfLostPets(PageRequest.of(pageNumber, pageSize), token);
    }

    @GetMapping("{en}/search/v1/finds")
    ResponseEntity<PostsOfLostFindPets> getPostsFind(@PathParam("pageNumber") int pageNumber,
                                                     @PathParam("pageSize") int pageSize,  @RequestHeader("X-Token") String token) {
        return lostFoundService.getPostsOfFindPets(PageRequest.of(pageNumber, pageSize), token);
    }

    @GetMapping("{en}/search/v1/{id}")
    ResponseEntity<PostByIdDto> getById(@PathVariable("id") int id,  @RequestHeader("X-Token") String token) {
        return lostFoundService.getPostByIdFindLostPets(id, token);
    }

    @PostMapping("{en}/search/v1/find/filter")
    ResponseEntity<FindLostPetsResponse> searchFindPetsPageable(@RequestBody FindPetDto findPetDto,
                                                                @PathParam("pageNumber") int pageNumber,
                                                                @PathParam("pageSize") int pageSize,
                                                                @RequestHeader("X-Token") String token) {
        return lostFoundService.searchFindPets(PageRequest.of(pageNumber, pageSize), findPetDto, token);
    }

    @PostMapping("{en}/search/v1/lost/filter")
    ResponseEntity<FindLostPetsResponse> searchLostPetsPageable(@RequestBody FindPetDto findPetDto,
                                                                @PathParam("pageNumber") int pageNumber,
                                                                @PathParam("pageSize") int pageSize,
                                                                @RequestHeader("X-Token") String token) {
        return lostFoundService.searchLostPets(PageRequest.of(pageNumber, pageSize), findPetDto, token);
    }

    @PutMapping("{en}/search/v1/{id}")
    ResponseEntity<String> changePetById(@PathVariable("id") int id,
                                         @RequestBody ChangePetDto changePetDto,  @RequestHeader("X-Token") String token) {
        return lostFoundService.changeLostFindPet(id, changePetDto, token);
    }

    @DeleteMapping("{en}/search/v1/{id}")
    ResponseEntity<ChangesOfFindLostPetDel> delPetsById(@PathVariable("id") int id,  @RequestHeader("X-Token") String token) {
        return lostFoundService.delPets(id, token);
    }

    @GetMapping("{en}/search/v1/tags&colors/")
    ResponseEntity<Set<String>> getTagsOfPicture(@PathParam("image_url") String image_url,  @RequestHeader("X-Token") String token) throws IOException {
        return lostFoundService.getTagsAndColors(image_url, token);
    }


}
