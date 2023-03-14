package com.pidev.phset.services;

import com.pidev.phset.entities.Comment;
import com.pidev.phset.entities.Post;
import com.pidev.phset.entities.React;

import java.util.List;

public interface ICommentServices {


    Comment addComment(Comment comment , int IdPost, int IdUser);
    Comment addCommentToComment(Comment comment , int idComm, int IdUser);

    Comment update(Comment comment);
    public void deleteComment(Integer postCommentId);
    public Comment getComment(Integer postCommentId);
    public List<Comment> getAllComments();
    public React addReactToComment(React react , int idcomment , int IdAccount);


}
