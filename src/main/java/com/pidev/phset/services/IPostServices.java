package com.pidev.phset.services;

import com.pidev.phset.entities.Claim;
import com.pidev.phset.entities.Event;
import com.pidev.phset.entities.Post;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface IPostServices {
    public List<Post> retrieveAllPosts();
    public void deletePost(Integer PostId);
    public void deletePostByAccountId(Integer PostId);
    public Post retrievePostById(Integer PostId);
    public List<Post> getPostByUser(Integer PostId);
    public String simpleAdd(Post post,Integer idAccount);
    public void reportPost(int IdPost);
   // public void MeilleurPost(int IdPost);
    public Post DonnerEtoilPost(int IdPost, int nb_etoil);
    public Post MeilleurPost();

    public void deletePostByTime();

    }


