package com.myfb.postservice.service;

import com.myfb.postservice.client.ClientDTO;
import com.myfb.postservice.config.PostConfig;
import com.myfb.postservice.dto.PostDTO;
import com.myfb.postservice.entity.PostEntity;
import com.myfb.postservice.repository.PostRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService{
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        PostEntity postEntity= new PostEntity();
        BeanUtils.copyProperties(postDTO, postEntity);
        postEntity=postRepository.save(postEntity);
        BeanUtils.copyProperties(postEntity, postDTO);
        return postDTO;
    }

    @Override
    public PostDTO getPostDetail(Long postId) {
        Optional<PostEntity> optEntity= postRepository.findById(postId);
        PostDTO postDTO= null;
        if(optEntity.isPresent()){
            postDTO= new PostDTO();
            BeanUtils.copyProperties(optEntity.get(), postDTO);
        }
        optEntity.orElseThrow(()-> new RuntimeException("No Post Found With"+postId));
        return  postDTO;
    }

    @Override
    public List<PostDTO> getAllPostByUser(Long userId) {
       List<PostEntity> posts= postRepository.findAllByUserId(userId);
       List<PostDTO> postDTOs=null;
        PostDTO postDTO = null;
       if(posts!=null && !posts.isEmpty()){
           postDTOs =new ArrayList<>();
           for(PostEntity pe : posts) {
               postDTO = new PostDTO();
               BeanUtils.copyProperties(pe, postDTO);
               postDTOs.add(postDTO);
           }
       }
       return postDTOs;
    }

    @Override
    public ClientDTO[] getAllCommentsForPostId(@PathVariable Long postId){
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ClientDTO[] comments= restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts/{postId}/comments", ClientDTO[].class, postId);
        System.out.println(comments.length);
        return comments;
    }

}
