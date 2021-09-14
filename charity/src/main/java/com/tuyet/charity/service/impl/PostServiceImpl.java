package com.tuyet.charity.service.impl;

import com.tuyet.charity.pojo.Post;
import com.tuyet.charity.pojo.PostAuction;
import com.tuyet.charity.repository.PostRepository;
import com.tuyet.charity.repository.LikeCustomRepository;
import com.tuyet.charity.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LikeCustomRepository postCustomRepository;

    @Override
    public int getAmountAllPosts() {
        return (int) postRepository.count();
    }

    @Override
    public Page<Post> getAllPosts(int currentPage) {
        //tham số đầu tiên chỉ định trang có index bắt đầu bằng mấy
        Pageable pageable = PageRequest.of(currentPage-1,30);
        return postRepository.findAllByOrderByCreatedDateDesc(pageable);
    }

    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

//    @Override
//    public void createPostWithPostAuction(PostAuction post) {
//        postCustomRepository.createPostWithPostAuction(post);
//    }

    @Override
    public Post getCreatedPos() {
        return postRepository.findFirstByOrderByCreatedDateDesc();
    }

    @Override
    public Post getPost(Integer id) {
        return postRepository.findById(id).get();
    }

    @Override
    public void deletePost(Integer id) {
        postRepository.deleteById(id);
    }
}
