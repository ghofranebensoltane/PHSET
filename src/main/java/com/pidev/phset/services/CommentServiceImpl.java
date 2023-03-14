package com.pidev.phset.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pidev.phset.entities.Account;
import com.pidev.phset.entities.Comment;
import com.pidev.phset.entities.Post;
import com.pidev.phset.entities.React;
import com.pidev.phset.repositories.IAccountRepository;
import com.pidev.phset.repositories.ICommentRepository;
import com.pidev.phset.repositories.IPostRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements ICommentServices{
    private ICommentRepository CommentRepository;
    private IPostRepository postRepository;
    private IAccountRepository  accountRepository;


    @Override
    public Comment addComment(Comment comment ,int IdPost, int IdAccount) {
      Post p =   postRepository.findById(IdPost).get();
      Account a =   accountRepository.findById(IdAccount).get();
      comment.setPost(p);
      comment.setAccount(a);
        return CommentRepository.save(comment) ;
    }

    @Override
    public Comment addCommentToComment(Comment comment, int idComm, int IdAccount) {
        Comment p =   CommentRepository.findById(idComm).get();
        Account a =   accountRepository.findById(IdAccount).get();
        comment.setPostCo(p);
        comment.setAccount(a);
        return CommentRepository.save(comment) ;    }

    @Override
    public Comment update(Comment comment) {
        return CommentRepository.save(comment);
    }

    @Override
    public void deleteComment(Integer postCommentId) {
        CommentRepository.deleteById(postCommentId);

    }

    @Override
    public Comment getComment(Integer postCommentId) {
        return CommentRepository.findById(postCommentId).get();
    }

    @Override
    public List<Comment> getAllComments() {
        return (List<Comment>) CommentRepository.findAll();
    }

    @Override
    public React addReactToComment(React react, int idcomment, int IdAccount) {
        return null;
    }
}
