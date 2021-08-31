package com.tuyet.charity.service.impl;

import com.tuyet.charity.pojo.Post;
import com.tuyet.charity.pojo.PostAuction;
import com.tuyet.charity.repository.PostRepository;
import com.tuyet.charity.repository.PostCustomRepository;
import com.tuyet.charity.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostCustomRepository postCustomRepository;

    @Override
    public Page<Post> getAllPosts(int currentPage) {
        //tham số đầu tiên chỉ định trang có index bắt đầu bằng mấy
        Pageable pageable = PageRequest.of(currentPage-1,30);
        return postRepository.findAllByOrderByCreatedDateDesc(pageable);
    }

    @Override
    public void createPost(Post post) {
        postRepository.save(post);
    }

    @Override
    public void createPostWithPostAuction(PostAuction post) {
        postCustomRepository.createPostWithPostAuction(post);
    }

    @Override
    public Post getCreatedPos() {
        return postRepository.findFirstByOrderByCreatedDateDesc();
    }

    @Override
    public Post getPost(Integer id) {
        return postRepository.getById(id);
    }

    @Override
    public void deletePost(Integer id) {
        postRepository.deleteById(id);
    }
}
