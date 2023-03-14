package com.pidev.phset.services;


import com.pidev.phset.entities.Account;
import com.pidev.phset.entities.Comment;
import com.pidev.phset.entities.Post;
import com.pidev.phset.entities.React;
import com.pidev.phset.repositories.IAccountRepository;
import com.pidev.phset.repositories.ICommentRepository;
import com.pidev.phset.repositories.IPostRepository;
import com.pidev.phset.repositories.IReactRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class ReactService implements IReactService{

    IPostRepository postRepository;
    IAccountRepository accountRepository;

    IReactRepository reactRepository;

    ICommentRepository commentRepository;


    @Override
    public React addReact(React react , int IdPost, int IdAccount) {
        Post p =   postRepository.findById(IdPost).orElse(null);
        Account a =   accountRepository.findById(IdAccount).orElse(null);
        react.setPost(p);
        react.setAccount(a);
        return reactRepository.save(react) ;
    }
@Override
    public React addReactToComment(React react ,int idcomment , int IdAccount) {
        Account account = accountRepository.findById(IdAccount).orElse(null);
        Comment comment = commentRepository.findById(idcomment).orElse(null);
        react.setAccount(account);

        react.setComments(comment);
        reactRepository.save(react);


        return react;

    }


    @Override
    public React updateReact(React react, int IdPost, int IdAccount) {
        Post p =postRepository.findById(IdPost).orElse(null);
        Account a = accountRepository.findById(IdAccount).orElse(null);
        react.setAccount(a);
        react.setPost(p);
        return reactRepository.save(react);
    }
}
