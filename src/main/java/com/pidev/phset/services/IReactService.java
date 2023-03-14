package com.pidev.phset.services;

import com.pidev.phset.entities.React;

public interface IReactService {
    public React addReact(React react , int IdPost, int IdAccount) ;
    public React updateReact(React react , int IdPost, int IdAccount);

    public React addReactToComment(React react ,int idcomment , int IdAccount) ;
    }
