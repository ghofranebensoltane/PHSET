package com.pidev.phset.controllers;

import com.pidev.phset.entities.Comment;
import com.pidev.phset.entities.Evaluation;
import com.pidev.phset.entities.Post;
import com.pidev.phset.entities.React;
import com.pidev.phset.repositories.IPublicityRepository;
import com.pidev.phset.services.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Forum")
public class ForumController {
   private IPostServices postServices;
  private ICommentServices CommentServices;
   private IReactService reactService;
    IPublicityService publicityService;
    IPublicityRepository publicityRepository;

    //   //Crud  simple Post
  @DeleteMapping("/delete/{PostId}")
  public void deletePost(@PathVariable("PostId") Integer PostId) {
     postServices.deletePost(PostId);
 }
//
  @DeleteMapping("/deletePostByUserId/{idAccount}")
   public void deletePostByAccountId(@PathVariable("idAccount") Integer idAccount) {
     postServices.deletePostByAccountId(idAccount);
  }
   @GetMapping("/getallPost")
   @ResponseBody
   public List<Post> retrieveAllPosts() {
      return postServices.retrieveAllPosts();
   }

   @GetMapping("/getPostByUser/{PostId}")
   @ResponseBody
   public Post retrievePostById(@PathVariable("PostId") Integer PostId) {
      return postServices.retrievePostById(PostId);
   }

  @PostMapping("/add/{idAccount}")
  public String simpleAdd(@RequestBody Post post, @PathVariable("idAccount") Integer idAccount) {
    return postServices.simpleAdd(post, idAccount);

  }
    @PutMapping("/SignalerPost/{idPost}")
   public void addSignal(@PathVariable int idPost) {
        postServices.reportPost(idPost);
    }
    @PutMapping("/donnerEtoile/{IdPost}/{nb_etoil}")
   public Post DonnerEtoilPost(@PathVariable("IdPost") int IdPost, @PathVariable("nb_etoil") int nb_etoil) {
      return postServices.DonnerEtoilPost(IdPost, nb_etoil);
   }
   //------------ La meilleur Post ----------------
       @GetMapping("/MeilleurPost")
       public Post MeilleurPost() {
       return postServices.MeilleurPost();
   }
   // ------------------ Dans cette fonction on va effacer post sans interaction pendant une semaine---------------------
    @PutMapping("/deletepost")
  public void deletePostByTime() {
      postServices.deletePostByTime();
   }


// ---------------------------  React -----------------------------
   @PostMapping("/addReact/{idPost}/{idAccount}")
   public React addReact(@RequestBody React react, @PathVariable("idPost") int idPost, @PathVariable("idAccount") int idAccount) {
      return reactService.addReact(react, idPost, idAccount);
   }
    @PutMapping("/updateReact/{IdAccount}/{IdPost}")
   public React updateReact(React react, int IdPost, int IdAccount) {
      return reactService.updateReact(react, IdPost, IdAccount);
   }
       //---------Crud Comment--------
    @PostMapping("/addComment/{idPost}/{idAccount}")
    public Comment addComment(@RequestBody Comment comment, @PathVariable int idPost, @PathVariable int idAccount) {
        return CommentServices.addComment(comment, idPost, idAccount);

    }
//------ Méehode avancé d'une fonction reflexive ----------------------
  @PostMapping("/addCommenttoComment/{idComment}/{idAccount}")
  public Comment addCommentComment(@RequestBody Comment comment, @PathVariable int idComment, @PathVariable int idAccount) {
      return CommentServices.addCommentToComment(comment, idComment, idAccount);
   }

   @PutMapping("/update/{postCommentId}")
   public Comment update(@RequestBody Comment comment, @PathVariable Integer postCommentId) {
     return CommentServices.update(comment);   }

   @DeleteMapping("/{postCommentId}")
  public void deleteComment(@PathVariable Integer postCommentId) {
      CommentServices.deleteComment(postCommentId);
   }

   @GetMapping("/get/{postCommentId}")
   public Comment getPost(@PathVariable Integer postCommentId) {
      return CommentServices.getComment(postCommentId);
   }

  @GetMapping("/getAllComments")
   public List<Comment> getAllComments() {
      return CommentServices.getAllComments();
   }



   @PutMapping("/AddReactToComment/{idcomment}/{IdAccount}")
   public React addReactToComment(React react, @PathVariable("idcomment") int idcomment, @PathVariable("IdAccount") int IdAccount) {
     return reactService.addReactToComment(react, idcomment, IdAccount);
   }
                         // ------------------------Publicity ---------
  @PutMapping("/addPublicityForAccounts")
  public void addPublicityForAccounts(@RequestParam("minAge") int minAge, @RequestParam("maxAge") int maxAge,
                                      @RequestParam("name") String name, @RequestParam("text") String text, @RequestParam("startDate") Date startDate,
                                      @RequestParam("endDate") Date endDate, @RequestParam("nbrInitialDesvues") int nbrInitialDesvues,
                                      @RequestParam("nbrFinalDesvues") int nbrFinalDesvues, @RequestParam("price") float price) {
      publicityService.addPublicityForAccounts(minAge, maxAge, name, text, startDate, endDate, nbrInitialDesvues,
              nbrFinalDesvues, price);
  }
    @GetMapping("/getCostPublicity/{idAccount}")
    public float getTotalCostByAccount(@PathVariable Integer idAccount) {
        return publicityService.getTotalCostByAccount(idAccount);
    }
    @PostMapping("/addImage/{publicityId}")
    public void addImageToPublicity(@PathVariable Integer publicityId,@PathParam("image") MultipartFile image) throws IOException {
        publicityService.addImageToPublicity(publicityId, image);
    }

    @GetMapping("/daysRemaining/{idPublicite}")
    public long getDaysRemaining(@PathVariable Integer idPublicite) {
        return publicityService.getDaysRemaining(idPublicite);
    }



}


