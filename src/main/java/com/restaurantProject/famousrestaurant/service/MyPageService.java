package com.restaurantProject.famousrestaurant.service;

import com.restaurantProject.famousrestaurant.dto.Restaurant;
import com.restaurantProject.famousrestaurant.entity.MemberEntity;
import com.restaurantProject.famousrestaurant.entity.ReviewEntity;
import com.restaurantProject.famousrestaurant.entity.WishListEntity;
import com.restaurantProject.famousrestaurant.repository.MemberRepository;
import com.restaurantProject.famousrestaurant.repository.ReviewRepository;
import com.restaurantProject.famousrestaurant.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MemberRepository memberRepository;
    private final WishListRepository wishListRepository;
    private final ReviewRepository reviewRepository;
    private final RestaurantService restaurantService;

    private String upload(MultipartFile file, String realPath) {
        if (!file.isEmpty()) {
            String fileRealName = file.getOriginalFilename(); // 파일명을 얻어낼 수 있는 메서드
            String fileExtension = Objects.requireNonNull(fileRealName).substring(fileRealName.lastIndexOf("."));
            UUID uuid = UUID.randomUUID();
            String[] uuids = uuid.toString().split("-");
            String uniqueName = uuids[0];
            String filePath = realPath + "/" + uniqueName + fileExtension;

            File saveFile = new File(filePath); // 적용 후

            try {
                file.transferTo(saveFile); // 실제 파일 저장메서드(filewriter 작업을 손쉽게 한방에 처리해준다.)
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return uniqueName + fileExtension;

        } else {
            return null;
        }
    }

    private File realPath() throws IOException{
        ClassPathResource classPathResourceProfile = new ClassPathResource("static/profile/");
        return classPathResourceProfile.getFile();
    }

    public void delete(String realPath, String fileName) {
        String filePath = realPath + fileName;
        File file = new File(filePath);
        file.delete();
    }

    public boolean profileUpload(MultipartFile file, String memberId) throws IOException {
        Optional<MemberEntity> memOptional = memberRepository.findByMemberId(memberId);
        if (memOptional.isPresent()) {
            MemberEntity mem = memOptional.get();
            if (mem.getMemberProfile().equals("default.jpeg")) {
                String profile = upload(file, realPath()+"/");
                mem.setMemberProfile(profile);
            } else {
                delete(realPath()+"/", mem.getMemberProfile());
                String profile = upload(file, realPath()+"/");
                mem.setMemberProfile(profile);
            }
            memberRepository.save(mem);
            return true;
        }else {
            return false;
        }
    }

    public boolean reviewDelete(Long id) {
        Optional<ReviewEntity> r = reviewRepository.findById(id);
        if (r.isPresent()) { // Optional에서 엔티티 추출
            ReviewEntity reviewEntity = r.get();
            reviewRepository.delete(reviewEntity); // 엔티티를 사용하여 삭제
            return true;
        } else return false;
    }

    public boolean wishListDelete(Long id) {
        Optional<WishListEntity> w = wishListRepository.findById(id);
        if (w.isPresent()) { // Optional에서 엔티티 추출
            WishListEntity wishListEntity = w.get();
            wishListRepository.delete(wishListEntity); // 엔티티를 사용하여 삭제
            return true;
        }return false;
    }

    public List<ReviewEntity> getMyRivew(String memberId) { return reviewRepository.findByMemberId(memberId);}

    public List<WishListEntity> getMyWish(String memberId){ return wishListRepository.findByMemberWishId(memberId); }

    public List<String> getMyWishImage(List<WishListEntity> wishList){
        List<String> image = new ArrayList<>();
        for (WishListEntity w : wishList) {
            Restaurant restaurant = restaurantService.findById(w.getRestaurantId());
            String query = restaurant.getRestaurantName();
            image.add(restaurantService.imgSearch(query));
        }
        return image;
    }

    public Optional<MemberEntity> findByMemberId(String id){
       return memberRepository.findByMemberId(id);
    }
}
