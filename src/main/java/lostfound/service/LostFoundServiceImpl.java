package lostfound.service;

import lostfound.dao.LostFoundRepository;
import lostfound.dto.*;
import lostfound.model.LostFoundPet;
import lostfound.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LostFoundServiceImpl implements LostFoundService {
    @Autowired
    LostFoundRepository lostFoundRepository;

    static long id = -1;

    @Override
    public ResponseEntity<String> addLostPet(LostFindDto lostFindDto, String token) {
        LostFoundPet lostFoundPet = lostFindDtoToModel(lostFindDto, false, token);
        lostFoundRepository.save(lostFoundPet);
        return ResponseEntity.ok("Your post has been successfully added");
    }


    @Override
    public ResponseEntity<String> addFindPet(LostFindDto lostFindDto, String token) {
        LostFoundPet lostFoundPet = lostFindDtoToModel(lostFindDto, true, token);
        lostFoundRepository.save(lostFoundPet);
        return ResponseEntity.ok("Your post has been successfully added");
    }

    @Override
    public ResponseEntity<PostsOfLostFindPets> getPostsOfLostPets(PageRequest pageRequest, String token) {
        PostsOfLostFindPets postsLostPets = getPostsLostFoundPets(pageRequest, false);
        return ResponseEntity.ok(postsLostPets);
    }


    @Override
    public ResponseEntity<PostsOfLostFindPets> getPostsOfFindPets(PageRequest pageRequest, String token) {
        PostsOfLostFindPets postsFindPets = getPostsLostFoundPets(pageRequest, true);
        return ResponseEntity.ok(postsFindPets);
    }

    @Override
    public ResponseEntity<PostByIdDto> getPostByIdFindLostPets(int id, String token) {
        LostFoundPet lostFoundPet = lostFoundRepository.findById(id).orElse(null);
        if (lostFoundPet == null) {
            return ResponseEntity.ok(new PostByIdDto());
        }
        LostFoundPet pet = lostFoundRepository.findById(id).get();
        PostByIdDto postByIdDto = PostByIdDto.builder()
                .datePost(pet.getDatePost())
                .id(pet.getPostId())
                .typePost(pet.getType())
                .location(pet.getLocation())
                .photos(pet.getPhotos())
                .tags(pet.getTags())
                .status(pet.isStatus())
                .user(pet.getUser())
                .build();
        return ResponseEntity.ok(postByIdDto);
    }

    @Override
    public ResponseEntity<FindLostPetsResponse> searchFindPets(PageRequest pageRequest,
                                                               FindPetDto findPetDto, String token) {
        FindLostPetsResponse findLostPetsResponse = getFindLostPetsResponse(pageRequest, findPetDto, true);
        return ResponseEntity.ok(findLostPetsResponse);
    }


    @Override
    public ResponseEntity<FindLostPetsResponse> searchLostPets(PageRequest pageRequest,
                                                               FindPetDto findPetDto, String token) {
        FindLostPetsResponse findLostPetsResponse = getFindLostPetsResponse(pageRequest, findPetDto, false);
        return ResponseEntity.ok(findLostPetsResponse);
    }

    @Override
    public ResponseEntity<String> changeLostFindPet(int id, ChangePetDto data, String token) {
        LostFoundPet lostFoundPet = lostFoundRepository.findById(id).orElse(null);
        if (lostFoundPet == null) {
            return ResponseEntity.ok("Pets not found");
        }
        lostFoundPet.setType(data.getType());
        lostFoundPet.setLocation(data.getLocation());
        lostFoundPet.setTags(data.getTags());
        lostFoundPet.setPhotos(data.getPhotos());
        lostFoundRepository.save(lostFoundPet);
        return ResponseEntity.ok("Changes was saved");
    }

    @Override
    public ResponseEntity<ChangesOfFindLostPetDel> delPets(int id, String token) {
        LostFoundPet pet = lostFoundRepository.findById(id).orElse(null);
        if (pet == null) {
            return ResponseEntity.ok(new ChangesOfFindLostPetDel());
        }
        ChangesOfFindLostPetDel petToDel = ChangesOfFindLostPetDel.builder()
                .datePost(pet.getDatePost())
                .deleteDate(LocalDate.now())
                .id(pet.getPostId())
                .location(pet.getLocation())
                .photos(pet.getPhotos())
                .tags(pet.getTags())
                .type(pet.getType())
                .typePost(pet.isStatus())
                .user(pet.getUser())
                .build();
        lostFoundRepository.delete(pet);
        return ResponseEntity.ok(petToDel);
    }

    @Override
    public ResponseEntity<Set<String>> getTagsAndColors(String urlPicture, String token) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic YWNjX2JhNjRjOGJjNTBiNDJhMDozZGY5Y2UzNWFlYjFhZTE1MzlmMWUxNDU4ZTJjZjk1ZA==");
        String url = "https://api.imagga.com/v2/tags";
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("image_url", urlPicture)
                .queryParam("language", "en");
        RequestEntity<String> request = new RequestEntity<>(headers, HttpMethod.GET, uriBuilder.build().toUri());
        ResponseEntity<TagsResponseDto> response =
                restTemplate.exchange(request, TagsResponseDto.class);
        Set<String> tags = Objects.requireNonNull(response.getBody()).getResult().getTags().stream()
                .map(tagDto -> tagDto.getTag().values().toString())
                .collect(Collectors.toSet());
        return ResponseEntity.ok(tags);
    }

    private LostFoundPet lostFindDtoToModel(LostFindDto lostFindDto, boolean status, String token) {
        if (id == -1) {
            id = lostFoundRepository.count();
        }
        double[] position = new double[]{lostFindDto.getLocation().getLatitude(), lostFindDto.getLocation().getLongitude()};
        return LostFoundPet.builder()
                .datePost(LocalDate.now())
                .location(lostFindDto.getLocation())
                .photos(lostFindDto.getPhotos())
                .postId(id++)
                .status(status)
                .tags(lostFindDto.getTags())
                .type(lostFindDto.getType())
                .user(getUserNameByToken(token))
                .position(position)
                .build();
    }

    private PostsOfLostFindPets getPostsLostFoundPets(PageRequest pageRequest, boolean status) {
        List<LostFoundPet> lostPets = lostFoundRepository.findByStatus(status, pageRequest);
        List<Data> data = lostPets.stream()
                .map(this::modelToData)
                .collect(Collectors.toList());
        Links links = getLinks(pageRequest, status);
        return new PostsOfLostFindPets(links, data);
    }

    private Data modelToData(LostFoundPet lostFoundPet) {
        return new Data(modelToLostFindDto(lostFoundPet),
                lostFoundPet.getUser(), lostFoundPet.getDatePost());
    }

    private LostFindDto modelToLostFindDto(LostFoundPet lostFoundPet) {
        return LostFindDto.builder()
                .location(lostFoundPet.getLocation())
                .photos(lostFoundPet.getPhotos())
                .tags(lostFoundPet.getTags())
                .type(lostFoundPet.getType())
                .build();
    }


    private FindLostPetsResponse getFindLostPetsResponse(PageRequest pageRequest, FindPetDto findPetDto, boolean status) {
        Links links = getLinks(pageRequest, status);
        Point point = new Point(findPetDto.getLocation().getLongitude(), findPetDto.getLocation().getLatitude());
        Circle circle = new Circle(point, findPetDto.getRadiusSearching());
        List<LostFoundPet> lostFoundPets = lostFoundRepository.findByTypeAndStatusAndPositionWithin(findPetDto.getType(), status, circle,
                pageRequest);
        Set<Data> dataFindPet = lostFoundPets.stream()
                .map(this::modelToData)
                .collect(Collectors.toSet());
        Meta meta = getMeta(lostFoundPets, pageRequest);
        return new FindLostPetsResponse(meta, links, dataFindPet);
    }

    private Links getLinks(PageRequest pageRequest, boolean status) {
        String path = "";
        int size = lostFoundRepository.findByStatus(status).size();
        if (status) {
            path = Constants.PATH_FIND;
        } else {
            path = Constants.PATH_LOST;
        }
        String first = path + "?pageNumber=" + pageRequest.getPageNumber() + "&pageSize=" + pageRequest.getPageSize();
        String prev = "";
        if (pageRequest.getPageNumber() != 0) {
            prev = path + "?pageNumber=" + (pageRequest.getPageNumber() - 1) + "&pageSize=" + pageRequest.getPageSize();
        } else {
            prev = path + "?pageNumber=" + pageRequest.getPageNumber() + "&pageSize=" + pageRequest.getPageSize();
        }
        String next = path + "?pageNumber=" + (pageRequest.getPageNumber() + 1) + "&pageSize=" + pageRequest.getPageSize();
        String last = path + "?pageNumber=" + (size / pageRequest.getPageSize()) + "&pageSize=" + pageRequest.getPageSize();
        return new Links(first, prev, next, last);
    }

    private Meta getMeta(List<LostFoundPet> lostFoundPets, Pageable pageable) {
        int from = pageable.getPageNumber() * pageable.getPageSize();
        int perPage = pageable.getPageNumber() == 0 ? 0 : pageable.getPageNumber() - 1;
        int to = from + pageable.getPageSize() > lostFoundPets.size() ? lostFoundPets.size() : pageable.getPageSize();
        Page page = new Page(pageable.getPageNumber(), perPage, from,
                to, lostFoundPets.size(), lostFoundPets.size() / pageable.getPageSize());
        return new Meta(page);
    }


    private String getUserNameByToken(String token) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Token", token);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(Constants.PATH_CHECK_TOKEN);
        RequestEntity<String> request = new RequestEntity<>(headers, HttpMethod.PUT, uriBuilder.build().toUri());
        ResponseEntity<String> response =
                restTemplate.exchange(request, String.class);
        return response.getHeaders().getFirst("Id");
    }
}
