package com.pidev.phset.services;

import com.pidev.phset.entities.Post;
import com.pidev.phset.repositories.IAccountRepository;
import com.pidev.phset.repositories.IPostRepository;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PostServiceImpl implements IPostServices {
     IPostRepository postRepository;
     IAccountRepository iAccountRepository;
     JavaMailSender javaMailSender;

    @Override
    public List<Post> retrieveAllPosts() {
        return (List<Post>) postRepository.findAll();
    }

    @Override
    public void deletePost(Integer PostId) {
        Post post = postRepository.findById(PostId).get();
        postRepository.delete(post);
    }

    @Override
    public void deletePostByAccountId(Integer PostId) {
     /*  List<Post> posts = postRepository.findAllById(iAccountRepository.findById(PostId).get());
      postRepository.deleteAll(posts);*/
    }




    @Override
    public Post retrievePostById(Integer PostId) {
        return postRepository.findById(PostId).get();
    }

    @Override
    public List<Post> getPostByUser(Integer PostId) {
        return (List<Post>) iAccountRepository.findById(PostId).get().getPosts();
    }

    @Override
    public String simpleAdd(Post post, Integer idAccount) {
        BadWordImpl b = new BadWordImpl();
        if(b.filterText(post.getBody()).equals("This post contain bad word") || b.filterText(post.getPostTitle()).equals("This post contain bad word"))
            return "This post contain bad word" ;
        else {
            post.setAccount(iAccountRepository.findById(idAccount).get());
            postRepository.save(post);
        return "add successfuly" ;
        }
    }



public void reportPost(int IdPost){
        Post p = postRepository.findById(IdPost).get();
        p.setNb_Signal(p.getNb_Signal()+1);
        if(p.getNb_Signal() == 5) {
            postRepository.deleteById(IdPost);
            this.sendEmail(p.getAccount().getEmailAccount(), "your post is deleted");

        }
        else {
            postRepository.save(p);
            this.sendEmail(p.getAccount().getEmailAccount(), "post recive a new report");

        }
}

    @Override
    public Post DonnerEtoilPost(int IdPost, int nb_etoil) {
        Post post1 = postRepository.findById(IdPost).orElseThrow(() -> new EntityNotFoundException("post not found")); //  Si la publication n'existe pas, la méthode lève une exception EntityNotFoundException.
        post1.setNb_etoil(nb_etoil);
        return postRepository.save(post1);
    }



    public Post MeilleurPost() {
        List<Post> posts = new ArrayList<>();   //une liste vide posts est créée pour stocker les posts récupérés de la base de données.
        postRepository.findAll().forEach(posts::add);  // récupérer tous les posts de la base de données et les ajouter à la liste posts.

        int etoile = 0;   // on va  mettre le nombre etoile à 0
        Post post = null;
        for(Post p : posts){    // une boucle for utilisé pour parcouri la liste
            if(p.getNb_etoil()>etoile){  // comparer le nombre etoile dans la poste avec le nombre d'etoile
                etoile = p.getNb_etoil();
                post=p;
            }
        }
        return post; // retourne le sujet le plus adéquant
    }





    public void sendEmail(String Recipient,String EmailMessage) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(Recipient);
        message.setSubject("Post");
        message.setText(EmailMessage);

        javaMailSender.send(message);

    }

    // @Scheduled(fixedRate = 86400000 ) //la méthode sera exécutée toutes les 24 heures, car fixedRate est défini à 86400000 millisecondes,
    @Override
    public void deletePostByTime(){
        List<Post> posts = new ArrayList<>();
        postRepository.findAll().forEach(posts::add);

        for(Post p : posts){
            if(p.getCreatedAt().isBefore(LocalDateTime.now().minusWeeks(1)) && p.getReacts().size()==0 && p.getComments().size()==0){
                postRepository.delete(p);
            }
        }

    }
//Une boucle for est utilisée pour parcourir tous les posts dans la liste posts. Pour chaque post, la méthode getCreatedAt est appelée pour
// récupérer la date et l'heure à laquelle le post a été créé. La méthode isBefore est ensuite utilisée pour vérifier si cette date et
// heure est antérieure à une semaine avant la date et l'heure actuelles (obtenues en appelant LocalDateTime.now() et en soustrayant une
// semaine avec minusWeeks(1)).
// La méthode getReacts et getComments sont appelées pour vérifier si le post n'a pas de réactions ou de commentaires associés.

}




