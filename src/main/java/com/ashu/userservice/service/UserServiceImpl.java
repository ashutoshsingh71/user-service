package com.ashu.userservice.service;

import com.ashu.userservice.entity.Hotel;
import com.ashu.userservice.entity.Rating;
import com.ashu.userservice.entity.User;
import com.ashu.userservice.exceptions.ResourceNotFoundException;
import com.ashu.userservice.external.services.HotelService;
import com.ashu.userservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HotelService hotelService;
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User addUser(User user) {
        String randomId = UUID.randomUUID().toString();
        user.setUserId(randomId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        List<User> users =  userRepository.findAll();
        return users.stream().map(user -> {
            String userId = user.getUserId();
            return getUser(userId);
        }).collect(Collectors.toList());
    }

    @Override
    public User getUser(String userId) {
        User user =  userRepository.findById(userId).orElseThrow(() ->new ResourceNotFoundException("This user not found!! : "+userId));
        String ratingUrl = "http://RATING-SERVICE/ratings/users/";
        Rating[]  ratings = restTemplate.getForObject(ratingUrl+ userId,Rating[].class);
        logger.info("{}"+ratings);
        List<Rating> ratingList = Arrays.asList(ratings);
        ratingList.stream().map(rating -> {
            String hotelId = rating.getHotelId();
            //String hotelUrl = "http://HOTEL-SERVICE/hotels/" + hotelId;
            //ResponseEntity<Hotel> responseEntity = restTemplate.getForEntity(hotelUrl, Hotel.class);
            //Hotel hotel = responseEntity.getBody();
            Hotel hotel = hotelService.getHotel(hotelId);
            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());
        user.setRatingList(ratingList);
        return user;
    }

    @Override
    public User deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("Resource not found!! :" +userId));
        userRepository.deleteById(userId);
        return user;
    }

    @Override
    public User updateUser(User user) {
        String uId = user.getUserId();
        User targetUser = userRepository.findById(uId).orElse(null);
        if(targetUser == null){
            targetUser = addUser(user);
        }else{
            targetUser.setName(user.getName());
            targetUser.setEmail(user.getEmail());
            targetUser.setAbout(user.getAbout());
            userRepository.save(targetUser);
        }
        return targetUser;
    }
}
